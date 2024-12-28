import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import MainFrame from "@/components/MainFrame.vue";

export const constantRoutes: RouteRecordRaw[] = [
  {
    path: "/",
    redirect: "/login",
  },
  {
    path: "/login",
    name: "login",
    meta: {
      title: "登录",
      hidden: true,
    },
    component: () => import("@/views/admin/LoginView.vue"),
  },
  {
    path: "/index",
    name: "index",
    component: MainFrame,
    children: [
      {
        path: "",
        name: "dashboard",
        meta: {
          title: "首页",
        },
        component: () => import("@/views/IndexView.vue"),
      },
    ],
  },
  {
    path: "/system1",
    name: "system1",
    component: MainFrame,
    children: [
      {
        path: "/menu",
        name: "menu",
        meta: {
          title: "菜单",
        },
        component: () => import("@/views/IndexView.vue"),
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: constantRoutes,
});

export default router;