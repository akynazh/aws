<template>
  <Header />
  <div class="home-container">
    <el-row justify="center">
      <el-col :span="12">
        <el-card class="user-card hover-effect">
          <template #header>
            <div class="panel-header">
              <span class="panel-title">
                <el-icon class="title-icon">
                  <User />
                </el-icon>
                个人信息
              </span>
            </div>
          </template>
          <div class="user-info">
            <el-descriptions :column="1" border class="hover-effect">
              <el-descriptions-item label="员工编号">{{ userStore.id }}</el-descriptions-item>
              <el-descriptions-item label="身份证号">{{ userStore.uid }}</el-descriptions-item>
              <el-descriptions-item label="名称">{{ userStore.name }}</el-descriptions-item>
              <el-descriptions-item label="角色">{{ formatRole(userStore.roles) }}</el-descriptions-item>
              <el-descriptions-item label="入职时间">
                {{ formatDateToYMD(userStore.createTime) }}
              </el-descriptions-item>
              <!-- <el-descriptions-item label="最后更新">
                {{ formatDateToYMD(userStore.updateTime) }}
              </el-descriptions-item> -->
            </el-descriptions>
          </div>
        </el-card>

        <!-- 操作面板 - 移到外面作为独立卡片 -->
        <el-card class="action-card hover-effect">
          <template #header>
            <div class="panel-header">
              <span class="panel-title">
                <el-icon class="title-icon">
                  <Operation />
                </el-icon>
                操作面板
              </span>
            </div>
          </template>
          <div class="action-buttons">
            <el-button type="primary" @click="openEditDialog">
              <el-icon>
                <Edit />
              </el-icon>
              编辑个人信息
            </el-button>
            <el-button type="primary" @click="openWeighRecordsDialog">
              <el-icon>
                <List />
              </el-icon>
              查看个人称重历史
            </el-button>
            <el-button type="primary" @click="openWorkSummaryDialog">
              <el-icon>
                <Histogram />
              </el-icon>
              查看个人分批次采摘量
            </el-button>
          </div>
        </el-card>

        <!-- 移除作业采摘量卡片 -->

      </el-col>
    </el-row>

    <!-- 编辑对话框 -->
    <el-dialog v-model="dialogVisible" title="编辑个人信息" width="30%">
      <el-form :model="form" label-width="80px">
        <el-form-item label="名称">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="新密码">
          <el-input v-model="form.password" type="password" placeholder="不修改请留空" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">确认</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 修改称重记录对话框 -->
    <el-dialog title="个人称重历史" v-model="weighRecordsDialogVisible" width="800px">
      <template #header>
        <div class="weigh-records-header">
          <span>个人称重历史</span>
          <el-button type="primary" @click="handleExport">
            <el-icon>
              <Download />
            </el-icon>
            导出历史称重数据
          </el-button>
        </div>
      </template>
      <el-table :data="recordsData" border class="hover-effect">
        <el-table-column prop="id" label="记录编号" width="120" />
        <el-table-column prop="workId" label="作业编号" width="120" />
        <el-table-column prop="scaleId" label="电子秤编号" width="120" />
        <el-table-column label="称重数据" width="150">
          <template #default="{ row }">
            {{ `${row.dataValue}${ScaleUnitMap[row.unit]}` }}
          </template>
        </el-table-column>
        <el-table-column label="误差" width="150">
          <template #default="{ row }">
            &plusmn;{{ `${row.dataErrorMargin}${ScaleUnitMap[row.unit]}` }}
          </template>
        </el-table-column>
        <el-table-column prop="dataTime" label="称重时间" width="180">
          <template #default="{ row }">
            {{ formatDate(row.dataTime) }}
          </template>
        </el-table-column>
      </el-table>
      <Pagination v-model:current-page="recordsCurrentPage" v-model:page-size="recordsPageSize" :total="recordsTotal"
        @size-change="fetchRecordsList" @current-change="fetchRecordsList" />
    </el-dialog>

    <!-- 作业采摘量对话框 -->
    <el-dialog title="作业采摘量" v-model="workSummaryDialogVisible" width="800px">
      <template #header>
        <div class="weigh-records-header">
          <span>作业采摘量</span>
          <el-button type="primary" @click="handleExportSummary">
            <el-icon>
              <Download />
            </el-icon>
            导出采摘量数据
          </el-button>
        </div>
      </template>
      <el-table :data="workSummaryData" border class="hover-effect">
        <el-table-column prop="workId" label="作业编号" width="120" />
        <el-table-column prop="name" label="员工名称" width="200" />
        <el-table-column prop="produceName" label="果实名称" width="150" />
        <el-table-column label="采摘量" width="150">
          <template #default="{ row }">
            {{ `${row.dataValue}${row.unit}` }}
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useUserStore } from '@/store/modules/user'
import { UserRoleMap } from '@/models/constants/user'
import { ElNotification } from "element-plus"
import { reqUpdateMe } from '@/api/user'
import { reqGetRecords, reqGetUserWorkSummary } from '@/api/weigh'
import type { UserUpdateMeVO, RecordVO, UserWorkOutputVO } from '@/models'
import { Edit, List, User, Operation, Download, DataLine, Histogram } from '@element-plus/icons-vue'
import { ScaleUnitMap } from '@/models/constants/scale'
import dayjs from 'dayjs'
import * as XLSX from 'xlsx'

