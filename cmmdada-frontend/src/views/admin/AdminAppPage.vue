<template>
  <div id="adminuserpage">
    <a-form
      class="form"
      :model="formSearchParams"
      :style="{ width: '400px' }"
      label-align="left"
      auto-label-width
      layout="inline"
      @submit="doSearch"
    >
      <a-form-item field="appName">
        <a-input
          v-model="formSearchParams.appName"
          placeholder="请输入应用名称"
        />
      </a-form-item>
      <a-form-item>
        <a-button html-type="submit" type="primary" class="login-button"
          >搜索</a-button
        >
      </a-form-item>
    </a-form>
    <a-table
      :columns="columns"
      :data="datalist"
      :pagination="{
        showTotal: true,
        pageSize: searchParams.pageSize,
        current: searchParams.current,
        total,
      }"
      @page-change="onPageChange"
    >
      <!--todo 插槽用法-->
      <template #appIcon="{ record }">
        <a-image width="64" :src="record.appIcon"> </a-image>
      </template>
      <template #optional="{ record }">
        <a-space>
          <a-button
            @click="doReview(record, REVIEW_STATUS_ENUM.PASS, '')"
            status="success"
            v-if="record.reviewStatus !== REVIEW_STATUS_ENUM.PASS"
            >通过</a-button
          >
          <a-button
            @click="
              doReview(record, REVIEW_STATUS_ENUM.REJECT, '不符合上架要求')
            "
            status="warning"
            v-if="record.reviewStatus !== REVIEW_STATUS_ENUM.REJECT"
            >拒绝</a-button
          >
          <a-button @click="doDelete(record)" status="danger">删除</a-button>
        </a-space>
      </template>
      <template #createTime="{ record }">
        {{ dayjs(record.createTime).format("YYYY-MM-DD HH:mm:ss") }}
      </template>
      <template #updateTime="{ record }">
        {{ dayjs(record.updateTime).format("YYYY-MM-DD HH:mm:ss") }}
      </template>
    </a-table>
  </div>
</template>

<script setup lang="ts">
import { ref, watchEffect } from "vue";
import API from "@/api";
import message from "@arco-design/web-vue/es/message";
import { dayjs } from "@arco-design/web-vue/es/_utils/date";
import {
  deleteAppUsingPost,
  doAppReviewUsingPost,
  listAppByPageUsingPost,
} from "@/api/appController";
import { REVIEW_STATUS_ENUM } from "@/constant/app";

const formSearchParams = ref<API.AppQueryRequest>({});
//初始值
const initSearchParams = {
  current: 1,
  pageSize: 10,
};
const searchParams = ref<API.AppQueryRequest>({
  ...initSearchParams,
});
const datalist = ref<API.App[]>([]);
const total = ref<number>(0);
/**
 * 获取数据
 */
const loadData = async () => {
  const res = await listAppByPageUsingPost(searchParams.value);
  if (res.data.code === 0) {
    datalist.value = res.data.data?.records || [];
    total.value = res.data.data?.total || 0;
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
/**
 *当页面当前页改变的时候
 */
//todo 分页使用场景
const onPageChange = (page: number) => {
  searchParams.value = {
    ...searchParams.value,
    current: page,
  };
};
/**
 * 删除记录
 */
const doDelete = async (record: API.App) => {
  console.log(record);
  if (!record.id) {
    return;
  }
  const res = await deleteAppUsingPost({
    id: record.id,
  });
  if (res.data.code === 0) {
    message.success("删除成功");
    loadData();
    message.success("删除成功");
  } else {
    message.error("删除失败" + res.data.message);
  }
};
/**
 * 修改记录
 */
const doReview = async (
  record: API.App,
  reviewStatus: number,
  reviewMessage?: string
) => {
  if (!record.id) {
    return;
  }
  const res = await doAppReviewUsingPost({
    id: record.id,
    reviewStatus,
    reviewMessage,
  });
  if (res.data.code === 0) {
    loadData();
    message.success("审核成功");
  } else {
    message.error("审核失败" + res.data.message);
  }
};
/**
 * 搜索记录
 */
const doSearch = () => {
  searchParams.value = {
    ...initSearchParams,
    ...formSearchParams.value,
  };
};
const columns = [
  {
    title: "应用名",
    dataIndex: "appName",
  },
  {
    title: "应用类型",
    dataIndex: "appType",
  },
  {
    title: "应用图标",
    dataIndex: "appIcon",
    slotName: "appIcon",
  },
  {
    title: "创建时间",
    dataIndex: "createTime",
    slotName: "createTime",
  },
  {
    title: "更新时间",
    dataIndex: "updateTime",
    slotName: "updateTime",
  },
  {
    title: "审核状态",
    dataIndex: "reviewStatus",
    slotName: "reviewStatus",
  },
  {
    title: "审核信息",
    dataIndex: "reviewMessage",
  },
  {
    title: "操作",
    slotName: "optional",
  },
];
</script>
