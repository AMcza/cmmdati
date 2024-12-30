<template>
  <div id="appStatisticPage">
    <a-card>
      <h2>热门应用统计</h2>
      <Vchart :option="appAnswerCountOptions" style="height: 300px"></Vchart>
    </a-card>
    <a-card>
      <h2>应用结果统计</h2>
      <div class="search-bar">
        <a-input-search
          :style="{ width: '320px' }"
          placeholder="输入appId"
          button-text="搜索"
          size="large"
          search-button
          @search="(value) => loadAppCountAppResult(value)"
        >
        </a-input-search>
      </div>
      <Vchart
        :option="appAnswerCountResultOptions"
        style="height: 300px"
      ></Vchart>
    </a-card>
  </div>
</template>
<script lang="ts" setup>
import { computed, ref, watchEffect } from "vue";
import API from "@/api";
import message from "@arco-design/web-vue/es/message";
import Vchart from "vue-echarts";
import "echarts";
import {
  getAppAnswerCountUsingGet,
  getAppAnswerResultCountUsingGet,
} from "@/api/statisticController";

const appAnswerCountList = ref<API.AppAnswerCountDTO[]>([]);
const appAnswerCountResultList = ref<API.AppAnswerResultCountDTO[]>([]);
/**
 * 获取数据
 */
const loadAppCountAppAnswer = async () => {
  const res = await getAppAnswerCountUsingGet();
  if (res.data.code === 0) {
    appAnswerCountList.value = res.data.data || [];
  } else {
    message.error("获取数据失败" + res.data.message);
  }
};
const loadAppCountAppResult = async (appId: string) => {
  if (!appId) {
    return;
  }
  const res = await getAppAnswerResultCountUsingGet({
    appId: appId as any,
  });
  if (res.data.code === 0) {
    appAnswerCountResultList.value = res.data.data || [];
  } else {
    message.error("获取数据失败" + res.data.message);
  }
};
//appAnswer
const appAnswerCountOptions = computed(() => {
  return {
    xAxis: {
      type: "category",
      data: appAnswerCountList.value.map((item) => item.appId),
      name: "应用 id",
    },
    yAxis: {
      type: "value",
      name: "用户答案数",
    },
    series: [
      {
        data: appAnswerCountList.value.map((item) => item.answerCount),
        type: "bar",
      },
    ],
  };
});
const appAnswerCountResultOptions = computed(() => {
  return {
    tooltip: {
      trigger: "item",
    },
    legend: {
      orient: "vertical",
      left: "left",
    },
    series: [
      {
        name: "应用答案结果分布",
        type: "pie",
        radius: "50%",
        data: appAnswerCountResultList.value.map((item) => {
          return { value: item.resultCount, name: item.resultName };
        }),
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: "rgba(0, 0, 0, 0.5)",
          },
        },
      },
    ],
  };
});
/**
 * 加载数据
 * 监听数据变化触发加载
 */
//todo 监听数据使用场景
watchEffect(() => {
  loadAppCountAppAnswer();
  loadAppCountAppResult("");
});
</script>
<style scoped>
#appdetailpage {
}
#appdetailpage .content-wrapper > * {
  margin-bottom: 24px;
}
</style>
