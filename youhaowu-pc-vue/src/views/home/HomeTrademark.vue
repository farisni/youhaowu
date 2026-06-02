<template>
  <div class="trademark">
    <div class="container">
      <template v-if="loading">
        <el-skeleton animated style="width:100%">
          <template #template>
            <div style="display:flex;gap:0">
              <el-skeleton-item v-for="i in 10" :key="i" variant="image" style="width:120px;height:37px" />
            </div>
          </template>
        </el-skeleton>
      </template>
      <ul v-else class="brand-list">
        <li v-for="item in brandList" :key="item.brandId" class="brand-item">
          <el-tooltip :content="item.name" placement="top">
            <img v-if="item.logo" :src="item.logo" />
          </el-tooltip>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '@/api/index.js'

const brandList = ref([])
const loading = ref(true)

onMounted(async () => {
  try {
    const res = await api.getBrandTop(10)
    if (res.code === 0) brandList.value = res.data?.list || []
  } catch {
    //  静默失败
  } finally {
    loading.value = false
  }
})
</script>

<style lang="scss" scoped>
.trademark {
  width: 100%;
  padding: 10px 0;

  .container {
    width: var(--width-content);
    margin: 0 auto;
  }

  .brand-list {
    overflow: hidden;
    padding: 15px 0;
    background: #f7f7f7;
    line-height: 18px;
    list-style: none;
    display: flex;

    .brand-item {
      img {
        border-left: 1px dotted #ccc;
        padding: 0 10px;
        height: 37px;
        object-fit: contain;
        cursor: pointer;
      }
      &:first-child img {
        border-left: 0;
      }
    }
  }
}
</style>
