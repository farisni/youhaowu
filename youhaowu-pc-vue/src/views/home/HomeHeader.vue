<template>
  <header class="header">
    <div class="top">
      <div class="top-inner">
        <div class="top-left">欢迎来到有好物 <router-link to="/login">登录</router-link> | <router-link to="/login">注册</router-link></div>
        <div class="top-right">
          <router-link to="/center">我的订单</router-link>
          <a href="#">购物车</a>
        </div>
      </div>
    </div>
    <div class="bottom">
      <router-link to="/" class="logo">LOGO</router-link>
      <div class="search">
        <el-input v-model="keyword" placeholder="搜索商品" size="large" @keydown.enter="onSearch" class="search-input" />
        <el-button size="large" class="search-btn" @click="onSearch">搜索</el-button>
      </div>
    </div>
  </header>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
const router = useRouter()
const keyword = ref('')
const onSearch = () => {
  if (keyword.value.trim()) {
    router.push({ path: '/search', query: { keyword: keyword.value.trim() } })
  }
}
</script>

<style lang="scss" scoped>
.header {
  width: 100%;

  .top {
    width: 100%;
    background: var(--color-bg-light);

    .top-inner {
      width: var(--width-content);
      margin: 0 auto;
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 6px 0;
      font-size: 12px;
      color: var(--color-text-light);

      a {
        color: var(--color-text);
        &:hover { color: var(--color-primary); }
      }

      .top-left { display: flex; gap: 8px; }
      .top-right { display: flex; gap: 16px; }
    }
  }

  .bottom {
    width: var(--width-content);
    margin: 0 auto;
    display: flex;
    align-items: center;
    padding: 16px 0;
    gap: 32px;

    .logo {
      width: 200px;
      height: 48px;
      flex-shrink: 0;
      background: #eee;
      display: flex;
      align-items: center;
      justify-content: center;
      border-radius: 4px;
      font-size: 14px;
      color: var(--color-text-light);
      text-decoration: none;
    }

    .search {
      display: flex;
      flex: 1;
      border: 2px solid var(--color-primary);

      .search-input {
        flex: 1;
        :deep(.el-input__wrapper) {
          border: none;
          border-radius: 0;
          box-shadow: none;
        }
      }

      .search-btn {
        width: 90px;
        border-radius: 0;
        background: var(--color-primary);
        border: none;
        color: #fff;
      }
    }
  }
}
</style>
