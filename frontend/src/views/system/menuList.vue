<template>
  <div class="search-area">
    <el-form>
      <el-row :gutter="10" >
        <el-col :span="5" >
          <el-form-item label="关键字" >
            <el-input placeholder="系统模块/操作人" v-model="state.searchObj.keyword" />
          </el-form-item>
        </el-col>
        <el-col :span="5" >
          <el-form-item label="请求路径" >
            <el-input placeholder=""  v-model="state.searchObj.operUrl"/>
          </el-form-item>
        </el-col>
        <el-col :span="7" >
          <el-form-item label="创建时间">
            <el-date-picker
                v-model="state.createTimes"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                type="daterange"
                range-separator="至"/>
          </el-form-item>
        </el-col>
        <el-col :span="7" class="btn-col" >
          <el-button type="primary" @click="fetchData()">
            <el-icon><Search /></el-icon>
            <span>搜索</span>
          </el-button>
          <el-button @click="reset">
            <el-icon><Refresh /></el-icon>
            <span>重置</span>
          </el-button>
        </el-col>
      </el-row>
    </el-form>
  </div>
  <div class="table-area">
    <!--列表操作-->
    <div class="table-area_operation">
      <el-button type="success" @click="add()">
        <el-icon><Plus /></el-icon><span>新建</span>
      </el-button>
    </div>
    <!--列表数据-->
    <div class="table-area_data">
      <el-table
          ref="tableRef"
          row-key="id"
          :data="state.tableData"
          :default-expand-all="false"
          highlight-current-row
          @selection-change="handleSelectionChange"
          @row-click="handleRowClick"
          @row-dblclick="handleRowDblClick"
          :tree-props="{children: 'children', checkStrictly: true}"> <!--checkStrictly 这样父子勾选不关联-->
        <el-table-column type="selection" width="30" :header-cell-class-name="() => 'no-select-all'">
        </el-table-column>
        <el-table-column prop="name" label="菜单名称" width="150"/>
        <el-table-column label="类型" align="center" width="75">
          <template #default="scope">
            <el-tag v-if="scope.row.type === 0" type="warning">目录</el-tag>
            <el-tag v-if="scope.row.type === 1" type="success">菜单</el-tag>
            <el-tag v-if="scope.row.type === 2" type="danger">按钮</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="图标">
          <template #default="scope">
            <el-icon><component :is="scope.row.icon" /></el-icon>
          </template>
        </el-table-column>
        <el-table-column prop="perms" label="权限标识" width="160"/>
        <el-table-column prop="path" label="路由地址" width="200"/>
        <el-table-column prop="component" label="组件路径" width="200"/>
        <el-table-column prop="sortValue" label="排序" width="60"/>
        <el-table-column label="状态" width="80">
          <template #default="scope">
            <el-switch
                :model-value="scope.row.status === 1"
                inline-prompt
                active-text="启用"
                inactive-text="停用"
                style="--el-switch-off-color: gray">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="170"/>
        <el-table-column label="操作" width="160" fixed="right">
          <template v-slot="scope">
            <el-button
                v-if="scope.row.type === 0 || scope.row.type === 1"
                type="primary" size="small"
                link icon="plus" @click="add(scope.row.id)">新建</el-button>
            <el-button
                type="primary"
                size="small"
                link
                icon="edit"
                @click="edit(scope.row.id)">
              编辑
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!--分页-->
    <div class="table-pagination" >
      <el-pagination
          v-model:current-page="state.currentPage"
          v-model:page-size="state.pageSize"
          :page-sizes="[20, 50, 100, 300]"
          :background="true"
          layout="total, sizes, prev, pager, next, jumper"
          :total="state.total"
          @current-change="fetchData"
          />
    </div>

  <!--详情/编辑/新增-->
  <el-drawer v-model="state.drawerVisible"  direction="rtl" size="35%">
    <template #header>
      <h4>{{state.drawerTitle}}</h4>
      <!--<div class="i-svg:api" />-->
    </template>
    <template #default>
      <div>
        <el-form ref="elFormRef" :model="state.formData" label-width="100px">
          <!--v-if="state.formData.parentId === ''"-->
          <el-form-item label="父级菜单" >
            <!--<el-input v-model="state.formData.parentName"/>-->
            <el-tree-select
                v-model="state.formData.parentId"
                :data="menuOptions"
                placeholder="选择上级菜单"
                check-strictly
                filterable
                :default-expanded-keys="['-1']"
                :render-after-expand="false"
            >
              <!-- 选中后输入框显示完整路径 -->
              <template #prefix>
                <span v-if="state.formData.parentId" class="full-path-prefix">
                  {{ getSelectedFullPath(state.formData.parentId) }}
                </span>
              </template>
              <template #default="{ data }">
                <span>{{ data.label }}</span>
              </template>
            </el-tree-select>
            <!--filterable 可搜索 -->
          </el-form-item>
          <el-form-item label="菜单类型" prop="type">
            <el-radio-group v-model="state.formData.type">
              <el-radio :value="0" >目录</el-radio>
              <el-radio :value="1" >菜单</el-radio>
              <el-radio :value="2" >按钮</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="菜单名称" prop="name">
            <el-input v-model="state.formData.name"/>
          </el-form-item>
          <el-form-item label="排序">
            <el-input-number v-model="state.formData.sortValue" controls-position="right" :min="0" />
          </el-form-item>
          <el-form-item prop="path" v-if="state.formData.type !== 2">
              <template #label>
                <div class="flex-x-center">
                  路由地址
                  <el-tooltip placement="top" content="访问的路由地址，如：/system/menuList">
                    <el-icon class="cursor-pointer ml-4"><QuestionFilled /></el-icon>
                  </el-tooltip>
                </div>
              </template>
              <el-input v-model="state.formData.path" placeholder="请输入路由地址" />
          </el-form-item>
          <!--如果是按钮的菜单则不显示-->
          <el-form-item v-if="state.formData.type !== MenuTypeEnum.BUTTON" label="图标" prop="icon">
            <!-- 图标选择器 -->
            <icon-select v-model="state.formData.icon" />
          </el-form-item>
          <el-form-item prop="component" v-if="state.formData.type !== 2 && state.formData.type !== 0">

            <template #label>
              <div class="flex-x-center">
              组件路径
              <el-tooltip placement="top" content="访问的组件路径，如：`system/user/index`，默认在`views`目录下" >
                <el-icon class="cursor-pointer ml-4"><QuestionFilled /></el-icon>
              </el-tooltip>
                </div>
            </template>
            <el-input v-model="state.formData.component" placeholder="system/user/index" >
              <template v-if="state.formData.type === MenuTypeEnum.MENU" #prepend>src/views/</template>
              <template v-if="state.formData.type === MenuTypeEnum.MENU" #append>.vue</template>
            </el-input>

          </el-form-item>
          <el-form-item v-if="state.formData.type === 2">
            <template #label>
              <div class="flex-x-center">
                权限字符
                <el-tooltip content="控制器中定义的权限字符，如：@PreAuthorize(hasAuthority('bnt.sysRole.list'))" placement="top">
                  <el-icon class="cursor-pointer ml-4"><QuestionFilled /></el-icon>
                </el-tooltip>
              </div>
            </template>
            <el-input v-model="state.formData.perms" placeholder="请输入权限标识" maxlength="100" />
          </el-form-item>
          <el-form-item label="状态" prop="type">
            <el-radio-group v-model="state.formData.status">
              <el-radio :value="1">正常</el-radio>
              <el-radio :value="0">停用</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </div>
    </template>
    <template #footer>
      <div style="flex: auto">
        <el-button @click="state.drawerVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </div>
    </template>
  </el-drawer>
  </div>
