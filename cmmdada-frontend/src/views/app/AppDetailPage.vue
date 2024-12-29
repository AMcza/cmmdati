<template>
  <div id="appdetailpage">
    <a-card>
      <a-row>
        <a-col flex="auto" class="content-wrapper">
          <div>
            <h2>{{ data.appName }}</h2>
            <p>{{ data.appDesc }}</p>
            <p>应用类型:{{ APP_TYPE_MAP[data.appType] }}</p>
            <p>评分策略:{{ APP_SCORING_STRATEGY_MAP[data.scoringStrategy] }}</p>
            <p>
              <a-space>
                作者:
                <div
                  :style="{
                    display: 'flex',
                    alignItems: 'center',
                    color: '#1D2129',
                  }"
                >
                  <a-avatar
                    :size="24"
                    :image-url="data.userVO?.userAvatar"
                    :style="{ marginRight: '8px' }"
                  ></a-avatar>
                  <a-typography-text>{{
                    data.userVO?.userName ?? "无名"
                  }}</a-typography-text>
                </div>
              </a-space>
            </p>
            <p>
              创建时间:{{
                dayjs(data.createTime).format("YYYY-MM-DD HH:mm:ss")
              }}
            </p>
            <a-space>
              <a-button type="primary" :href="`/answer/do/${id}`"
                >开始答题</a-button
              >
              <a-button>分享应用</a-button>
              <a-button :href="`/add/question/${id}`" v-if="isMy"
                >设置题目</a-button
              >
              <a-button :href="`/add/scoring_result/${id}`" v-if="isMy"
                >设置评分</a-button
              >
              <a-button :href="`/add/app/${id}`" v-if="isMy">修改应用</a-button>
            </a-space>
          </div>
        </a-col>
        <a-col flex="320px">
          <a-image style="width: 100%" :src="data.appIcon"></a-image>
        </a-col>
      </a-row>
    </a-card>
  </div>
</template>
<script lang="ts" setup>
import { computed, ref, watchEffect } from "vue";
import API from "@/api";
import { getAppVoByIdUsingGet } from "@/api/appController";
import message from "@arco-design/web-vue/es/message";
import { dayjs } from "@arco-design/web-vue/es/_utils/date";
import {
  APP_SCORING_STRATEGY_MAP,
  APP_TYPE_ENUM,
  APP_TYPE_MAP,
} from "../../constant/app";
import { useLoginUserStore } from "@/store/userStore";
interface Props {
  id: string;
}
// eslint-disable-next-line no-undef
const props = withDefaults(defineProps<Props>(), {
  id: () => {
    return "";
  },
});
const data = ref<API.AppVO>({});
/**
 * 获取数据
 */
const loadData = async () => {
  if (!props.id) return;
  const res = await getAppVoByIdUsingGet({
    id: props.id as any,
  });
  if (res.data.code === 0) {
    data.value = res.data.data as API.AppVO;
  } else {
    message.error("获取数据失败." + res.data.message);
  }
};
/**
 * 加载数据
 * 监听数据变化触发加载
 */
//todo 监听数据使用场景
watchEffect(() => {
  loadData();
});

//获取登录用户
const loginUserStore = useLoginUserStore();
let loginUserId = loginUserStore.loginUser.id;
const isMy = computed(() => {
  return loginUserId && loginUserId === data.value.userId;
});
</script>
<style scoped>
#appdetailpage {
}
#appdetailpage .content-wrapper > * {
  margin-bottom: 24px;
}
</style>
