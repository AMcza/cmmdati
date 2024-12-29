<template>
  <div id="addscoringresult">
    <h2 class="title">设置评分</h2>
    <a-form
      :model="form"
      :style="{ width: '480px' }"
      label-align="left"
      auto-label-width
      @submit="handleSubmit"
    >
      {{ form }}
      <a-form-item label="应用id" field="appId">
        {{ appId }}
      </a-form-item>
      <a-form-item label="修改应该id" v-if="updateId">
        {{ updateId }}
      </a-form-item>
      <a-form-item label="结果名称" field="resultName">
        <a-input
          v-model="form.resultName"
          placeholder="请输入结果名称"
        ></a-input>
      </a-form-item>
      <a-form-item label="题目列表" field="resultDesc">
        <a-input
          v-model="form.resultDesc"
          placeholder="请输入结果描述"
        ></a-input>
      </a-form-item>
      <a-form-item label="结果图标" field="resultPicture">
        <a-input
          v-model="form.resultPicture"
          placeholder="请输入结果图标地址"
        ></a-input>
      </a-form-item>
      <a-form-item label="结果集">
        <a-input-tag
          v-model="form.resultProp"
          :style="{ width: '320px' }"
          placeholder="请输入结果集,回车确定"
        ></a-input-tag>
      </a-form-item>
      <a-form-item label="结果得分范围" field="resultScoreRange">
        <a-input-number
          v-model="form.resultScoreRange"
          placeholder="请输入结果得分范围"
        >
        </a-input-number>
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="handleSubmit" style="width: 200px"
          >提交</a-button
        >
      </a-form-item>
    </a-form>
  </div>
  <h2>评分管理</h2>
  <ScoringResultTable
    :doUpdate="doUpdate"
    ref="tableRef"
    :appId="appId"
  ></ScoringResultTable>
</template>

<script setup lang="ts">
import { ref, watchEffect, defineExpose } from "vue";
import API from "@/api";
import router from "@/router";
import message from "@arco-design/web-vue/es/message";
import {
  addScoringResultUsingPost,
  editScoringResultUsingPost,
  getScoringResultVoByIdUsingGet,
} from "@/api/scoringResultController";
import ScoringResultTable from "@/views/add/components/ScoringResultTable.vue";

interface Props {
  appId: string;
}
const form = ref({
  resultDesc: "",
  resultPicture: "",
  resultName: "",
} as API.ScoringResultAddRequest);

//todo ref与reactive的区别
// eslint-disable-next-line no-undef
const props = withDefaults(defineProps<Props>(), {
  appId: () => {
    return "";
  },
});

// const loadData = async () => {
//   if (!props.appId) {
//     return;
//   }
//   const res = await getScoringResultVoByIdUsingGet({
//     id: props.appId as any,
//   });
//   if (res.data.code === 0) {
//     console.log("获取结果集成功");
//   } else {
//     message.error("获取结果集失败");
//   }
// };
// watchEffect(() => {
//   loadData();
// });
const tableRef = ref();
/**
 *提交
 */
const handleSubmit = async () => {
  if (!props.appId) {
    return;
  }
  let res: any;
  if (updateId.value) {
    res = await editScoringResultUsingPost({
      id: updateId as any,
      ...form.value,
    });
  } else {
    res = await addScoringResultUsingPost({
      appId: props.appId as any,
      ...form.value,
    });
  }
  if (res.data.code === 0) {
    message.success("操作成功");
  } else {
    message.error("提交失败" + res.data.message);
  }
  if (tableRef.value) {
    tableRef.value.loadData();
    updateId.value = undefined;
  }
};
const updateId = ref<any>();
const doUpdate = (scoringResult: API.ScoringResultVO) => {
  updateId.value = scoringResult.id;
  form.value = scoringResult;
};
</script>

<style scoped>
#addscoringresult {
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

/* Style for the title within each question block */
.question-border h3 {
  margin-top: 0; /* Remove the top margin */
  border-bottom: 2px solid #007bff; /* Add a bottom border to the title */
  padding-bottom: 8px; /* Add some padding below the title */
  margin-bottom: 16px; /* Space below the title */
  color: #007bff; /* Title color */
}

/* Style for the options within each question block */
.question-border h4 {
  color: #333; /* Darker color for options title */
}
</style>
