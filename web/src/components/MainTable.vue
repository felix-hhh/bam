<script setup lang="ts">
import { onMounted, onUnmounted, reactive, ref } from "vue";
import { ArrowDown } from "@element-plus/icons-vue";
import { ElMessage, ElMessageBox, Sort, UploadFile } from "element-plus";
import * as XLSX from "xlsx";
import fs from "file-saver";
import {
  ColumnData, FormColumn,
  PageResult,
  SearchData,
  TableColumn,
  TableColumnHandle,
  TableOptButton,
  ViewColumnConfig,
  ViewConfig,
  ViewSearchConfig,
} from "#/conponent.ts";
import { ElMessageBoxOptions } from "element-plus/es/components/message-box/src/message-box.type";
import Base64 from "crypto-js/enc-base64";
import Utf8 from "crypto-js/enc-utf8";
import { useRouter } from "vue-router";
import OSS from "ali-oss";
import { ToolsFile } from "#/entity.ts";
import useAxios from "@/api";
import { booleanFormat, datetimeFormat, depthCopy, getOssClient } from "@/utils/util.ts";

const props = defineProps<{
  rowBtns?: TableColumnHandle[];
}>();

/**
 * 数据定义
 */
const pageData = ref<PageResult>();
const loading = ref<boolean>(false);
const page = ref<boolean>(true);
const title = ref<string>("");
const multiSelect = ref<boolean>(true);

const router = useRouter();
const { sendPut, sendGet, sendPost, sendDel } = useAxios();
//页面配置
const displayControl = reactive({
  loading: false,
  addDialog: false,
  detailDialog: false,
  isEdit: false,
  searchbar: true,
});
const viewConfig = ref<ViewConfig>({} as ViewConfig);
const viewColumnConfig = ref<ViewColumnConfig[]>();
const viewSearchConfig = ref<ViewSearchConfig[]>([] as ViewSearchConfig[]);
const viewData = ref<object[]>([]);
const viewPageData = ref<PageResult>({} as PageResult);
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
});
const sort: Sort = { prop: "createDatetime", order: "descending" };


const $emit = defineEmits(["selection-change"]);

const imageBaseUrl = import.meta.env.VITE_IMAGE_CDN_URL;
const multipleSelection = ref([]);

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
 * 取回数据
 */
