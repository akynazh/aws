<template>
    <Header />
    <div>
        <el-row justify="center">
            <el-col :span="30">
                <!-- 操作面板 -->
                <div class="operation-wrapper">
                    <el-card class="operation-panel hover-effect">
                        <div class="panel-header">
                            <span class="panel-title">
                                <el-icon class="title-icon"><User /></el-icon>
                                用户管理
                            </span>
                            <div class="panel-buttons">
                                <el-button 
                                    type="primary" 
                                    :icon="Plus"
                                    @click="handleAdd"
                                >
                                    添加用户
                                </el-button>
                                <el-button 
                                    type="primary" 
                                    :icon="Search"
                                    @click="handleSearch"
                                >
                                    查询用户
                                </el-button>
                                <!-- 这里可以添加更多按钮 -->
                            </div>
                        </div>
                    </el-card>
                </div>

                <!-- 用户表格 -->
                <el-table 
                    :data="tableData" 
                    border 
                    class="custom-table hover-effect"
                    style="width: 100%"
                >
                    <el-table-column prop="id" label="编号" width="100" />
                    <el-table-column prop="uid" label="身份证号" width="180" />
                    <el-table-column prop="name" label="姓名" width="180" />
                    <el-table-column 
                        prop="roles" 
                        label="角色" 
                        width="150"
                        :filters="roleFilters"
                        :filter-method="filterRole"
                        filter-placement="bottom-end"
                    >
                        <template #default="{ row }">
                            <span>{{ formatRoles(row.roles) }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column 
                        prop="status" 
                        label="状态" 
                        width="120"
                        :filters="statusFilters"
                        :filter-method="filterStatus"
                        filter-placement="bottom-end"
                    >
                        <template #default="{ row }">
                            <el-tag :type="statusTag(row.status)">
                                {{ statusText(row.status) }}
                            </el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column prop="createTime" label="创建时间" width="180">
                        <template #default="{ row }">
                            {{ formatDate(row.createTime) }}
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="320" fixed="right">
                        <template #default="{ row }">
                            <el-button type="primary" link @click="handleViewRecords(row)">称重历史</el-button>
                            <el-button type="primary" link @click="handleViewWorkSummary(row)">分批采摘量</el-button>
                            <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
                        </template>
                    </el-table-column>
                </el-table>

                <!-- 分页 -->
                <Pagination 
                    v-model:current-page="currentPage" 
                    v-model:page-size="pageSize" 
                    :total="total"
                    @size-change="fetchUserList" 
                    @current-change="fetchUserList"
                />
            </el-col>
        </el-row>

        <!-- 用户表单对话框 -->
        <el-dialog 
            :title="isEdit ? '编辑用户' : '添加用户'" 
            v-model="dialogVisible" 
            width="500px"
        >
            <el-form 
                ref="formRef"
                :model="form"
                :rules="rules"
                label-width="100px"
            >
                <el-form-item label="身份证号" prop="uid">
                    <el-input v-model="form.uid" placeholder="请输入身份证号" />
                </el-form-item>
                <el-form-item label="姓名" prop="name">
                    <el-input v-model="form.name" placeholder="请输入姓名" />
                </el-form-item>
                <el-form-item label="角色" prop="roles">
                    <el-select 
                        v-model="selectedRoles" 
                        multiple 
                        placeholder="请选择角色（至少选择一个）"
                        @change="handleRolesChange"
                        :min="1"
                    >
                        <el-option 
                            :label="UserRoleMap[UserRole.ADMIN]" 
                            :value="UserRole.ADMIN" 
                        />
                        <el-option 
                            :label="UserRoleMap[UserRole.EMPLOYEE]" 
                            :value="UserRole.EMPLOYEE" 
                        />
                    </el-select>
                </el-form-item>
                <el-form-item label="状态" prop="status">
                    <el-select v-model="form.status" placeholder="请选择状态">
                        <el-option 
                            :label="UserStatusMap[UserStatus.ENABLE]" 
                            :value="UserStatus.ENABLE" 
                        />
                        <el-option 
                            :label="UserStatusMap[UserStatus.DISABLED]" 
                            :value="UserStatus.DISABLED" 
                        />
                        <el-option 
                            :label="UserStatusMap[UserStatus.DELETED]" 
                            :value="UserStatus.DELETED" 
                        />
                    </el-select>
                </el-form-item>
            </el-form>
            <template #footer>
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="handleSubmit">确定</el-button>
            </template>
        </el-dialog>

        <!-- 搜索用户对话框 -->
        <el-dialog 
            title="查询用户" 
            v-model="searchDialogVisible" 
            width="400px"
        >
            <el-form 
                ref="searchFormRef"
                :model="searchForm"
                :rules="searchRules"
                label-width="100px"
            >
                <el-form-item label="身份证号" prop="uid">
                    <el-input v-model="searchForm.uid" placeholder="请输入身份证号" />
                </el-form-item>
            </el-form>
            <template #footer>
                <el-button @click="searchDialogVisible = false">取消</el-button>
                <el-button type="primary" @click="handleSearchSubmit">查询</el-button>
            </template>
        </el-dialog>

        <!-- 称重记录对话框 -->
        <el-dialog 
            title="称重记录" 
            v-model="recordsDialogVisible" 
            width="800px"
        >
            <template #header>
                <div class="dialog-header">
                    <span>称重记录 - {{ currentUser?.name }}</span>
                    <el-button type="primary" @click="handleExportRecords">
                        <el-icon><Download /></el-icon>
                        导出历史称重数据
                    </el-button>
                </div>
            </template>
            <el-table :data="recordsData" border class="hover-effect">
                <el-table-column prop="id" label="记录编号" width="120" />
                <el-table-column prop="workId" label="作业编号" width="120" />
                <el-table-column prop="produceId" label="果实编号" width="120" />
                <el-table-column prop="image" label="果实图像" width="120" />
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
            <Pagination 
                v-model:current-page="recordsCurrentPage" 
                v-model:page-size="recordsPageSize" 
                :total="recordsTotal"
                @size-change="fetchRecordsList" 
                @current-change="fetchRecordsList"
            />
        </el-dialog>

        <!-- 作业采摘量对话框 -->
        <el-dialog 
            title="作业采摘量" 
            v-model="workSummaryDialogVisible" 
            width="800px"
        >
            <template #header>
                <div class="dialog-header">
                    <span>作业采摘量 - {{ currentUser?.name }}</span>
                    <el-button type="primary" @click="handleExportSummary">
                        <el-icon><Download /></el-icon>
                        导出采摘量数据
                    </el-button>
                </div>
            </template>
            <el-table :data="paginatedWorkSummaryData" border class="hover-effect">
                <el-table-column prop="workId" label="作业编号" width="200" />
                <el-table-column prop="name" label="员工名称" width="200" />
                <el-table-column prop="produceName" label="果实名称" width="200" />
                <el-table-column label="采摘量" width="200">
                    <template #default="{ row }">
                        {{ `${row.dataValue}${row.unit}` }}
                    </template>
                </el-table-column>
            </el-table>
            <Pagination 
                v-model:current-page="workSummaryCurrentPage" 
                v-model:page-size="workSummaryPageSize" 
                :total="workSummaryTotal"
                @size-change="handleWorkSummaryPageChange" 
                @current-change="handleWorkSummaryPageChange" 
            />
        </el-dialog>
    </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed } from "vue";
