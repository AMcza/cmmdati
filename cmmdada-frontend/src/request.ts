import axios from "axios";
import { Message } from "@arco-design/web-vue";
//全局请求实例
const instance = axios.create({
  baseURL: "http://localhost:8101",
  timeout: 60000,
  headers: { "X-Custom-Header": "foobar" },
  withCredentials: true,
});
//全局请求拦截器
// 添加请求拦截器
axios.interceptors.request.use(
  function (config) {
    // 在发送请求之前做些什么
    return config;
  },
  function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
  }
);

// 添加响应拦截器
axios.interceptors.response.use(
  function (response) {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    console.log(response);
    const { data } = response;
    //todo:全局拦截学习点
    //未登录
    if (data.code === 40100) {
      //不是获取用户信息的请求,并且用户目前不是已经在用户登录页面,则跳转到登录页面
      if (
        !response.request.responseURL.includes("user/get/login") &&
        !window.location.pathname.includes("/login")
      ) {
        Message.error("请先登录");
        window.location.href = `user/login?redirect=${window.location.href}`;
      }
    }
    return response;
  },
  function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    return Promise.reject(error);
  }
);
export default instance;
