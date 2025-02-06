<template>
    <Header />
    <div>
        <el-row justify="center">
            <el-col :span="30">
                <!-- 操作面板 -->
                <div class="operation-wrapper">
                    <el-card class="operation-panel">
                        <div class="panel-header">
                            <span class="panel-title">农作物管理</span>
                            <div class="panel-buttons">
                                <el-button 
                                    type="primary" 
                                    :icon="Plus"
                                    @click="handleAdd"
                                >
                                    添加农作物
                                </el-button>
                            </div>
                        </div>
                    </el-card>
                </div>

                <!-- 农作物表格 -->
                <el-table :data="tableData" border style="width: 100%">
                    <el-table-column prop="id" label="ID" width="100" />
                    <el-table-column prop="name" label="名称" width="180" />
                    <el-table-column prop="status" label="状态" width="120">
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
                    <el-table-column label="操作" width="120" fixed="right">
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

        <!-- 农作物表单对话框 -->
        <el-dialog 
            :title="isEdit ? '编辑农作物' : '添加农作物'" 
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
    </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from "vue";
import type { ProduceVO, ProduceAddVO, ProduceUpdateVO } from "@/api/models";
import { reqGetProduces, reqAddProduce, reqUpdateProduce } from "@/api/produce";
import { ElMessage } from 'element-plus';
import { Plus } from '@element-plus/icons-vue';
import dayjs from "dayjs";

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

const fetchProduceList = async () => {
    try {
        const result = await reqGetProduces(currentPage.value - 1, pageSize.value);
        tableData.value = result?.produceList || [];
        total.value = result?.count || 0;
    } catch (error) {
        console.error("获取农作物列表失败", error);
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
</style>
