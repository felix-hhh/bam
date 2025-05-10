<script setup lang="ts">
import { onMounted, reactive, ref } from "vue";
import useAxios from "@/api";
import {
  FormColumn,
  PageResult,
  SearchItem,
  TableColumn,
  TableOptButton,
  ViewColumnConfig,
  ViewConfig,
} from "#/conponent.ts";
import { ElMessage, ElMessageBox, Sort } from "element-plus";
import MainTable from "@/components/MainTable.vue";
import { datetimeFormat, depthCopy } from "@/utils/util";
import { useRouter } from "vue-router";

/**
 * 显示添加窗口
 */
const showAddDialog = () => {
  displayControl.addDialog = true;
  displayControl.isEdit = false;
};

/**
 * 显示编辑窗口
 */
const showEditDialog = (index: number, data: any) => {
  addFormData.value = depthCopy(data);
  displayControl.addDialog = true;
  displayControl.isEdit = true;
};

/**
 * 删除处理器
 */
const delHandle = () => {
  ElMessageBox.confirm(
    "确认要删除本条数据么？",
    "再次确认",
    {
      confirmButtonText: "确认",
      cancelButtonText: "取消",
      type: "warning",
    },
  )
    .then(() => {
      ElMessage({
        type: "success",
        message: "Delete completed",
      });
    })
    .catch(() => {

    });
};


/**
 * 关闭添加窗口
 */
const hideDialog = () => {
  addFormRef.value.resetFields();
  displayControl.addDialog = false;
};

const router = useRouter();
const { sendPut, sendGet, sendPost } = useAxios();
//页面配置
const displayControl = reactive({
  loading: false,
  addDialog: false,
  isEdit: false,
});

const searchItem = ref<SearchItem[]>([
  {
    label: "姓名",
    key: "name",
    value: "",
  },
  {
    label: "手机号码",
    key: "phone",
    value: "",
  },
  {
    label: "状态",
    key: "status",
    value: "",
  },
]);

const viewConfig = ref<ViewConfig>({
  initDataUrl: "/medical/manage/patient/page",
} as ViewConfig);
const viewColumnConfig = ref<ViewColumnConfig[]>();
const viewData = ref<object[]>([]);
const viewPageData = ref<PageResult>();
const optButtons = ref<TableOptButton[]>([]);
const gridColumn = ref<TableColumn[]>([
  {
    label: "序号",
    prop: "id",
    width: 80,
    fixed: "left",
  },
  {
    label: "名字",
    prop: "name",
    width: 150,
    fixed: "left",
  },
  {
    label: "电话",
    prop: "phone",
    width: 150,
  },
  {
    label: "身份证",
    prop: "idCard",
    width: 200,
  },
  {
    label: "年龄",
    prop: "age",
    width: 100,
  },
  {
    label: "上次就诊时间",
    prop: "lastTreatDatetime",
    width: 180,
    format: datetimeFormat,
  },
  {
    label: "就诊次数",
    prop: "treatCount",
    width: 100,
  },
  {
    label: "备注",
    prop: "remark",
  },
  {
    label: "创建时间",
    prop: "createDatetime",
    width: 180,
    format: datetimeFormat,
  },
  {
    label: "操作",
    width: 120,
    type: "handle",
    fixed: "right",
    handles: [
      {
        label: "编辑",
        handleFun: showEditDialog,
        type: "primary",
      },
      {
        label: "删除",
        handleFun: delHandle,
        type: "danger",
      },
    ],
  },
]);
const addFormRef = ref();
const addFormData = ref({
  imageView: null,
});
const addFormItems = ref<FormColumn[]>([]);
const addFormRules = ref({});
const searchData = reactive({
  limit: 20,
  page: 1,
  data: {},
  dir: "ASC",
});
const sort: Sort = { prop: "createDatetime", order: "descending" };


/**
 * 添加处理器
 */
const saveHandle = () => {
  addFormRef.value.validate((valid: boolean) => {
    if (valid) {
      if (viewConfig.value) {
        const submitData = JSON.parse(JSON.stringify(addFormData.value));
        if (displayControl.isEdit) {
          sendPut(viewConfig.value.optEditUrl, submitData).then(() => {
            getViewData();
            hideDialog();
            ElMessage.success("操作成功");
          });
        } else {
          sendPost(viewConfig.value.optAddUrl, submitData).then(() => {
            getViewData();
            hideDialog();
            ElMessage.success("操作成功");
          });
        }
      }
    }
  });
};

/**
 * 取回数据
 */
const getViewData = () => {
  if (viewConfig) {
    sendPost(viewConfig.value.initDataUrl, searchData)
      .then((rep: any) => {
        viewPageData.value = rep;
        viewData.value = viewPageData.value ? viewPageData.value.records : [];
      });
  }
};


onMounted(() => {
  getViewData();
  // getViewConfig();
  // getOssSts();
});
</script>

<template>
  <MainTable
    :grid-column="gridColumn"
    :grid-data="viewData"
    :page-data="viewPageData"
    :search-item="searchItem"
    :button-item="optButtons"
    :multi-select="true"
    :loading="displayControl.loading"
    :grid-data-fun="getViewData"
    :default-sort="sort"
    :page="true"
  />

  <el-drawer
    v-model="displayControl.addDialog"
    :title="(displayControl.isEdit ? '编辑' : '添加')+'患者'"
    direction="rtl"
    :before-close="hideDialog"
    size="400"
  >
    <el-form label-width="80" ref="addFormRef" :model="addFormData" :rules="addFormRules" label-position="top">
      <el-space fill>
        <el-alert v-if="viewConfig.tips" type="info" show-icon :closable="false">
          <p>{{ viewConfig.tips }}</p>
        </el-alert>
        <template v-for="item in addFormItems">
          <el-form-item v-if="(item.addHandle && !displayControl.isEdit) || (item.editHandle && displayControl.isEdit)"
                        :label="item.label" :prop="item.prop">
            <template v-if="item.type==='switch'">
              <el-switch v-model="addFormData[item.prop]" />
            </template>
            <template v-else>
              <el-input :type="item.type" v-model="addFormData[item.prop]" />
            </template>
          </el-form-item>
        </template>
      </el-space>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="hideDialog">取消</el-button>
        <el-button type="primary" @click="saveHandle">确认</el-button>
      </div>
    </template>
  </el-drawer>
</template>

<style scoped lang="scss">

</style>