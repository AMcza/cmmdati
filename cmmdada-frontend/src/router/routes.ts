import { RouteRecordRaw } from "vue-router";
import ACCESS_ENUM from "@/access/accessEnum";
export const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    redirect: "/home",
  },
  {
    path: "/home",
    name: "首页",
    component: () => import("@/views/HomePage.vue"),
  },
  {
    path: "/user",
    name: "用户",
    component: () => import("@//layouts/UserLayout.vue"),
    meta: {
      hideInMenu: true,
    },
    children: [
      {
        path: "/user/login",
        name: "登录",
        component: () => import("@/views/user/LoginPage.vue"),
      },
      {
        path: "/user/register",
        name: "注册",
        component: () => import("@/views/user/RegisterPage.vue"),
      },
    ],
  },
  {
    path: "/admin/user",
    name: "用户管理",
    component: () => import("@/views/admin/AdminUserPage.vue"),
    meta: {
      access: ACCESS_ENUM.ADMIN,
    },
  },
  {
    path: "/admin/app",
    name: "应用管理",
    component: () => import("@/views/admin/AdminAppPage.vue"),
    meta: {
      access: ACCESS_ENUM.ADMIN,
    },
  },
  {
    path: "/admin/question",
    name: "题目管理",
    component: () => import("@/views/admin/AdminQuestionPage.vue"),
    meta: {
      access: ACCESS_ENUM.ADMIN,
    },
  },
  {
    path: "/admin/scoring_result",
    name: "评分管理",
    component: () => import("@/views/admin/AdminScoringResultPage.vue"),
    meta: {
      access: ACCESS_ENUM.ADMIN,
    },
  },
  {
    path: "/admin/user_answer",
    name: "回答管理",
    component: () => import("@/views/admin/AdminUserAnswerPage.vue"),
    meta: {
      access: ACCESS_ENUM.ADMIN,
    },
  },
  {
    path: "/noauth",
    name: "没有权限",
    component: () => import("@/views/NoAuthPage.vue"),
    meta: {
      hideInMenu: true,
    },
  },
  {
    path: "/app/detail/:id",
    name: "应用详情",
    props: true,
    component: () => import("@/views/app/AppDetailPage.vue"),
    meta: {
      hideInMenu: true,
    },
  },
  /**
   * todo 创建应用需求
   */
  {
    path: "/add/app",
    name: "创建应用",
    component: () => import("@/views/add/AddAppPage.vue"),
    meta: {
      access: ACCESS_ENUM.ADMIN,
    },
  },
  {
    path: "/add/app/:id",
    name: "编辑应用",
    props: true,
    component: () => import("@/views/add/AddAppPage.vue"),
    meta: {
      hideInMenu: true,
    },
  },
  {
    path: "/add/question/:appId",
    name: "创建题目",
    component: () => import("@/views/add/AddQuestionPage.vue"),
    props: true,
    meta: {
      hideInMenu: true,
    },
  },
  {
    path: "/add/scoring_result/:appId",
    name: "创建评分",
    component: () => import("@/views/add/AddScoringResult.vue"),
    props: true,
    meta: {
      hideInMenu: true,
    },
  },
  {
    path: "/answer/do/:appId",
    name: "答题",
    component: () => import("@/views/answer/DoAnswerPage.vue"),
    props: true,
    meta: {
      hideInMenu: true,
      access: ACCESS_ENUM.USER,
    },
  },
  {
    path: "/answer/result/:data",
    name: "答题结果",
    component: () => import("@/views/answer/AnswerResultPage.vue"),
    props: true,
    meta: {
      hideInMenu: true,
      access: ACCESS_ENUM.USER,
    },
  },
  {
    path: "/answer/my",
    name: "我的答题",
    component: () => import("@/views/answer/MyAnswerPage.vue"),
    meta: {
      access: ACCESS_ENUM.USER,
    },
  },
];
