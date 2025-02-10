<template>
  <div class="not-found">
    <div class="content">
      <div class="error-code">404</div>
      <div class="emoji">😅</div>
      <h1>页面未找到</h1>
      <p>抱歉！您访问的页面不存在。</p>
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
  color: #dc3545;
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
