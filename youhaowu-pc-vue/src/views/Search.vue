<template>
  <div class="search-page">
    <div class="container">
      <h2>搜索结果：{{ keyword }}</h2>
      <p class="placeholder">搜索功能开发中...</p>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
const route = useRoute()
const keyword = computed(() => route.query.keyword || '')
</script>

<style lang="scss" scoped>
.search-page {
  width: 100%;
  padding: 40px 0;

  .container {
    width: var(--width-content);
    margin: 0 auto;

    .placeholder {
      color: var(--color-text-light);
      font-size: 14px;
      margin-top: 16px;
    }
  }
}
</style>
