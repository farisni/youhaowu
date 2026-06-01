<template>
  <div class="commodity">
    <div class="container">
      <h3 class="sec-title">商品列表</h3>
      <div class="grid">
        <ProductCard v-for="i in 8" :key="i" :title="'商品名称 ' + i" price="¥999" />
      </div>
    </div>
  </div>
</template>

<script setup>
import ProductCard from '@/components/ProductCard.vue'
</script>

<style lang="scss" scoped>
.commodity {
  width: 100%;
  padding: 24px 0;
  .container {
    width: var(--width-content);
    margin: 0 auto;
    .sec-title {
      font-size: 20px;
      font-weight: 700;
      color: var(--color-text-dark);
      margin-bottom: 16px;
    }
    .grid {
      display: grid;
      grid-template-columns: repeat(4, 1fr);
      gap: 12px;
    }
  }
}
</style>
