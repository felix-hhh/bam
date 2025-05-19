<script setup lang="ts">

import TableView from "@/views/TableView.vue";
import { onMounted, reactive, ref } from "vue";
import { SelectItem, TableColumnHandle } from "#/conponent.ts";
import { MedicalQueue } from "#/entity.ts";
import useAxios from "@/api";
import { ElMessage, ElMessageBox } from "element-plus";
import MainTable from "@/components/MainTable.vue";

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
  <MainTable
    :row-btns="columnBtns"
  >

  </MainTable>
  <el-drawer
    v-model="displayControl.checkDialog"
    title="诊断报告"
    direction="rtl"
    :before-close="hideDialog"
    size="800"
  >
    <el-form
      ref="formRef"
      label-width="auto"
      class="demo-dynamic"
    >
      <el-form-item label="职业">
        <el-input />
      </el-form-item>
      <el-form-item label="运动习惯">
        <el-checkbox value="Simple brand exposure" name="type">
          规律运动
        </el-checkbox>
        <el-checkbox value="Simple brand exposure" name="type">
          偶尔运动
        </el-checkbox>
        <el-checkbox value="Simple brand exposure" name="type">
          基本不动
        </el-checkbox>
      </el-form-item>
      <el-form-item label="手术史">
        <el-input />
      </el-form-item>
      <el-form-item label="既往史">
        <el-checkbox value="Simple brand exposure" name="type">
          心脏病
        </el-checkbox>
        <el-checkbox value="Simple brand exposure" name="type">
          高血压
        </el-checkbox>
        <el-checkbox value="Simple brand exposure" name="type">
          哮喘
        </el-checkbox>
        <el-checkbox value="Simple brand exposure" name="type">
          慢性肺病
        </el-checkbox>
        <el-checkbox value="Simple brand exposure" name="type">
          骨质疏松
        </el-checkbox>
        <el-checkbox value="Simple brand exposure" name="type">
          糖尿病
        </el-checkbox>
        <el-checkbox value="Simple brand exposure" name="type">
          甲亢
        </el-checkbox>
        <el-checkbox value="Simple brand exposure" name="type">
          甲减
        </el-checkbox>
        <el-checkbox value="Simple brand exposure" name="type">
          贫血
        </el-checkbox>
      </el-form-item>
      <el-form-item label="症状部位">
        <el-checkbox value="Simple brand exposure" name="type">
          头
        </el-checkbox>
        <el-checkbox value="Simple brand exposure" name="type">
          颈
        </el-checkbox>
        <el-checkbox value="Simple brand exposure" name="type">
          胸
        </el-checkbox>
        <el-checkbox value="Simple brand exposure" name="type">
          肩
        </el-checkbox>
        <el-checkbox value="Simple brand exposure" name="type">
          肘
        </el-checkbox>
        <el-checkbox value="Simple brand exposure" name="type">
          腕
        </el-checkbox>
        <el-checkbox value="Simple brand exposure" name="type">
          手
        </el-checkbox>
        <el-checkbox value="Simple brand exposure" name="type">
          腰
        </el-checkbox>
        <el-checkbox value="Simple brand exposure" name="type">
          骨盆
        </el-checkbox>
        <el-checkbox value="Simple brand exposure" name="type">
          髋
        </el-checkbox>
        <el-checkbox value="Simple brand exposure" name="type">
          膝
        </el-checkbox>
        <el-checkbox value="Simple brand exposure" name="type">
          踝
        </el-checkbox>
        <el-checkbox value="Simple brand exposure" name="type">
          脚
        </el-checkbox>
      </el-form-item>
      <el-form-item label="持续时间">
        <el-input />
      </el-form-item>
      <el-form-item label="疼痛VAS评分">
        <el-input />
      </el-form-item>
      <el-form-item label="现病史">
        <el-input type="textarea" />
      </el-form-item>
      <el-row>2.关节活动度ROM</el-row>
      <el-row>肩关节</el-row>
      <el-form-item label="屈曲">
        <el-row class="report">
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
        </el-row>
      </el-form-item>
      <el-form-item label="伸展">
        <el-row class="report">
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
        </el-row>
      </el-form-item>
      <el-form-item label="内旋">
        <el-row class="report">
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
        </el-row>
      </el-form-item>
      <el-form-item label="外旋">
        <el-row class="report">
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
        </el-row>
      </el-form-item>
      <el-form-item label="内收">
        <el-row class="report">
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
        </el-row>
      </el-form-item>
      <el-form-item label="外展">
        <el-row class="report">
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
        </el-row>
      </el-form-item>
      <el-row>肘关节</el-row>
      <el-form-item label="屈曲">
        <el-row class="report">
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
        </el-row>
      </el-form-item>
      <el-form-item label="伸展">
        <el-row class="report">
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
        </el-row>
      </el-form-item>
      <el-row>腕关节</el-row>
      <el-form-item label="掌屈">
        <el-row class="report">
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
        </el-row>
      </el-form-item>
      <el-form-item label="背伸">
        <el-row class="report">
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
        </el-row>
      </el-form-item>
      <el-form-item label="桡偏">
        <el-row class="report">
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
        </el-row>
      </el-form-item>
      <el-form-item label="尺偏">
        <el-row class="report">
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
        </el-row>
      </el-form-item>
      <el-form-item label="旋前">
        <el-row class="report">
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
        </el-row>
      </el-form-item>
      <el-form-item label="旋后">
        <el-row class="report">
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
        </el-row>
      </el-form-item>
      <el-row>髋关节</el-row>
      <el-form-item label="屈曲">
        <el-row class="report">
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
        </el-row>
      </el-form-item>
      <el-form-item label="伸展">
        <el-row class="report">
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
        </el-row>
      </el-form-item>
      <el-form-item label="外展">
        <el-row class="report">
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
        </el-row>
      </el-form-item>
      <el-form-item label="内收">
        <el-row class="report">
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
        </el-row>
      </el-form-item>
      <el-form-item label="内旋">
        <el-row class="report">
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
        </el-row>
      </el-form-item>
      <el-form-item label="外旋">
        <el-row class="report">
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
          <el-row class="report_item">
            <el-row class="report_item_label">左</el-row>
            <el-row class="report_item_input">
              <el-input />
            </el-row>
          </el-row>
        </el-row>
      </el-form-item>
    </el-form>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="hideDialog">关闭</el-button>
        <el-button @click="addCheck" type="primary">提交</el-button>
      </div>
    </template>
  </el-drawer>
</template>

<style scoped lang="scss">
.report_item {

  &_label {
    width: 50px;
    padding-right: 10px;
    justify-content: flex-end;
  }

  &_input {
    flex: 1;
  }
}
</style>t