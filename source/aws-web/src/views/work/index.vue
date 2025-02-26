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
                                <el-icon class="title-icon"><Timer /></el-icon>
                                作业管理
                            </span>
                            <div class="panel-buttons">
                                <el-button 
                                v-if="store.roles?.includes(UserRole.ADMIN)"
                                    type="primary" 
                                    :icon="Plus"
                                    @click="handleAdd"
                                >
                                    添加作业
                                </el-button>
                                <el-button 
                                    type="primary" 
                                    :icon="Search"
                                    @click="handleSearch"
                                >
                                    查询作业
                                </el-button>
                                <el-button 
                                    type="primary" 
                                    :icon="Download"
                                    @click="handleExport"
                                     v-if="store.roles?.includes(UserRole.ADMIN)"
                                >
                                    导出作业数据
                                </el-button>
                            </div>
                        </div>
                    </el-card>
                </div>

                <!-- 作业表格 -->
                <el-table 
                    :data="tableData" 
                    border 
                    class="custom-table hover-effect"
                    style="width: 100%"
                >
                    <el-table-column prop="id" label="编号" width="100" />
                    <!-- <el-table-column label="作业名称" width="200">
                        <template #default="{ row }">
                            {{ generateWorkName(row) }}
                        </template>
                    </el-table-column> -->
                    <el-table-column prop="produceId" label="采摘产品" width="120">
                        <template #default="{ row }">
                            <el-tooltip :content="getProduceName(row.produceId)" placement="top">
                                <span>{{ getProduceName(row.produceId) || row.produceId }}</span>
                            </el-tooltip>
                        </template>
                    </el-table-column>
                    <el-table-column label="开始时间" width="180">
                        <template #default="{ row }">
                            {{ formatDate(row.startTime) }}
                        </template>
                    </el-table-column>
                    <el-table-column label="结束时间" width="180">
                        <template #default="{ row }">
                            {{ formatDate(row.endTime) }}
                        </template>
                    </el-table-column>
                    <el-table-column label="采摘量" width="150">
                        <template #default="{ row }">
                            {{ row.dataValue ? `${row.dataValue}${formatUnit(row.unit)}` : '-' }}
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
                    @size-change="fetchWorkList" 
                    @current-change="fetchWorkList"
                />
            </el-col>
        </el-row>

        <!-- 作业表单对话框 -->
        <el-dialog 
            :title="isEdit ? '编辑作业' : '添加作业'" 
            v-model="dialogVisible" 
            width="500px"
        >
            <el-form 
                ref="formRef"
                :model="form"
                :rules="rules"
                label-width="100px"
            >
                <!-- 产品选择仅在添加时可用 -->
                <el-form-item label="采摘产品" prop="produceId">
                    <el-select 
                        v-model="form.produceId" 
                        placeholder="请选择采摘产品"
                        filterable
                        :loading="produceLoading"
                        :disabled="isEdit"
                    >
                        <el-option
                            v-for="item in produceList"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id"
                        />
                    </el-select>
                </el-form-item>
                <el-form-item label="开始时间" prop="startTime">
                    <el-date-picker
                        v-model="form.startTime"
                        type="datetime"
                        placeholder="选择开始时间"
                        value-format="x"
                        :default-time="new Date(2000, 1, 1, 0, 0, 0)"
                    />
                </el-form-item>
                <el-form-item label="结束时间" prop="endTime">
                    <el-date-picker
                        v-model="form.endTime"
                        type="datetime"
                        placeholder="选择结束时间"
                        value-format="x"
                        :default-time="new Date(2000, 1, 1, 0, 0, 0)"
                    />
                </el-form-item>
                <el-form-item v-if="isEdit" label="采摘量">
                    <span>{{ form.dataValue ? `${form.dataValue}${formatUnit(form.unit)}` : '-' }}</span>
                </el-form-item>
                <el-form-item v-if="isEdit" label="状态" prop="status">
                    <el-select v-model="form.status" placeholder="请选择状态">
                        <el-option 
                            v-for="(text, value) in WorkStatusMap" 
                            :key="value"
                            :label="text"
                            :value="Number(value)"
                        />
                    </el-select>
                </el-form-item>
            </el-form>
            <template #footer>
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="handleSubmit">确定</el-button>
            </template>
        </el-dialog>

        <!-- 添加搜索作业对话框 -->
        <el-dialog 
            title="查询作业" 
            v-model="searchDialogVisible" 
            width="400px"
        >
            <el-form 
                ref="searchFormRef"
                :model="searchForm"
                :rules="searchRules"
                label-width="100px"
            >
                <el-form-item label="作业编号" prop="workId">
                    <el-input v-model.number="searchForm.workId" type="number" placeholder="请输入作业编号" />
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
import type { WorkVO, WorkAddVO, WorkUpdateVO, ProduceVO } from "@/models";
import { reqGetWorks, reqAddWork, reqUpdateWork, reqGetWork } from "@/api/work";
import { reqGetProduces } from "@/api/produce";
import { ElMessage } from 'element-plus';
import { Plus, Timer, Search, Download } from '@element-plus/icons-vue';
import dayjs from "dayjs";
import { WorkStatus, WorkStatusMap, ScaleUnit, ScaleUnitMap } from '@/models/constants/work';
import userStore from "@/store/modules/user";
import { UserRole } from '@/models/constants/user';
import * as XLSX from 'xlsx';

