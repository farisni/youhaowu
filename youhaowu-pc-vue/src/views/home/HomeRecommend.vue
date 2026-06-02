<template>
  <div class="recommend">
    <div class="container">
      <div class="clock">
        <img :src="iconClock" class="clock-icon" />
        <span>今日推荐</span>
      </div>
      <div v-if="loading" v-for="i in 4" class="rec-item">
        <el-skeleton animated>
          <template #template>
            <div style="display:flex;flex-direction:column;align-items:center;gap:8px">
              <el-skeleton-item variant="image" style="width:100%;height:120px" />
              <el-skeleton-item variant="text" style="width:60%" />
            </div>
          </template>
        </el-skeleton>
      </div>
      <div v-else-if="error" v-for="i in 4" class="rec-item rec-placeholder">—</div>
      <a v-else v-for="item in list" :key="item.id" class="rec-item" :href="item.linkUrl || '#'" target="_blank">
        <img :src="item.imgUrl" :alt="item.title" />
        <span class="rec-title">{{ item.title }}</span>
      </a>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import iconClock from '@/assets/icons/shizhong.svg'

const list = ref([])
const loading = ref(true)
const error = ref(false)

onMounted(async () => {
  try {
    const res = await fetch('/api/cms/recommend/list')
    const data = await res.json()
    if (data.code === 0) {
      list.value = data.data
    }
  } catch (e) {
    error.value = true
  } finally {
    loading.value = false
  }
})
</script>

<style lang="scss" scoped>
.recommend {
  width: 100%;

  .container {
    width: var(--width-content);
    margin: 0 auto;
    display: flex;
    height: 160px;
    background: #eaeaea;
    margin-top: 10px;
    margin-bottom: 10px;

    .clock {
      width: 200px;
      background: #5c5251;
      color: #fff;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      font-size: 18px;
      font-weight: bold;
      gap: 8px;
      flex-shrink: 0;

      .clock-icon { width: 60px; height: 60px; }
    }

    .rec-item {
      flex: 1;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      text-decoration: none;
      color: inherit;
      background: #dcdcdc;
      margin: 4px;
      overflow: hidden;

      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
      }

      .rec-title {
        font-size: 12px;
        color: #666;
        margin-top: 4px;
      }
    }

    .rec-placeholder {
      font-size: 14px;
      color: #ccc;
    }
  }
}
</style>
