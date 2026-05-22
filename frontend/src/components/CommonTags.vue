<template>
  <div class="tags">
    <el-tag
      v-for="tag in appStore.tagsData"
      :key="tag.path"
      :closable="tag.path !== '/home'"
      :type="tag.path === route.path ? '' : 'info'"
      :effect="tag.path === route.path ? 'dark' : 'plain'"
      @click="changeRoute(tag)"
      @close="handleClose(tag)"
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

const handleClose = (tag) => {
  appStore.removeTag(tag)
  if (tag.path === route.path) {
    const tags = appStore.tagsData
    const idx = tags.findIndex((t) => t.path === tag.path)
    if (idx >= tags.length) {
      router.push(tags[tags.length - 1].path)
    } else {
      router.push(tags[idx].path)
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