let store = userStore();

const tableData = ref<WorkVO[]>([]);
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);

const dialogVisible = ref(false);
const isEdit = ref(false);
const formRef = ref();
const form = reactive<WorkUpdateVO & WorkAddVO>({
    id: 0,
    produceId: 0,
    startTime: 0,
    endTime: 0,
    dataValue: 0,
    unit: ScaleUnit.KG,
    status: WorkStatus.NOT_STARTED
});

// 添加产品相关变量
const produceList = ref<ProduceVO[]>([]);
const produceLoading = ref(false);
const produceMap = ref<Map<number, string>>(new Map());

// 获取产品列表
const fetchProduceList = async () => {
    produceLoading.value = true;
    try {
        const result = await reqGetProduces(0, 1000); // 获取足够多的产品
        produceList.value = result?.produceList || [];
        // 建立产品编号到名称的映射
        produceMap.value = new Map(
            produceList.value.map(produce => [produce.id, produce.name])
        );
    } catch (error) {
        console.error("获取产品列表失败", error);
        ElMessage.error('获取产品列表失败');
    } finally {
        produceLoading.value = false;
    }
};

// 获取产品名称
const getProduceName = (id: number): string => {
    return produceMap.value.get(id) || `产品${id}`;
};

// 添加作业名称生成函数
const generateWorkName = (work: WorkVO): string => {
    const date = dayjs(work.startTime);
    const dateStr = date.format('YYYY-MM-DD');
    const produceName = getProduceName(work.produceId);
    return `${dateStr}-${produceName}`;
};

// 修改表单验证规则
const rules = {
    produceId: [{ required: true, message: '请选择采摘产品', trigger: 'change' }],
    startTime: [{ required: true, message: '请选择开始时间', trigger: 'change' }],
    endTime: [{ required: true, message: '请选择结束时间', trigger: 'change' }]
};

// 搜索相关
const searchDialogVisible = ref(false);
const searchFormRef = ref();
const searchForm = reactive({
    workId: null as number | null
});

const searchRules = {
    workId: [
        { required: true, message: '请输入作业编号', trigger: 'blur' },
        { type: 'number', message: '作业编号必须是数字', trigger: 'blur' }
    ]
};

// 添加状态筛选选项
const statusFilters = Object.entries(WorkStatusMap).map(([value, label]) => ({
    text: label,
    value: Number(value)
}));

// 添加状态筛选方法
const filterStatus = (value: number, row: WorkVO) => {
    return row.status === value;
};

// Functions
const resetForm = () => {
    if (formRef.value) formRef.value.resetFields();
    Object.assign(form, {
        id: 0,
        produceId: 0,
        startTime: 0,
        endTime: 0,
        dataValue: 0,
        unit: ScaleUnit.KG,
        status: WorkStatus.NOT_STARTED
    });
};

const handleAdd = () => {
    isEdit.value = false;
    resetForm();
    dialogVisible.value = true;
};

const handleEdit = (row: WorkVO) => {
    isEdit.value = true;
    // 只复制允许编辑的字段
    const { id, startTime, endTime, status, produceId, dataValue, unit } = row;
    Object.assign(form, {
        id,
        produceId,
        startTime,
        endTime,
        status,
        dataValue,  // 仅用于显示
        unit       // 仅用于显示
    });
    dialogVisible.value = true;
};

