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
                                <el-icon class="title-icon"><Platform /></el-icon>
                                果实管理
                            </span>
                            <div class="panel-buttons">
                                <el-button v-if="store.roles?.includes(UserRole.ADMIN)"
                                    type="primary" 
                                    :icon="Plus"
                                    @click="handleAdd"
                                >
                                    添加果实
                                </el-button>
                                <el-button 
                                    type="primary" 
                                    :icon="Search"
                                    @click="handleSearch"
                                >
                                    查询果实
                                </el-button>
                            </div>
                        </div>
                    </el-card>
                </div>

                <!-- 果实表格 -->
                <el-table 
                    :data="tableData" 
                    border 
                    class="custom-table hover-effect"
                    style="width: 100%"
                >
                    <el-table-column prop="id" label="编号" width="100" />
                    <el-table-column prop="name" label="名称" width="180" />
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
                    <el-table-column prop="updateTime" label="更新时间" width="180">
                        <template #default="{ row }">
                            {{ formatDate(row.updateTime) }}
                        </template>
                    </el-table-column>
                    <el-table-column v-if="store.roles?.includes(UserRole.ADMIN)" label="操作" width="120" fixed="right">
                        <template #default="{ row }">
                            <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
                        </template>
                    </el-table-column>
                </el-table>

                <!-- 分页 -->
                <Pagination 
                    v-model:current-page="currentPage" 
                    v-model:page-size="pageSize" 
                    :total="total"
                    @size-change="fetchProduceList" 
                    @current-change="fetchProduceList"
                />
            </el-col>
        </el-row>

        <!-- 果实表单对话框 -->
        <el-dialog 
            :title="isEdit ? '编辑果实' : '添加果实'" 
            v-model="dialogVisible" 
            width="500px"
        >
            <el-form 
                ref="formRef"
                :model="form"
                :rules="rules"
                label-width="100px"
            >
                <el-form-item label="名称" prop="name">
                    <el-input v-model="form.name" placeholder="请输入名称" />
                </el-form-item>
                <el-form-item v-if="isEdit" label="状态" prop="status">
                    <el-select v-model="form.status" placeholder="请选择状态">
                        <el-option label="未种植" :value="0" />
                        <el-option label="在种植" :value="1" />
                        <el-option label="已删除" :value="2" />
                    </el-select>
                </el-form-item>
            </el-form>
            <template #footer>
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="handleSubmit">确定</el-button>
            </template>
        </el-dialog>

        <!-- 搜索果实对话框 -->
        <el-dialog 
            title="查询果实" 
            v-model="searchDialogVisible" 
            width="400px"
        >
            <el-form 
                ref="searchFormRef"
                :model="searchForm"
                :rules="searchRules"
                label-width="100px"
            >
                <el-form-item label="名称" prop="name">
                    <el-input v-model="searchForm.name" placeholder="请输入果实名称" />
                </el-form-item>
            </el-form>
            <template #footer>
                <el-button @click="searchDialogVisible = false">取消</el-button>
                <el-button type="primary" @click="handleSearchSubmit">查询</el-button>
            </template>
        </el-dialog>
    </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from "vue";
import type { ProduceVO, ProduceAddVO, ProduceUpdateVO } from "@/models";
import { reqGetProduces, reqAddProduce, reqUpdateProduce, reqGetProduce, reqGetProduceByName } from "@/api/produce";
import { ElMessage } from 'element-plus';
import { Platform, Plus, Search } from '@element-plus/icons-vue';
import dayjs from "dayjs";
import userStore from "@/store/modules/user";
import { UserRole } from '@/models/constants/user';

let store = userStore();

const tableData = ref<ProduceVO[]>([]);
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);

const dialogVisible = ref(false);
const isEdit = ref(false);
const formRef = ref();
const form = reactive<ProduceAddVO & Partial<ProduceUpdateVO>>({
    name: '',
    status: 1,
    id: undefined
});

const rules = {
    name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
    status: [{ required: true, message: '请选择状态', trigger: 'change' }]
};

const resetForm = () => {
    if (formRef.value) {
        formRef.value.resetFields();
    }
    form.name = '';
    form.status = 1;
    form.id = undefined;
};

const handleAdd = () => {
    isEdit.value = false;
    resetForm();
    dialogVisible.value = true;
};

const handleEdit = (row: ProduceVO) => {
    isEdit.value = true;
    Object.assign(form, row);
    dialogVisible.value = true;
};

const handleSubmit = async () => {
    if (!formRef.value) return;
    
    await formRef.value.validate(async (valid: boolean) => {
        if (valid) {
            try {
                if (isEdit.value && form.id !== undefined) {
                    await reqUpdateProduce({
                        id: form.id,
                        name: form.name,
                        status: form.status
                    });
                    ElMessage.success('更新成功');
                } else {
                    await reqAddProduce({
                        name: form.name
                    });
                    ElMessage.success('添加成功');
                }
                dialogVisible.value = false;
                fetchProduceList();
            } catch (error) {
                console.error('操作失败', error);
                ElMessage.error('操作失败');
            }
        }
    });
};

// 修改搜索相关的响应式变量
const searchDialogVisible = ref(false);
const searchFormRef = ref();
const searchForm = reactive({
    name: ''
});

const searchRules = {
    name: [
        { required: true, message: '请输入果实名称', trigger: 'blur' },
        { min: 1, message: '名称不能为空', trigger: 'blur' }
    ]
};

// 处理搜索按钮点击
const handleSearch = () => {
    searchForm.name = '';
    searchDialogVisible.value = true;
};

// 处理搜索提交
const handleSearchSubmit = async () => {
    if (!searchFormRef.value) return;
    await searchFormRef.value.validate(async (valid: boolean) => {
        if (valid && searchForm.name) {
            try {
                const produce = await reqGetProduceByName(searchForm.name);
                if (produce) {
                    tableData.value = [produce];
                    total.value = 1;
                    searchDialogVisible.value = false;
                    ElMessage.success('查询成功');
                }
            } catch (error) {
                console.error('查询失败', error);
                ElMessage.error('查询失败');
            }
        }
    });
};

// 添加状态筛选选项
const statusFilters = [
    { text: '未种植', value: 0 },
    { text: '在种植', value: 1 },
    { text: '已删除', value: 2 }
];

// 添加状态筛选方法
const filterStatus = (value: number, row: ProduceVO) => {
    return row.status === value;
};

// 修改后的获取列表方法
const fetchProduceList = async () => {
    try {
        const result = await reqGetProduces(currentPage.value - 1, pageSize.value);
        tableData.value = result?.produceList || [];
        total.value = result?.count || 0;
    } catch (error) {
        console.error("获取果实列表失败", error);
        ElMessage.error('获取果实列表失败');
    }
};

onMounted(() => {
    fetchProduceList();
});

const formatDate = (timestamp?: number): string => {
    return timestamp ? dayjs(timestamp).format("YYYY-MM-DD HH:mm:ss") : "N/A";
};

const statusText = (status?: number): string => {
    switch (status) {
        case 0:
            return "未种植";
        case 1:
            return "在种植";
        case 2:
            return "已删除";
        default:
            return "未知";
    }
};

const statusTag = (status?: number): string => {
    switch (status) {
        case 0:
            return "info";
        case 1:
            return "success";
        case 2:
            return "danger";
        default:
            return "info";
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
</style>
