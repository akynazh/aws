import Pagination from "./Pagination/index.vue";
import Header from "./Header/index.vue";
import Footer from "./Footer/index.vue";
import type { App, Component } from "vue";

const allGlobalComponents: { [name: string]: Component } = {
  Pagination,
  Header,
  Footer,
};
export default {
  install(app: App) {
    Object.keys(allGlobalComponents).forEach((key: string) => {
      app.component(key, allGlobalComponents[key]);
    });
  },
};
