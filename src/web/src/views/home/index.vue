<template>
  <Header />
  <div class="home-container">
    <el-row justify="center">
      <el-col :span="12">
        <el-card class="user-card">
          <template #header>
            <div class="card-header">
              <span>欢迎回来，{{ userStore.name }}</span>
            </div>
          </template>
          <div class="user-info">
            <el-descriptions :column="1" border>
              <el-descriptions-item label="编号">{{ userStore.id }}</el-descriptions-item>
              <el-descriptions-item label="身份证号">{{ userStore.uid }}</el-descriptions-item>
              <el-descriptions-item label="名称">{{ userStore.name }}</el-descriptions-item>
              <el-descriptions-item label="角色">{{ formatRole(userStore.roles) }}</el-descriptions-item>
              <el-descriptions-item label="创建时间">
                {{ new Date(userStore.createTime).toLocaleString() }}
              </el-descriptions-item>
              <el-descriptions-item label="最后更新">
                {{ new Date(userStore.updateTime).toLocaleString() }}
              </el-descriptions-item>
            </el-descriptions>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useUserStore } from '@/store/modules/user'
import { UserRoleMap } from '@/constants/user'
import { ElNotification } from "element-plus";
import { getTime } from "@/utils/time";

const userStore = useUserStore()

const formatRole = (roles: string) => {
  return roles.split(',')
    .map(role => UserRoleMap[role as keyof typeof UserRoleMap] || role)
    .join('、');
};

onMounted(async () => {
  await userStore.getUserInfo()
})
</script>

<style scoped>
.home-container {
  padding: 20px;
}

.user-card {
  margin-top: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 18px;
  font-weight: bold;
}

.user-info {
  margin-top: 10px;
}
</style>