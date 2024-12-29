<template>
  <div id="userRegisterPage">
    <h2 class="title">用户注册</h2>
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
      <a-form-item
        field="checkPassword"
        label="确认密码"
        tooltip="密码不小于8位"
      >
        <a-input v-model="form.checkPassword" placeholder="请重新输入密码..." />
      </a-form-item>
      <a-form-item>
        <a-button
          html-type="submit"
          type="primary"
          class="register-button"
          style="width: 120px; margin-right: 5px"
          >注册</a-button
        >
        <a-button href="/user/login" type="primary" class="cancel-button"
          >取消</a-button
        >
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import { reactive } from "vue";
import API from "@/api";
import { userRegisterUsingPost } from "@/api/userController";
import { useLoginUserStore } from "@/store/userStore";
import router from "@/router";
import message from "@arco-design/web-vue/es/message";
const loginUserStore = useLoginUserStore();
const form = reactive({
  userAccount: "",
  userPassword: "",
  checkPassword: "",
} as API.UserRegisterRequest);
/**
 *提交登录
 */
const handleSubmit = async () => {
  const res = await userRegisterUsingPost(form);
  if (res.data.code === 0) {
    await loginUserStore.fetchLoginUser();
    message.success("注册成功");
    router.push({
      path: "/user/login",
      replace: true,
    });
  } else {
    message.error("注册失败:" + res.data.message);
  }
};
</script>

<style scoped>
#userRegisterPage {
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

.register-button,
.cancel-button {
  width: 120px;
  margin-top: 10px;
}

.register-button {
  background-color: #1890ff;
  border-color: #1890ff;
}

.cancel-button {
  background-color: #40a9ff;
  border-color: #40a9ff;
}
</style>
