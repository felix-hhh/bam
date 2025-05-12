<script setup lang="ts">

import { onMounted, reactive, ref, onUnmounted } from "vue";
import useAxios from "@/api";
import {
  FormColumn,
  PageResult,
  SearchData,
  TableColumn,
  TableOptButton,
  ViewColumnConfig,
  ViewConfig,
  ViewSearchConfig,
} from "#/conponent.ts";
import { ElMessage, ElMessageBox, Sort, UploadFile } from "element-plus";
import MainTable from "@/components/MainTable.vue";
import { booleanFormat, datetimeFormat, depthCopy, filePathFormat, getOssClient } from "@/utils/util";
import { useRouter } from "vue-router";
import Base64 from "crypto-js/enc-base64";
import Utf8 from "crypto-js/enc-utf8";
import OSS from "ali-oss";
import { ToolsFile } from "#/entity.ts";

const router = useRouter();
const { sendPut, sendGet, sendPost, sendDel } = useAxios();
//页面配置
const displayControl = reactive({
  loading: false,
  addDialog: false,
  detailDialog: false,
  isEdit: false,
});

const viewConfig = ref<ViewConfig>({} as ViewConfig);
const viewColumnConfig = ref<ViewColumnConfig[]>();
const viewSearchConfig = ref<ViewSearchConfig[]>([] as ViewSearchConfig[]);
const viewData = ref<object[]>([]);
const viewPageData = ref<PageResult>();
const optButtons = ref<TableOptButton[]>([]);
const gridColumn = ref<TableColumn[]>([]);
const addFormRef = ref();
const addFormData = ref({
  imageView: null,
});
const addFormItems = ref<FormColumn[]>([]);
const addFormRules = ref({});
const searchData = ref<SearchData>({
  limit: 20,
  page: 1,
  data: {},
  dir: "DESC",
});
const sort: Sort = { prop: "createDatetime", order: "descending" };


/**
 * 取回页面配置
 */
const getViewConfig = () => {
  try {
    const path = Base64.stringify(Utf8.parse(router.currentRoute.value.fullPath));
    sendGet<ViewConfig>(`/system/manage/view/path/${path}`)
      .then(req => {
        if (req !== undefined) {
          viewConfig.value = req;
          initOptButton();
          getViewColumnConfig(viewConfig.value.id);
        }
      });

  } catch (error) {
    console.error("Failed to initialize view:", error);
    ElMessage.error("页面初始化失败");
  }
};

/**
 * 取回页面列配置
 * @param viewId 页面ID
 */
const getViewColumnConfig = (viewId: number) => {
  sendGet<ViewColumnConfig[]>(`/system/manage/view/column/list/${viewId}`)
    .then(req => {
      viewColumnConfig.value = req;
      initViewColumns();
      getViewSearchConfig(viewId);
      initForm();
      // 列配置初始化完成后再获取数据
      if (viewConfig.value.initData) {
        getViewData();
      }
    });
};

/**
 * 取回页面检索配置
 * @param viewId 页面ID
 *
 */
const getViewSearchConfig = (viewId: number) => {
  sendGet<ViewSearchConfig[]>(`/system/manage/view/search/get/${viewId}`)
    .then(req => {
      viewSearchConfig.value = req;
    });
};

/**
 * 初始化视图表头
 */
const initViewColumns = () => {
  if (viewColumnConfig.value) {
    viewColumnConfig.value.map(viewColumn => {
      if (viewColumn.showColumn) {
        const tableColumn: TableColumn = {
          label: viewColumn.columnLabel,
          prop: viewColumn.columnName,
          width: viewColumn.showWidth,
          fixed: viewColumn.showFixed,
          type: viewColumn.dataType === "image" ? viewColumn.dataType : null,
          sortable: viewColumn.sortable,
          format: getColumnFormat(viewColumn.showFormat),
        };
        gridColumn.value.push(tableColumn);
      }
    });

    const optLine: TableColumn = {
      label: "操作",
      width: 180,
      type: "handle",
      fixed: "right",
      handles: [],
    };

    //按钮显示
    const value = viewConfig.value;
    if (value) {
      const optAddShowRegion = value.optAddShowRegion;
      if (value.optAdd && (optAddShowRegion === "S_V_R_LINE" || optAddShowRegion === "S_V_R_BOTH")) {

      }

      const optViewShowRegion = value.optViewShowRegion;
      if (value.optView && (optViewShowRegion === "S_V_R_LINE" || optViewShowRegion === "S_V_R_BOTH")) {
        optLine.handles?.push({
          label: value.optViewLabel || "查看",
          handleFun: showViewDialog,
          type: "primary",
        });
      }

      //编辑按钮
      const optEditShowRegion = value.optEditShowRegion;
      if (value.optEdit && (optEditShowRegion === "S_V_R_LINE" || optEditShowRegion === "S_V_R_BOTH")) {
        optLine.handles?.push({
          label: value.optEditLabel || "编辑",
          handleFun: showEditDialog,
          type: "primary",
        });
      }

      //删除按钮
      const optDeleteShowRegion = value.optDeleteShowRegion;
      if (value.optDelete && (optDeleteShowRegion === "S_V_R_LINE" || optDeleteShowRegion === "S_V_R_BOTH")) {
        optLine.handles?.push({
          label: value.optDeleteLabel || "删除",
          handleFun: delHandle,
          type: "danger",
        });
      }
    }

    gridColumn.value.push(optLine);
  }
};

