<template>
  <div class="home">
    <HomeListView />
    <HomeRecommend />
    <HomeCommodityList />
    <HomeGuess />
    <HomeFloor
      v-for="floor in floorList"
      :key="floor.id"
      :title="floor.name"
      :keywords="floor.keywords"
      :tabs="floor.tabs"
      :bg="floor.id % 2 === 0 ? '#fafafa' : '#fff'"
    />
    <HomeTrademark />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import HomeListView from './HomeListView.vue'
import HomeRecommend from './HomeRecommend.vue'
import HomeCommodityList from './HomeCommodityList.vue'
import HomeGuess from './HomeGuess.vue'
import HomeFloor from './HomeFloor.vue'
import HomeTrademark from './HomeTrademark.vue'
import api from '@/api/index.js'

const floorList = ref([])

onMounted(async () => {
  const res = await api.getFloorList()
  if (res.code === 0) floorList.value = res.data
})
</script>
