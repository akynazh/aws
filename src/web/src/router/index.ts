import { createRouter, createWebHashHistory } from "vue-router";
import { constantRoutes } from "./routers";

let router = createRouter({
  history: createWebHashHistory(),
  routes: constantRoutes,
});

router.beforeEach((to, from, next) => {
    const isAuthenticated = localStorage.getItem("token");
    if (to.path !== "/login" && !isAuthenticated) {
      next("/login"); // 未登录，跳转到登录页
    } else {
      next(); // 放行
    }
  });

  export default router;
