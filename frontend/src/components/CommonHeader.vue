<template>
  <div class="header-container">
    <div class="hamburger">
      <el-icon :size="25" @click="appStore.collapseMenu">
        <Expand v-if="!appStore.isCollapse" />
        <Fold v-else />
      </el-icon>
      <el-breadcrumb separator="/">
        <transition-group name="breadcrumb">
          <el-breadcrumb-item key="/home" :to="{ path: '/home' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item
            v-for="tab in breadcrumbList"
            :key="tab.path"
            :to="tab.path"
          >
            {{ tab.meta.title }}
          </el-breadcrumb-item>
        </transition-group>
      </el-breadcrumb>
    </div>

    <div class="right">
      <span class="username">{{ appStore.userInfo.username || '未登录' }}</span>
      <el-dropdown @command="handleCommand">
        <el-icon :size="22" class="user-icon"><UserFilled /></el-icon>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAppStore } from '@/stores/app.js'
import { removeDynamicRoutes } from '@/router/index.js'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const appStore = useAppStore()

const breadcrumbList = computed(() => {
  const paths = route.path.split('/').filter(Boolean)
  const items = []
  let accumulated = ''
  paths.forEach((seg) => {
    accumulated += `/${seg}`
    if (accumulated === '/home') return
    const matched = router.getRoutes().find((r) => r.path === accumulated)
    items.push({
      path: accumulated,
      meta: { title: matched?.meta?.title || seg },
    })
  })
  return items
})

const handleCommand = (cmd) => {
  if (cmd === 'logout') {
    appStore.clearPersistedData()
    removeDynamicRoutes()
    ElMessage.success('已退出')
    router.push('/login')
  }
}
</script>

<style lang="scss" scoped>
.header-container {
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);

  .hamburger {
    display: flex;
    align-items: center;
    cursor: pointer;
    .el-icon { margin-right: 15px; }
  }

  .right {
    display: flex;
    align-items: center;
    gap: 8px;
    cursor: pointer;

    .username {
      font-size: 14px;
      color: #666;
    }

    .user-icon {
      color: #999;
    }
  }
}

.breadcrumb-enter-active {
  transition: all 0.3s ease;
}
.breadcrumb-enter-from {
  opacity: 0;
  transform: translateX(10px);
}
.breadcrumb-leave-active {
  display: none;
}
</style>
