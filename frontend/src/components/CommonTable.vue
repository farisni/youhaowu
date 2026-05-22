<template>
  <div class="common-table-wrapper">
    <!-- 搜索区（slot） -->
    <div v-if="$slots.search" class="common-table-search" :style="typeof searchHeight === 'number' ? { height: searchHeight + 'px', overflow: 'auto' } : {}">
      <slot name="search" />
    </div>

    <!-- 表格区 -->
    <div class="common-table-area">
      <!-- 操作按钮 -->
      <div v-if="$slots['operation-buttons']" class="common-table-operation">
        <slot name="operation-buttons" />
      </div>

      <!-- 表格数据 -->
      <div class="common-table-data">
        <el-table
          ref="tableRef"
          v-loading="loading"
          v-bind="$attrs"
          :data="tableData"
          :default-expand-all="false"
          highlight-current-row
          @selection-change="emit('selection-change', $event)"
          @row-click="emit('row-click', $event)"
          @row-dblclick="emit('row-dblclick', $event)"
          @sort-change="emit('sort-change', $event)"
        >
          <!-- 自动渲染列 -->
          <slot name="table-columns">
            <el-table-column v-for="col in columns" :key="col.prop || col.label" v-bind="col">
              <template v-if="col.prop && $slots[col.prop]" #default="scope">
                <slot :name="col.prop" v-bind="scope" />
              </template>
            </el-table-column>
          </slot>
          <!-- 透传其他自定义列 slot -->
          <template v-for="(_, name) in $slots" :key="name" #[name]="scope">
            <slot v-if="isColumnSlot(name)" :name="name" v-bind="scope" />
          </template>
          <!-- 操作列 -->
          <el-table-column
            v-if="$slots.actions"
            label="操作"
            :width="actionsWidth"
            fixed="right"
          >
            <template #default="{ row }">
              <slot name="actions" :row="row" />
            </template>
          </el-table-column>
        </el-table>
      </div>

      <!-- 分页 -->
      <div v-if="total > 0 || !hidePagination" class="common-table-pagination">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="pageSizes"
          :background="true"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @current-change="fetchData"
          @size-change="fetchData"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'

const props = defineProps({
  /** 列配置（el-table-column props），_actions 列自动跳过 */
  columns: { type: Array, default: () => [] },
  /** API 模块（需有 page 方法） */
  api: { type: Object, required: true },
  /** API 方法名，默认 'page' */
  apiMethod: { type: String, default: 'page' },
  /** 每页条数选项 */
  pageSizes: { type: Array, default: () => [20, 50, 100, 300] },
  /** 操作列宽度 */
  actionsWidth: { type: [String, Number], default: 160 },
  /** 默认每页条数 */
  defaultPageSize: { type: Number, default: 20 },
  /** 搜索区高度（auto = 自适应内容） */
  searchHeight: { type: [String, Number], default: 'auto' },
  /** 隐藏分页 */
  hidePagination: { type: Boolean, default: false },
  /** 初始时不自动加载 */
  lazy: { type: Boolean, default: false },
})

const emit = defineEmits(['selection-change', 'row-click', 'row-dblclick', 'sort-change', 'loaded'])

const tableRef = ref(null)
const loading = ref(false)
const tableData = ref([])
const currentPage = ref(1)
const pageSize = ref(props.defaultPageSize)
const total = ref(0)

// 搜索参数（由外部通过 refresh 传入）
let _searchObj = {}

/** 获取数据 */
const fetchData = async (page) => {
  if (page) currentPage.value = page
  loading.value = true
  try {
    const res = await props.api[props.apiMethod](currentPage.value, pageSize.value, _searchObj)
    tableData.value = res.data?.list ?? res.data?.records ?? []
    total.value = res.data?.total ?? 0
    emit('loaded', { list: tableData.value, total: total.value })
  } finally {
    loading.value = false
  }
}

/** 外部调用：刷新数据（可传入新搜索条件） */
const refresh = (searchObj) => {
  if (searchObj !== undefined) _searchObj = searchObj
  currentPage.value = 1
  fetchData(1)
}

/** 外部调用：重置搜索 */
const reset = () => {
  _searchObj = {}
  currentPage.value = 1
  fetchData(1)
}

/** 获取表格实例 */
const getTableRef = () => tableRef.value

if (!props.lazy) onMounted(() => fetchData())

// 判断哪些 slot 是列级别的自定义渲染（排除系统 slot）
const reservedSlots = ['search', 'operation-buttons', 'actions', 'table-columns', 'after-table']
const isColumnSlot = (name) => !reservedSlots.includes(name)

defineExpose({ refresh, reset, getTableRef, fetchData })
</script>

<style lang="scss" scoped>
.common-table-wrapper {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.common-table-search {
  flex-shrink: 0;
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #EBEEF5;
  border-radius: 4px;
}

.common-table-area {
  flex: 1;
  display: flex;
  flex-direction: column;
  min-height: 0;
  border: 1px solid #EBEEF5;
  border-radius: 4px;
}

.common-table-operation {
  flex-shrink: 0;
  padding: 0 10px;
  height: 50px;
  display: flex;
  align-items: center;
  border-bottom: 1px solid #EBEEF5;
}

.common-table-data {
  flex: 1;
  overflow: hidden;

  :deep(.el-table) {
    height: 100%;
  }
}

.common-table-pagination {
  flex-shrink: 0;
  height: 45px;
  display: flex;
  align-items: center;
  justify-content: flex-end;
  padding: 0 10px;
}
</style>
