<script setup lang="ts"></script>
<template>
  <div id="userLayout">
    <a-layout style="height: 100vh">
      <a-layout-header class="header">
        <a-image src="@/assets/local.png" width="28px" />
        <h1>智能答题应用平台</h1>
      </a-layout-header>
      <a-layout-content class="content">
        <router-view />
      </a-layout-content>
      <a-layout-footer class="footer">
        <a
          href="https://space.bilibili.com/3546378166864335?spm_id_from=333.337.0.0"
          target="_blank"
          >Made by 橙猫猫</a
        >
      </a-layout-footer>
    </a-layout>
  </div>
</template>
<style scoped>
#userLayout {
  text-align: center;
}
.content {
  background: linear-gradient(to right, #fefefe, #fff);
  margin-bottom: 28px;
  padding: 20px;
}
#basicLayout .header {
  margin-bottom: 16px;
  box-shadow: #eee 1px 1px 5px;
}
.footer {
  background-color: #adabab;
  padding: 18px;
  font-size: 16px;
  text-align: center;
  text-decoration: none;
}
.header {
  display: flex;
  justify-content: center;
}
</style>