</template>

<script setup>
import {onMounted, ref} from "vue";
import api from "@/api/system/menuApi.js"
import IconSelect from "@/components/IconSelect/index.vue"
import { processDateRange } from '@/utils/dateUtils'
import {ElMessage, ElTreeSelect} from 'element-plus' // 按需导入elementPlus组件

// const elFormRef = ref(ElForm);

const MenuTypeEnum = {
  CATALOG: 0,     // 目录
  MENU: 1,        // 菜单
  BUTTON: 2       // 按钮
}
// 初始化菜单表单数据
const initFormData = {
  id: '',
  parentId: '',
  parentName:'',
  name: '',
  type: 0,
  path: '',
  component: '',
  perms: '',
  icon: '',
  sortValue: 1,
  status: 1,
}

// 菜单表单数据
const formData = {...initFormData.value };

const menuOptions = ref([]);
const currentRow = ref();

const state = ref(
    {
      currentPage: 1,
      pageSize: 20,
      total: 0,
      createTimes: [],
      searchObj: {
        keyword:"",
        operUrl:"",
        createTimeBegin:"",
        createTimeEnd:"",
      }, // 查询表单对象
      tableData: [],
      formData:formData,
      listLoading: false,
      drawerVisible:false,
      drawerTitle:"",
      // selectedRow:[],
    }
)

// 直接在 setup 中执行，相当于 created
const fetchData = async (page=1) => {
  let refreshPage = page
  let {pageSize, searchObj} = state.value
  const timeRange = processDateRange(state.value.createTimes)
  searchObj.createTimeBegin = timeRange[0]
  searchObj.createTimeEnd = timeRange[1]
  // 获取数据的逻辑
  let res = await api.page(refreshPage, pageSize, searchObj)
  state.value.tableData = res.data.list
  state.value.total = res.data.total
}

