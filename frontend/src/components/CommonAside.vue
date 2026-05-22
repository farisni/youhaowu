<template>
  <el-menu
    :default-active="appStore.activePath"
    :collapse="appStore.isCollapse"
    active-text-color="#409eff"
    background-color="#304156"
    text-color="#BFCBD9"
  >
    <div id="title">
      <img class="logo" src="@/assets/images/logo.png" alt="" />
      <img v-if="!appStore.isCollapse" class="logo-text" src="@/assets/images/logo-text.png" alt="" />
    </div>
    <CommonMenu
      :menu-data="appStore.userInfo.menu"
      @menu-item-click="handleMenuClick"
    />
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
  min-height: 400px;
}

.el-menu {
  height: 100vh;
  border-right: none;

  #title {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    padding: 10px;

    .logo {
      width: 45px;
      height: 45px;
    }

    .logo-text {
      height: 33px;
      width: 135px;
    }
  }
}
</style>