import type { UserVO, RecordVO, UserWorkOutputVO } from "@/models";
import { reqGetUsers, reqAddUser, reqUpdateUser, reqGetEmployee } from "@/api/user";
import { reqGetRecords, reqGetUserWorkSummary } from '@/api/weigh';
import { ElMessage } from 'element-plus';
import { Plus, Search, User, Download } from '@element-plus/icons-vue';
import { UserStatus, UserStatusMap, UserRole, UserRoleMap } from '@/models/constants/user';
import { ScaleUnitMap } from '@/models/constants/scale';
import dayjs from "dayjs";
import * as XLSX from 'xlsx';

const tableData = ref<UserVO[]>([]);
const currentPage = ref(1); // 当前页码
const pageSize = ref(7); // 每页条数
const total = ref(0); // 总条数

const dialogVisible = ref(false);
const isEdit = ref(false);
const formRef = ref();
const form = reactive<UserVO>({
    uid: '',
    name: '',
    roles: '',
    status: UserStatus.ENABLE  // 默认启用
});

const selectedRoles = ref<string[]>([]);

const rules = {
    uid: [{ required: true, message: '请输入身份证号', trigger: 'blur' }],
    name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
    roles: [{ 
        required: true, 
        message: '请至少选择一个角色', 
        trigger: 'change',
        validator: (rule: any, value: string) => {
            if (!value || value.split(',').filter(Boolean).length === 0) {
                return Promise.reject('请至少选择一个角色');
            }
            return Promise.resolve();
        }
    }],
    status: [{ required: true, message: '请选择状态', trigger: 'change' }]
};

