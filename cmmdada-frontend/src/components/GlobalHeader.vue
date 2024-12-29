<template>
  <div class="globalheader">
    <a-row class="grid-demo" align="center" :wrap="false">
      <a-col flex="auto">
        <a-menu
          mode="horizontal"
          :default-selected-keys="selectKey"
          @menu-item-click="doMenuClick"
        >
          <a-menu-item
            key="0"
            :style="{ padding: 0, marginRight: '38px' }"
            disabled
          >
            <div class="titleBar">
              <img class="logo" src="../assets/研发平台.png" width="30px" />
              <div class="title">智能答题平台</div>
            </div>
          </a-menu-item>
          <a-menu-item v-for="item in visibleRoutes" :key="item.path">{{
            item.name
          }}</a-menu-item>
        </a-menu>
      </a-col>
      <a-col flex="100px" class="userinfo">
        <div v-if="loginUserStore.loginUser.id" class="userstatus">
          <a-row align="center" :wrap="false">
            <a-avatar>
              <a-image
                :src="loginUserStore.loginUser.userAvatar"
                style="border-radius: 50%"
              />
            </a-avatar>
            <span style="font-size: 12px; margin-left: 5px">{{
              loginUserStore.loginUser.userName
            }}</span>
          </a-row>
        </div>
        <div v-else>
          <a-button type="primary" size="mini" href="/user/login"
            >登录</a-button
          >
        </div>
      </a-col>
    </a-row>
  </div>
</template>
<script setup lang="ts">
import { routes } from "@/router/routes";
import { useRouter } from "vue-router";
import { computed, ref } from "vue";
import { useLoginUserStore } from "@/store/userStore";
import checkAccess from "@/access/checkAccess";
const router = useRouter();
const loginUserStore = useLoginUserStore();
//当前选择中的菜单项
const selectKey = ref(["/"]);
//路由跳转的时候,自动更新选中的菜单项
router.afterEach((to) => {
  selectKey.value = [to.path];
});
//获取当前路由对应的菜单项
const visibleRoutes = computed(() => {
  return routes.filter((item) => {
    if (item.meta?.hideInMenu) {
      return false;
    }
    if (!checkAccess(loginUserStore.loginUser, item.meta?.access as string)) {
      return false;
    }
    return true;
  });
});
//点击菜单跳转到对应页面
const doMenuClick = (key: string) => {
  router.push({ path: key });
};
</script>
<style scoped>
.globalheader {
  box-sizing: border-box;
  width: 100%;
}
.titleBar {
  display: flex;
  align-items: center;
  justify-content: center;
}
.title {
  color: black;
  margin-left: 16px;
  font-size: 18px;
}
</style>
<script setup lang="ts"></script>
