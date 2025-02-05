import { createApp } from "vue";
import App from "@/App.vue";
import ElementPlus from "element-plus";
import gloablComponent from "@/components";
import "element-plus/dist/index.css";
import zhCn from "element-plus/dist/locale/zh-cn.mjs";
import "@/styles/index.scss";
import router from "./router";
import pinia from "./store";

const app = createApp(App);
app.use(router);
app.use(pinia);
app.use(ElementPlus, {
  locale: zhCn,
});
app.use(gloablComponent);
app.mount("#app");
