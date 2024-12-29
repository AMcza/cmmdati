<template>
  <div id="answerResultpage">
    <a-card>
      <a-row>
        <a-col flex="auto" class="content-wrapperAnswerer">
          <div>
            <h2>{{ data.resultName }}</h2>
            <p>结果描述:{{ data.resultDesc }}</p>
            <p>结果ID:{{ data.resultId }}</p>
            <p>应用ID:{{ data.appId }}</p>
            <p>应用类型:{{ APP_TYPE_MAP[data.appType] }}</p>
            <p>评分策略:{{ APP_SCORING_STRATEGY_MAP[data.scoringStrategy] }}</p>
            <p>结果得分:{{ data.resultScore }}</p>
            <p>我的答案:{{ data.choices }}</p>
            <p>
              时间:{{ dayjs(data.createTime).format("YYYY-MM-DD HH:mm:ss") }}
            </p>
            <a-space>
              <a-button type="primary" :href="`/answer/do/${data.appId}`"
                >重新答题</a-button
              >
            </a-space>
          </div>
        </a-col>
        <a-col flex="320px">
          <a-image style="width: 100%" :src="data.resultPicture"></a-image>
        </a-col>
      </a-row>
    </a-card>
  </div>
</template>
<script lang="ts" setup>
import { computed, ref, watchEffect } from "vue";
import API from "@/api";
import message from "@arco-design/web-vue/es/message";
import { dayjs } from "@arco-design/web-vue/es/_utils/date";
import {
  APP_SCORING_STRATEGY_MAP,
  APP_TYPE_ENUM,
  APP_TYPE_MAP,
} from "../../constant/app";
import { useLoginUserStore } from "@/store/userStore";
import { getUserAnswerVoByIdUsingGet } from "@/api/userAnswerController";
interface Props {
  data: string;
}
// eslint-disable-next-line no-undef
const props = withDefaults(defineProps<Props>(), {
  data: () => {
    return "";
  },
});
const data = ref<API.UserAnswer>({});

/**
 * 获取数据
 */
const loadData = async () => {
  if (!props.data) return;
  const res = await getUserAnswerVoByIdUsingGet({
    id: props.data as any,
  });
  if (res.data.code === 0) {
    data.value = res.data.data as API.UserAnswer;
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
</script>
<style scoped>
#answerResultpage {
}
#answerResultpage .content-wrapperAnswerer > * {
  margin-bottom: 24px;
}
</style>
