import { createRouter, createWebHistory, RawRouteComponent, RouteRecordRaw } from "vue-router";
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
  {
    path: "/medical",
    name: "medical",
    component: MainFrame,
    meta: {
      title: "医护",
    },
    children: [
      {
        path: "/patient",
        name: "patient",
        meta: {
          title: "患者",
        },
        component: () => import("@/views/medical/PatientView.vue"),
      },
      {
        path: "/hospital",
        name: "hospital",
        meta: {
          title: "医院",
        },
        component: () => import("@/views/medical/PatientView.vue"),
      },
      {
        path: "/doctor",
        name: "doctor",
        meta: {
          title: "医生",
        },
        component: () => import("@/views/medical/PatientView.vue"),
      },
      {
        path: "/queue",
        name: "queue",
        meta: {
          title: "预约号",
        },
        component: () => import("@/views/medical/PatientView.vue"),
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: constantRoutes,
});

const getComponent = (component?: "MainFrame" | "TableView" | RawRouteComponent) => {
  switch (component) {
    case "TableView":
      return TableView;
    case "MainFrame":
      return MainFrame;
    default:
      return component;
  }
};

/**
 * 初始化路由
 */
const initRouter = async () => {
  const store = useStore();
  const menuList: SysMenu[] = await sendGet<SysMenu[]>("/system/manage/menu/list")
    .then((req: SysMenu[]) => {
      constantRoutes.forEach(menu => {
        console.log("menu", menu);
        if (menu.name !== undefined) {
          const children: RouteRecordRaw[] = menu.children;
          if ((children !== undefined && children.length > 0) && (menu.meta !== undefined && !menu.meta.hidden)) {
            const newMenu: SysMenu = {
              name: menu.meta.title,
              path: menu.path,
              type: "S_M_T_FIRST",
              component: menu.component,
              children: [],
            };


            children.forEach(childMenu => {
              if (childMenu.meta !== undefined && !childMenu.meta.hidden) {
                const newChildrenMenu: SysMenu = {
                  name: childMenu.meta.title,
                  path: childMenu.path,
                  component: childMenu.component,
                  type: "S_M_T_CHILD",
                };
                newMenu.children.push(newChildrenMenu);
              }
            });

            req.push(newMenu);
          }
        }
      });
      console.log("menuList", req);
      store.setMenuList(req);
      return req;
    });


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
    // @ts-ignore
    newRouter.children = childrenRouter;
    constantRoutes.push(newRouter);
  });

  return createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: constantRoutes,
  });
};

export default initRouter;