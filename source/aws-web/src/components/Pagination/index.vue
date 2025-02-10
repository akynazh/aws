<template>
  <div class="pagination-wrapper">
    <el-pagination
      :current-page="currentPage"
      :page-size="pageSize"
      :total="total"
      :page-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="onSizeChange"
      @current-change="onCurrentChange"
    />
  </div>
</template>

<script setup lang="ts">
import { defineProps, defineEmits } from "vue";

const props = defineProps({
  currentPage: {
    type: Number,
    required: true,
  },
  pageSize: {
    type: Number,
    required: true,
  },
  total: {
    type: Number,
    required: true,
  },
});

const emits = defineEmits(["update:current-page", "update:page-size", "size-change", "current-change"]);

const onSizeChange = (size: number) => {
  emits("update:page-size", size);
  emits("size-change", size);
};

const onCurrentChange = (page: number) => {
  emits("update:current-page", page);
  emits("current-change", page);
};
</script>

<style scoped>
.pagination-wrapper {
  display: flex;
  justify-content: center;
  margin: 20px 0;
}
</style>