// 立即执行，相当于 created
fetchData()

onMounted(() => {
  // 后端应做缓存菜单数据
  api.findNodes().then((resp)=>{
    menuOptions.value = [{ value: "-1", label: "顶级菜单", children: transformData(resp.data) }];
  })

})

const reset = ()=>{
  state.value.createTimes = []
  state.value.searchObj = {}
  fetchData()
}


// 数据转换函数
const transformData = (data) => {
  return data
      .filter(item => item.type !== 2) // 不要按钮
      .map(item => ({
    value: item.id,
    label: item.name,
    children: item.children ? transformData(item.children) : undefined
  }))
}


// 获取选中节点的完整路径
const getSelectedFullPath = (nodeId) => {
  const findPath = (nodes, targetId, path = []) => {
    for (const node of nodes) {
      if (node.value === targetId) {
        return path // 不包含自己
      }
      if (node.children) {
        const found = findPath(node.children, targetId, [...path, node.label])
        if (found.length) return found
      }
    }
    return []
  }
  const path = findPath(menuOptions.value, nodeId)
  if (!path.length) return ''
  return path.join(' / ') + ' /'
}

const add = (id)=>{

  // let parentId = currentRow.value?.id;
  if (id) {
    // 从行数据点击的新建
    state.value.formData.parentId = id;
  }

  state.value.drawerVisible = true
  state.value.drawerTitle = "新建菜单";
}


const edit = async (id) => {
  let resp = await api.getById(id)
  console.log(resp)
  state.value.formData = { ...resp.data };
  // state.value.formData = {
  //   ...state.value.formData,  // 保留原有值
  //   ...resp.data              // 用新值覆盖
  // }
  state.value.drawerTitle = "编辑菜单";
  state.value.drawerVisible = true
}

const saveOrUpdate = async () =>{
  let resp
  if (!state.value.formData.id) {
    // 如果id不存在则走新增保存
    resp = await api.save(state.value.formData)
  } else {
    // 否则走更新
    resp = await api.updateById(state.value.formData)
  }
  ElMessage.success(resp.msg ||'操作成功')
  fetchData(state.value.currentPage)
  state.value.drawerVisible = false
}


// 表格引用
const tableRef = ref()
// const selectedRow = ref([])



// 控制只能单选行
function handleSelectionChange(rows) {
  const last = rows[rows.length - 1]
  console.log(rows.length) // 会打印3次
  if (rows.length > 1) {
    // 只保留最后一次选中的
    tableRef.value.clearSelection() // 清空所有 会重新触发 handleSelectionChange
    tableRef.value.toggleRowSelection(last, true) // 重新选中当前，再次触发handleSelectionChange
  }
  currentRow.value = last
  // 高亮当前行
  tableRef.value.setCurrentRow(last) // 设置当前选择的行高亮
}

// 点击当前行同时选择复选框再高亮
function handleRowClick(row) {
  // 先清空所有勾选
  tableRef.value.clearSelection()
  // 勾选当前行
  tableRef.value.toggleRowSelection(row, true)
  // 高亮当前行
  tableRef.value.setCurrentRow(row)
  currentRow.value = row
}


// 检查是否有子节点
const hasChildren = (row) => {
  return Array.isArray(row.children) && row.children.length > 0
}

function handleRowDblClick(row) {
  if (!hasChildren(row)) {
    // 如果没有子节点，可以添加提示或执行其他操作
    // console.log('该行没有子节点')
    return
  }
  // 切换当前行的展开状态
  toggleRowExpansion(row)
}

// 切换展开状态
const toggleRowExpansion = (row) => {
  if (!tableRef.value) return
  // 使用 toggleRowExpansion 方法
  // 第二个参数为 undefined 表示切换状态
  tableRef.value.toggleRowExpansion(row, undefined)
}


</script>

<style lang="scss" >
// 引人公共样式 不要写scoped

$search-wrapper-height:55px;
$table-area_operation-height:50px;
@use '@/styles/commonTable.scss'; // 引人公共table样式

.search-area {
  height: $search-wrapper-height; // 覆盖父类
}

.table-area_operation {
  height: $table-area_operation-height; // 覆盖父类
}

</style>
<style scoped>
/*el-drawer 要在div下，否则css选择不到，注意一下*/
/* 组件自身的 scoped 样式 */
:deep(.el-drawer__header) {
  margin-bottom: 0;
  padding-bottom: 20px;
  border-bottom: 1px solid var(--el-border-color-light);
}
/* 被选择的行字体加粗 */
:deep(.el-table__body .current-row > .el-table__cell) {
  font-weight: bold; /*font-weight会继承*/
}

:deep(.el-form-item__label) {
  font-weight: bold;
}

:deep(.el-table__header .el-checkbox) {
  display: none;
}

</style>
