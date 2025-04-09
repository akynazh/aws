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
                                电子秤管理
                            </span>
                            <div class="panel-buttons">
                                <el-button 
                                v-if="store.roles?.includes(UserRole.ADMIN)"
                                    type="primary" 
                                    :icon="Plus"
                                    @click="handleAdd"
                                >
                                    添加电子秤
                                </el-button>
                                <el-button 
                                    type="primary" 
                                    :icon="Search"
                                    @click="handleSearch"
                                >
                                    查询电子秤
                                </el-button>
                            </div>
                        </div>
                    </el-card>
                </div>

                <!-- 电子秤表格 -->
                <el-table 
                    :data="tableData" 
                    border 
                    class="custom-table hover-effect"
                    style="width: 100%"
                >
                    <el-table-column prop="id" label="编号" width="80" />
                    <!-- <el-table-column prop="skey" label="密钥" width="120">
                        <template #default="{ row }">
                            <span 
                                @click="toggleSkeyVisibility(row.id)"
                                class="skey-text"
                            >
                                {{ skeyVisibility[row.id] ? row.skey : '********' }}
                            </span>
                        </template>
                    </el-table-column> -->
                    <el-table-column prop="sid" label="客户端号" width="120">
                        <template #default="{ row }">
                            <span 
                                class="sid-text"
                                @click="toggleSidVisibility(row.id)"
                            >
                                {{ sidVisibility[row.id] ? row.sid : formatSid(row.sid) }}
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="model" label="型号" width="80" />
                    <el-table-column label="量程" width="80">
                        <template #default="{ row }">
                            {{ `${row.minCapacity}-${row.maxCapacity}${ScaleUnitMap[row.unit]}` }}
                        </template>
                    </el-table-column>
                    <el-table-column label="显示分度值" width="80">
                        <template #default="{ row }">
                            {{ `${row.displayInterval}${ScaleUnitMap[row.unitDv]}` }}
                        </template>
                    </el-table-column>
                    <el-table-column label="检定分度值" width="80">
                        <template #default="{ row }">
                            {{ `${row.verificationInterval}${ScaleUnitMap[row.unitDv]}` }}
                        </template>
                    </el-table-column>
                    <!-- <el-table-column label="通信协议" width="120">
                        <template #default="{ row }">
                            {{ 
                                row.protocol === 0 ? 'MQTT' : 
                                row.protocol === 1 ? 'HTTP' :
                                row.protocol === 2 ? 'CoAP' :
                                row.protocol === 3 ? 'STOMP' : '未知'
                            }}
                        </template>
                    </el-table-column> -->
                    <el-table-column 
                        prop="status" 
                        label="状态" 
                        width="80"
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
                    <el-table-column prop="createTime" label="创建时间" width="120">
                        <template #default="{ row }">
                            {{ formatDate(row.createTime) }}
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="150" fixed="right" v-if="store.roles?.includes(UserRole.ADMIN)">
                        <template #default="{ row }">
                            <el-button type="primary" link @click="handleViewRecords(row)">称重记录</el-button>
                            <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
                        </template>
                    </el-table-column>
                </el-table>

                <!-- 电子秤表单对话框 -->
                <el-dialog 
                    :title="isEdit ? '编辑电子秤' : '添加电子秤'" 
                    v-model="dialogVisible" 
                    width="500px"
                >
                    <el-form 
                        ref="formRef"
                        :model="form"
                        :rules="rules"
                        label-width="120px"
                    >
                        <template v-if="!isEdit">
                            <el-form-item label="密钥" prop="skey">
                                <el-input v-model="form.skey" placeholder="请输入密钥" />
                            </el-form-item>
                            <el-form-item label="型号" prop="model">
                                <el-input v-model="form.model" placeholder="请输入型号" />
                            </el-form-item>
                            <el-form-item label="最小量程" prop="minCapacity">
                                <el-input-number v-model="form.minCapacity" :min="0" />
                            </el-form-item>
                            <el-form-item label="最大量程" prop="maxCapacity">
                                <el-input-number v-model="form.maxCapacity" :min="0" />
                            </el-form-item>
                            <el-form-item label="单位" prop="unit">
                                <el-select v-model="form.unit">
                                    <el-option 
                                        v-for="(value, key) in ScaleUnitMap" 
                                        :key="key"
                                        :label="value"
                                        :value="Number(key)"
                                    />
                                </el-select>
                            </el-form-item>
                            <el-form-item label="检定分度值" prop="verificationInterval">
                                <el-input-number v-model="form.verificationInterval" :min="0" />
                            </el-form-item>
                            <el-form-item label="显示分度值" prop="displayInterval">
                                <el-input-number v-model="form.displayInterval" :min="0" />
                            </el-form-item>
                            <el-form-item label="分度值单位" prop="unitDv">
                                <el-select v-model="form.unitDv">
                                    <el-option 
                                        v-for="(value, key) in ScaleUnitMap" 
                                        :key="key"
                                        :label="value"
                                        :value="Number(key)"
                                    />
                                </el-select>
                            </el-form-item>
                            <!-- <el-form-item label="通信协议" prop="protocol">
                                <el-select v-model="form.protocol">
                                    <el-option label="MQTT" :value="0" />
                                    <el-option label="HTTP" :value="1" />
                                    <el-option label="COAP" :value="2" />
                                    <el-option label="STOMP" :value="3" />
                                </el-select>
                            </el-form-item> -->
                        </template>
                        <template v-else>
                            <el-form-item label="状态" prop="status">
                                <el-select v-model="form.status">
                                    <el-option 
                                        v-for="(value, key) in ScaleStatusMap" 
                                        :key="key"
                                        :label="value"
                                        :value="Number(key)"
                                    />
                                </el-select>
                            </el-form-item>
                        </template>
                    </el-form>
                    <template #footer>
                        <el-button @click="dialogVisible = false">取消</el-button>
                        <el-button type="primary" @click="handleSubmit">确定</el-button>
                    </template>
                </el-dialog>

                <!-- 称重记录对话框 -->
                <el-dialog 
                    title="称重记录" 
                    v-model="recordsDialogVisible" 
                    width="800px"
                >
                    <template #header>
                        <div class="weigh-records-header">
                            <span>称重记录</span>
                            <el-button type="primary" @click="handleExport(currentScaleId!)">
                                <el-icon><Download /></el-icon>
                                导出历史称重数据
                            </el-button>
                        </div>
                    </template>
                    <el-table :data="recordsData" border>
                        <!-- <el-table-column prop="id" label="ID" width="80" /> -->
                        <el-table-column prop="workId" label="电子秤编号" width="120" />
                        <el-table-column prop="employeeId" label="员工编号" width="120" />
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

                <!-- 搜索设备对话框 -->
                <el-dialog 
                    title="查询设备" 
                    v-model="searchDialogVisible" 
                    width="400px"
                >
                    <el-form 
                        ref="searchFormRef"
                        :model="searchForm"
                        :rules="searchRules"
                        label-width="100px"
                    >
                        <el-form-item label="设备密钥" prop="key">
                            <el-input v-model="searchForm.key" placeholder="请输入设备密钥" />
                        </el-form-item>
                    </el-form>
                    <template #footer>
                        <el-button @click="searchDialogVisible = false">取消</el-button>
                        <el-button type="primary" @click="handleSearchSubmit">查询</el-button>
                    </template>
                </el-dialog>
            </el-col>
        </el-row>
    </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from "vue";
