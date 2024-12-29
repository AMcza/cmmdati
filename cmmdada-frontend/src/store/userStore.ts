import { defineStore } from "pinia";
import { ref } from "vue";
import { getLoginUserUsingGet } from "@/api/userController";
import ACCESS_ENUM from "@/access/accessEnum";

/**
 * 登录用户信息全局状态
 */
export const useLoginUserStore = defineStore("userlogin", () => {
  const loginUser = ref<API.LoginUserVO>({
    userName: "未登录",
  });
  async function fetchLoginUser() {
    const res = await getLoginUserUsingGet();
    console.log(res);
    console.log(res.data.code);
    if (res.data.code === 0 && res.data.data) {
      loginUser.value = res.data.data;
    } else {
      loginUser.value = { userRole: ACCESS_ENUM.NOT_LOGIN, userName: "未登录" };
    }
  }
  function setLoginUser(newLoginUser: API.LoginUserVO) {
    loginUser.value = newLoginUser;
  }
  return { loginUser, setLoginUser, fetchLoginUser };
});
