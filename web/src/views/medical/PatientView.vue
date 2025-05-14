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
    <el-descriptions
      title="基础资料"
      :column="2"
      border
    >
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
            就诊卡ID
          </div>
        </template>
        {{ currentView['id'] }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            性别
          </div>
        </template>
        {{ currentView['gender'] == 1 ?'男':'女' }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            年龄
          </div>
        </template>
        {{currentView['age']}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            与账号关系
          </div>
        </template>
        {{ currentView['relationStr'] }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            联系电话
          </div>
        </template>
        {{ currentView['phone'] }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            就诊次数
          </div>
        </template>
        {{ currentView['treatCount'] }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            上次就诊时间
          </div>
        </template>
        {{ currentView['lastTreatDatetime'] }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            创建时间
          </div>
        </template>
        {{ currentView['createDatetime'] }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            状态
          </div>
        </template>
        {{ currentView['statusStr'] }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            身份证号码
          </div>
        </template>
        {{ currentView['idCard'] }}
      </el-descriptions-item>
    </el-descriptions>

    <el-descriptions
      title="就诊记录"
      :column="2"
      border
    >
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            就诊时间
          </div>
        </template>
        kooriookami
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            康复项目
          </div>
        </template>
        18100000000
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            诊断医生
          </div>
        </template>
        Suzhou
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            诊断报告
          </div>
        </template>
        男
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            身份证
          </div>
        </template>
        123456789012345678
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            联系电话
          </div>
        </template>
        18100000000
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