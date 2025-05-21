<script setup lang="ts">

import TableView from "@/views/TableView.vue";
import { reactive, ref } from "vue";
import MainTable from "@/components/MainTable.vue";
import { TableColumnHandle } from "#/conponent.ts";
import { datetimeFormatHandler } from "@/utils/util.ts";

const displayControl = reactive({
  viewDialog: false,
});
const currentView = ref({})

const showViewDialog = (index,row) => {
  displayControl.viewDialog = true;
  currentView.value = row;
};

const hideViewDialog = () => {
  displayControl.viewDialog = false;
};

const columnBtns: TableColumnHandle[] = [
  {
    format: (row): string => {
      return "查看";
    },
    handleFun: (index: number, row: any) => {
      showViewDialog(index, row);
    },
  },
];

</script>



<template>
  <MainTable
    :row-btns="columnBtns"
  >

  </MainTable>
  <el-drawer
    v-model="displayControl.viewDialog"
    title="患者详情"
    direction="rtl"
    :before-close="hideViewDialog"
    size="800"
  >
    <el-descriptions
      :title="'基础资料（'+currentView['id']+'）'"
      :column="3 "
      direction="vertical"
      border
    >
      <el-descriptions-item
        :rowspan="4"
        :width="140"
        label="照片"
        align="center"
      >
        <el-image
          style="width: 100px; height: 100px"
          src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
        />
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            姓名
          </div>
        </template>
        {{ currentView['name'] }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            性别
          </div>
        </template>
        {{ currentView['name'] }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            手机号
          </div>
        </template>
        {{ currentView['phone'] }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            医院
          </div>
        </template>
        {{ currentView['hospitalName'] }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            权限
          </div>
        </template>
        {{ currentView['roles'] }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            注册时间
          </div>
        </template>
        {{ currentView['createDatetime'] }}
      </el-descriptions-item>
    </el-descriptions>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="hideViewDialog">关闭</el-button>
      </div>
    </template>
  </el-drawer>
</template>

<style scoped lang="scss">

</style>