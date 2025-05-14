<script setup lang="ts">

import TableView from "@/views/TableView.vue";
import { reactive, ref } from "vue";

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
</script>

<template>
  <TableView
    :view-fun="showViewDialog"
  >

  </TableView>
  <el-drawer
    v-model="displayControl.viewDialog"
    title="患者详情"
    direction="rtl"
    :before-close="hideViewDialog"
    size="800"
  >

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="hideViewDialog">关闭</el-button>
      </div>
    </template>
  </el-drawer>
</template>

<style scoped lang="scss">

</style>