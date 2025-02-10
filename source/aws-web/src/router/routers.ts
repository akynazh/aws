import { UserRole } from "@/models/constants/user";

export const constantRoutes = [
  {
    path: "/login",
    component: () => import("@/views/login/index.vue"),
    name: "login",
  },
  {
    path: "/",
    component: () => import("@/views/home/index.vue"),
    name: "home",
  },
  {
    path: "/user",
    component: () => import("@/views/user/index.vue"),
    name: "user",
    meta: {
      requiresRoles: [UserRole.ADMIN]
    }
  },
  {
    path: "/produce",
    component: () => import("@/views/produce/index.vue"),
    name: "produce",
  },
  {
    path: "/work",
    component: () => import("@/views/work/index.vue"),
    name: "work",
  },
  {
    path: "/weigh",
    component: () => import("@/views/weigh/index.vue"),
    name: "weigh",
  },
  // {
  //   path: "/data",
  //   component: () => import("@/views/data/index.vue"),
  //   name: "data",
  // },
  {
    path: "/404",
    component: () => import("@/views/404/index.vue"),
    name: "404",
  },
  {
    path: "/502",
    component: () => import("@/views/502/index.vue"),
    name: "502",
  },
  {
    path: "/:pathMatch(.*)*",
    redirect: "/404",
    name: "Any",
  },
];
