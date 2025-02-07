<template>
  <div class="header-container">
    <div class="left-section">
      <img src="@/assets/images/xdu.png" alt="XDU Logo" class="logo">
      <h1>农业果实称重云端软件</h1>
    </div>
    <div class="right-section">
      <div class="nav-menu">
        <el-button type="primary" plain @click="router.push('/')">
          <el-icon><User /></el-icon>
          个人中心
        </el-button>
        <el-button type="primary" plain @click="router.push('/weigh')">
          <el-icon><SwitchButton /></el-icon>
          称重模块
        </el-button>
        <el-button type="primary" plain @click="router.push('/user')">
          <el-icon><Management /></el-icon>
          用户模块
        </el-button>
        <el-button type="primary" plain @click="router.push('/produce')">
          <el-icon><Platform /></el-icon>
          果实模块
        </el-button>
        <el-button type="primary" plain @click="router.push('/work')">
          <el-icon><DataLine /></el-icon>
          作业模块
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
  transition: box-shadow 0.3s ease;
}

.header-container:hover {
  box-shadow: 0 4px 8px rgba(0,0,0,.15);
}

.left-section {
  display: flex;
  align-items: center;
  gap: 12px;
}

.left-section h1 {
  margin: 0;
  font-size: 20px;
  color: #303133;
  font-weight: 600;
}

.logo {
  height: 40px;
  width: 40px;
  object-fit: contain;
  transition: transform 0.3s ease;
}

.logo:hover {
  transform: scale(1.1);
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

.el-button {
  transition: all 0.3s ease !important;
}

.el-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 2px 4px rgba(0,0,0,.1);
}

.logout-btn {
  margin-left: 16px;
}

.logout-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 2px 4px rgba(255,0,0,.2);
}

.el-icon {
  margin-right: 4px;
  vertical-align: middle;
}
</style>