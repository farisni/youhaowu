<template>
  <div class="guess">
    <div class="container">
      <div class="sec-header">
      <h3 class="sec-title">猜你喜欢</h3>
      <a href="javascript:;" class="change-btn" @click="refresh">换一换 <el-icon class="change-icon"><Refresh /></el-icon></a>
    </div>
      <div class="grid" :key="key">
        <ProductCard v-for="i in 4" :key="i" :title="'猜你喜欢 ' + i" price="¥599" />
      </div>
    </div>
  </div>
</template>

<script setup>
import ProductCard from '@/components/ProductCard.vue'
import { Refresh } from '@element-plus/icons-vue'
import { ref } from 'vue'
const key = ref(0)
const refresh = () => { key.value++ }
</script>

<style lang="scss" scoped>
.guess {
  width: 100%;
  padding: 10px 0;
  .container {
    width: var(--width-content);
    margin: 0 auto;
    .sec-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 16px;
    }
    .change-btn {
      display: inline-flex; align-items: center; gap: 4px;
      font-size: 14px;
      color: #999;
      text-decoration: none;
    }
    .sec-title {
      font-size: 20px;
      font-weight: 700;
      color: var(--color-text-dark);
      margin-bottom: 0;
    }
    .grid {
      display: flex; flex-wrap: wrap;

      gap: 12px;
      & > * { width: calc(25% - 9px); }    }
  }
}
.change-icon { font-size: 16px; }
</style>
