<template>
  <nav class="type-nav">
    <div class="container">
      <div class="all" @mouseenter="showPopup = true" @mouseleave="showPopup = false">全部商品分类</div>
      <div class="nav-list">
        <span v-for="n in navs" class="nav-item">{{ n }}</span>
      </div>
      <!--  一级分类弹层 -->
      <div v-show="showPopup" class="popup" @mouseenter="showPopup = true" @mouseleave="showPopup = false">
        <div
          v-for="item in categories"
          :key="item.catId"
          class="popup-item"
          @mouseenter="activeCat = item"
          @mouseleave="activeCat = null"
        >
          {{ item.name }}
        </div>
      </div>
      <!--  二级 + 三级子分类 -->
      <div
        v-if="showPopup && activeCat && activeCat.children?.length"
        class="sub-popup"
        @mouseenter="showPopup = true"
        @mouseleave="showPopup = false"
      >
        <div v-for="sub in activeCat.children" :key="sub.catId" class="sub-block">
          <span class="sub-title">{{ sub.name }}</span>
          <div class="sub-tags">
            <span v-for="child in sub.children" :key="child.catId">{{ child.name }}</span>
          </div>
        </div>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '@/api/index.js'

const navs = ['服装城', '美妆馆', '超市', '全球购', '闪购', '团购', '有趣', '秒杀']
const showPopup = ref(false)
const activeCat = ref(null)
const categories = ref([])

onMounted(async () => {
  try {
    const res = await api.getCategoryList()
    categories.value = res.data || []
  } catch {
    //  静默失败，弹层为空
  }
})
</script>

<style lang="scss" scoped>
.type-nav {
  width: 100%;
  border-bottom: 2px solid var(--color-primary);

  .container {
    width: var(--width-content);
    margin: 0 auto;
    display: flex;
    position: relative;

    .all {
      width: 210px;
      height: 45px;
      background: var(--color-primary);
      color: #fff;
      text-align: center;
      line-height: 45px;
      font-size: 14px;
      font-weight: bold;
      cursor: pointer;
    }

    .nav-list {
      display: flex;
      align-items: center;
      margin-left: 24px;

      .nav-item {
        margin: 0 18px;
        font-size: 16px;
        color: var(--color-text-dark);
        cursor: pointer;
        &:hover { color: var(--color-primary); }
      }
    }

    .popup {
      position: absolute;
      left: 0;
      top: 45px;
      width: 210px;
      background: var(--color-bg-page);
      z-index: 999;

      .popup-item {
        padding: 0 20px;
        line-height: 32px;
        font-size: 14px;
        cursor: pointer;
        &:hover { background: #fff; }
      }
    }

    .sub-popup {
      position: absolute;
      left: 210px;
      top: 45px;
      width: 730px;
      min-height: 480px;
      background: #fff;
      border: 1px solid #ddd;
      z-index: 9999;
      padding: 16px;

      .sub-block {
        border-top: 1px solid #eee;
        padding: 6px 0;
        &:first-child { border-top: none; }

        .sub-title {
          display: inline-block;
          width: 60px;
          text-align: right;
          font-weight: 700;
          font-size: 13px;
          margin-right: 8px;
        }

        .sub-tags {
          display: inline-flex;
          flex-wrap: wrap;
          gap: 4px;

          span {
            padding: 0 8px;
            border-left: 1px solid #ccc;
            font-size: 12px;
            color: var(--color-text-light);
            cursor: pointer;
            &:hover { color: var(--color-primary); }
          }
        }
      }
    }
  }
}
</style>
