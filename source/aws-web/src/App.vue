<template>
  <div>
    <router-view></router-view>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useUserStore } from '@/store/modules/user'
const userStore = useUserStore()
onMounted(async () => {
  await userStore.getUserInfo()
})
</script>

<style scoped></style>
