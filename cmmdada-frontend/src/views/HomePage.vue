<template>
  <div id="home">
    <div class="searchBar">
      <a-input-search
        :style="{ width: '320px', marginBottom: '5px' }"
        placeholder="请输入内容"
        button-text="搜索"
        search-button
      ></a-input-search>
    </div>
    <a-list
      class="list-demo-action-layout"
      :grid-props="{ gutter: [20, 20], sm: 24, md: 12, lg: 8, xl: 6 }"
      :bordered="false"
      :data="datalist"
      :pagination-props="{
        pageSize: searchParams.pageSize,
        current: searchParams.current,
        total,
      }"
      @page-change="onPageChange"
    >
      <template #item="{ item }">
        <div class="card-container">
          <AppCard :app="item"></AppCard>
        </div>
      </template>
    </a-list>
  </div>
</template>

<script lang="ts" setup>
import { ref, watchEffect } from "vue";
import AppCard from "@/components/AppCard.vue";
import API from "@/api";
import { listAppVoByPageUsingPost } from "@/api/appController";
import message from "@arco-design/web-vue/es/message";
import { REVIEW_STATUS_ENUM } from "@/constant/app";

const formSearchParams = ref<API.AppQueryRequest>({});
//初始值
const initSearchParams = {
  current: 1,
  pageSize: 12,
};
const searchParams = ref<API.AppQueryRequest>({
  ...initSearchParams,
});
const datalist = ref<API.AppVO[]>([]);
const total = ref<number>(0);
/**
 * 获取数据
 */
const loadData = async () => {
  const params = {
    reviewStatus: REVIEW_STATUS_ENUM.PASS,
    ...searchParams.value,
  };
  const res = await listAppVoByPageUsingPost(params);
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
</script>
<style scoped>
.list-demo-action-layout .image-area {
  width: 183px;
  height: 119px;
  border-radius: 2px;
  overflow: hidden;
}

.list-demo-action-layout .list-demo-item {
  padding: 20px 0;
  border-bottom: 1px solid var(--color-fill-3);
}

.list-demo-action-layout .image-area img {
  width: 100%;
}

.list-demo-action-layout .arco-list-item-action .arco-icon {
  margin: 0 4px;
}
.searchBar {
  text-align: center;
}
.card-container {
  transition: transform 0.3s ease-in-out;
}

.card-container:hover {
  transform: scale(1.05);
}
</style>
