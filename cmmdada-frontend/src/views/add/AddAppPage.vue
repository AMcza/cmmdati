<template>
  <div id="userLoginPage">
    <h2 class="title" v-if="props.id">应用修改</h2>
    <h2 class="title" v-else>应用创建</h2>
    <a-form
      class="form"
      :model="form"
      :style="{ width: '400px', margin: '0 auto' }"
      @submit="handleSubmit"
      label-align="left"
      auto-label-width
    >
      <a-form-item
        field="appName"
        tooltip="Please enter username"
        label="应用名称"
      >
        <a-input v-model="form.appName" placeholder="请输入应用名称" />
      </a-form-item>
      <a-form-item field="appType" label="应用类型">
        <a-select v-model="form.appType" placeholder="请选择应用类型">
          <!--todo 枚举映射使用-->
          <a-option
            v-for="(value, key) of APP_TYPE_MAP"
            :key="key"
            :value="Number(key)"
            :label="value"
          ></a-option>
        </a-select>
      </a-form-item>
      <a-form-item field="scoringStrategy" label="评分策略">
        <a-select v-model="form.scoringStrategy" placeholder="请选择评分策略">
          <a-option
            v-for="(value, key) of APP_SCORING_STRATEGY_MAP"
            :key="key"
            :value="Number(key)"
            :label="value"
          ></a-option>
        </a-select>
      </a-form-item>
      <a-form-item field="appIcon" label="应用图片" tooltip="密码不小于8位">
        <a-input v-model="form.appIcon" placeholder="请输入图片地址" />
      </a-form-item>
      <a-form-item field="appDesc" label="应用描述">
        <a-input v-model="form.appDesc" placeholder="请输入应用描述" />
      </a-form-item>
      <a-form-item>
        <a-button html-type="submit" type="primary" class="login-button"
          >提交</a-button
        >
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import { ref, watchEffect } from "vue";
import API from "@/api";
import router from "@/router";
import message from "@arco-design/web-vue/es/message";
import {
  addAppUsingPost,
  editAppUsingPost,
  getAppVoByIdUsingGet,
} from "@/api/appController";
import { APP_SCORING_STRATEGY_MAP, APP_TYPE_MAP } from "@/constant/app";
import { useRouter } from "vue-router";

//获取动态路由的值
interface Props {
  id: string;
}
const form = ref({
  appDesc: "",
  appIcon: "",
  appName: "",
  appType: 0,
  scoringStrategy: 0,
} as API.AppAddRequest);
// eslint-disable-next-line no-undef
const props = withDefaults(defineProps<Props>(), {
  id: () => {
    return "";
  },
});
const routes = useRouter();

const oldApp = ref<API.AppVO>();
const loadData = async () => {
  if (!props.id) return;
  const res = await getAppVoByIdUsingGet({
    id: props.id as any,
  });
  if (res.data.code === 0 && res.data.data) {
    oldApp.value = res.data.data;
    form.value = res.data.data;
  } else {
    message.error("获取数据失败." + res.data.message);
  }
};
watchEffect(() => {
  loadData();
});

/**
 *提交登录
 */
const handleSubmit = async () => {
  console.log(form);
  let res: any;
  if (props.id) {
    res = await editAppUsingPost({
      id: props.id as any,
      ...form.value,
    });
  } else {
    res = await addAppUsingPost(form.value);
  }
  if (res.data.code === 0) {
    message.success("操作成功,即将跳转到应用详情页");
    //todo ??的用法
    setTimeout(() => {
      router.push({
        path: `/app/detail/${props.id || res.data.data}`,
      });
    }, 3000);
  } else {
    message.error("操作失败:" + res.data.message);
  }
};
</script>

<style scoped>
#userLoginPage {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 80vh;
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
.login-button {
  background-color: #1890ff;
  border-color: #1890ff;
}
</style>
