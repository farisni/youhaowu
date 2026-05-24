<template>
  <section class="floor" :style="{ background: bg }">
    <div class="container">
      <div class="title">
        <h3>{{ title }}</h3>
        <div class="tabs">
          <span v-for="t in tabs" :key="t" class="active">{{ t }}</span>
        </div>
      </div>
      <div class="body">
        <div class="left">
          <div class="keywords">
            <span v-for="kw in keywords" :key="kw">{{ kw }}</span>
          </div>
          <div class="left-img">图片</div>
        </div>
        <div class="center">
          <el-carousel height="360px">
            <el-carousel-item v-for="i in 3" :key="i">
              <div class="carousel-placeholder">轮播 {{ i }}</div>
            </el-carousel-item>
          </el-carousel>
        </div>
        <div class="right">
          <div v-for="i in 6" :key="i" class="product-cell">商品{{ i }}</div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
defineProps({
  title: { type: String, default: '楼层' },
  bg: { type: String, default: '#fff' },
  keywords: { type: Array, default: () => [] },
  tabs: { type: Array, default: () => [] },
})
</script>

<style lang="scss" scoped>
.floor {
  width: 100%;
  padding: 32px 0;

  .container {
    width: var(--width-content);
    margin: 0 auto;

    .title {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 16px;

      h3 {
        color: var(--color-primary-dark);
        font-size: 20px;
        font-weight: 700;
        margin: 0;
      }

      .tabs {
        display: flex;
        gap: 0;

        span {
          padding: 0 12px;
          font-size: 14px;
          cursor: pointer;
          &::after { content: "|"; padding-left: 12px; color: #ccc; }
          &:last-child::after { content: ""; }
          &.active { color: var(--color-primary); }
        }
      }
    }

    .body {
      display: flex;
      height: 360px;
      border-top: 2px solid var(--color-primary-dark);

      .left {
        width: 210px;
        flex-shrink: 0;
        background: var(--color-bg-gray);
        padding: 16px;
        display: flex;
        flex-direction: column;

        .keywords {
          display: grid;
          grid-template-columns: 1fr 1fr;
          gap: 6px;
          flex: 1;

          span {
            text-align: center;
            font-size: 12px;
            cursor: pointer;
            &:hover { color: var(--color-primary); }
          }
        }

        .left-img {
          width: 100%;
          height: 60px;
          background: #ddd;
          display: flex;
          align-items: center;
          justify-content: center;
          font-size: 12px;
          color: var(--color-text-light);
          margin-top: 8px;
        }
      }

      .center {
        width: 330px;
        flex-shrink: 0;

        .carousel-placeholder {
          width: 100%;
          height: 100%;
          background: #e0e0e0;
          display: flex;
          align-items: center;
          justify-content: center;
          font-size: 14px;
          color: var(--color-text-light);
        }
      }

      .right {
        flex: 1;
        display: grid;
        grid-template-columns: 1fr 1fr 1fr;

        .product-cell {
          border: 1px solid var(--color-border);
          display: flex;
          align-items: center;
          justify-content: center;
          font-size: 13px;
          color: var(--color-text-light);
          background: var(--color-bg-page);
        }
      }
    }
  }
}
</style>
