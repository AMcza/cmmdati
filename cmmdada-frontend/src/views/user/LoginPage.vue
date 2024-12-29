<template>
  <div id="userLoginPage">
    <h2 class="title">用户登录</h2>
    <a-form
      class="form"
      :model="form"
      :style="{ width: '400px', margin: '0 auto' }"
      @submit="handleSubmit"
      label-align="left"
      auto-label-width
    >
      <a-form-item
        field="userAccount"
        tooltip="Please enter username"
        label="账号"
      >
        <a-input v-model="form.userAccount" placeholder="请输入用户名..." />
      </a-form-item>
      <a-form-item field="userPassword" label="密码" tooltip="密码不小于8位">
        <a-input v-model="form.userPassword" placeholder="请输入密码..." />
      </a-form-item>
      <a-form-item>
        <a-button html-type="submit" type="primary" class="login-button"
          >登录</a-button
        >
        <a-button
          href="/user/register"
          type="primary"
          class="register-button"
          style="margin-left: 5px"
          >注册</a-button
        >
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import { reactive } from "vue";
import API from "@/api";
import { userLoginUsingPost } from "@/api/userController";
import { useLoginUserStore } from "@/store/userStore";
import router from "@/router";
import message from "@arco-design/web-vue/es/message";
const loginUserStore = useLoginUserStore();
const form = reactive({
  userAccount: "",
  userPassword: "",
} as API.UserLoginRequest);
/**
 *提交登录
 */
const handleSubmit = async () => {
  console.log(form);
  const res = await userLoginUsingPost(form);
  if (res.data.code === 0) {
    await loginUserStore.fetchLoginUser();
    message.success("登录成功");
    router.push({
      path: "/",
      replace: true,
    });
  } else {
    message.error("登录失败:" + res.data.message);
  }
};
</script>

<style scoped>
#userLoginPage {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 60vh;
  background-color: #f5f5f5;
}

.title {
  font-size: 24px;
  margin-bottom: 20px;
}

.form {
  background-color: #fff;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.login-button,
.register-button {
  width: 120px;
  margin-top: 10px;
}

.login-button {
  background-color: #1890ff;
  border-color: #1890ff;
}

.register-button {
  background-color: #40a9ff;
  border-color: #40a9ff;
}
</style>