const handleSubmit = async () => {
    if (!formRef.value) return;
    await formRef.value.validate(async (valid: boolean) => {
        if (valid) {
            try {
                if (isEdit.value) {
                    // 编辑时只提交允许更新的字段
                    const updateData: WorkUpdateVO = {
                        id: form.id,
                        startTime: form.startTime,
                        endTime: form.endTime,
                        status: form.status,
                        // 保持原有值不变
                        produceId: form.produceId,
                        dataValue: form.dataValue,
                        unit: form.unit
                    };
                    await reqUpdateWork(updateData);
                    ElMessage.success('更新成功');
                } else {
                    // 添加时只提交必要字段
                    const addData: WorkAddVO = {
                        produceId: form.produceId,
                        startTime: form.startTime,
                        endTime: form.endTime
                    };
                    await reqAddWork(addData);
                    ElMessage.success('添加成功');
                }
                dialogVisible.value = false;
                fetchWorkList();
            } catch (error) {
                console.error('操作失败', error);
                ElMessage.error('操作失败');
            }
        }
    });
};

const fetchWorkList = async (resetPage: boolean = false) => {
    if (resetPage) {
        currentPage.value = 1;
    }
    try {
        const result = await reqGetWorks(currentPage.value - 1, pageSize.value);
        if (result) {
            tableData.value = result.workList || [];
            total.value = result.count || 0;
        }
    } catch (error) {
        console.error("获取作业列表失败", error);
        ElMessage.error('获取作业列表失败');
    }
};

const handleSearch = () => {
    searchForm.workId = null;
    searchDialogVisible.value = true;
};

const handleSearchSubmit = async () => {
    if (!searchFormRef.value) return;
    await searchFormRef.value.validate(async (valid: boolean) => {
        if (valid && searchForm.workId !== null) {
            try {
                const work = await reqGetWork(searchForm.workId);
                if (work) {
                    tableData.value = [work];
                    total.value = 1;
                    currentPage.value = 1;
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

const handleRefresh = () => {
    searchForm.workId = null;
    fetchWorkList(true);
};

const handleExport = async () => {
    try {
        // First get page one and total count
        const firstPage = await reqGetWorks(0, 100);
        
        if (!firstPage || !firstPage.count) {
            ElMessage.warning('暂无数据可导出');
            return;
        }

        const totalPages = Math.ceil(firstPage.count / 100);
        const allWorks = [...(firstPage.workList || [])];

        // If there are more pages, fetch them all
        if (totalPages > 1) {
            const otherPagesPromises = Array.from({ length: totalPages - 1 }, (_, i) =>
                reqGetWorks(i + 1, 100)
            );

            const results = await Promise.all(otherPagesPromises);
            results.forEach(result => {
                if (result?.workList) {
                    allWorks.push(...result.workList);
                }
            });
        }

        // Prepare Excel data
        const excelData = allWorks.map(work => ({
            '编号': work.id,
            // '作业名称': generateWorkName(work),
            '采摘产品': getProduceName(work.produceId),
            '开始时间': formatDate(work.startTime),
            '结束时间': formatDate(work.endTime),
            '采摘量': work.dataValue ? `${work.dataValue}${formatUnit(work.unit)}` : '-',
            '状态': statusText(work.status)
        }));

        // Create workbook
        const wb = XLSX.utils.book_new();
        const ws = XLSX.utils.json_to_sheet(excelData);
        XLSX.utils.book_append_sheet(wb, ws, '作业记录');

        // Export Excel
        const fileName = `作业记录_${dayjs().format('YYYY-MM-DD')}.xlsx`;
        XLSX.writeFile(wb, fileName);

        ElMessage.success(`成功导出${allWorks.length}条记录`);
    } catch (error) {
        console.error('导出失败', error);
        ElMessage.error('导出失败');
    }
};

onMounted(async () => {
    await fetchProduceList();
    fetchWorkList();
});

const formatDate = (timestamp?: number): string => {
    return timestamp ? dayjs(timestamp).format("YYYY-MM-DD HH:mm:ss") : "N/A";
};

const statusText = (status: number): string => {
    return WorkStatusMap[status as keyof typeof WorkStatusMap] || "未知";
};

const statusTag = (status: number): string => {
    const tagMap = {
        [WorkStatus.NOT_STARTED]: 'info',
        [WorkStatus.ONGOING]: 'warning',
        [WorkStatus.FINISHED]: 'success',
        [WorkStatus.CANCELED]: 'danger',
        [WorkStatus.DELETED]: 'info'
    };
    return tagMap[status as keyof typeof tagMap] || 'info';
};

const formatUnit = (unit: number): string => {
    return ScaleUnitMap[unit as keyof typeof ScaleUnitMap] || 'unknown';
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
