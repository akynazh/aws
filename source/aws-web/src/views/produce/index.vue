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
                                <el-icon class="title-icon">
                                    <Platform />
                                </el-icon>
                                果实管理
                            </span>
                            <div class="panel-buttons">
                                <el-button v-if="store.roles?.includes(UserRole.ADMIN)" type="primary" :icon="Plus"
                                    @click="handleAdd">
                                    添加果实
                                </el-button>
                                <el-button type="primary" :icon="Search" @click="handleSearch">
                                    查询果实
                                </el-button>
                                <el-button v-if="store.roles?.includes(UserRole.ADMIN)" type="primary" :icon="Download" @click="handleExportAllAnnual">
                                    导出所有果实年产量数据
                                </el-button>
                                <el-button v-if="store.roles?.includes(UserRole.ADMIN)" type="primary" :icon="Download" @click="handleExportAllWork">
                                    导出所有果实分批产量数据
                                </el-button>
                            </div>
                        </div>
                    </el-card>
                </div>

                <!-- 果实表格 -->
                <el-table :data="tableData" border class="custom-table hover-effect" style="width: 100%">
                    <el-table-column prop="id" label="编号" width="100" />
                    <el-table-column prop="name" label="名称" width="120" />
                    <el-table-column prop="status" label="状态" width="120" :filters="statusFilters"
                        :filter-method="filterStatus" filter-placement="bottom-end">
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
                    <el-table-column v-if="store.roles?.includes(UserRole.ADMIN)" label="操作" width="200" fixed="right">
                        <template #default="{ row }">
                            <el-button type="primary" link @click="handleViewAnnualOutput(row)">年产量</el-button>
                            <el-button type="primary" link @click="handleViewWorkOutput(row)">分批产量</el-button>
                            <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
                        </template>
                    </el-table-column>
                </el-table>

                <!-- 分页 -->
                <Pagination v-model:current-page="currentPage" v-model:page-size="pageSize" :total="total"
                    @size-change="fetchProduceList" @current-change="fetchProduceList" />
            </el-col>
        </el-row>

        <!-- 果实表单对话框 -->
        <el-dialog :title="isEdit ? '编辑果实' : '添加果实'" v-model="dialogVisible" width="500px">
            <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
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
        <el-dialog title="查询果实" v-model="searchDialogVisible" width="400px">
            <el-form ref="searchFormRef" :model="searchForm" :rules="searchRules" label-width="100px">
                <el-form-item label="名称" prop="name">
                    <el-input v-model="searchForm.name" placeholder="请输入果实名称" />
                </el-form-item>
            </el-form>
            <template #footer>
                <el-button @click="searchDialogVisible = false">取消</el-button>
                <el-button type="primary" @click="handleSearchSubmit">查询</el-button>
            </template>
        </el-dialog>

        <!-- 年产量对话框 -->
        <el-dialog title="年产量数据" v-model="annualOutputDialogVisible" width="600px">
            <template #header>
                <div class="dialog-header">
                    <div class="dialog-title">
                        <span>年产量 - {{ currentProduce?.name }}</span>
                        <el-button-group class="view-toggle">
                            <el-button 
                                :type="showAnnualChart ? 'primary' : ''" 
                                @click="showAnnualChart = true"
                            >
                                <el-icon><TrendCharts /></el-icon>
                            </el-button>
                            <el-button 
                                :type="!showAnnualChart ? 'primary' : ''" 
                                @click="showAnnualChart = false"
                            >
                                <el-icon><Grid /></el-icon>
                            </el-button>
                            <el-button 
                                v-if="showAnnualChart"
                                @click="downloadChart('annual')"
                            >
                                <el-icon><Download /></el-icon>
                            </el-button>
                        </el-button-group>
                    </div>
                    <el-button type="primary" @click="handleExportAnnual">
                        <el-icon>
                            <Download />
                        </el-icon>
                        导出年产量数据
                    </el-button>
                </div>
            </template>
            
            <!-- 切换显示图表或表格 -->
            <div v-if="showAnnualChart" class="chart-container">
                <v-chart ref="annualChartRef" class="chart" :option="annualChartOption" autoresize />
            </div>
            <el-table v-else :data="annualOutputData" border class="hover-effect">
                <el-table-column prop="name" label="果实名称" width="150" />
                <el-table-column prop="year" label="年份" width="120" />
                <el-table-column label="产量" width="150">
                    <template #default="{ row }">
                        {{ `${row.dataValue}${row.unit}` }}
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>

        <!-- 分批产量对话框 -->
        <el-dialog title="分批产量数据" v-model="workOutputDialogVisible" width="600px">
            <template #header>
                <div class="dialog-header">
                    <span>分批产量 - {{ currentProduce?.name }}</span>
                    <el-button-group class="view-toggle">
                        <el-button 
                            :type="showWorkChart ? 'primary' : ''" 
                            @click="showWorkChart = true"
                        >
                            <el-icon><TrendCharts /></el-icon>
                        </el-button>
                        <el-button 
                            :type="!showWorkChart ? 'primary' : ''" 
                            @click="showWorkChart = false"
                        >
                            <el-icon><Grid /></el-icon>
                        </el-button>
                        <el-button 
                            v-if="showWorkChart"
                            @click="downloadChart('work')"
                        >
                            <el-icon><Download /></el-icon>
                        </el-button>
                    </el-button-group>
                    <el-button type="primary" @click="handleExportWork">
                        <el-icon>
                            <Download />
                        </el-icon>
                        导出分批产量数据
                    </el-button>
                </div>
            </template>
            <div v-if="showWorkChart" class="chart-container">
                <v-chart ref="workChartRef" class="chart" :option="workChartOption" autoresize />
            </div>
            <el-table v-else :data="workOutputData" border class="hover-effect">
                <el-table-column prop="name" label="果实名称" width="150" />
                <el-table-column prop="workId" label="作业编号" width="120" />
                <el-table-column label="产量" width="150">
                    <template #default="{ row }">
                        {{ `${row.dataValue}${row.unit}` }}
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>
    </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed } from "vue";
