<template>
  <div class="tags">
    <el-tag
      v-for="(tag, index) in appStore.tagsData"
      :key="tag.path"
      :closable="tag.path !== '/home'"
      :type="tag.path === route.path ? '' : 'info'"
      :effect="tag.path === route.path ? 'dark' : 'plain'"
      @click="changeRoute(tag)"
      @close="handleClose(tag, index)"
    >
      {{ tag.name }}
    </el-tag>
  </div>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'
import { useAppStore } from '@/stores/app.js'

const route = useRoute()
const router = useRouter()
const appStore = useAppStore()

const changeRoute = (tag) => {
  router.push(tag.path)
  appStore.activePath = tag.path
}

const handleClose = (tag, index) => {
  appStore.removeTag(tag)
  if (tag.path === route.path) {
    const tags = appStore.tagsData
    const next = tags.length === index ? tags[index - 1] : tags[index]
    if (next) {
      router.push(next.path)
      appStore.activePath = next.path
    }
  }
}
</script>

<style lang="scss" scoped>
.tags {
  padding: 8px 10px 6px;
  background: #fff;
  border-bottom: 1px solid #e4e7ed;

  .el-tag {
    margin-right: 10px;
    cursor: pointer;
  }
}
</style>