const userStore = useUserStore()

const formatRole = (roles: string) => {
  return roles.split(',')
    .map(role => UserRoleMap[role as keyof typeof UserRoleMap] || role)
    .join('、');
};

const dialogVisible = ref(false)
const form = ref<UserUpdateMeVO>({
  name: '',
  password: ''
})

const openEditDialog = () => {
  form.value.name = userStore.name
  form.value.password = ''
  dialogVisible.value = true
}

const handleSubmit = async () => {
  try {
    await reqUpdateMe(form.value)
    ElNotification.success({
      title: '成功',
      message: '个人信息更新成功'
    })
    await userStore.getUserInfo()
    dialogVisible.value = false
  } catch (error) {
    ElNotification.error({
      title: '错误',
      message: error
    })
  }
}

// 添加称重记录相关的状态
const weighRecordsDialogVisible = ref(false)
const recordsData = ref<RecordVO[]>([])
const recordsCurrentPage = ref(1)
const recordsPageSize = ref(10)
const recordsTotal = ref(0)

// 打开称重记录对话框
const openWeighRecordsDialog = async () => {
  weighRecordsDialogVisible.value = true
  recordsCurrentPage.value = 1
  await fetchRecordsList()
}

// 获取称重记录列表
const fetchRecordsList = async () => {
  try {
    const result = await reqGetRecords({
      page: recordsCurrentPage.value - 1,
      size: recordsPageSize.value,
      workId: -1,
      scaleId: -1,
      employeeId: userStore.id
    })
    if (result) {
      recordsData.value = result.recordList
      recordsTotal.value = result.count
    }
  } catch (error) {
    console.error('获取称重记录失败', error)
    ElNotification.error({
      title: '错误',
      message: '获取称重记录失败'
    })
  }
}

// 修改导出处理函数
const handleExport = async () => {
  try {
    // 先获取第一页和总数
    const firstPage = await reqGetRecords({
      page: 0,
      size: 100,
      workId: -1,
      scaleId: -1,
      employeeId: userStore.id
    })

    if (!firstPage || !firstPage.count) {
      ElNotification.warning({
        title: '提示',
        message: '暂无数据可导出'
      })
      return
    }

    const totalPages = Math.ceil(firstPage.count / 100)
    const allRecords: RecordVO[] = [...firstPage.recordList]

    // 如果有多页，继续获取其他页的数据
    if (totalPages > 1) {
      const otherPagesPromises = Array.from({ length: totalPages - 1 }, (_, i) =>
        reqGetRecords({
          page: i + 1,
          size: 100,
          workId: -1,
          scaleId: -1,
          employeeId: userStore.id
        })
      )

      const results = await Promise.all(otherPagesPromises)
      results.forEach(result => {
        if (result?.recordList) {
          allRecords.push(...result.recordList)
        }
      })
    }

    // 准备 Excel 数据
    const excelData = allRecords.map(record => ({
      '记录编号': record.id,
      '作业编号': record.workId,
      '电子秤编号': record.scaleId,
      '称重数据': `${record.dataValue}${ScaleUnitMap[record.unit]}`,
      '误差': `±${record.dataErrorMargin}${ScaleUnitMap[record.unit]}`,
      '称重时间': formatDate(record.dataTime)
    }))

    // 创建工作簿
    const wb = XLSX.utils.book_new()
    const ws = XLSX.utils.json_to_sheet(excelData)
    XLSX.utils.book_append_sheet(wb, ws, '称重记录')

    // 导出Excel
    XLSX.writeFile(wb, `称重记录_${userStore.name}_${formatDateToYMD(Date.now())}.xlsx`)

    ElNotification.success({
      title: '成功',
      message: `成功导出${allRecords.length}条记录`
    })
  } catch (error) {
    console.error('导出失败', error)
    ElNotification.error({
      title: '错误',
      message: '导出失败'
    })
  }
}

