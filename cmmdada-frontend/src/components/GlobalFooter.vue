<script setup lang="ts"></script>

<template>
  <!-- -----------------底部分享按钮------------------------- -->
  <div class="bottom-container">
    <!-- 在各个分享选项添加超链接 -->
    <div class="icon-container">
      <icon-qq />
    </div>

    <!-- WeChat Icon -->
    <div class="icon-container">
      <a href="https://weixin.qq.com/" target="_blank">
        <img src="矢量图/weixin.png" alt="WeChat" class="icon" title="WeChat" />
      </a>
    </div>

    <!-- QQ Icon -->
    <div class="icon-container">
      <a href="https://qq.com/" target="_blank">
        <img src="矢量图/分享-QQ.png" alt="QQ" class="icon" title="QQ" />
      </a>
    </div>

    <!-- Weibo Icon -->
    <div class="icon-container">
      <a href="https://weibo.com/" target="_blank">
        <img src="矢量图/weibo.png" alt="Weibo" class="icon" title="Weibo" />
      </a>
    </div>
  </div>
</template>

<style scoped>
.bottom-container {
  display: flex;
  margin: 0 auto;
  border-radius: 15px; /* Slightly reduced */
  justify-content: space-between;
  align-content: center;
  background-color: rgba(255, 255, 255, 0);
  color: #282626;
  width: 250px; /* Reduced */
}

.icon-container {
  display: flex;
  justify-content: center; /* Centered */
  align-items: center; /* Centered */
  width: 20px; /* Reduced */
  height: 20px; /* Reduced */
  background-color: rgba(204, 216, 234, 0.5);
  border-radius: 8px; /* Slightly reduced */
  border: none;
}

.icon {
  width: 30px; /* Reduced */
  height: 30px; /* Reduced */
  transition: filter 0.3s ease;
}

/* 当鼠标悬停在图标上时，图标颜色高亮 */
.icon-container:hover {
  filter: brightness(120%); /* Slightly reduced for subtler effect */
}
</style>
