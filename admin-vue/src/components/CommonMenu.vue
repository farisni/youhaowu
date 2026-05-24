<template>
  <template v-for="item in menuData" :key="item.id">
    <el-sub-menu v-if="item.children && item.children.length" :index="item.path">
      <template #title>
        <el-icon v-if="item.icon"><component :is="item.icon" /></el-icon>
        <div v-else-if="item.icon" :class="`i-svg:${item.icon}`" class="menu-icon" />
        <span>{{ item.name }}</span>
      </template>
      <CommonMenu :menu-data="item.children" @menu-item-click="emit('menu-item-click', $event)" />
    </el-sub-menu>

    <el-menu-item v-else :index="item.path" @click="handleClick(item, $event)">
      <el-icon v-if="item.icon"><component :is="item.icon" /></el-icon>
      <div v-else-if="item.icon" :class="`i-svg:${item.icon}`" class="menu-icon" />
      <template #title><span>{{ item.name }}</span></template>
    </el-menu-item>
  </template>
</template>

<script setup>
defineProps({
  menuData: { type: Array, default: () => [] },
})

const emit = defineEmits(['menu-item-click'])

const handleClick = (item, event) => {
  event?.stopPropagation?.()
  emit('menu-item-click', item)
}
</script>

<style lang="scss" scoped>
$subMenuBg: #1f2d3d;
$subMenuHover: #001528;

.el-menu {
  border-right: none;

  .el-sub-menu .el-menu-item,
  .nest-menu .el-sub-menu > .el-sub-menu__title {
    background-color: $subMenuBg;
    &:hover { background-color: $subMenuHover; }
  }

  .el-menu-item,
  .el-sub-menu {
    span { padding-left: 5px; }
  }
}

.menu-icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 24px;
  height: 22px;
  margin-right: 5px;
  font-size: 18px;
  color: currentColor;
}
</style>
