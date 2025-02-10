<template>
  <div class="not-found">
    <div class="content">
      <div class="error-code">502</div>
      <div class="emoji">😓</div>
      <h1>服务暂时不可用</h1>
      <p>抱歉！服务器出现临时故障，请稍后再试。</p>
      <router-link to="/" class="home-button">
        返回首页
      </router-link>
    </div>
  </div>
</template>

<script setup lang="ts">
// No script needed
</script>

<style scoped>
.not-found {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  background-color: #f8f9fa;
}

.content {
  padding: 2rem;
}

.error-code {
  font-size: 6rem;
  font-weight: bold;
  color: #fd7e14; /* 使用橙色区分 404 */
  margin-bottom: 1rem;
}

.emoji {
  font-size: 4rem;
  margin-bottom: 1rem;
}

h1 {
  color: #343a40;
  margin-bottom: 1rem;
}

p {
  color: #6c757d;
  margin-bottom: 2rem;
}

.home-button {
  display: inline-block;
  padding: 0.8rem 1.5rem;
  background-color: #007bff;
  color: white;
  text-decoration: none;
  border-radius: 5px;
  transition: background-color 0.3s;
}

.home-button:hover {
  background-color: #0056b3;
}
</style>