import { ElMessage } from 'element-plus';
import { Plus, Platform, Search, Download } from '@element-plus/icons-vue';  // Remove Hide and View icons from import since we don't need them anymore
import * as XLSX from 'xlsx';  // Add XLSX import
import type { ScaleVO, RecordVO } from "@/models";
import { reqGetScales, reqAddScale, reqUpdateScale, reqGetRecords, reqGetScaleByKey } from "@/api/weigh";  // 添加 reqGetScale
import { ScaleUnit, ScaleUnitMap, ScaleStatus, ScaleStatusMap } from '@/models/constants/scale';
import dayjs from "dayjs";
import userStore from "@/store/modules/user";
import { UserRole } from '@/models/constants/user';

let store = userStore();

// 表格数据
const tableData = ref<ScaleVO[]>([]);
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);

// 表单相关
const dialogVisible = ref(false);
const isEdit = ref(false);
const formRef = ref();
const form = reactive({
    id: 0,  // 添加 id 字段
    skey: '',
    model: '',
    maxCapacity: 0,
    minCapacity: 0,
    unit: ScaleUnit.KG,
    verificationInterval: 0,
    displayInterval: 0,
    unitDv: 0,
    // protocol: 0,
    status: ScaleStatus.ENABLE
});

// 表单验证规则
const rules = {
    skey: [{ required: true, message: '请输入密钥', trigger: 'blur' }],
    model: [{ required: true, message: '请输入型号', trigger: 'blur' }],
    minCapacity: [{ required: true, message: '请输入最小量程', trigger: 'blur' }],
    maxCapacity: [{ required: true, message: '请输入最大量程', trigger: 'blur' }],
    unit: [{ required: true, message: '请选择单位', trigger: 'change' }],
    verificationInterval: [{ required: true, message: '请输入检定分度值', trigger: 'blur' }],
    displayInterval: [{ required: true, message: '请输入显示分度值', trigger: 'blur' }],
    unitDv: [{ required: true, message: '请输入分度值单位', trigger: 'blur' }],
    // protocol: [{ required: true, message: '请选择通信协议', trigger: 'change' }]
};

