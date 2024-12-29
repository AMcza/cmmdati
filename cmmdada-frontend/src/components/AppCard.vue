<template>
  <a-card @click="doCardClick" class="appCard" hoverable>
    <template #actions>
      <span class="icon-hover"> <IconThumbUp /> </span>
      <span class="icon-hover"> <IconShareInternal /> </span>
    </template>
    <template #cover>
      <div
        :style="{
          height: '204px',
          overflow: 'hidden',
        }"
      >
        <img
          :style="{ width: '100%', transform: 'translateY(-20px)' }"
          :alt="app.appName"
          :src="app.appIcon"
        />
      </div>
    </template>
    <a-card-meta :title="app.appName" :description="app.appDesc">
      <template #avatar>
        <div
          :style="{ display: 'flex', alignItems: 'center', color: '#1D2129' }"
        >
          <a-avatar
            :size="24"
            :style="{ marginRight: '8px' }"
            :image-url="app.userVO?.userAvatar"
          >
            A
          </a-avatar>
          <a-typography-text>{{
            app.userVO?.userName ?? "无名"
          }}</a-typography-text>
        </div>
      </template>
    </a-card-meta>
  </a-card>
</template>

<script setup lang="ts">
import { IconThumbUp, IconShareInternal } from "@arco-design/web-vue/es/icon";
import API from "@/api";
import { withDefaults, defineProps } from "vue";
import router from "@/router";
interface Props {
  app: API.AppVO;
}
//todo 组件传参
const props = withDefaults(defineProps<Props>(), {
  app: () => {
    return {};
  },
});

const doCardClick = () => {
  router.push(`/app/detail/${props.app.id}`);
};
</script>
<style scoped>
.appCard {
  cursor: pointer;
}
.icon-hover {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 24px;
  height: 24px;
  border-radius: 50%;
  transition: all 0.1s;
}
.icon-hover:hover {
  background-color: rgb(var(--gray-2));
}
</style>
