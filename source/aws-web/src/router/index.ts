import { createRouter, createWebHashHistory } from "vue-router";
import { constantRoutes } from "./routers";
import { GET_TOKEN } from "@/utils/storage";

let router = createRouter({
  history: createWebHashHistory(),
  routes: constantRoutes,
});

router.beforeEach((to, from, next) => {
  const isAuthenticated = GET_TOKEN()
  if (to.path !== "/login" && !isAuthenticated) {
    next("/login");
  } else {
    next();
  }
});

export default router;
