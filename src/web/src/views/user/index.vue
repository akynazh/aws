<template>
    <Header/>

    <div>
        <el-row justify="center">
            <el-col :span="30">
        <!-- 用户表格 -->
        <el-table :data="tableData" border style="width: 100%">
            <el-table-column prop="id" label="ID" width="100" />
            <el-table-column prop="uid" label="用户身份证" width="180" />
            <el-table-column prop="name" label="姓名" width="180" />
            <el-table-column prop="roles" label="角色" width="250" />
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
        
        </el-table>

                <!-- 分页 -->
                <el-pagination
            v-model:current-page="currentPage"
            v-model:page-size="pageSize"
            :total="total"
            :page-sizes="[10, 20, 50, 100]"
            layout="total, sizes, prev, pager, next, jumper"
            @size-change="fetchUserList"
            @current-change="fetchUserList"
            style="margin-top: 20px; text-align: right;"
        />
    </el-col>
    </el-row>


    </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import type { UserVo } from "@/api/models";
import { reqGetUserList } from "@/api/user";
import dayjs from "dayjs";

const tableData = ref<UserVo[]>([]);
const currentPage = ref(1); // 当前页码
const pageSize = ref(10); // 每页条数
const total = ref(0); // 总条数

// 获取用户列表
const fetchUserList = async () => {
    try {
        const result = await reqGetUserList(currentPage.value - 1, pageSize.value);
        tableData.value = result.data?.userList || [];
        total.value = result.data?.count || 0;
    } catch (error) {
        console.error("获取用户列表失败", error);
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

// 显示状态文本
const statusText = (status?: number): string => {
    return status === 0 ? "禁用" : status === 1 ? "启用" : "已删除";
};

// 显示状态颜色
const statusTag = (status?: number): string => {
    return status === 0 ? "danger" : status === 1 ? "success" : "info";
};
</script>