------
Ai智能应答平台
------
技术选型 
---
  前端:
---
    vue3+webpack
    Qrcode.js
    axios
    pinia
    vue-router
    openapi接口生成
    ESLint+Prettier+TypeScript前端工程化
---
  后端：
---
    springtboot 2.6.3
    mybatisplus 
    springAop
    Caffeine本地缓存
    Redission分布式锁
    Rxjava响应式框架
    多线程/线程池
    Mysql
    SSE服务端推送
    Rxjava响应式编程
    GLM-4
---
项目介绍：
---
- 核心功能：基于GLM-4大模型搭建的答题应用平台,用户创建的答题应用,调用后端系统进行AI结果获取与返回。
该项目采用策略模式实现答题评价多模式评判，除了调用llm大模型生成结果外,也可使用简单的传统积分算法来实现内存计算答题最终得分等结果。
- 系统优化：为解决平台与llm大模型结合后响应缓慢等问题,采用了多种优化，Rxjava响应式返回结果，SSE后端实时推送给前端，本地缓存保存结果对于相同请求的快速返回，幂等设计等。

---
项目展示(部分)
---
- 首页
<img width="1907" height="830" alt="image" src="https://github.com/user-attachments/assets/a4ab6161-1035-49d6-98f7-579080a63c5f" />
- 应用答题
<img width="1500" height="702" alt="image" src="https://github.com/user-attachments/assets/c2c0c13b-3b1a-4b98-8ad1-818cd94f48f0" />
- 结果生成与展示
<img width="1500" height="788" alt="image" src="https://github.com/user-attachments/assets/2b0f3c47-70ba-44a9-8802-90f4674796e1" />
- 应用创建与发布
<img width="1614" height="833" alt="image" src="https://github.com/user-attachments/assets/e1b833dc-557f-4c7f-b4ba-ef8b87c0b4ff" />

---
核心业务流程
---
- 调用AI生成答题结果
<img width="481" height="101" alt="评分生成" src="https://github.com/user-attachments/assets/29d88fef-68eb-4e4a-99f1-7738ad35c8b7" />

- 调用AI生成应用题目
<img width="481" height="101" alt="评分生成 drawio" src="https://github.com/user-attachments/assets/769567aa-a040-4698-b1f1-112501020b4a" />

