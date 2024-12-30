<template>
  <div id="doanswerpage">
    <a-card>
      <h1 class="title">{{ app.appName }}</h1>
      <p>{{ app.appDesc }}</p>
      <h2 style="margin-bottom: 32px"></h2>
      <h2 style="margin-bottom: 32px">
        {{ currentQuestion?.title }}
      </h2>
      <div>
        <a-radio-group
          direction="vertical"
          v-model="currentAnswer"
          :options="currentOptions"
          @change="doRadioChange"
        />
      </div>
      <div style="margin-top: 24px">
        <a-space size="large">
          <a-button
            type="primary"
            circle
            v-if="current < questionContent.length"
            :disabled="!currentAnswer"
            @click="current += 1"
          >
            下一题
          </a-button>
          <a-button
            :loading="submitting"
            type="primary"
            v-if="current === questionContent.length"
            circle
            :disabled="!currentAnswer"
            @click="doSubmit"
            >提交</a-button
          >
          <a-button v-if="current > 1" circle @click="current - 1"
            >上一题</a-button
          >
        </a-space>
      </div>
    </a-card>
  </div>
</template>

<script setup lang="ts">
import { computed, reactive, ref, watchEffect } from "vue";
import API from "@/api";
import router from "@/router";
import message from "@arco-design/web-vue/es/message";
import { listQuestionVoByPageUsingPost } from "@/api/questionController";
import { useRouter } from "vue-router";
import { getAppVoByIdUsingGet } from "@/api/appController";
import {
  addUserAnswerUsingPost,
  generateUserAnswerIdUsingGet,
} from "@/api/userAnswerController";

interface Props {
  appId: string;
}
const submitting = ref(false);
const route = useRouter();
//题目内容结构
const app = ref<API.AppVO>({});
const questionContent = ref<API.QuestionContentDto[]>([]);
//当前题目的序号
const current = ref(1);
//当前的题目
const currentQuestion = ref<API.QuestionContentDto>({});

//当前回答
//todo 代码写法
const currentOptions = computed(() => {
  return currentQuestion.value?.options
    ? currentQuestion.value.options.map((option) => {
        return {
          label: `${option.key}.${option.value}`,
          value: option.key,
        };
      })
    : [];
});
//当前答案
const currentAnswer = ref<string>();
//回答列表
const answerList = reactive<string[]>([]);
// eslint-disable-next-line no-undef
const props = withDefaults(defineProps<Props>(), {
  appId: () => {
    return "";
  },
});

const loadData = async () => {
  if (!props.appId) {
    return;
  }
  //获取app
  const res1 = await getAppVoByIdUsingGet({
    id: props.appId as any,
  });
  if (res1.data.code === 0) {
    app.value = res1.data.data as API.AppVO;
  } else {
    message.error("获取数据失败." + res1.data.message);
  }
  //获取题目
  const res = await listQuestionVoByPageUsingPost({
    id: props.appId as any,
    current: 1,
    pageSize: 1,
    sortField: "createTime",
    sortOrder: "descend",
  });
  if (res.data.code === 0 && res.data.data?.records) {
    questionContent.value = res.data.data?.records[0].questionContent;
  } else {
    message.error("获取题目失败");
  }
};
watchEffect(() => {
  loadData();
});
//获取旧数据
watchEffect(() => {
  currentQuestion.value = questionContent.value[current.value - 1];
  currentAnswer.value = answerList[current.value - 1];
});

const doRadioChange = (value: string) => {
  answerList[current.value - 1] = value;
};
const id = ref<number>();
const generateId = async () => {
  const res = await generateUserAnswerIdUsingGet();
  if (res.data.code === 0) {
    app.value = res.data.data as any;
  } else {
    message.error("获取id失败", res.data.message);
  }
};
const doSubmit = async () => {
  if (!props.appId || !answerList) {
    return;
  }
  submitting.value = true;
  const res = await addUserAnswerUsingPost({
    appId: props.appId as any,
    choices: answerList,
    id: id.value as any,
  });
  if (res.data.code === 0 && res.data.data) {
    message.success("提交成功,正在跳转结果页");
    setTimeout(() => {
      router.push(`/answer/result/${res.data.data}`);
    }, 300);
  } else {
    message.error("提交失败" + res.data.message);
  }
  submitting.value = false;
};
//进入页面的时候，生成唯一id
watchEffect(() => {
  generateId();
});
</script>

<style scoped></style>
