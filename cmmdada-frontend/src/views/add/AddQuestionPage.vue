<template>
  <div id="userLoginPage">
    <h2 class="title">添加题目</h2>
    <a-form
      v-model="questionContent"
      :style="{ width: '480px' }"
      label-align="left"
      auto-label-width
      @submit="handleSubmit"
    >
      <a-form-item label="应用id">{{ appId }}</a-form-item>
      <a-form-item label="题目列表" :content-flex="false" :merge-props="false">
        <a-space>
          <a-button @click="AddQuestion(questionContent.length)" type="primary"
            >底部添加题目</a-button
          >
          <AiGenerateQuestionDrawer
            :appId="appId"
            :onSuccess="onAiGenerateSuccess"
            :onSSESuccess="onAiGenerateSuccessSSE"
            :onSSEStart="onSSEStart"
            :onSSClose="onSSEClose"
          ></AiGenerateQuestionDrawer>
        </a-space>
        <!--遍历每道题目-->
        <div
          v-for="(question, index) in questionContent"
          :key="index"
          style="border-bottom: 1px solid #ccc; padding: 10px 0"
        >
          <a-space size="large">
            <h3>题目{{ index + 1 }}</h3>
            <a-button size="mini" @click="AddQuestion(index + 1)"
              >添加题目</a-button
            >
            <a-button size="mini" @click="DeleteQuestion(index)" status="danger"
              >删除题目</a-button
            >
          </a-space>
          <a-form-item field="posts.post1" :label="`题目${index + 1}的标题`">
            <a-input v-model="question.title" placeholder="请输入题目名称" />
          </a-form-item>
          <!--题目选项-->
          <a-space>
            <h4>题目{{ index + 1 }}选项列表</h4>
            <a-button
              size="small"
              type="primary"
              @click="AddQuestionOption(question, question.options.length)"
              >底部添加选项</a-button
            >
          </a-space>
          <a-form-item
            v-for="(option, optionindex) in question.options"
            :key="optionindex"
            :label="`选项${optionindex + 1}`"
            :content-flex="false"
            :merge-props="false"
          >
            <a-form-item label="选项 key">
              <a-input
                v-model="option.key"
                placeholder="请输入选项 key"
              ></a-input>
            </a-form-item>
            <a-form-item label="选项 值">
              <a-input
                v-model="option.value"
                placeholder="请输入选项 值"
              ></a-input>
            </a-form-item>
            <a-button
              size="mini"
              @click="AddQuestionOption(question, optionindex + 1)"
              status="success"
              >添加选项</a-button
            >
            <a-button
              size="mini"
              @click="DeleteQuestionOption(question, optionindex)"
              status="danger"
              >删除选项</a-button
            >
          </a-form-item>
          <!--题目选项结尾-->
        </div>
      </a-form-item>
      <a-form-item>
        <a-space>
          <a-button type="primary" @click="handleSubmit" style="width: 200px"
            >提交</a-button
          >
          <a-button @click="router.back()" style="width: 200px" status="normal"
            >取消</a-button
          >
        </a-space>
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
  addQuestionUsingPost,
  editQuestionUsingPost,
  listQuestionVoByPageUsingPost,
} from "@/api/questionController";
import AiGenerateQuestionDrawer from "@/views/add/components/AiGenerateQuestionDrawer.vue";

interface Props {
  appId: string;
}
//todo ref与reactive的区别
const questionContent = ref<API.QuestionContentDto[]>([]);
// eslint-disable-next-line no-undef
const props = withDefaults(defineProps<Props>(), {
  appId: () => {
    return "";
  },
});
const oldQuestion = ref<API.QuestionVO>();
const loadData = async () => {
  if (!props.appId) {
    return;
  }
  const res = await listQuestionVoByPageUsingPost({
    id: props.appId as any,
    current: 1,
    pageSize: 1,
    sortField: "createTime",
    sortOrder: "descend",
  });
  if (res.data.code === 0 && res.data.data?.records) {
    oldQuestion.value = res.data.data?.records[0];
    if (oldQuestion.value) {
      questionContent.value = oldQuestion.value.questionContent ?? [];
    }
  } else {
    message.error("获取题目失败");
  }
};
watchEffect(() => {
  loadData();
});

/**
 *提交
 */
const handleSubmit = async () => {
  if (!props.appId || !questionContent.value) {
    return;
  }
  let res: any;
  if (oldQuestion.value?.id) {
    res = await editQuestionUsingPost({
      appId: props.appId as any,
      questionContent: questionContent.value,
    });
  } else {
    res = await addQuestionUsingPost({
      appId: props.appId as any,
      questionContent: questionContent.value,
    });
  }

  if (res.data.data === 0) {
    message.success("提交成功");
    setTimeout(() => {
      router.push(`/app/detail/${props.appId || res.data.data}  `);
    }, 3000);
  } else {
    message.error("提交失败" + res.data.message);
  }
};

//todo 修改逻辑
/**
 * 添加题目
 * @constructor
 */
const AddQuestion = (index: number) => {
  questionContent.value.splice(index, 0, {
    title: "",
    options: [],
  });
};
/**
 * 删除题目
 * @param index
 */
const DeleteQuestion = (index: number) => {
  questionContent.value.splice(index, 1);
};
/**
 * 添加题目选项
 * @constructor
 */
const AddQuestionOption = (question: API.QuestionContentDto, index: number) => {
  if (!question.options) {
    question.options = [];
  }
  question.options.splice(index, 0, {
    key: "",
    value: "",
  });
};
/**
 * 删除题目选项
 * @param index
 */
const DeleteQuestionOption = (
  question: API.QuestionContentDto,
  index: number
) => {
  if (!question.options) {
    question.options = [];
  }
  question.options.splice(index, 1);
};

const onAiGenerateSuccess = (result: API.QuestionContentDto[]) => {
  questionContent.value = [...questionContent.value, ...result];
  message.success(`Ai生成题目成功,已新增${result.length}道题目`);
};
const onAiGenerateSuccessSSE = (result: API.QuestionContentDto[]) => {
  questionContent.value = [...questionContent.value, result];
};
const onSSEStart = (event: any) => {
  message.success("开始生成");
};
const onSSEClose = (event: any) => {
  message.success("生成完毕");
};
</script>

<style scoped>
#userLoginPage {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background-color: #f5f5f5;
  padding: 20px;
}

.title {
  font-size: 24px;
  margin-bottom: 20px;
  color: #333;
}

h3 {
  margin: 0;
  font-size: 18px;
  color: #333;
}

h4 {
  margin: 16px 0 8px;
  font-size: 16px;
  color: #666;
}
</style>
