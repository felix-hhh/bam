import { createApp } from "vue";
import { createPinia, Pinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
import ElementPlus from "element-plus";
import "@/styles/index.scss";
import "element-plus/dist/index.css";
import "element-plus/theme-chalk/display.css";
import zhCn from "element-plus/dist/locale/zh-cn.mjs";
import initRouter from "@/router";
import App from "./App.vue";
import * as ElementPlusIconsVue from "@element-plus/icons-vue";
import { Router } from "vue-router";


const pinia: Pinia = createPinia();
pinia.use(piniaPluginPersistedstate);

const app:App<Element> = createApp(App);
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
}

app.use(pinia);
const router:Router = await initRouter()
app.use(router);

app.use(ElementPlus, {
  locale: zhCn,
});

app.mount("#app");