const resetForm = () => {
    if (formRef.value) {
        formRef.value.resetFields();
    }
    form.uid = '';
    form.name = '';
    form.roles = '';
    form.status = UserStatus.ENABLE;
    selectedRoles.value = [];
};

const handleAdd = () => {
    isEdit.value = false;
    resetForm();
    dialogVisible.value = true;
};

const handleEdit = (row: UserVO) => {
    isEdit.value = true;
    Object.assign(form, row);
    selectedRoles.value = row.roles.split(',').filter(Boolean);
    dialogVisible.value = true;
};

const handleSubmit = async () => {
    if (!formRef.value) return;
    await formRef.value.validate(async (valid: boolean) => {
        if (valid) {
            try {
                if (isEdit.value) {
                    await reqUpdateUser(form);
                    ElMessage.success('更新成功');
                } else {
                    await reqAddUser(form);
                    ElMessage.success('添加成功');
                }
                dialogVisible.value = false;
                fetchUserList();
            } catch (error) {
                console.error('操作失败', error);
                ElMessage.error('操作失败: ' + error);
            }
        }
    });
};

// 添加搜索相关的响应式变量
const searchDialogVisible = ref(false);
const searchFormRef = ref();
const searchForm = reactive({
    uid: ''
});

const searchRules = {
    uid: [{ required: true, message: '请输入身份证号', trigger: 'blur' }]
};

// 处理搜索按钮点击
const handleSearch = () => {
    searchForm.uid = '';
    searchDialogVisible.value = true;
};

// 处理搜索提交
const handleSearchSubmit = async () => {
    if (!searchFormRef.value) return;
    await searchFormRef.value.validate(async (valid: boolean) => {
        if (valid) {
            try {
                const user = await reqGetEmployee(searchForm.uid);
                if (user) {
                    tableData.value = [user];
                    total.value = 1;
                    searchDialogVisible.value = false;
                    ElMessage.success('查询成功');
                }
            } catch (error) {
                console.error('查询失败', error);
                ElMessage.error('查询失败: ' + error);
            }
        }
    });
};

// 获取用户列表
const fetchUserList = async () => {
    try {
        const result = await reqGetUsers(currentPage.value - 1, pageSize.value);
        tableData.value = result?.userList || [];
        total.value = result?.count || 0;
    } catch (error) {
        console.error("获取用户列表失败", error);
        ElMessage.error('获取用户列表失败: ' + error);
    }
};

// 组件挂载时加载数据
onMounted(() => {
    fetchUserList();
});

// 格式化时间戳
const formatDate = (timestamp?: number): string => {
    return timestamp ? dayjs(timestamp).format("YYYY-MM-DD HH:mm:ss") : "N/A";
};

// 角色文本映射
const roleText = (role: string): string => {
    return role in UserRoleMap ? UserRoleMap[role as keyof typeof UserRoleMap] : role;
};

// 显示状态文本
const statusText = (status?: number): string => {
    return status !== undefined ? (UserStatusMap[status as keyof typeof UserStatusMap] || "未知") : "未知";
};