import type { ProduceVO, ProduceAddVO, ProduceUpdateVO, ProduceAnnualOutputVO, ProduceWorkOutputVO } from "@/models";
import { reqGetProduces, reqAddProduce, reqUpdateProduce, reqGetProduce, reqGetProduceByName, reqGetProduceAnnualOutput, reqGetProduceWorkOutput } from "@/api/produce";
import { ElMessage } from 'element-plus';
import { Platform, Plus, Search, Download, TrendCharts, Grid } from '@element-plus/icons-vue';
import dayjs from "dayjs";
import userStore from "@/store/modules/user";
import { UserRole } from '@/models/constants/user';
import * as XLSX from 'xlsx';
import VChart from 'vue-echarts';
import { use } from 'echarts/core';
import { LineChart } from 'echarts/charts';
import {
  TitleComponent,
  TooltipComponent,
  GridComponent,
  LegendComponent
} from 'echarts/components';
import { CanvasRenderer } from 'echarts/renderers';

// Register ECharts components
use([
  CanvasRenderer,
  LineChart,
  TitleComponent,
  TooltipComponent,
  GridComponent,
  LegendComponent
]);

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

// Add new state variables
const annualOutputDialogVisible = ref(false);
const workOutputDialogVisible = ref(false);
const currentProduce = ref<ProduceVO>();
const annualOutputData = ref<ProduceAnnualOutputVO[]>([]);
const workOutputData = ref<ProduceWorkOutputVO[]>([]);

// View annual output handler
const handleViewAnnualOutput = async (row: ProduceVO) => {
    currentProduce.value = row;
    annualOutputDialogVisible.value = true;
    await fetchAnnualOutput();
};

// View work output handler
const handleViewWorkOutput = async (row: ProduceVO) => {
    currentProduce.value = row;
    workOutputDialogVisible.value = true;
    await fetchWorkOutput();
};

// Fetch annual output data
const fetchAnnualOutput = async () => {
    if (!currentProduce.value) return;
    try {
        const result: ProduceAnnualOutputVO[] = await reqGetProduceAnnualOutput(currentProduce.value.id);
        if (result) {
            annualOutputData.value = result;
        }
    } catch (error) {
        console.error('获取年产量失败', error);
        ElMessage.error('获取年产量失败');
    }
};

