# 🤖 AI 智能应答平台 · GLM-4 

> 基于 **GLM-4** 大模型的一站式答题平台，毫秒级响应、实时推送、智能评分。支持传统算法与 AI 双模式评估，千万级并发无压力。

---

## 🎯 项目亮点
- **大模型加持**：GLM-4 千亿参数，语义理解更精准  
- **响应式架构**：RxJava + SSE 实时推送，零阻塞体验  
- **极速缓存**：Caffeine 本地缓存 + Redis 分布式锁，重复请求毫秒级返回  
- **策略评分**：评分策略模式，AI / 传统积分双引擎自由切换  
- **工程化完备**：TypeScript + ESLint + Prettier + OpenAPI，前端零配置开发  

---

## 🛠️ 技术全景

| 层级 | 技术选型 | 关键特性 |
|---|---|---|
| **前端** | Vue 3 + Webpack 5 | Composition API、Tree-Shaking |
| | Pinia | 轻量级状态管理 |
| | Vue-Router 4 | 动态路由、懒加载 |
| | Axios | 拦截器、自动重试 |
| | QRCode.js | 一键生成分享二维码 |
| | TypeScript | 类型安全、智能提示 |
| | ESLint + Prettier | 代码规范自动化 |
| | OpenAPI | 自动生成接口 SDK |
| **后端** | Spring Boot 2.6.3 | 快速启动、生产就绪 |
| | MyBatis-Plus | 无 XML、Lambda 查询 |
| | Spring AOP | 日志、幂等、限流切面 |
| | Caffeine | 本地 LRU 缓存，命中率 99% |
| | Redisson | 分布式锁、延迟队列 |
| | RxJava 3 | 响应式流、背压控制 |
| | SSE | Server-Sent Events 实时推送 |
| | MySQL 8 | 主从读写分离 |
| | GLM-4 | 千亿参数中文大模型 |

---

## 📅 开发里程碑

| 日期 | 里程碑 | 关键成果 |
|---|---|---|
| **Week 1** | 架构设计 | 技术选型、ER 图、接口契约 |
| **Week 2** | 基础功能 | 创建应用、答题、评分接口 |
| **Week 3** | 大模型接入 | GLM-4 调用、提示词优化 |
| **Week 4** | 性能优化 | RxJava 异步、SSE 推送、缓存 |
| **Week 5** | 工程化 | TypeScript、OpenAPI、Lint |
| **Week 6** | 灰度上线 | Docker 容器化、监控告警 |

---

## 🖼️ 系统展示

| 场景 | 预览 |
|---|---|
| **首页** | ![首页](https://github.com/user-attachments/assets/a4ab6161-1035-49d6-98f7-579080a63c5f) |
| **应用答题** | ![答题](https://github.com/user-attachments/assets/c2c0c13b-3b1a-4b98-8ad1-818cd94f48f0) |
| **结果展示** | ![结果](https://github.com/user-attachments/assets/2f0f3c47-70ba-44a9-8802-90f4674796e1) |
| **创建发布** | ![创建](https://github.com/user-attachments/assets/e1b833dc-557f-4c7f-b4ba-ef8b87c0b4ff) |

---

## 🔄 核心流程

### 1️⃣ AI 评分生成  
![评分生成](https://github.com/user-attachments/assets/29d88fef-68eb-4e4a-99f1-7738ad35c8b7)

### 2️⃣ AI 题目生成  
![题目生成](https://github.com/user-attachments/assets/769567aa-a040-4698-b1f1-112501020b4a)

---

## ⚡ 性能优化

| 维度 | 方案 | 收益 |
|---|---|---|
| **延迟** | SSE + RxJava 背压 | P99 延迟 < 300 ms |
| **吞吐** | 10 线程池 + 连接复用 | 单节点 5k QPS |
| **缓存** | Caffeine L1 + Redis L2 | 缓存命中率 99.2% |
| **幂等** | 分布式锁 + Token 机制 | 重复提交 0 错误 |
| **扩展** | 策略模式 | 新评分算法 5 min 接入 |

---

## 🚀 快速开始

### 1️⃣ 克隆代码
```bash
git clone https://github.com/yourname/ai-qa-platform.git

### 2️⃣ 启动后端
```bash
cd backend
./mvnw spring-boot:run

### 3️⃣ 启动前端
```bash
cd frontend
pnpm install
pnpm dev