// 显示状态颜色
const statusTag = (status?: number): string => {
    if (status === undefined) return 'info';
    const tagMap = {
        [UserStatus.DISABLED]: 'warning',
        [UserStatus.ENABLE]: 'success',
        [UserStatus.DELETED]: 'danger'
    };
    return tagMap[status] || 'info';
};

const handleRolesChange = (values: string[]) => {
    if (values.length === 0) {
        ElMessage.warning('请至少选择一个角色');
    }
    form.roles = values.join(',');
};

const formatRoles = (roles: string): string => {
    return roles.split(',')
        .map(role => roleText(role))
        .filter(Boolean)
        .join('、');
};

// 添加角色筛选选项
const roleFilters = [
    { text: UserRoleMap[UserRole.ADMIN], value: UserRole.ADMIN },
    { text: UserRoleMap[UserRole.EMPLOYEE], value: UserRole.EMPLOYEE }
];

// 添加状态筛选选项
const statusFilters = Object.entries(UserStatusMap).map(([value, label]) => ({
    text: label,
    value: Number(value)
}));

// 添加角色筛选方法
const filterRole = (value: string, row: UserVO) => {
    return row.roles.split(',').includes(value);
};

// 添加状态筛选方法
const filterStatus = (value: number, row: UserVO) => {
    return row.status === value;
};

// Add new state variables
const recordsDialogVisible = ref(false);
const workSummaryDialogVisible = ref(false);
const currentUser = ref<UserVO>();
const recordsData = ref<RecordVO[]>([]);
const workSummaryData = ref<UserWorkOutputVO[]>([]);
const recordsCurrentPage = ref(1);
const recordsPageSize = ref(5);
const recordsTotal = ref(0);

// 添加作业采摘量分页相关的状态
const workSummaryCurrentPage = ref(1);
const workSummaryPageSize = ref(5);
const workSummaryTotal = ref(0);
const allWorkSummaryData = ref<UserWorkOutputVO[]>([]);

// 计算当前页的数据
const paginatedWorkSummaryData = computed(() => {
  const start = (workSummaryCurrentPage.value - 1) * workSummaryPageSize.value;
  const end = start + workSummaryPageSize.value;
  return allWorkSummaryData.value.slice(start, end);
});

// 处理分页变化
const handleWorkSummaryPageChange = () => {
  // 不需要重新请求数据，只需要让计算属性重新计算即可
};

// View records handler
const handleViewRecords = async (row: UserVO) => {
  currentUser.value = row;
  recordsCurrentPage.value = 1;
  recordsDialogVisible.value = true;
  await fetchRecordsList();
};

// View work summary handler 
const handleViewWorkSummary = async (row: UserVO) => {
  currentUser.value = row;
  workSummaryDialogVisible.value = true;
  await fetchWorkSummary();
};

// Fetch records list
const fetchRecordsList = async () => {
  if (!currentUser.value) return;
  try {
    const result = await reqGetRecords({
      page: recordsCurrentPage.value - 1,
      size: recordsPageSize.value,
      workId: -1,
      scaleId: -1,
      employeeId: currentUser.value.id
    });
    recordsData.value = result?.recordList || [];
    recordsTotal.value = result?.count || 0;
  } catch (error) {
    console.error("获取称重记录失败", error);
    ElMessage.error('获取称重记录失败: ' + error);
  }
};

// Fetch work summary
const fetchWorkSummary = async () => {
  if (!currentUser.value) return;
  try {
    const result = await reqGetUserWorkSummary(currentUser.value.id);
    if (result) {
      allWorkSummaryData.value = result;
      workSummaryTotal.value = result.length;
      workSummaryCurrentPage.value = 1;
    }
  } catch (error) {
    console.error('获取作业采摘量失败', error);
    ElMessage.error('获取作业采摘量失败: ' + error);
  }
};

