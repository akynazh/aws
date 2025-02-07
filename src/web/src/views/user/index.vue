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
                    <el-table-column prop="id" label="ID" width="100" />
                    <el-table-column prop="uid" label="用户身份证" width="180" />
                    <el-table-column prop="name" label="姓名" width="180" />
                    <el-table-column prop="roles" label="角色" width="250">
                        <template #default="{ row }">
                            <span>{{ formatRoles(row.roles) }}</span>
                        </template>
                    </el-table-column>
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
    </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from "vue";
import type { UserVO } from "@/models";
import { reqGetUsers, reqAddUser, reqUpdateUser, reqGetEmployee } from "@/api/user";
import { ElMessage } from 'element-plus';
import { Plus, Search, User } from '@element-plus/icons-vue';
import { UserStatus, UserStatusMap, UserRole, UserRoleMap } from '@/models/constants/user';

import dayjs from "dayjs";

const tableData = ref<UserVO[]>([]);
const currentPage = ref(1); // 当前页码
const pageSize = ref(10); // 每页条数
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
                ElMessage.error('操作失败');
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
                ElMessage.error('查询失败');
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
        ElMessage.error('获取用户列表失败');
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