// 重置表单
const resetForm = () => {
    if (formRef.value) {
        formRef.value.resetFields();
    }
    form.id = 0;
    form.skey = '';
    form.model = '';
    form.maxCapacity = 0;
    form.minCapacity = 0;
    form.unit = ScaleUnit.KG;
    form.verificationInterval = 0;
    form.displayInterval = 0;
    form.unitDv = 0;
    // form.protocol = 0;
    form.status = ScaleStatus.ENABLE;
};

// 处理添加
const handleAdd = () => {
    isEdit.value = false;
    resetForm();
    dialogVisible.value = true;
};

// 处理编辑
const handleEdit = (row: ScaleVO) => {
    isEdit.value = true;
    // 只需要复制 id 和 status
    form.id = row.id;
    form.status = row.status;
    dialogVisible.value = true;
};

// 处理提交
const handleSubmit = async () => {
    if (!formRef.value) return;
    await formRef.value.validate(async (valid: boolean) => {
        if (valid) {
            try {
                if (isEdit.value) {
                    // 只更新 id 和 status
                    await reqUpdateScale({
                        id: form.id,
                        status: form.status
                    });
                    ElMessage.success('更新成功');
                } else {
                    await reqAddScale(form);
                    ElMessage.success('添加成功');
                }
                dialogVisible.value = false;
                fetchScaleList();
            } catch (error) {
                ElMessage.error('操作失败: ' + error);
            }
        }
    });
};

// 获取电子秤列表
const fetchScaleList = async () => {
    try {
        const result = await reqGetScales(currentPage.value - 1, pageSize.value);
        tableData.value = result?.scaleList || [];
        total.value = result?.count || 0;
    } catch (error) {
        ElMessage.error('获取电子秤列表失败: ' + error);
    }
};

// 称重记录相关
const recordsDialogVisible = ref(false);
const recordsData = ref<RecordVO[]>([]);
const recordsCurrentPage = ref(1);
const recordsPageSize = ref(10);
const recordsTotal = ref(0);
const currentScaleId = ref<number>();

// 查看称重记录
const handleViewRecords = async (row: ScaleVO) => {
    currentScaleId.value = row.id;
    recordsDialogVisible.value = true;
    recordsCurrentPage.value = 1;
    await fetchRecordsList();
};

// 获取称重记录列表
const fetchRecordsList = async () => {
    if (!currentScaleId.value) return;
    try {
      console.log({
            page: recordsCurrentPage.value - 1,
            size: recordsPageSize.value,
            scaleId: currentScaleId.value,
            workId: 0,
            employeeId: 0
        })
        const result = await reqGetRecords({
            page: recordsCurrentPage.value - 1,
            size: recordsPageSize.value,
            scaleId: currentScaleId.value,
            workId: 0,
            employeeId: 0
        });
        console.log(result)
        recordsData.value = result?.recordList || [];
        recordsTotal.value = result?.count || 0;
    } catch (error) {
        console.error("获取称重记录失败", error);
        ElMessage.error('获取称重记录失败: ' + error);
    }
};

const statusText = (status?: number): string => {
    return status !== undefined ? (ScaleStatusMap[status as keyof typeof ScaleStatusMap] || "未知") : "未知";
};

const statusTag = (status?: number): string => {
    if (status === undefined) return 'info';
    const tagMap = {
        [ScaleStatus.DISABLED]: 'warning',
        [ScaleStatus.ENABLE]: 'success',
        [ScaleStatus.DELETED]: 'danger'
    };
    return tagMap[status] || 'info';
};

const formatDate = (timestamp?: number): string => {
    return timestamp ? dayjs(timestamp).format("YYYY-MM-DD HH:mm:ss") : "N/A";
};