// 添加新的日期格式化方法
const formatDate = (timestamp?: number): string => {
  return timestamp ? dayjs(timestamp).format('YYYY-MM-DD HH:mm:ss') : 'N/A'
}

// 添加新的日期格式化方法
const formatDateToYMD = (timestamp?: number): string => {
  if (!timestamp) return 'N/A';
  const date = new Date(timestamp);
  return `${date.getFullYear()}年${date.getMonth() + 1}月${date.getDate()}日`;
}

// 添加作业采摘量相关的状态
const workSummaryDialogVisible = ref(false)
const workSummaryData = ref<UserWorkOutputVO[]>([])

// 打开作业采摘量对话框
const openWorkSummaryDialog = async () => {
  workSummaryDialogVisible.value = true
  await fetchWorkSummary()
}

// 获取作业采摘量数据
const fetchWorkSummary = async () => {
  try {
    const result = await reqGetUserWorkSummary(userStore.id);
    if (result) {
      workSummaryData.value = result;
    }
  } catch (error) {
    console.error('获取作业采摘量失败', error);
    ElNotification.error({
      title: '错误',
      message: '获取作业采摘量失败'
    });
  }
}

// 导出作业采摘量
const handleExportSummary = () => {
  try {
    if (!workSummaryData.value.length) {
      ElNotification.warning({
        title: '提示',
        message: '暂无数据可导出'
      })
      return
    }

    // 准备 Excel 数据
    const excelData = workSummaryData.value.map(summary => ({
      '作业编号': summary.workId,
      '作业名称': summary.name,
      '产品': summary.produceName,
      '采摘量': `${summary.dataValue}${summary.unit}`
    }))

    // 创建工作簿
    const wb = XLSX.utils.book_new()
    const ws = XLSX.utils.json_to_sheet(excelData)
    XLSX.utils.book_append_sheet(wb, ws, '作业采摘量')

    // 导出Excel
    XLSX.writeFile(wb, `作业采摘量_${userStore.name}_${formatDateToYMD(Date.now())}.xlsx`)

    ElNotification.success({
      title: '成功',
      message: `成功导出作业采摘量数据`
    })
  } catch (error) {
    console.error('导出失败', error)
    ElNotification.error({
      title: '错误',
      message: '导出失败'
    })
  }
}
</script>

<style scoped>
.home-container {
  padding: 20px;
}

.user-card {
  margin-top: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 25px;
  font-weight: bold;
}

.card-header-mini {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 15px;
  font-weight: bold;
}

.user-info {
  margin-top: 10px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.button-container {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

.action-card {
  margin-top: 20px;
}

.action-buttons {
  display: flex;
  gap: 16px;
  flex-wrap: wrap;
  justify-content: center;
  padding: 16px 0;
}

.action-buttons .el-button {
  min-width: 160px;
  transition: all 0.3s ease;
}

.action-buttons .el-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.hover-effect {
  transition: all 0.3s ease;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.hover-effect:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 16px 0 rgba(0, 0, 0, 0.2);
}

:deep(.el-descriptions) {
  background-color: white;
  border-radius: 4px;
  overflow: hidden;
  transition: all 0.3s ease;
}

:deep(.el-descriptions:hover) {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

:deep(.el-descriptions__header) {
  background-color: #f5f7fa;
  padding: 12px 16px;
  margin-bottom: 0;
}

:deep(.el-descriptions__body) {
  padding: 16px;
}

:deep(.el-descriptions__label) {
  color: #606266;
  font-weight: 600;
}

.panel-header {
  display: flex;
  justify-content: center;
  /* Changed from space-between to center */
  align-items: center;
  font-size: 20px;
  font-weight: bold;
}

.panel-title {
  font-size: 20px;
  font-weight: 600;
  color: #303133;
  display: flex;
  align-items: center;
  gap: 8px;
  position: relative;
  padding-left: 12px;
}

.panel-title::before {
  content: '';
  position: absolute;
  left: 0;
  height: 100%;
  width: 4px;
  background: var(--el-color-primary);
  border-radius: 2px;
}

.title-icon {
  font-size: 22px;
  color: var(--el-color-primary);
}

/* 添加表格相关样式 */
:deep(.el-table) {
  border-radius: 8px;
  overflow: hidden;
  margin-bottom: 20px;
}

:deep(.el-table__header) {
  background-color: #f5f7fa;
}

:deep(.el-table__row) {
  transition: all 0.3s ease;
}

:deep(.el-table__row:hover) {
  background-color: #f8f9fa !important;
  transform: scale(1.001);
}

.table-header {
  margin-bottom: 16px;
  display: flex;
  justify-content: flex-end;
}

.weigh-records-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  font-size: 18px;
  font-weight: bold;
}
</style>