<script setup lang="ts">
import { ChatLineRound, Bell, Expand, Fold, FullScreen, HomeFilled } from "@element-plus/icons-vue";
import { RouteRecordRaw, useRouter } from "vue-router";
import { onMounted, reactive, ref } from "vue";
import useStore from "@/stores";
import useAxios from "@/api";
import { SysMenu, TokenInfo } from "#/entity.ts";
import MainFrame from "@/components/MainFrame.vue";
import TableView from "@/views/TableView.vue";

const isShow = ref(true);
const isCollapse = ref(false);
const router = useRouter();
const routerList: RouteRecordRaw[] = router.options.routes;
const menuList = ref<SysMenu[]>();

const username = ref();
const { sendPut, sendGet } = useAxios();
const displayControl = reactive({
  changePwdDialog: false,
});

const getUsername = () => {
  const store = useStore();
  const tokenInfo: TokenInfo = store.getTokenInfo();
  username.value = tokenInfo.userData.nickname || tokenInfo.userData.username;
};

const logout = () => {
  sendPut("/system/manage/admin/info/logout").then(() => {
    router.push("/");
  });
};

/**
 * 取回所有菜单列表
 */
const getMenuList = () => {
  sendGet<SysMenu[]>("/system/manage/menu/list").then((req) => {
    menuList.value = req;
    initMenu();
  });
};

const initMenu = async () => {
  menuList.value?.map((menu) => {
    router.addRoute({
      name: menu.name,
      path: menu.path,
      component: getComponent(menu.component),
    });

    menu.children?.map((child) => {
      router.addRoute(menu.name, {
        name: child.name,
        path: menu.path + child.path,
        component: getComponent(child.component),
      });
    });
  });
};

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

const showChangePwdDialog = () => {
  displayControl.changePwdDialog = true;
};


onMounted(() => {
  getMenuList();
  //getUsername();
});
</script>

<template>
  <div class="main-frame">
    <header>
      <div class="header-left">
        <el-icon class="header-left-control hidden-xs-only" @click="isCollapse = !isCollapse">
          <template v-if="isCollapse">
            <Expand />
          </template>
          <template v-else>
            <Fold />
          </template>
        </el-icon>
        <el-divider direction="vertical" />
        <div class="header-left-logo">
          后台管理
        </div>
      </div>
      <div class="header-right">
        <div class="header-right-fullscreen">
          <el-icon size="larger">
            <FullScreen />
          </el-icon>
        </div>
        <el-divider direction="vertical" />
        <div class="header-right-message">
          <el-popover popper-class="message-panel" :width="300" trigger="click">
            <template #reference>
              <el-badge :value="3">
                <el-icon size="larger">
                  <ChatLineRound />
                </el-icon>
              </el-badge>
            </template>
            <template #default>
              <div class="message-panel-header">
                <div class="message-panel-header-title">
                  <el-text size="large" tag="b" type="primary">消息</el-text>
                </div>
                <div class="message-panel-header-opt">
                  <el-button link>设置</el-button>
                  <el-button link>全部已读</el-button>
                </div>
              </div>
              <div class="message-panel-body"></div>
              <div class="message-panel-footer">
                <el-button link>查看全部消息</el-button>
              </div>
            </template>
          </el-popover>
        </div>
        <el-divider direction="vertical" />
        <div class="header-right-message">
          <el-popover popper-class="message-panel" :width="300" trigger="click">
            <template #reference>
              <el-badge :value="3">
                <el-icon size="larger">
                  <Bell />
                </el-icon>
              </el-badge>
            </template>
            <template #default>
              <div class="message-panel-header">
                <div class="message-panel-header-title">
                  <el-text size="large" tag="b" type="primary">通知</el-text>
                </div>
                <div class="message-panel-header-opt">
                  <el-button link>全部已读</el-button>
                </div>
              </div>
              <div class="message-panel-body"></div>
              <div class="message-panel-footer">
                <el-button link>查看全部通知</el-button>
              </div>
            </template>
          </el-popover>
        </div>
        <el-divider direction="vertical" />
        <el-dropdown trigger="click">
          <div class="header-right-userinfo">
            <el-avatar
              :size="30"
              src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"
            />
            <span class="header-right-userinfo-name">{{ username }}</span>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <!--                <el-dropdown-item icon="User">个人中心</el-dropdown-item>-->
              <el-dropdown-item icon="Key" @click="showChangePwdDialog">
                修改密码
              </el-dropdown-item>
              <el-dropdown-item icon="SwitchButton" @click="logout" divided>
                退出登录
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </header>
    <main>
      <aside>
        <el-menu
          v-if="isShow"
          class="main-menu"
          :default-active="router.currentRoute.value.path"
          :default-openeds="[router.currentRoute.value.matched[0].path]"
          :collapse="isCollapse"
          :unique-opened="true"
          :router="true"
        >
          <template v-for="menu in menuList" :key="menu.id">
            <el-menu-item
              v-if="
                menu.children !== undefined &&
                menu.children.length === 1 &&
                menu.children[0].path === ''
              "
              :index="menu.path"
            >
              <el-icon>
                <HomeFilled />
              </el-icon>
              <span>{{ menu.children[0].name }}</span>
            </el-menu-item>
            <el-sub-menu
              v-else-if="menu.children !== undefined && menu.children.length !== 0"
              :index="menu.path"
            >
              <template #title>
                <el-icon>
                  <Avatar />
                </el-icon>
                <span>{{ menu.name }}</span>
              </template>
              <template v-for="subMenu in menu.children" :key="subMenu.name">
                <el-menu-item
                  :index="menu.path + subMenu.path"
                >
                  {{ subMenu.name }}
                </el-menu-item>
              </template>
            </el-sub-menu>
          </template>
        </el-menu>
      </aside>
      <section class="main-section">
        <div class="main-header">
          <div class="main-header-title">
            <h1>{{ router.currentRoute.value.name }}</h1>
          </div>
          <div class="main-header-breadcrumb">
            <el-breadcrumb separator="/">
              <el-breadcrumb-item :to="'/index'">首页</el-breadcrumb-item>
              <template v-if="router.currentRoute.value.path !== '/index'">
                <el-breadcrumb-item>
                  {{ router.currentRoute.value.matched[0].name }}
                </el-breadcrumb-item>
                <el-breadcrumb-item>
                  {{ router.currentRoute.value.name }}
                </el-breadcrumb-item>
              </template>
            </el-breadcrumb>
          </div>
        </div>
        <div class="main-content">
          <RouterView />
        </div>
      </section>
    </main>
  </div>
