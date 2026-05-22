<template>
  <el-menu
    :default-active="appStore.activePath"
    :collapse="appStore.isCollapse"
    active-text-color="#409eff"
    background-color="$sidebarBg"
    text-color="$sidebarText"
  >
    <div class="logo-area">
      <img class="logo-img" src="@/assets/images/logo.svg" alt="" />
      <span v-if="!appStore.isCollapse" class="logo-title">WheatMall</span>
    </div>
    <CommonMenu :menu-data="appStore.userInfo.menu" @menu-item-click="handleMenuClick" />
  </el-menu>
</template>

<script setup>
import { useAppStore } from '@/stores/app.js'
import { useRouter } from 'vue-router'
import CommonMenu from '@/components/CommonMenu.vue'

const appStore = useAppStore()
const router = useRouter()

const handleMenuClick = (item) => {
  router.push(item.path)
  appStore.addTag(item)
  appStore.activePath = item.path
}
</script>

<style lang="scss" scoped>
.el-menu:not(.el-menu--collapse) {
  width: 200px;
}

.el-menu {
  height: 100vh;
  background-color: $sidebarBg;
  border-right: none;
}

.logo-area {
  display: flex;
  align-items: center;
  padding: 12px 16px;
  gap: 10px;

  .logo-img {
    width: 36px;
    height: 36px;
  }

  .logo-title {
    color: #fff;
    font-size: 16px;
    font-weight: 600;
    white-space: nowrap;
  }
}
</style>
