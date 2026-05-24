<template>
  <div class="commodity">
    <div class="container">
      <h3 class="sec-title">商品列表</h3>
      <div class="grid">
        <div v-for="i in 8" class="card">
          <div class="card-img">图</div>
          <div class="card-name">商品名称 {{ i }}</div>
          <div class="card-price">¥999</div>
        </div>
      </div>
    </div>
  </div>
</template>

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

      .card {
        border: 1px solid var(--color-border);
        padding: 8px;
        cursor: pointer;
        &:hover { border-color: var(--color-primary); }

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
      }
    }
  }
}
</style>
