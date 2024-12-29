<script setup lang="ts">
import GlobalHeader from "@/components/GlobalHeader.vue";
import {
  IconQq,
  IconGithub,
  IconWechat,
  IconWeibo,
} from "@arco-design/web-vue/es/icon";
</script>
<template>
  <div id="basicLayout">
    <a-layout style="height: 100vh">
      <a-layout-header class="header">
        <GlobalHeader></GlobalHeader>
      </a-layout-header>
      <a-layout-content class="content">
        <router-view />
      </a-layout-content>
      <a-layout-footer class="footer">
        <a-space direction="vertical" :size="16" style="display: block">
          <a-row>
            <a-col>
              <a
                href="https://space.bilibili.com/3546378166864335?spm_id_from=333.337.0.0"
                target="_blank"
                class="footer-link"
                >Made by 橙猫猫</a
              >
            </a-col>
            <a-col>
              <a-space size="medium">
                <icon-qq />
                <icon-github />
                <icon-wechat />
                <icon-weibo />
              </a-space>
            </a-col>
          </a-row>
        </a-space>
      </a-layout-footer>
    </a-layout>
  </div>
</template>
<style scoped>
#basicLayout {
}
#basicLayout .content {
  max-width: 1200px;
  background: linear-gradient(to right, #fefefe, #fff);
  margin: 0 auto 28px;
  padding: 20px;
  width: 100%;
  box-sizing: border-box;
  max-width: 1200px;
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

.footer-link {
  display: block;
  color: white; /* Change the text color */
  font-weight: bold; /* Make the text bold */
  text-decoration: none; /* Remove underline */
  margin-bottom: 5px;
}

.footer-link:hover {
  color: #f0e68c; /* Change color on hover */
}
</style>
