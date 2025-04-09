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
                                <el-icon class="title-icon"><DocumentChecked /></el-icon>
                                待办管理
                            </span>
                        </div>
                    </el-card>
                </div>

                <!-- 待办表格 -->
                <el-table 
                    :data="tableData" 
                    border 
                    class="custom-table hover-effect"
                    style="width: 100%"
                >
                    <el-table-column prop="id" label="编号" width="80" />
                    <el-table-column prop="produceId" label="果实编号" width="120">
                        <template #default="{ row }">
                            {{ row.produceId || '-' }}
                        </template>
                    </el-table-column>
                    <el-table-column prop="produceName" label="果实名称" width="120">
                        <template #default="{ row }">
                            {{ row.produceName || '-' }}
                        </template>
                    </el-table-column>
                    <el-table-column prop="image" label="果实图像" width="120">
                        <template #default="{ row }">
                            <div v-if="row.image" class="image-preview" @click="handlePreview(row.image)">
                                <el-image
                                    class="produce-image"
                                    :src="row.image"
                                    fit="cover"
                                    style="width: 50px; height: 50px;"
                                />
                            </div>
                            <span v-else>-</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="employeeId" label="员工编号" width="120" />
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
                    <el-table-column label="操作" width="200" fixed="right">
                        <template #default="{ row }">
                            <el-button 
                                type="primary" 
                                link 
                                @click="handleEdit(row)"
                            >
                                提交
                            </el-button>
                            <el-button 
                                type="danger" 
                                link 
                                @click="handleDrop(row)"
                            >
                                丢弃
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>

                <!-- 编辑表单对话框 -->
                <el-dialog 
                    title="提交待办" 
                    v-model="dialogVisible" 
                    width="500px"
                >
                    <el-form 
                        ref="formRef"
                        :model="form"
                        :rules="rules"
                        label-width="120px"
                    >
                        <el-form-item label="选择果实" prop="produceId">
                            <el-select 
                                v-model="form.produceId" 
                                placeholder="请选择果实(如果置空将尝试识别果实图像)"
                                filterable
                                @change="handleProduceChange"
                            >
                                <el-option 
                                    v-for="item in produceList" 
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id"
                                />
                            </el-select>
                        </el-form-item>
                        <el-form-item label="果实名称" prop="produceName">
                            <el-input v-model="form.produceName" disabled />
                        </el-form-item>
                        <el-form-item label="果实编号" prop="produceName">
                            <el-input v-model="form.produceId" disabled />
                        </el-form-item>
                        <el-form-item label="果实图像" prop="image">
                            <el-input v-model="form.image" disabled />
                        </el-form-item>
                        <el-form-item label="员工编号">
                            <el-input v-model="form.employeeId" disabled />
                        </el-form-item>
                        <el-form-item label="电子秤编号">
                            <el-input v-model="form.scaleId" disabled />
                        </el-form-item>
                        <el-form-item label="称重数据">
                            <el-input :value="`${form.dataValue}${ScaleUnitMap[form.unit]}`" disabled />
                        </el-form-item>
                        <el-form-item label="误差">
                            <el-input :value="`±${form.dataErrorMargin}${ScaleUnitMap[form.unit]}`" disabled />
                        </el-form-item>
                        <el-form-item label="称重时间">
                            <el-input :value="formatDate(form.dataTime)" disabled />
                        </el-form-item>
                    </el-form>
                    <template #footer>
                        <el-button @click="dialogVisible = false">取消</el-button>
                        <el-button type="primary" @click="handleSubmit">确定</el-button>
                    </template>
                </el-dialog>

                <!-- 添加图片预览弹框 -->
                <el-dialog
                    v-model="previewVisible"
                    width="500px"
                    :show-close="true"
                    class="preview-dialog"
                >
                    <img 
                        v-if="previewImage" 
                        :src="previewImage" 
                        style="width: 100%; height: auto;"
                    />
                </el-dialog>

                <Pagination 
                    v-model:current-page="currentPage" 
                    v-model:page-size="pageSize" 
                    :total="total"
                    @size-change="fetchTodoList" 
                    @current-change="fetchTodoList"
                />
            </el-col>
        </el-row>
    </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, ElMessageBox } from 'element-plus';
