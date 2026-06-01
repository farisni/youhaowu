<template>
  <div class="recommend">
    <div class="container">
      <div class="clock">
        <img :src="iconClock" class="clock-icon" />
        <span>今日推荐</span>
      </div>
      <div v-for="i in 4" class="rec-item">推荐图 {{ i }}</div>
    </div>
  </div>
</template>

<script setup>
import iconClock from '@/assets/icons/shizhong.svg'
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

      .clock-icon { width: 60px; height: 60px; }
    }

    .rec-item {
      flex: 1;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 14px;
      color: var(--color-text-light);
      background: #dcdcdc;
      margin: 4px;
    }
  }
}
</style>
