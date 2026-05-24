<template>
  <div class="trademark">
    <div class="container">
      <div v-for="i in 6" class="brand">品牌 {{ i }}</div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.trademark {
  width: 100%;
  padding: 24px 0;

  .container {
    width: var(--width-content);
    margin: 0 auto;
    display: flex;
    gap: 12px;

    .brand {
      flex: 1;
      height: 80px;
      background: var(--color-bg-light);
      border: 1px solid var(--color-border);
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 14px;
      color: var(--color-text-light);
    }
  }
}
</style>
