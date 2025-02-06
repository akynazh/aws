<template>
  <div class="header-container">
    <div class="left-section">
      <h1>农业果实称重云端软件</h1>
    </div>
    <div class="right-section">
      <div class="nav-menu">
        <el-button type="primary" plain @click="router.push('/')">
          <el-icon><User /></el-icon>
          个人中心
        </el-button>
        <el-button type="primary" plain @click="router.push('/user')">
          <el-icon><Management /></el-icon>
          用户管理
        </el-button>
        <el-button type="primary" plain @click="router.push('/produce')">
          <el-icon><Platform /></el-icon>
          农作物管理
        </el-button>
        <el-button type="primary" plain @click="router.push('/work')">
          <el-icon><DataLine /></el-icon>
          作业管理
        </el-button>
        <el-button type="primary" plain @click="router.push('/weigh')">
          <el-icon><SwitchButton /></el-icon>
          称重管理
        </el-button>
      </div>
      <el-button type="danger" @click="logout" class="logout-btn">
        <el-icon><SwitchButton /></el-icon>
        退出登录
      </el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { Platform, Management, DataLine, User, SwitchButton } from '@element-plus/icons-vue'
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
    duration: 500
  });
  router.push("/login");
};
</script>

<style scoped>
.header-container {
  height: 60px;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0,0,0,.12);
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.left-section h1 {
  margin: 0;
  font-size: 20px;
  color: #303133;
  font-weight: 600;
}

.right-section {
  display: flex;
  align-items: center;
  gap: 20px;
}

.nav-menu {
  display: flex;
  gap: 10px;
}

.logout-btn {
  margin-left: 16px;
}

.el-icon {
  margin-right: 4px;
  vertical-align: middle;
}
</style>