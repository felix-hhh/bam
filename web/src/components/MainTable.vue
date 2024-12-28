<script setup lang="ts">
import { onMounted, reactive, ref } from "vue";
import { ArrowDown } from "@element-plus/icons-vue";
import { ElMessageBox, Sort } from "element-plus";
import * as XLSX from "xlsx";
import fs from "file-saver";
import { PageResult, TableColumn, TableOptButton, TableSearch } from "#/conponent.ts";
import { ElMessageBoxOptions } from "element-plus/es/components/message-box/src/message-box.type";

const props = defineProps<{
  gridData: object[];
  pageData?: PageResult;
  gridColumn: TableColumn[];
  defaultSort: Sort;
  searchItem?: TableSearch[];
  buttonItem?: TableOptButton[];
  loading: boolean;
  page: boolean;
  title?: string;
  multiSelect?: boolean;
  gridDataFun?: Function;
}>();


const $emit = defineEmits(["selection-change"]);

const displayControl = reactive({
  searchbar: true,
});
const imageBaseUrl = import.meta.env.VITE_IMAGE_CDN_URL;
const multipleSelection = ref([]);
const handleSelectionChange = val => {
  multipleSelection.value = val;
  $emit("selection-change", val);
};

const sizeChangeHandle = size => {
  props.gridDataFun(1, size);
};

const sortChangeHandle = (columnData) => {
  props.gridDataFun(null, null, columnData.prop, columnData.order);
};

const currentChangeHandle = current => {
  props.gridDataFun(current, props.gridData.size);
};

const searchBarTagger = () => {
  displayControl.searchbar = displayControl.searchbar === false;
};

const fullscreen = () => {
  const baseGrids = document.getElementById("BaseGrids");
  baseGrids.requestFullscreen();
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
    props.gridColumn.forEach(column => {
      if (column.type !== "handle") {
        excelHeadData.push(column.label);
      }
    });
    excelData.push(excelHeadData);

    const gridData = typeof props.gridData === "PageResult" ? (props.gridData as PageResult).records : props.gridData;
    gridData.forEach(item => {
      const excelDataTemp = [];
      props.gridColumn?.forEach(column => {
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

    console.log(excelData);

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

onMounted(() => {
  const ruls = typeof props.gridData;
});
</script>

<template>
  <el-card id="BaseGrids" shadow="never">
    <template v-if="title" #header>
      <div>
        <span>{{ title }}</span>
      </div>
    </template>
    <div class="search-bar" v-if="displayControl.searchbar">
      <el-form v-if="searchItem" label-width="100" :inline="true" :model="searchItem">
        <div class="search-bar-form">
          <el-row class="search-bar-form-item">
            <template v-for="item in searchItem" :key="item.key">
              <el-col :xl="6" :lg="12" :md="12">
                <el-form-item :label="item.label" style="width: 100%">
                  <el-input :placeholder="'请输入' + item.label" v-model="item.value" />
                </el-form-item>
              </el-col>
            </template>
          </el-row>
          <el-row class="search-bar-form-btn">
            <el-button type="primary" icon="Search" @click="gridDataFun">查询</el-button>
            <el-button icon="RefreshRight" @click="gridDataFun">重置</el-button>
          </el-row>
        </div>
      </el-form>
    </div>
    <div class="operate-bar">
      <div class="operate-bar-left">
        <template v-for="item in buttonItem">
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
                       @click="item.handle">{{ item.label }}
            </el-button>
            <el-button v-else :type="item.type" @click="item.handle">{{ item.label }}</el-button>
          </template>
        </template>
      </div>
      <div class="operate-bar-right">
        <el-tooltip content="刷新" placement="top">
          <el-button icon="Refresh" @click="gridDataFun" plain link />
        </el-tooltip>
        <el-tooltip content="全屏" placement="top">
          <el-button icon="FullScreen" @click="fullscreen" plain link />
        </el-tooltip>
        <el-tooltip v-if="searchItem" content="查询" placement="top">
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
    <el-table
      :data="gridData"
      style="width: 100%"
      v-loading="loading"
      size="large"
      :default-sort="defaultSort"
      @sort-change="sortChangeHandle"
      @selection-change="handleSelectionChange"
    >
      <el-table-column v-if="multiSelect" type="selection" width="55" />
      <template v-for="item in gridColumn">
        <template v-if="item.type === 'handle'">
          <el-table-column :label="item.label" :fixed="!item.fixed" :width="item.width">
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
            :sortable="item.sortable ? item.sortable : false"
          >
            <template #default="scope">
              <el-image class="table-image" :src="imageBaseUrl + scope.row.coverPath" fit="cover">
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
      <div class="grid-footer" v-if="pageData">
        <div v-if="multiSelect">
          <el-text>
            已选中
            <el-text type="primary">{{ multipleSelection.length }}</el-text>
            行数据
          </el-text>
        </div>
        <div v-else>
          <el-text> 多选功能禁用</el-text>
        </div>
        <div v-if="page">
          <el-pagination
            v-model:current-page="pageData.current"
            v-model:page-size="pageData.size"
            :page-sizes="[20, 50, 100, 200]"
            size="small"
            background
            layout="total, sizes, prev, pager, next"
            :total="pageData.total || 0"
            @size-change="sizeChangeHandle"
            @current-change="currentChangeHandle"
          />
        </div>
      </div>
    </template>
  </el-card>
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


</style>
