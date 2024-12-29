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
      <a-form-item field="userId">
        <a-input v-model="formSearchParams.userId" placeholder="请输入用户id" />
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
      <template #resultPicture="{ record }">
        <a-image width="64" :src="record.resultPicture"> </a-image>
      </template>
      <template #optional="{ record }">
        <a-space>
          <a-button @click="doUpdate(record)" status="warning">修改</a-button>
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
  deleteUserAnswerUsingPost,
  listUserAnswerByPageUsingPost,
} from "@/api/userAnswerController";

const formSearchParams = ref<API.UserAnswerQueryRequest>({});
//初始值
const initSearchParams = {
  current: 1,
  pageSize: 10,
};
const searchParams = ref<API.UserAnswerQueryRequest>({
  ...initSearchParams,
});
const datalist = ref<API.UserAnswer[]>([]);
const total = ref<number>(0);
/**
 * 获取数据
 */
const loadData = async () => {
  const res = await listUserAnswerByPageUsingPost(searchParams.value);
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
const doDelete = async (record: API.User) => {
  console.log(record);
  if (!record.id) {
    return;
  }
  const res = await deleteUserAnswerUsingPost({
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
const doUpdate = (record: any) => {
  console.log(record);
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
    title: "id",
    dataIndex: "id",
  },
  {
    title: "应用类型",
    dataIndex: "appType",
  },
  {
    title: "选项",
    dataIndex: "choices",
  },
  {
    title: "结果描述",
    dataIndex: "resultDesc",
  },
  {
    title: "结果名称",
    dataIndex: "resultName",
  },
  {
    title: "结果图片",
    dataIndex: "resultPicture",
  },
  {
    title: "评分策略",
    dataIndex: "scoringStrategy",
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
    title: "操作",
    slotName: "optional",
  },
];
</script>
