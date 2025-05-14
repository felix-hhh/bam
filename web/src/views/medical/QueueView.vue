<script setup lang="ts">

import TableView from "@/views/TableView.vue";
import { onMounted, reactive, ref } from "vue";
import { SelectItem, SelectOption, TableColumnHandle } from "#/conponent.ts";
import { MedicalQueue } from "#/entity.ts";
import useAxios from "@/api";

const { sendGet } = useAxios();
const displayControl = reactive({
  viewDialog: false,
  addDialog: false,
});
const currentView = ref({});
const addForm = ref<MedicalQueue>({} as MedicalQueue);

const showViewDialog = (index, row) => {
  displayControl.viewDialog = true;
  currentView.value = row;
};
const doctorOptions = ref<SelectItem[]>([])

/**
 * 显示添加窗口哦
 */
const showAddDialog = (row: any) => {
  displayControl.addDialog = true;
  currentView.value = row;
};

const hideDialog = () => {
  displayControl.viewDialog = false;
  displayControl.addDialog = false;
};

const columnBtns: TableColumnHandle[] = [
  {
    format: (row): string => {
      const status = row["status"];
      switch (status) {
        case "M_Q_S_WAIT":
          return "排队取号";
        default:
          return "";
      }
    },
    handleFun: (index: number, row: any) => {
      showAddDialog(row);
    },
  },
];

const getDoctorList= ()=>{
  sendGet("medical/manage/doctor/list/option")
  .then(req => {
    doctorOptions.value = req;
  })
}

onMounted(()=>{
  getDoctorList();
})
</script>

<template>
  <TableView
    :view-fun="showViewDialog"
    :row-btns="columnBtns"
  >

  </TableView>
  <el-drawer
    v-model="displayControl.addDialog"
    title="排队取号"
    direction="rtl"
    :before-close="hideDialog"
    size="500"
  >
    <el-row class="queue">
      <el-row class="queue_info">
        <el-col :span="8">姓名：{{ currentView["patientName"] }}</el-col>
        <el-col :span="8">年龄：</el-col>
        <el-col :span="8">电话：{{ currentView["patientPhone"] }}</el-col>
      </el-row>
      <el-row class="queue_details">
        <el-row class="queue_detail_title">
          患者情况：
        </el-row>
        <el-row class="queue_details_item">
          <el-form :model="addForm" label-width="80" style="width: 100%" >
            <el-form-item label="血压">
              <el-col :span="11">
                高压
                <el-input class="queue_details_item_input" />
              </el-col>
              <el-col :span="2"></el-col>
              <el-col :span="11">
                低压
                <el-input class="queue_details_item_input" />
              </el-col>
            </el-form-item>
            <el-form-item label="血糖">
              <el-input type="number" />
            </el-form-item>
            <el-form-item label="手术史">
              <el-input type="textarea" rows="4" />
            </el-form-item>
            <el-form-item label="既往史">
              <el-input type="textarea" rows="4" />
            </el-form-item>
            <el-form-item label="康复项目">
              <el-select v-model="addForm.program" placeholder="请选择康复项目">
                <el-option
                  key="M_Q_C_POSTURE_FUSION"
                  label="体位融合"
                  value="M_Q_C_POSTURE_FUSION"
                />
                <el-option
                  key="M_Q_C_CHRONIC_PAIN_ASSESSMENT"
                  label="慢性疼痛评估"
                  value="M_Q_C_CHRONIC_PAIN_ASSESSMENT"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="医生">
              <el-select placeholder="请选择医生" v-model="addForm.doctor">
                <el-option v-for="item in doctorOptions"
                  :key="item.key"
                  :label="item.value"
                  :value="item.key"
                />
              </el-select>
            </el-form-item>
          </el-form>
        </el-row>
      </el-row>
    </el-row>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="hideDialog">关闭</el-button>
        <el-button @click="hideDialog" type="primary">提交</el-button>
      </div>
    </template>
  </el-drawer>
</template>

<style scoped lang="scss">
.queue {
  &_info {
    width: 100%;
  }

  &_details {
    width: 100%;

    &_item {
      width: 100%;

      &_input {
        width: 90px;
      }
    }
  }
}
</style>