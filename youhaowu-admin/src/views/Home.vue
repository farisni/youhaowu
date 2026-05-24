<template>
  <div class="home">
    <el-card class="welcome-card" shadow="hover">
      <h2>欢迎回来，{{ appStore.userInfo.username }}</h2>
      <p>有好物 后台管理系统</p>
    </el-card>

    <el-row :gutter="20">
      <el-col :span="6" v-for="card in cards" :key="card.title">
        <el-card shadow="hover" class="stat-card">
          <div class="stat">
            <el-icon :size="36" :color="card.color"><component :is="card.icon" /></el-icon>
            <div class="stat-info">
              <div class="stat-value">{{ card.value }}</div>
              <div class="stat-label">{{ card.title }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { useAppStore } from '@/stores/app.js'

const appStore = useAppStore()

const cards = [
  { title: '商品数量', value: '--', icon: 'Goods', color: '#409eff' },
  { title: '今日订单', value: '--', icon: 'Tickets', color: '#67c23a' },
  { title: '用户数量', value: '--', icon: 'User', color: '#e6a23c' },
  { title: '本月销售', value: '--', icon: 'TrendCharts', color: '#f56c6c' },
]
</script>

<style lang="scss" scoped>
.home {
  padding: 20px 0;
}

.stat-card {
  .stat {
    display: flex;
    align-items: center;
    gap: 16px;
  }
  .stat-info {
    .stat-value { font-size: 24px; font-weight: 600; }
    .stat-label { font-size: 13px; color: #999; margin-top: 4px; }
  }
}

.welcome-card {
  margin-bottom: 20px;
  h2 { margin: 0 0 8px; }
  p { color: #999; margin: 0; }
}
</style>
