<script setup lang="ts">

import TableView from "@/views/TableView.vue";
import { onMounted, reactive, ref } from "vue";
import { SelectItem, TableColumnHandle } from "#/conponent.ts";
import { MedicalQueue } from "#/entity.ts";
import useAxios from "@/api";
import { ElMessage, ElMessageBox } from "element-plus";

const { sendGet, sendPost, sendPut } = useAxios();
const displayControl = reactive({
  viewDialog: false,
  checkDialog: false,
});
const currentView = ref({});
const addForm = ref<MedicalQueue>({
  patientInfo: {},
} as MedicalQueue);

const doctorOptions = ref<SelectItem[]>([]);

/**
 * 显示添加窗口哦
 */
const showCheckDialog = (row: any) => {
  displayControl.checkDialog = true;
  currentView.value = row;
};

const hideDialog = () => {
  displayControl.viewDialog = false;
  displayControl.checkDialog = false;
};


const addCheck = () => {
  addForm.value.patientId = currentView.value.patientId;
  sendPost("/medical/manage/queue/add", addForm.value)
    .then((res) => {
      hideDialog();
      console.log(res);
    });
};

const columnBtns: TableColumnHandle[] = [
  {
    format: (row): string => {
      const status = row["status"];
      if (status === "M_Q_S_QUEUING") {
        return "开始检查";
      } else {
        return "";
      }


    },
    handleFun: (index: number, row: any) => {
        showCheckDialog(row);
    },
  },
];


const getDoctorList = () => {
  sendGet("medical/manage/doctor/list/option")
    .then(req => {
      doctorOptions.value = req;
    });
};

onMounted(() => {
  getDoctorList();
});
</script>

<template>
  <TableView
    :row-btns="columnBtns"
  >

  </TableView>
  <el-drawer
    v-model="displayControl.checkDialog"
    title="诊断报告"
    direction="rtl"
    :before-close="hideDialog"
    size="800"
  >
    <el-row class="queue">
      <el-row class="queue_info">
        <el-descriptions
          title="基础信息"
          :column="3"
          class="queue_info_desc"
        >
          <el-descriptions-item label="姓名">{{ currentView["patientName"] }}</el-descriptions-item>
          <el-descriptions-item label="性别">{{ currentView["patientGenderStr"] }}</el-descriptions-item>
          <el-descriptions-item label="年龄">{{ currentView["patientAge"] }}</el-descriptions-item>
          <el-descriptions-item label="手机号">{{ currentView["patientPhone"] }}</el-descriptions-item>
        </el-descriptions>
      </el-row>
      <el-row class="queue_details">
        <el-row class="queue_details_title">
          患者情况
        </el-row>
        <el-row class="queue_details_item">
          <el-form :model="addForm" label-width="80" style="width: 100%">
            <el-form-item label="血压">
              <el-col :span="11">
                高压
                <el-input v-model="addForm.patientInfo.height"
                          class="queue_details_item_input" />
              </el-col>
              <el-col :span="2"></el-col>
              <el-col :span="11">
                低压
                <el-input v-model="addForm.patientInfo.low"
                          class="queue_details_item_input" />
              </el-col>
            </el-form-item>
            <el-form-item label="血糖值">
              <el-input v-model="addForm.patientInfo.bloodGlucose"
                        placeholder="请填写血糖值"
                        type="number" />
            </el-form-item>
            <el-form-item label="手术史">
              <el-input v-model="addForm.patientInfo.surgicalHistory"
                        placeholder="请填写手术史"
                        type="textarea"
                        :rows="4" />
            </el-form-item>
            <el-form-item label="既往史">
              <el-input v-model="addForm.patientInfo.previousHistory"
                        placeholder="请填写既往史"
                        type="textarea"
                        :rows="4" />
            </el-form-item>
            <el-form-item label="康复项目">
              <el-select v-model="addForm.checkItem"
                         placeholder="请选择康复项目">
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
              <el-select placeholder="请选择医生" v-model="addForm.doctorId">
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
        <el-button @click="addCheck" type="primary">提交</el-button>
      </div>
    </template>
  </el-drawer>
</template>

<style scoped lang="scss">
.queue {
  &_info {
    width: 100%;

    &_desc {
      width: 100%;
    }
  }

  &_details {
    width: 100%;

    &_title {
      font-weight: bold;
      margin: 20px 0;
    }

    &_item {
      width: 100%;

      &_input {
        width: 90px;
      }
    }
  }
}
</style>