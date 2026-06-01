<template>
  <div class="card">
    <div class="card-img">图</div>
    <div class="card-name">{{ title }}</div>
    <div class="card-price">{{ price }}</div>
  </div>
</template>

<script setup>
defineProps({
  title: { type: String, default: '' },
  price: { type: String, default: '' },
})
</script>

<style lang="scss" scoped>
.card {
  border: 1px solid var(--color-border);
  padding: 8px;
  cursor: pointer;
  &:hover { border-color: var(--color-primary); }
}

.card-img {
  height: 200px;
  background: #eee;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--color-text-light);
  font-size: 14px;
  margin-bottom: 8px;
}

.card-name {
  font-size: 13px;
  color: var(--color-text-dark);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.card-price {
  font-size: 16px;
  font-weight: bold;
  color: var(--color-primary);
  margin-top: 4px;
}
</style>
