<template>
  <div class="app">
    <template v-if="!isLoginPage">
      <HomeHeader />
      <HomeCategoryNav />
    </template>
    <RouterView />
    <HomeFooter v-if="!isLoginPage" />
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import HomeHeader from '@/views/home/HomeHeader.vue'
import HomeCategoryNav from '@/views/home/HomeCategoryNav.vue'
import HomeFooter from '@/views/home/HomeFooter.vue'

const route = useRoute()
const isLoginPage = computed(() => route.meta?.hideLayout)
</script>

<style lang="scss" scoped>
.app {
  min-height: 100vh;
  min-width: var(--width-content);
}
</style>

<style lang="scss">
html, body, #app {
  height: 100%;
}
</style>
