<template>
    <div class="header-container">
      <h1>HOME</h1>
      <!-- logout button -->
      <el-button type="primary" @click="logout">Logout</el-button>
      <el-button type="primary" @click="toUser">用户管理</el-button>
      <el-button type="primary" @click="toProduce">农作物管理</el-button>
      <el-button type="primary" @click="toWork">作业管理</el-button>
      <el-button type="primary" @click="toWeigh">称重管理</el-button>
    </div>
  </template>
  
  <script setup lang="ts">
  import userStore from "@/store/modules/user";
  import { ElNotification } from "element-plus";
  import { useRouter } from "vue-router";
  
  let store = userStore();
  let router = useRouter();
  
  const logout = () => {
    store.logout();
    ElNotification({
      type: "success",
      title: "退出登录",
      message: "注销成功",
    });
    router.push("/login");
  };
  
  const toUser = () => {
    router.push("/user");
  };
  
  const toProduce = () => {
    router.push("/produce");
  };
  
  const toWork = () => {
    router.push("/work");
  };
  
  const toWeigh = () => {
    router.push("/weigh");
  };
  </script>
  
  <style scoped>
  .header-container {
    padding: 10px;
    display: flex;
    align-items: center;
    gap: 20px; /* Adjusts space between buttons */
  }
  
  h1 {
    margin-right: 20px; /* Adds space between title and buttons */
  }
  </style>