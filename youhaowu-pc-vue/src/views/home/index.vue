<template>
  <div class="home">
    <HomeListView />
    <HomeRecommend />
    <HomeCommodityList />
    <HomeGuess />
    <HomeFloor title="家用电器" :keywords="['节能补贴','4K电视','空气净化器','IH电饭煲','滚筒洗衣机','对开门冰箱']" :tabs="['热门','大家电','生活电器','厨房电器','应季电器','空气/净水']" />
    <HomeFloor title="手机通讯" bg="var(--color-bg-page)" :keywords="['全面屏','游戏手机','拍照手机','5G手机','老人机','学生机']" :tabs="['热门','手机','运营商','数码']" />
    <HomeFloor title="电脑办公" :keywords="['笔记本','游戏本','平板电脑','台式机','一体机','显示器']" :tabs="['热门','电脑整机','外设','办公']" />
    <HomeTrademark />
  </div>
</template>

<script setup>
import HomeListView from './HomeListView.vue'
import HomeRecommend from './HomeRecommend.vue'
import HomeCommodityList from './HomeCommodityList.vue'
import HomeGuess from './HomeGuess.vue'
import HomeFloor from './HomeFloor.vue'
import HomeTrademark from './HomeTrademark.vue'
</script>