import { DocumentChecked } from '@element-plus/icons-vue';
import type { TodoVO } from "@/models/todo";
import type { ProduceVO } from "@/models";
import { reqGetTodos, reqHandleTodoRecord, dropTodoRecord } from "@/api/weigh";
import { reqGetProduces } from "@/api/produce";
import { ScaleUnitMap } from '@/models/constants/scale';
import dayjs from "dayjs";

// 表格数据
const tableData = ref<TodoVO[]>([]);
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);

// 获取待办列表
const fetchTodoList = async () => {
    try {
        const result = await reqGetTodos({
            page: currentPage.value - 1,
            size: pageSize.value
        });
        tableData.value = result?.todoVOList || [];
        total.value = result?.count || 0;
    } catch (error) {
        ElMessage.error('获取待办列表失败: ' + error);
    }
};

// 表单相关
const dialogVisible = ref(false);
const formRef = ref();
const form = reactive({
    id: 0,
    produceId: 0,
    produceName: '',
    image: '',
    employeeId: 0,
    scaleId: 0,
    dataValue: 0,
    dataErrorMargin: 0,
    unit: 0,
    dataTime: 0
});

// 表单验证规则
const rules = {
    produceId: [{ required: false, message: '请选择果实', trigger: 'blur' }],
    produceName: [{ required: false, message: '请选择果实', trigger: 'blur' }]
};

// 果实列表数据
const produceList = ref<ProduceVO[]>([]);

// 获取果实列表
const fetchProduceList = async () => {
    try {
        const result = await reqGetProduces(0, 100);  // 获取前100个果实
        produceList.value = result?.produceList || [];
    } catch (error) {
        ElMessage.error('获取果实列表失败: ' + error);
    }
};

// 处理果实选择变化
const handleProduceChange = (value: number) => {
    const selectedProduce = produceList.value.find(p => p.id === value);
    if (selectedProduce) {
        form.produceName = selectedProduce.name;
        form.produceId = selectedProduce.id;
    }
};

// 处理编辑
const handleEdit = (row: TodoVO) => {
    Object.assign(form, row);
    dialogVisible.value = true;
};

// 处理提交
const handleSubmit = async () => {
    if (!formRef.value) return;
    await formRef.value.validate(async (valid: boolean) => {
        if (valid) {
            try {
                await reqHandleTodoRecord(form);
                ElMessage.success('提交成功');
                dialogVisible.value = false;
                fetchTodoList();
            } catch (error) {
                ElMessage.error('提交失败: ' + error);
            }
        }
    });
};

// 处理丢弃
const handleDrop = (row: TodoVO) => {
    ElMessageBox.confirm(
        '确定要丢弃这条待办记录吗？',
        '警告',
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }
    ).then(async () => {
        try {
            await dropTodoRecord(row.id);
            ElMessage.success('丢弃成功');
            fetchTodoList();
        } catch (error) {
            ElMessage.error('丢弃失败: ' + error);
        }
    }).catch(() => {
        ElMessage.info('已取消丢弃');
    });
};

const formatDate = (timestamp?: number): string => {
    return timestamp ? dayjs(timestamp).format("YYYY-MM-DD HH:mm:ss") : "N/A";
};

// 图片预览相关
const previewVisible = ref(false);
const previewImage = ref('');

const handlePreview = (imageUrl: string) => {
    previewImage.value = imageUrl;
    previewVisible.value = true;
};

onMounted(() => {
    fetchTodoList();
    fetchProduceList();  // 添加获取果实列表
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

.el-image {
    cursor: pointer;
    border-radius: 4px;
    transition: transform 0.2s;
}

.el-image:hover {
    transform: scale(1.05);
}

.produce-image {
    position: relative;
    border-radius: 4px;
    cursor: pointer;
    transition: all 0.3s ease;
}

/* .produce-image:hover {
    position: absolute;
    width: 200px !important;
    height: 200px !important;
    transform: translate(-50%, -50%);
    z-index: 1000;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
} */

.image-preview {
    cursor: pointer;
}

.preview-dialog :deep(.el-dialog__body) {
    padding: 0;
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>