const getViewData = (currentPage?: number, pageSize?: number, sort?: string, dir?: "ASC" | "DESC") => {
  searchData.value.page = currentPage | 1;
  searchData.value.limit = pageSize | 20;
  searchData.value.dir = dir ? dir : "DESC";
  searchData.value.sort = sort ? sort : "createDatetime";

  viewSearchConfig.value.forEach((item) => {
    if (item.searchValue !== undefined && item.searchValue !== null && item.searchValue.length > 0) {
      searchData.value.data[item.searchKey] = item.searchValue;
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
          sortable: viewColumn.columnSortable ? "custom" : undefined,
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

    const rowBtns = props.rowBtns;
    if (rowBtns) {
      rowBtns.forEach((btn) => {
        optLine.handles.push(btn);
      });
    }

    //按钮显示
    const value = viewConfig.value;
    if (value) {
      const optAddShowRegion = value.optAddShowRegion;
      if (value.optAdd && (optAddShowRegion === "S_V_R_LINE" || optAddShowRegion === "S_V_R_BOTH")) {

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

      //变更状态按钮
      const optChangeShowRegion = value.optChangeShowRegion;
      if (value.optChange && (optChangeShowRegion === "S_V_R_LINE" || optChangeShowRegion === "S_V_R_BOTH")) {
        optLine.handles?.push({
          label: value.optChangeLabel || "禁用",
          handleFun: delHandle,
          type: "warning",
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

const handleSelectionChange = (val) => {
  multipleSelection.value = val;
  $emit("selection-change", val);
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

const sizeChangeHandle = (size: number) => {
  getViewData(1, size);
};

/**
 * 排序变更事件处理器
 * @param columnData
 */
const sortChangeHandle = (columnData: ColumnData) => {
  getViewData(null, null, columnData.prop, columnData.order);
};

const currentChangeHandle = (current: number) => {
  getViewData(current, pageData.value.size);
};

const searchBarTagger = () => {
  displayControl.searchbar = displayControl.searchbar === false;
};

const fullscreen = () => {
  const baseGrids = document.getElementById("BaseGrids");
  baseGrids.requestFullscreen();
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
 * 导出当前页面数据成excel
 */
const exportData = () => {
  ElMessageBox.confirm(
    `确认要下载当前页面数据？`,
    "确认",
    {
      confirmButtonText: "确认",
      cancelButtonText: "取消",
      type: "warning",
    } as ElMessageBoxOptions,
  ).then(() => {
    const filename = "data.xlsx";
    //数据准备-表头
    const excelData = [];
    const excelHeadData = [];
    gridColumn.value.forEach(column => {
      if (column.type !== "handle") {
        excelHeadData.push(column.label);
      }
    });
    excelData.push(excelHeadData);

    viewData.value.forEach(item => {
      const excelDataTemp = [] as object[];
      gridColumn.value.forEach(column => {
        if (column.type !== "handle") {
          let itemData = item[column.prop];
          if (column.format) {
            itemData = column.format(null, null, itemData);
          }
          excelDataTemp.push(itemData);
        }
      });
      excelData.push(excelDataTemp);
    });

    const workBook = XLSX.utils.book_new();
    const workSheet = XLSX.utils.aoa_to_sheet(excelData);
    XLSX.utils.book_append_sheet(workBook, workSheet, "sheet1");

    //创建下载流
    const xlsxBlob = new Blob(
      [
        stringToBuff(
          XLSX.write(workBook, {
            bookType: "xlsx",
            bookSST: false,
            type: "binary",
          }),
        ),
      ],
      {
        type: "application/octet-stream",
      },
    );
    fs.saveAs(xlsxBlob, filename);

  });
};

const stringToBuff = str => {
  let arrayBuffer = new ArrayBuffer(str.length);
  let uint8Array = new Uint8Array(arrayBuffer);
  for (let i = 0; i < str.length; ++i) {
    uint8Array [i] = str.charCodeAt(i) & 0xff;
  }
  return arrayBuffer;
};

defineExpose({
  getViewData,
});

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
  <el-card id="BaseGrids" shadow="never">
    <template v-if="title" #header>
      <div>
        <span>{{ title }}</span>
      </div>
    </template>
    <div class="search-bar" v-if="displayControl.searchbar && viewSearchConfig.length > 0">
      <el-form v-if="viewSearchConfig" label-width="100" :inline="true" :model="viewSearchConfig">
        <div class="search-bar-form">
          <el-row class="search-bar-form-item">
            <template v-for="item in viewSearchConfig" :key="item.key">
              <el-col :xl="4" :lg="6" :md="12">
                <el-form-item :label="item.searchLabel" v-if="item.dataType==='input'" style="width: 100%">
                  <el-input :placeholder="'请输入' + item.searchLabel" v-model="item.searchValue" />
                </el-form-item>
                <el-form-item :label="item.searchLabel" v-if="item.dataType==='select'" style="width: 100%">
                  <el-select
                    clearable
                    v-model="item.searchValue"
                    :placeholder="'请选择' + item.searchLabel"
                    style="width: 100%"
                  >
                    <el-option
                      v-for="data in item.dataSource"
                      :key="data.key"
                      :label="data.value"
                      :value="data.key"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
            </template>
          </el-row>
          <el-row class="search-bar-form-btn">
            <el-button type="primary" icon="Search" @click="getViewData">查询</el-button>
            <el-button icon="RefreshRight" @click="getViewData">重置</el-button>
          </el-row>
        </div>
      </el-form>
    </div>
    <div class="operate-bar">
      <div class="operate-bar-left">
        <template v-for="item in optButtons">
          <template v-if="item.type === 'group'">
            <el-dropdown>
              <el-button plain>
                {{ item.label }}
                <el-icon class="">
                  <ArrowDown />
                </el-icon>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <template v-for="subItem in item.items">
                    <el-dropdown-item v-if="subItem.selectHandler" :disabled="multipleSelection.length <= 0">
                      {{ subItem.label }}
                    </el-dropdown-item>
                    <el-dropdown-item v-else>
                      {{ subItem.label }}
                    </el-dropdown-item>
                  </template>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
          <template v-else>
            <el-button v-if="item.selectHandler" :disabled="multipleSelection.length <= 0" :type="item.type"
                       @click="item.handle">
              {{ item.label }}
            </el-button>
            <el-button v-else :type="item.type" @click="item.handle">{{ item.label }}</el-button>
          </template>
        </template>
      </div>
      <div class="operate-bar-right">
        <el-tooltip content="刷新" placement="top">
          <el-button icon="Refresh" @click="getViewData" plain link />
        </el-tooltip>
        <el-tooltip content="全屏" placement="top">
          <el-button icon="FullScreen" @click="fullscreen" plain link />
        </el-tooltip>
        <el-tooltip v-if="viewSearchConfig" content="查询" placement="top">
          <el-button icon="Search" @click="searchBarTagger" plain link />
        </el-tooltip>
        <el-tooltip content="下载" placement="top">
          <el-button icon="Download" @click="exportData" plain link />
        </el-tooltip>
        <el-tooltip content="打印" placement="top">
          <el-button icon="Printer" plain link />
        </el-tooltip>
      </div>
    </div>
    <slot name="descriptions" />
    <el-table
      :data="viewData"
      style="width: 100%"
      v-loading="loading"
      size="large"
      :default-sort="sort"
      @sort-change="sortChangeHandle"
      @selection-change="handleSelectionChange"
    >
      <el-table-column v-if="multiSelect" type="selection" width="55" />
      <template v-for="item in gridColumn">
        <template v-if="item.type === 'handle'">
          <el-table-column
            :sortable="item.sortable"
            :label="item.label"
            :fixed="!item.fixed"
            :width="item.width">
            <template #default="scope">
              <template v-for="handle in item.handles">
                <template v-if="handle.format !== undefined">
                  <el-button
                    @click="handle.handleFun(scope.$index, scope.row)"
                    :type="
                      handle !== undefined
                        ? handle.type !== undefined
                          ? handle.type
                          : 'primary'
                        : ''
                    "
                    link
                  >
                    {{ handle.format(scope.row) }}
                  </el-button>
                </template>
                <template v-else>
                  <template v-if="handle.type === 'group'">
                    <el-dropdown trigger="click">
                      <el-button link>
                        {{ handle.label }}
                        <el-icon class="el-icon--right">
                          <ArrowDown />
                        </el-icon>
                      </el-button>
                      <template #dropdown>
                        <el-dropdown-menu>
                          <template v-for="subItem in handle.items" :key="subItem.id">
                            <el-dropdown-item @click="subItem.handleFun(scope.$index, scope.row)">
                              {{ subItem.label }}
                            </el-dropdown-item>
                          </template>
                        </el-dropdown-menu>
                      </template>
                    </el-dropdown>
                  </template>
                  <template v-else>
                    <el-button
                      @click="handle.handleFun(scope.$index, scope.row)"
                      :type="
                      handle !== undefined
                        ? handle.type !== undefined
                          ? handle.type
                          : 'primary'
                        : ''
                    "
                      link
                    >
                      {{ handle.label }}
                    </el-button>
                  </template>
                </template>
              </template>
            </template>
          </el-table-column>
        </template>
        <template v-else-if="item.type === 'image'">
          <el-table-column
            :prop="item.prop"
            :fixed="item.fixed ? item.fixed : false"
            :label="item.label"
            :width="item.width ? item.width : ''"
          >
            <template #default="scope">
              <el-image class="table-image" :src="scope.row[item.prop]" fit="cover">
                <template #error>
                  <div class="image-slot">
                    <el-icon>
                      <Picture />
                    </el-icon>
                  </div>
                </template>
              </el-image>
            </template>
          </el-table-column>
        </template>
        <template v-else>
          <el-table-column
            :show-overflow-tooltip="item.showOverflowTooltip"
            :formatter="item.format"
            :prop="item.prop"
            :fixed="item.fixed ? item.fixed : false"
            :label="item.label"
            :width="item.width ? item.width : ''"
            :sortable="item.sortable ? item.sortable : false"
          />
        </template>
      </template>
    </el-table>
    <template #footer>
      <div class="grid-footer" v-if="viewData">
        <div v-if="multiSelect">
          <el-text>
            已选中
            <el-text type="primary">{{ multipleSelection.length }}</el-text>
            行数据
          </el-text>
        </div>
        <div v-else>
          <el-text>多选功能禁用</el-text>
        </div>
        <div v-if="page">
          <el-pagination
            v-model:current-page="viewPageData.current"
            v-model:page-size="viewPageData.size"
            :page-sizes="[20, 50, 100, 200]"
            size="small"
            background
            layout="total, sizes, prev, pager, next"
            :total="viewPageData.total || 0"
            @size-change="sizeChangeHandle"
            @current-change="currentChangeHandle"
          />
        </div>
      </div>
    </template>
  </el-card>
  <!--添加/编辑窗口-->
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


<style lang="scss" scoped>
.search-bar {
  &-form {
    display: flex;
    justify-content: space-between;

    &-item {
      flex: 1;
    }

    &-btn {
      margin-left: 12px;
    }
  }
}

.operate-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;

  &-left {
    /*> :not(:last-child) {
      margin-right: 15px;
    }*/
    .el-dropdown {
      margin-left: 12px;
    }
  }
}

.el-button + .el-dropdown {
  margin-left: 12px;
}

.grid-footer {
  display: flex;
  justify-content: space-between;
}

.table-image {
  width: 50px;
  height: 50px;
}
</style>

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