// Export handlers
const handleExportRecords = async () => {
  if (!currentUser.value) return;
  try {
    const firstPage = await reqGetRecords({
      page: 0,
      size: 100,
      workId: -1,
      scaleId: -1,
      employeeId: currentUser.value.id
    });
    
    if (!firstPage || !firstPage.count) {
      ElMessage.warning('暂无数据可导出');
      return;
    }

    const totalPages = Math.ceil(firstPage.count / 100);
    const allRecords: RecordVO[] = [...firstPage.recordList];

    if (totalPages > 1) {
      const otherPagesPromises = Array.from({ length: totalPages - 1 }, (_, i) =>
        reqGetRecords({
          page: i + 1,
          size: 100,
          workId: -1,
          scaleId: -1,
          employeeId: currentUser.value.id
        })
      );

      const results = await Promise.all(otherPagesPromises);
      results.forEach(result => {
        if (result?.recordList) {
          allRecords.push(...result.recordList);
        }
      });
    }

    const excelData = allRecords.map(record => ({
      '编号': record.id,
      '作业编号': record.workId,
      '果实编号': record.produceId,
      '果实图像': record.image,
      '电子秤编号': record.scaleId,
      '称重数据': `${record.dataValue}${ScaleUnitMap[record.unit]}`,
      '误差': `±${record.dataErrorMargin}${ScaleUnitMap[record.unit]}`,
      '称重时间': formatDate(record.dataTime)
    }));

    const wb = XLSX.utils.book_new();
    const ws = XLSX.utils.json_to_sheet(excelData);
    XLSX.utils.book_append_sheet(wb, ws, '称重记录');

    XLSX.writeFile(wb, `称重记录_${currentUser.value.name}_${formatDate(Date.now())}.xlsx`);

    ElMessage.success(`成功导出${allRecords.length}条记录`);
  } catch (error) {
    console.error('导出失败', error);
    ElMessage.error('导出失败: ' + error);
  }
};

const handleExportSummary = () => {
  if (!currentUser.value || !allWorkSummaryData.value.length) {
    ElMessage.warning('暂无数据可导出');
    return;
  }

  try {
    const excelData = allWorkSummaryData.value.map(summary => ({
      '作业编号': summary.workId,
      '员工名称': summary.name,
      '果实名称': summary.produceName,
      '采摘量': `${summary.dataValue}${summary.unit}`
    }));

    const wb = XLSX.utils.book_new();
    const ws = XLSX.utils.json_to_sheet(excelData);
    XLSX.utils.book_append_sheet(wb, ws, '作业采摘量');

    XLSX.writeFile(wb, `作业采摘量_${currentUser.value.name}_${formatDate(Date.now())}.xlsx`);

    ElMessage.success(`成功导出${allWorkSummaryData.value.length}条记录`);
  } catch (error) {
    console.error('导出失败', error);
    ElMessage.error('导出失败: ' + error);
  }
};
</script>

<style scoped>
.operation-wrapper {
    margin-top: 20px;
    margin-bottom: 20px;
    display: flex;
    justify-content: center;
}

.operation-panel {
    min-width: 300px;
    max-width: 100%;
    flex-grow: 0;
}

.panel-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 32px;
}

.panel-buttons {
    display: flex;
    gap: 12px;
    flex-wrap: wrap;
}

.el-table {
    margin-top: 20px;
}

.hover-effect {
    transition: all 0.3s ease;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.hover-effect:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 16px 0 rgba(0, 0, 0, 0.2);
}

.custom-table {
    border-radius: 8px;
    overflow: hidden;
    background-color: white;
}

:deep(.el-table__row) {
    transition: all 0.3s ease;
}

:deep(.el-table__row:hover) {
    background-color: #f8f9fa !important;
    transform: scale(1.001);
}

:deep(.el-table) {
    border-radius: 8px;
    overflow: hidden;
}

:deep(.el-table__header) {
    background-color: #f5f7fa;
}

:deep(.el-table__header-row th) {
    background-color: #f5f7fa;
    color: #606266;
    font-weight: 600;
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

.dialog-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  font-size: 18px;
  font-weight: bold;
}
</style>