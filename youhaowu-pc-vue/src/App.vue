<template>
  <div class="app">
    <HomeHeader />
    <HomeCategoryNav />
    <RouterView />
    <HomeFooter />
  </div>
</template>

<script setup>
import HomeHeader from '@/views/home/HomeHeader.vue'
import HomeCategoryNav from '@/views/home/HomeCategoryNav.vue'
import HomeFooter from '@/views/home/HomeFooter.vue'
</script>

<style lang="scss" scoped>
.app {
  min-width: var(--width-content);
}
</style>
