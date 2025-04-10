import { createRouter, createWebHistory, Router, RouteRecordRaw } from "vue-router";
import { SysMenu } from "#/entity.ts";
import useAxios from "@/api";
import TableView from "@/views/TableView.vue";
import MainFrame from "@/components/MainFrame.vue";
import useStore from "@/stores";

const { sendGet } = useAxios();

const constantRoutes: RouteRecordRaw[] = [
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
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: constantRoutes,
});

const getComponent = (componentName?: "MainFrame" | "TableView") => {
  switch (componentName) {
    case "TableView":
      return TableView;
    case "MainFrame":
      return MainFrame;
    default:
      return MainFrame;
  }
};

const initRouter = async () => {
  const store = useStore();
  let menuList = store.getMenuList();
  if (menuList === undefined) {
    menuList = await sendGet<SysMenu[]>("/system/manage/menu/list")
      .then((req) => {
        store.setMenuList(req);
        return req;
      });
  }

  menuList?.map((menu) => {
    const newRouter: RouteRecordRaw = {
      name: menu.name,
      path: menu.path,
      component: getComponent(menu.component),
    };

    const childrenRouter: RouteRecordRaw[] = [];
    menu.children?.map((child) => {
      childrenRouter.push({
        name: child.name,
        path: menu.path + child.path,
        component: getComponent(child.component),
      });
    });
    newRouter.children = childrenRouter;
    constantRoutes.push(newRouter);
  });

  return createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: constantRoutes,
  });
};

export default initRouter;