</template>

<style scoped lang="scss">
.main-frame {
  display: flex;
  flex-direction: column;
  height: 100vh;

  header {
    height: 50px;
    display: flex;
    align-items: center;
    background-color: var(--el-color-white);
    box-shadow: var(--el-box-shadow-lighter);
    color: var(--el-text-color-placeholder);
    padding: 0 15px;
    z-index: 100;

    .header-left {
      width: 200px;
      display: flex;
      align-items: center;

      &-control {
        cursor: pointer;
      }

      &-logo {
        font-weight: bold;
        color: var(--el-color-primary);
        font-size: 20px;
      }
    }

    .header-right {
      flex: 1;
      display: flex;
      align-items: center;
      justify-content: flex-end;

      &-message, &-fullscreen {
        padding: 0 5px;
        cursor: pointer;
      }

      &-userinfo {
        display: flex;
        cursor: pointer;
        align-items: center;

        &-name {
          padding-left: 5px;
        }
      }
    }
  }

  main {
    display: flex;
    flex: 1;
    flex-shrink: 0;
    height: 0;

    aside {
      background-color: var(--el-color-white);
      border-right: 1px solid var(--el-border-color);
      overflow-y: auto;
      width: 200px;
    }

    section.main-section {
      flex: 1;
      overflow: scroll;
      border-bottom: 1px solid var(--el-border-color);
      //padding: 10px 10px 0 10px;

      .main-header {
        display: flex;
        align-items: center;
        height: 60px;
        padding: 0 20px;
        background-color: var(--el-color-white);

        &-title {
          flex-grow: 1;
        }
      }

      .main-content {
        padding: 10px 10px 0 10px;
      }

      > .el-card {
        margin-bottom: 20px;
      }
    }
  }
}

.el-menu {
  border: 0;

  .el-menu-item.is-active {
    background-color: var(--el-menu-hover-bg-color);
    border-right: 1px solid var(--el-menu-active-color);
  }
}

.message-panel {

  &-header {
    background-color: var(--el-color-white);
    box-shadow: var(--el-box-shadow-lighter);
    height: 40px;
    display: flex;
    align-items: center;
    padding: 0 10px;

    &-title {
      flex-grow: 1;
    }

    &-opt {
      width: 120px;
      text-align: right;
    }
  }

  &-body {
    height: 100px;
  }

  &-footer {
    height: 40px;
    background-color: var(--el-color-white);
    box-shadow: var(--el-box-shadow-lighter);
    text-align: center;
    line-height: 40px;
  }
}
</style>