// 添加搜索相关的响应式变量
const searchDialogVisible = ref(false);
const searchFormRef = ref();
const searchForm = reactive({
    key: ''
});

const searchRules = {
    key: [{ required: true, message: '请输入设备密钥', trigger: 'blur' }]
};

// 处理搜索按钮点击
const handleSearch = () => {
    searchForm.key = '';
    searchDialogVisible.value = true;
};

// 处理搜索提交
const handleSearchSubmit = async () => {
    if (!searchFormRef.value) return;
    await searchFormRef.value.validate(async (valid: boolean) => {
        if (valid) {
            try {
                const scale = await reqGetScaleByKey(searchForm.key);
                if (scale) {
                    tableData.value = [scale];
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

// 添加状态筛选选项
const statusFilters = Object.entries(ScaleStatusMap).map(([value, label]) => ({
    text: label,
    value: Number(value)
}));

// 添加状态筛选方法
const filterStatus = (value: number, row: ScaleVO) => {
    return row.status === value;
};

// Add skey visibility control
const skeyVisibility = ref<{ [key: number]: boolean }>({});

const toggleSkeyVisibility = (id: number) => {
    skeyVisibility.value[id] = !skeyVisibility.value[id];
};

// Add sid visibility control
const sidVisibility = ref<{ [key: number]: boolean }>({});

const toggleSidVisibility = (id: number) => {
    sidVisibility.value[id] = !sidVisibility.value[id];
};

const formatSid = (sid: string) => {
    if (!sid) return '';
    return sid.slice(0, 10) + '...';
};

// Add export handler function
const handleExport = async (scaleId: number) => {
    try {
        // First get page one and total count
        const firstPage = await reqGetRecords({
            page: 0,
            size: 100,
            workId: -1,
            scaleId: scaleId,
            employeeId: -1
        });
        
        if (!firstPage || !firstPage.count) {
            ElMessage.warning('暂无数据可导出');
            return;
        }

        const totalPages = Math.ceil(firstPage.count / 100);
        const allRecords: RecordVO[] = [...firstPage.recordList];

        // If there are more pages, fetch them all
        if (totalPages > 1) {
            const otherPagesPromises = Array.from({ length: totalPages - 1 }, (_, i) =>
                reqGetRecords({
                    page: i + 1,
                    size: 100,
                    workId: -1,
                    scaleId: scaleId,
                    employeeId: -1
                })
            );

            const results = await Promise.all(otherPagesPromises);
            results.forEach(result => {
                if (result?.recordList) {
                    allRecords.push(...result.recordList);
                }
            });
        }

        // Prepare Excel data
        const excelData = allRecords.map(record => ({
            '编号': record.id,
            '作业编号': record.workId,
            '员工编号': record.employeeId,
            '称重数据': `${record.dataValue}${ScaleUnitMap[record.unit]}`,
            '误差': `±${record.dataErrorMargin}${ScaleUnitMap[record.unit]}`,
            '称重时间': formatDate(record.dataTime)
        }));

        // Create workbook
        const wb = XLSX.utils.book_new();
        const ws = XLSX.utils.json_to_sheet(excelData);
        XLSX.utils.book_append_sheet(wb, ws, '称重记录');

        // Export Excel
        const currentScale = tableData.value.find(scale => scale.id === scaleId);
        const fileName = `称重记录_${currentScale?.model || scaleId}_${dayjs().format('YYYY-MM-DD')}.xlsx`;
        XLSX.writeFile(wb, fileName);

        ElMessage.success(`成功导出${allRecords.length}条记录`);
    } catch (error) {
        console.error('导出失败', error);
        ElMessage.error('导出失败: ' + error);
    }
};

onMounted(() => {
    fetchScaleList();
});
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

.weigh-records-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    font-size: 18px;
    font-weight: bold;
}

.skey-text {
    cursor: pointer;
    color: var(--el-color-primary);
    transition: opacity 0.2s;
}

.skey-text:hover {
    opacity: 0.8;
}

.sid-container {
    display: inline-flex;
    align-items: center;
    gap: 4px;
}

.sid-ellipsis {
    color: var(--el-color-primary);
    cursor: pointer;
    transition: opacity 0.2s;
}

.sid-ellipsis:hover {
    opacity: 0.8;
}

.sid-text {
    cursor: pointer;
    color: var(--el-color-primary);
    transition: opacity 0.2s;
}

.sid-text:hover {
    opacity: 0.8;
}
</style>
