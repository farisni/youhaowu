<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="auto">
        <CommonAside />
      </el-aside>
      <el-container>
        <el-header>
          <CommonHeader />
        </el-header>
        <CommonTags />
        <el-main>
          <RouterView />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import CommonAside from '@/components/CommonAside.vue'
import CommonHeader from '@/components/CommonHeader.vue'
import CommonTags from '@/components/CommonTags.vue'
</script>

<style lang="scss" scoped>
.el-header {
  height: $headerHeight;
  padding: 0;
}

.el-main {
  padding: 0 10px;
  height: calc(100vh - $headerHeight - 42px);
  overflow: auto;
}
</style>