// Fetch work output data
const fetchWorkOutput = async () => {
    if (!currentProduce.value) return;
    try {
        const result: ProduceWorkOutputVO[] = await reqGetProduceWorkOutput(currentProduce.value.id);
        if (result) {
            workOutputData.value = result;
        }
    } catch (error) {
        console.error('获取分批产量失败', error);
        ElMessage.error('获取分批产量失败');
    }
};

// Export handlers
const handleExportAnnual = () => {
    if (!currentProduce.value || !annualOutputData.value.length) {
        ElMessage.warning('暂无数据可导出');
        return;
    }

    try {
        const excelData = annualOutputData.value.map(data => ({
            '果实名称': data.name,
            '年份': data.year,
            '产量': `${data.dataValue}${data.unit}`
        }));

        const wb = XLSX.utils.book_new();
        const ws = XLSX.utils.json_to_sheet(excelData);
        XLSX.utils.book_append_sheet(wb, ws, '年产量');

        XLSX.writeFile(wb, `${currentProduce.value.name}_年产量_${formatDate(Date.now())}.xlsx`);
        ElMessage.success('成功导出年产量数据');
    } catch (error) {
        console.error('导出失败', error);
        ElMessage.error('导出失败');
    }
};

const handleExportWork = () => {
    if (!currentProduce.value || !workOutputData.value.length) {
        ElMessage.warning('暂无数据可导出');
        return;
    }

    try {
        const excelData = workOutputData.value.map(data => ({
            '果实名称': data.name,
            '作业编号': data.workId,
            '产量': `${data.dataValue}${data.unit}`
        }));

        const wb = XLSX.utils.book_new();
        const ws = XLSX.utils.json_to_sheet(excelData);
        XLSX.utils.book_append_sheet(wb, ws, '分批产量');

        XLSX.writeFile(wb, `${currentProduce.value.name}_分批产量_${formatDate(Date.now())}.xlsx`);
        ElMessage.success('成功导出分批产量数据');
    } catch (error) {
        console.error('导出失败', error);
        ElMessage.error('导出失败');
    }
};

// Add new handlers for batch export
const handleExportAllAnnual = async () => {
    try {
        // Get first page and total count
        const firstPage = await reqGetProduces(0, 10);
        if (!firstPage?.count) {
            ElMessage.warning('暂无果实数据');
            return;
        }

        // Calculate total pages and fetch all produces
        const totalPages = Math.ceil(firstPage.count / 100);
        const allProduces = [...(firstPage.produceList || [])];

        if (totalPages > 1) {
            const otherPagesPromises = Array.from({ length: totalPages - 1 }, (_, i) =>
                reqGetProduces(i + 1, 100)
            );

            const results = await Promise.all(otherPagesPromises);
            results.forEach(result => {
                if (result?.produceList) {
                    allProduces.push(...result.produceList);
                }
            });
        }

        // Fetch annual output for each produce
        const allDataPromises = allProduces.map(produce => 
            reqGetProduceAnnualOutput(produce.id)
        );

        const results = await Promise.all(allDataPromises);
        const allData = results.flat().filter(Boolean);

        if (!allData.length) {
            ElMessage.warning('暂无年产量数据可导出');
            return;
        }

        // Prepare and export Excel data
        const excelData = allData.map(data => ({
            '果实名称': data.name,
            '年份': data.year,
            '产量': `${data.dataValue}${data.unit}`
        }));

        const wb = XLSX.utils.book_new();
        const ws = XLSX.utils.json_to_sheet(excelData);
        XLSX.utils.book_append_sheet(wb, ws, '所有果实年产量');

        XLSX.writeFile(wb, `果实年产量汇总_${formatDate(Date.now())}.xlsx`);
        ElMessage.success(`成功导出${allData.length}条记录`);
    } catch (error) {
        console.error('导出失败', error);
        ElMessage.error('导出失败');
    }
};