/**
 * 初始化表格
 */
const initForm = () => {
  if (viewConfig.value?.optAdd) {
    viewColumnConfig.value?.map(column => {
      //表格数据
      const itemData = {
        label: column.columnLabel,
        prop: column.columnName,
        type: column.dataType,
        addHandle: column.addHandle,
        editHandle: column.editHandle,
        dataSource: column.dataSource,
      };


      addFormItems.value?.push(itemData);

      //表格规则
      if (column.ruleRequired) {
        addFormRules.value[column.columnName] = {
          required: true,
          message: `${column.columnLabel}不能为空`,
        };
      }
    });
  }
};

const getColumnFormat = (formatName?: string) => {
  switch (formatName) {
    case "datetimeFormat":
      return datetimeFormat;
    case "booleanFormat":
      return booleanFormat;
    default:
      return undefined;
  }
};

/**
 * 初始化操作按钮
 */
const initOptButton = () => {
  if (viewConfig.value) {
    const config = viewConfig.value;
    if (config.optAdd && (config.optAddShowRegion === "S_V_R_OPT" || config.optAddShowRegion === "S_V_R_BOTH")) {
      const optButton: TableOptButton = {
        label: config.optAddLabel || "添加",
        selectHandler: false,
        type: "primary",
        handle: showAddDialog,
      };
      optButtons.value.push(optButton);
    }
  }
};

/**
 * 显示详情页面
 */
const showViewDialog = () => {
  displayControl.detailDialog = true;
};

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
 * 关闭添加窗口
 */
const hideDialog = () => {
  addFormRef.value.resetFields();
  displayControl.addDialog = false;
  displayControl.detailDialog = false;
};

/**
 * 删除处理器
 */
const delHandle = (index, row) => {
  const id: number = row.id;
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
      sendDel(`${viewConfig.value.optDeleteUrl}/${id}`)
        .then(() => {
          getViewData();
          ElMessage.success("操作成功");
        });
    });
};

const handleImageUploadBefore = (uploadFile) => {
  fileUpload(uploadFile);
};

const fileUpload = async (file: UploadFile) => {
  if (!file) {
    console.log("请选择文件");
    return;
  }
  const fileType = file.name.substring(file.name.lastIndexOf(".") + 1);
  const fileName = crypto.randomUUID() + "." + fileType;
  const postData: ToolsFile = {
    realFileName: fileName,
    origFileName: file.name,
    filePath: `sts_temp/${fileName}`,
  };

  const client: OSS = await getOssClient();

  const result: OSS.PutObjectResult = await client.put(postData.filePath, file.raw);
  console.log("文件上传成功", result);
  addFormData.value.imageView = URL.createObjectURL(file.raw!);

  await sendPost("/tools/manage/file/add", postData).then(req => {
    console.log(req);
  });
};

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
  console.log(searchData.value);
  viewSearchConfig.value.forEach((item) => {
    if (item.value !== undefined && item.value !== null && item.value.length > 0) {
      searchData.data[item.key] = item.value;
    }
  });
  if (viewConfig.value) {
    const config = viewConfig.value;
    sendPost(config.initDataUrl, searchData.value)
      .then((rep: any) => {
        viewPageData.value = rep;
        viewData.value = viewPageData.value ? viewPageData.value.records : [];
      });
  }
};


onMounted(() => {
  getViewConfig();
});

onUnmounted(() => {
  // 清理数据
  viewConfig.value = null;
  viewColumnConfig.value = null;
  viewData.value = [];
  viewPageData.value = null;
  optButtons.value = [];
  gridColumn.value = [];
  addFormItems.value = [];
  addFormRules.value = {};
});
</script>

<template>
  <MainTable
    :grid-column="gridColumn"
    :grid-data="viewData"
    :page-data="viewPageData"
    :search-data="searchData"
    :button-item="optButtons"
    :multi-select="true"
    :loading="displayControl.loading"
    :grid-data-fun="getViewData"
    :default-sort="sort"
    :page="true"
  />
  <el-drawer
    v-model="displayControl.detailDialog"
    title="详情"
    direction="rtl"
    :before-close="hideDialog"
    size="600"
  >
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="hideDialog">关闭</el-button>
      </div>
    </template>
  </el-drawer>
  <el-drawer
    v-model="displayControl.addDialog"
    :title="(displayControl.isEdit ? '编辑' : '添加') + viewConfig.name"
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
            <template v-else-if="item.type==='image'">
              <el-upload
                class="avatar-uploader"
                :auto-upload="false"
                :show-file-list="false"
                :on-change="handleImageUploadBefore"
              >
                <el-image v-if="addFormData.imageView" :src="addFormData.imageView" class="avatar" />
                <el-image v-else-if="addFormData[item.prop]" :src="addFormData[item.prop]" class="avatar" />
                <el-icon v-else class="avatar-uploader-icon">
                  <Plus />
                </el-icon>
              </el-upload>
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

<style lang="scss">
.avatar-uploader {
  .el-upload {
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
  }

  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>