const handleExportAllWork = async () => {
    try {
        // Get first page and total count
        const firstPage = await reqGetProduces(0, 10);
        if (!firstPage?.count) {
            ElMessage.warning('暂无果实数据');
            return;
        }

        // Calculate total pages and fetch all produces
        const totalPages = Math.ceil(firstPage.count / 100);
        const allProduces = [...(firstPage.produceList || [])];

        if (totalPages > 1) {
            const otherPagesPromises = Array.from({ length: totalPages - 1 }, (_, i) =>
                reqGetProduces(i + 1, 100)
            );

            const results = await Promise.all(otherPagesPromises);
            results.forEach(result => {
                if (result?.produceList) {
                    allProduces.push(...result.produceList);
                }
            });
        }

        // Fetch work output for each produce
        const allDataPromises = allProduces.map(produce => 
            reqGetProduceWorkOutput(produce.id)
        );

        const results = await Promise.all(allDataPromises);
        const allData = results.flat().filter(Boolean);

        if (!allData.length) {
            ElMessage.warning('暂无分批产量数据可导出');
            return;
        }

        // Prepare and export Excel data
        const excelData = allData.map(data => ({
            '果实名称': data.name,
            '作业编号': data.workId,
            '产量': `${data.dataValue}${data.unit}`
        }));

        const wb = XLSX.utils.book_new();
        const ws = XLSX.utils.json_to_sheet(excelData);
        XLSX.utils.book_append_sheet(wb, ws, '所有果实分批产量');

        XLSX.writeFile(wb, `果实分批产量汇总_${formatDate(Date.now())}.xlsx`);
        ElMessage.success(`成功导出${allData.length}条记录`);
    } catch (error) {
        console.error('导出失败', error);
        ElMessage.error('导出失败');
    }
};

// Add view toggle state
const showAnnualChart = ref(true);
const showWorkChart = ref(true);

// Add chart options
const annualChartOption = computed(() => ({
  tooltip: {
    trigger: 'axis'
  },
  xAxis: {
    type: 'category',
    data: annualOutputData.value.map(item => item.year),
    name: '年份'
  },
  yAxis: {
    type: 'value',
    name: `产量(${annualOutputData.value[0]?.unit || ''})`
  },
  series: [{
    data: annualOutputData.value.map(item => item.dataValue),
    type: 'line',
    name: '产量',
    smooth: true,
    label: {
      show: true,
      formatter: (params: any) => {
        return `${params.value}${annualOutputData.value[0]?.unit || ''}`;
      }
    }
  }]
}));

const workChartOption = computed(() => ({
  tooltip: {
    trigger: 'axis'
  },
  xAxis: {
    type: 'category',
    data: workOutputData.value.map(item => item.workId),
    name: '作业编号'
  },
  yAxis: {
    type: 'value',
    name: `产量(${workOutputData.value[0]?.unit || ''})`
  },
  series: [{
    data: workOutputData.value.map(item => item.dataValue),
    type: 'line',
    name: '产量',
    smooth: true,
    label: {
      show: true,
      formatter: (params: any) => {
        return `${params.value}${workOutputData.value[0]?.unit || ''}`;
      }
    }
  }]
}));

// Add chart refs for downloading
const annualChartRef = ref<any>();
const workChartRef = ref<any>();

// Add chart download function
const downloadChart = (type: 'annual' | 'work') => {
  const chartRef = type === 'annual' ? annualChartRef.value : workChartRef.value;
  if (!chartRef) return;
  
  try {
    // Get chart base64 image
    const dataUrl = chartRef.getDataURL({
      type: 'png',
      pixelRatio: 2,
      backgroundColor: '#fff'
    });

    // Create download link
    const link = document.createElement('a');
    link.download = `${currentProduce.value?.name}_${type === 'annual' ? '年产量' : '分批产量'}_${formatDate(Date.now())}.png`;
    link.href = dataUrl;
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);

    ElMessage.success('图表下载成功');
  } catch (error) {
    console.error('图表下载失败', error);
    ElMessage.error('图表下载失败');
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

.dialog-title {
  display: flex;
  align-items: center;
  gap: 16px;
}

.view-toggle {
  margin-left: 16px;
  display: flex;
  align-items: center;
}

.view-toggle .el-button {
  padding: 8px 12px;
}

.chart-container {
  height: 400px;
  width: 100%;
}

.chart {
  height: 100%;
  width: 100%;
}
</style>