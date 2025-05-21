<script setup lang="ts">

import TableView from "@/views/TableView.vue";
import { onMounted, reactive, ref } from "vue";
import { SelectItem, TableColumnHandle } from "#/conponent.ts";
import { MedicalReport } from "#/entity.ts";
import useAxios from "@/api";
import { ElMessage, ElMessageBox } from "element-plus";
import MainTable from "@/components/MainTable.vue";

const { sendGet, sendPost, sendPut } = useAxios();
const displayControl = reactive({
  viewDialog: false,
  checkDialog: false,
});
const currentView = ref({});

const formSetting = ref([
  {
    title: "基本情况",
    value: "",
    children: [
      {
        label: "个人资料",
        value: "",
        children: [
          {
            label: "职业",
            type: "input",
            value: "",
          },
          {
            label: "运动习惯",
            value: "",
            children: [
              {
                label: "规律运动",
                value: "0",
              },
              {
                label: "偶尔运动",
                value: "1",
              },
              {
                label: "基本不动",
                value: "2",
              },
            ],
          },
          {
            label: "手术史",
            type: "input",
            value: "",
          },
          {
            label: "既往史",
            value: "",
            children: [
              {
                label: "心脏病",
                value: "0",
              },
              {
                label: "高血压",
                value: "1",
              },
              {
                label: "哮喘",
                value: "2",
              },
              {
                label: "慢性肺病",
                value: "3",
              },
              {
                label: "骨质疏松",
                value: "4",
              },
              {
                label: "糖尿病",
                value: "5",
              },
              {
                label: "甲亢",
                value: "6",
              },
              {
                label: "甲减",
                value: "7",
              },
              {
                label: "贫血",
                value: "8",
              },
            ],
          },
          {
            label: "症状部位",
            value: "",
            children: [
              {
                label: "头",
                value: "0",
              },
              {
                label: "颈",
                value: "1",
              },
              {
                label: "胸",
                value: "2",
              },
              {
                label: "肩",
                value: "3",
              },
              {
                label: "肘",
                value: "4",
              },
              {
                label: "腕",
                value: "5",
              },
              {
                label: "手",
                value: "6",
              },
              {
                label: "腰",
                value: "7",
              },
              {
                label: "骨盆",
                value: "8",
              },
              {
                label: "髋",
                value: "9",
              },
              {
                label: "膝",
                value: "10",
              },
              {
                label: "踝",
                value: "11",
              },
              {
                label: "脚",
                value: "12",
              },
            ],
          },
        ],
      },
      {
        label: "持续时间",
        value: "",
        children: [
          {
            type: "input",
            value:""
          },
        ],
      },
      {
        label: "疼痛评分",
        value: "",
        children: [
          {
            type: "input",
            value:""
          },
        ],
      },
      {
        label: "现病史",
        value: "",
        children: [
          {
            type: "input",
            value:""
          },
        ],
      },
    ],
  },
  {
    title: "损伤相关",
    value: "",
    children: [
      {
        label: "手术情况",
        value: "",
        children: [
          {
            children: [
              {
                label: "无手术史",
                value: "0",
              },
              {
                label: "无支具",
                value: "1",
              },
              {
                label: "内固定",
                value: "2",
              },
              {
                label: "外固定",
                value: "3",
              },
            ],
          },
        ],
      },
      {
        label: "肿胀程度",
        value: "",
        children: [
          {
            children: [
              {
                label: "无",
                value: "0",
              },
              {
                label: "轻度",
                value: "1",
              },
              {
                label: "中度",
                value: "2",
              },
              {
                label: "重度",
                value: "3",
              },
            ],
          },
        ],
      },
      {
        label: "瘢痕情况",
        value: "",
        children: [
          {
            children: [
              {
                label: "无",
                value: "0",
              },
              {
                label: "有（红肿/硬结/疼痛）",
                value: "1",
              },
            ],
          },
        ],
      },
    ],
  },
  {
    title: "关节活动度ROM",
    value: "",
    children: [
      {
        label: "肩关节",
        value: "",
        children: [
          {
            label: "屈曲",
            type: "inputGroup",
            value: "",
            children: [
              {
                label: "左",
                value: "",
              },
              {
                label: "右",
                value: "",
              },
            ],
          },
          {
            label: "伸展",
            type: "inputGroup",
            value: "",
            children: [
              {
                label: "左",
                value: "",
              },
              {
                label: "右",
                value: "",
              },
            ],
          },
          {
            label: "内旋",
            type: "inputGroup",
            value: "",
            children: [
              {
                label: "左",
                value: "",
              },
              {
                label: "右",
                value: "",
              },
            ],
          },
          {
            label: "外旋",
            type: "inputGroup",
            value: "",
            children: [
              {
                label: "左",
                value: "",
              },
              {
                label: "右",
                value: "",
              },
            ],
          },
          {
            label: "内收",
            type: "inputGroup",
            value: "",
            children: [
              {
                label: "左",
                value: "",
              },
              {
                label: "右",
                value: "",
              },
            ],
          },
          {
            label: "外展",
            type: "inputGroup",
            value: "",
            children: [
              {
                label: "左",
                value: "",
              },
              {
                label: "右",
                value: "",
              },
            ],
          },
        ],
      },
      {
        label: "肘关节",
        value: "",
        children: [
          {
            label: "屈曲",
            type: "inputGroup",
            value: "",
            children: [
              {
                label: "左",
                value: "",
              },
              {
                label: "右",
                value: "",
              },
            ],
          },
          {
            label: "伸展",
            type: "inputGroup",
            value: "",
            children: [
              {
                label: "左",
                value: "",
              },
              {
                label: "右",
                value: "",
              },
            ],
          },
        ],
      },
      {
        label: "腕关节",
        value: "",
        children: [
          {
            label: "掌屈",
            type: "inputGroup",
            value: "",
            children: [
              {
                label: "左",
                value: "",
              },
              {
                label: "右",
                value: "",
              },
            ],
          },
          {
            label: "背伸",
            type: "inputGroup",
            value: "",
            children: [
              {
                label: "左",
                value: "",
              },
              {
                label: "右",
                value: "",
              },
            ],
          },
          {
            label: "桡偏",
            type: "inputGroup",
            value: "",
            children: [
              {
                label: "左",
                value: "",
              },
              {
                label: "右",
                value: "",
              },
            ],
          },
          {
            label: "尺偏",
            type: "inputGroup",
            value: "",
            children: [
              {
                label: "左",
                value: "",
              },
              {
                label: "右",
                value: "",
              },
            ],
          },
          {
            label: "旋前",
            type: "inputGroup",
            value: "",
            children: [
              {
                label: "左",
                value: "",
              },
              {
                label: "右",
                value: "",
              },
            ],
          },
          {
            label: "旋后",
            type: "inputGroup",
            value: "",
            children: [
              {
                label: "左",
                value: "",
              },
              {
                label: "右",
                value: "",
              },
            ],
          },
        ],
      },
      {
        label: "髋关节",
        value: "",
        children: [
          {
            label: "屈曲",
            type: "inputGroup",
            value: "",
            children: [
              {
                label: "左",
                value: "",
              },
              {
                label: "右",
                value: "",
              },
            ],
          },
          {
            label: "伸展",
            type: "inputGroup",
            value: "",
            children: [
              {
                label: "左",
                value: "",
              },
              {
                label: "右",
                value: "",
              },
            ],
          },
          {
            label: "内旋",
            type: "inputGroup",
            value: "",
            children: [
              {
                label: "左",
                value: "",
              },
              {
                label: "右",
                value: "",
              },
            ],
          },
          {
            label: "外旋",
            type: "inputGroup",
            value: "",
            children: [
              {
                label: "左",
                value: "",
              },
              {
                label: "右",
                value: "",
              },
            ],
          },
          {
            label: "内收",
            type: "inputGroup",
            value: "",
            children: [
              {
                label: "左",
                value: "",
              },
              {
                label: "右",
                value: "",
              },
            ],
          },
          {
            label: "外展",
            type: "inputGroup",
            value: "",
            children: [
              {
                label: "左",
                value: "",
              },
              {
                label: "右",
                value: "",
              },
            ],
          },
        ],
      },
      {
        label: "膝关节",
        value: "",
        children: [
          {
            label: "屈曲",
            type: "inputGroup",
            value: "",
            children: [
              {
                label: "左",
                value: "",
              },
              {
                label: "右",
                value: "",
              },
            ],
          },
          {
            label: "伸展",
            type: "inputGroup",
            value: "",
            children: [
              {
                label: "左",
                value: "",
              },
              {
                label: "右",
                value: "",
              },
            ],
          },
        ],
      },
      {
        label: "踝关节",
        value: "",
        children: [
          {
            label: "背伸",
            type: "inputGroup",
            value: "",
            children: [
              {
                label: "左",
                value: "",
              },
              {
                label: "右",
                value: "",
              },
            ],
          },
          {
            label: "跖屈",
            type: "inputGroup",
            value: "",
            children: [
              {
                label: "左",
                value: "",
              },
              {
                label: "右",
                value: "",
              },
            ],
          },
          {
            label: "内翻",
            type: "inputGroup",
            value: "",
            children: [
              {
                label: "左",
                value: "",
              },
              {
                label: "右",
                value: "",
              },
            ],
          },
          {
            label: "外翻",
            type: "inputGroup",
            value: "",
            children: [
              {
                label: "左",
                value: "",
              },
              {
                label: "右",
                value: "",
              },
            ],
          },
        ],
      },
    ],
  },
  {
    title: "关节稳定性测试",
    value: "",
    children: [
      {
        label: "肩关节",
        value: "",
        children: [
          {
            label: "前侧",
            value: "",
            children: [
              {
                label: "阳性",
                value: "0",
              },
              {
                label: "阴性",
                value: "1",
              },
            ],
          },
          {
            label: "下侧",
            value: "",
            children: [
              {
                label: "阳性",
                value: "0",
              },
              {
                label: "阴性",
                value: "1",
              },
            ],
          },
          {
            label: "后侧",
            value: "",
            children: [
              {
                label: "阳性",
                value: "0",
              },
              {
                label: "阴性",
                value: "1",
              },
            ],
          },
        ],
      },
      {
        label: "肘关节",
        value: "",
        children: [
          {
            label: "内侧稳定试验",
            value: "",
            children: [
              {
                label: "阳性",
                value: "0",
              },
              {
                label: "阴性",
                value: "1",
              },
            ],
          },
          {
            label: "外侧稳定试验",
            value: "",
            children: [
              {
                label: "阳性",
                value: "0",
              },
              {
                label: "阴性",
                value: "1",
              },
            ],
          },
        ],
      },
      {
        label: "腕关节",
        value: "",
        children: [
          {
            label: "琴键试验",
            value: "",
            children: [
              {
                label: "阳性",
                value: "0",
              },
              {
                label: "阴性",
                value: "1",
              },
            ],
          },
          {
            label: "尺腕应力试验",
            value: "",
            children: [
              {
                label: "阳性",
                value: "0",
              },
              {
                label: "阴性",
                value: "1",
              },
            ],
          },
        ],
      },
      {
        label: "髋关节",
        value: "",
        children: [
          {
            label: "长腿牵拉试验",
            value: "",
            children: [
              {
                label: "阳性",
                value: "0",
              },
              {
                label: "阴性",
                value: "1",
              },
            ],
          },
          {
            label: "关节面擦抹测试",
            value: "",
            children: [
              {
                label: "阳性",
                value: "0",
              },
              {
                label: "阴性",
                value: "1",
              },
            ],
          },
        ],
      },
      {
        label: "膝关节",
        value: "",
        children: [
          {
            label: "前抽屉试验",
            value: "",
            children: [
              {
                label: "阳性",
                value: "0",
              },
              {
                label: "阴性",
                value: "1",
              },
            ],
          },
          {
            label: "后抽屉试验",
            value: "",
            children: [
              {
                label: "阳性",
                value: "0",
              },
              {
                label: "阴性",
                value: "1",
              },
            ],
          },
          {
            label: "内侧稳定试验",
            value: "",
            children: [
              {
                label: "阳性",
                value: "0",
              },
              {
                label: "阴性",
                value: "1",
              },
            ],
          },
          {
            label: "外侧稳定试验",
            value: "",
            children: [
              {
                label: "阳性",
                value: "0",
              },
              {
                label: "阴性",
                value: "1",
              },
            ],
          },
        ],
      },
      {
        label: "踝关节",
        value: "",
        children: [
          {
            label: "前抽屉试验",
            value: "",
            children: [
              {
                label: "阳性",
                value: "0",
              },
              {
                label: "阴性",
                value: "1",
              },
            ],
          },
          {
            label: "距骨倾斜试验",
            value: "",
            children: [
              {
                label: "阳性",
                value: "0",
              },
              {
                label: "阴性",
                value: "1",
              },
            ],
          },
        ],
      },
    ],
  },
  {
    title: "静态姿势（背面）",
    value: "",
    children: [
      {
        label: "跟骨内外侧是否平均着地",
        value: "",
        children: [
          {
            label: "左侧",
            value: "",
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "外",
                value: "1",
              },
              {
                label: "内",
                value: "2",
              },
            ],
          },
          {
            label: "右侧",
            value: "",
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "外",
                value: "1",
              },
              {
                label: "内",
                value: "2",
              },
            ],
          },
        ],
      },
      {
        label: "脚掌有无外旋",
        value: "",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "有（右侧/左侧/双侧）",
                value: "1",
              },
            ],
          },
        ],
      },
      {
        label: "双侧臀大肌宽度是否对称",
        value: "",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "左宽",
                value: "1",
              },
              {
                label: "右宽",
                value: "2",
              },
            ],
          },
        ],
      },
      {
        label: "左右肋骨下缘是否一致",
        value: "",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "左侧低",
                value: "1",
              },
              {
                label: "右侧低",
                value: "2",
              },
              {
                label: "双侧",
                value: "3",
              },
            ],
          },
        ],
      },
      {
        label: "体前屈下观看脊柱有没有正常弧度",
        value: "",
        children: [
          {
            label: "颈椎",
            value: "",
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "变曲",
                value: "1",
              },
              {
                label: "变直",
                value: "2",
              },
              {
                label: "左凸",
                value: "3",
              },
              {
                label: "凸",
                value: "4",
              },
            ],
          },
          {
            label: "胸椎",
            value: "",
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "变曲",
                value: "1",
              },
              {
                label: "变直",
                value: "2",
              },
              {
                label: "左凸",
                value: "3",
              },
              {
                label: "凸",
                value: "4",
              },
            ],
          },
          {
            label: "腰椎",
            value: "",
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "变曲",
                value: "1",
              },
              {
                label: "变直",
                value: "2",
              },
              {
                label: "左凸",
                value: "3",
              },
              {
                label: "凸",
                value: "4",
              },
            ],
          },
        ],
      },
      {
        label: "脊柱有无异常弧度",
        value: "",
        children: [
          {
            children: [
              {
                label: "无",
                value: "0",
              },
              {
                label: "颈胸交界处过曲",
                value: "1",
              },
              {
                label: "胸腰交界处过曲",
                value: "2",
              },
            ],
          },
        ],
      },
    ],
  },
  {
    title: "静态姿势（侧面）",
    value: "",
    children: [
      {
        label: "肋骨有没有前倾",
        value: "",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "有",
                value: "1",
              },
            ],
          },
        ],
      },
      {
        label: "有没有大肚腩",
        value: "",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "有",
                value: "1",
              },
            ],
          },
        ],
      },
    ],
  },
  {
    title: "呼吸方式",
    value: "",
    children: [
      {
        children: [
          {
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "无明显起伏",
                value: "1",
              },
              {
                label: "单腹部起伏",
                value: "2",
              },
              {
                label: "耸肩呼吸",
                value: "3",
              },
            ],
          },
        ],
      },
    ],
  },
  {
    title: "动态评估（站立体前屈）",
    value: "",
    children: [
      {
        label: "下弯时上半身有无偏离中线",
        value: "",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "偏左",
                value: "1",
              },
              {
                label: "偏右",
                value: "2",
              },
            ],
          },
        ],
      },
      {
        label: "下弯时骨盆有无变宽",
        value: "",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "左变宽",
                value: "1",
              },
              {
                label: "右变宽",
                value: "2",
              },
            ],
          },
        ],
      },
      {
        label: "颈胸腰椎是否有联动",
        value: "",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "无",
                value: "1",
              },
            ],
          },
        ],
      },
      {
        label: "下弯时有无过凸的部分",
        value: "",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "颈胸交界",
                value: "1",
              },
              {
                label: "胸椎",
                value: "2",
              },
              {
                label: "胸腰交界",
                value: "3",
              },
              {
                label: "腰椎",
                value: "4",
              },
            ],
          },
        ],
      },
      {
        label: "下弯时双手指间可触及部分",
        value: "",
        children: [
          {
            children: [
              {
                label: "大腿中段",
                value: "0",
              },
              {
                label: "髌骨",
                value: "1",
              },
              {
                label: "胫骨粗隆",
                value: "2",
              },
              {
                label: "胫骨一半",
                value: "3",
              },
              {
                label: "踝关节",
                value: "4",
              },
              {
                label: "脚趾",
                value: "5",
              },
              {
                label: "地面",
                value: "6",
              },
            ],
          },
        ],
      },
      {
        label: "下弯时肋骨是否能靠近大腿",
        value: "",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "不能",
                value: "1",
              },
            ],
          },
        ],
      },
    ],
  },
  {
    title: "动态评估（正面）",
    value: "",
    children: [
      {
        label: "有无膝内/外翻",
        value: "",
        children: [
          {
            label: "左侧",
            value: "",
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "内翻",
                value: "1",
              },
              {
                label: "外翻",
                value: "2",
              },
            ],
          },
          {
            label: "右侧",
            value: "",
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "内翻",
                value: "1",
              },
              {
                label: "外翻",
                value: "2",
              },
            ],
          },
        ],
      },
      {
        label: "有无足外翻",
        value: "",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "左侧外翻",
                value: "1",
              },
              {
                label: "右侧外翻",
                value: "2",
              },
              {
                label: "双侧外翻",
                value: "3",
              },
            ],
          },
        ],
      },
    ],
  },
  {
    title: "动态评估（侧面）",
    value: "",
    children: [
      {
        label: "能否双侧足跟着地蹲到底",
        value: "",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "不能",
                value: "1",
              },
            ],
          },
        ],
      },
      {
        label: "有没有上半身前倾",
        value: "",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "有在髋屈30°时",
                value: "1",
              },
              {
                label: "有在髋屈60°时",
                value: "2",
              },
              {
                label: "有在髋屈90°时",
                value: "3",
              },
            ],
          },
        ],
      },
    ],
  },
  {
    title: "动态评估（背面）",
    value: "",
    children: [
      {
        label: "跟骨是否垂直",
        value: "",
        children: [
          {
            label: "左侧",
            value: "",
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "内翻",
                value: "1",
              },
              {
                label: "外翻",
                value: "2",
              },
            ],
          },
          {
            label: "右侧",
            value: "",
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "内翻",
                value: "1",
              },
              {
                label: "外翻",
                value: "2",
              },
            ],
          },
        ],
      },
      {
        label: "蹲到底时骨盆有没有横移",
        value: "",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "向左",
                value: "1",
              },
              {
                label: "向左右",
                value: "2",
              },
            ],
          },
        ],
      },
    ],
  },
  {
    title: "下肢",
    value: "",
    children: [
      {
        label: "直腿上提",
        value: "",
        children: [
          {
            label: "左侧",
            type: "input",
            value: "",
          },
          {
            label: "左侧",
            type: "input",
            value: "",
          },
        ],
      },
      {
        label: "髋屈",
        value: "",
        children: [
          {
            label: "左侧",
            type: "input",
            value: "",
          },
          {
            label: "左侧",
            type: "input",
            value: "",
          },
        ],
      },
      {
        label: "髋内旋",
        value: "",
        children: [
          {
            label: "左侧",
            type: "input",
            value: "",
          },
          {
            label: "左侧",
            type: "input",
            value: "",
          },
        ],
      },
      {
        label: "足跟到臀部距离",
        value: "",
        children: [
          {
            label: "左侧",
            type: "input",
            value: "",
          },
          {
            label: "左侧",
            type: "input",
            value: "",
          },
        ],
      },
      {
        label: "足大脚趾背伸 ",
        value: "",
        children: [
          {
            label: "左侧",
            value: "",
            children: [
              {
                label: "45°",
                value: "0",
              },
              {
                label: "不足45°",
                value: "1",
              },
            ],
          },
          {
            label: "右侧",
            value: "",
            children: [
              {
                label: "45°",
                value: "0",
              },
              {
                label: "不足45°",
                value: "1",
              },
            ],
          },
        ],
      },
    ],
  },
  {
    title: "上肢",
    value: "",
    children: [
      {
        label: "站立摸大椎",
        value: "",
        children: [
          {
            label: "左侧",
            value: "",
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "可以摸到但是有身体代偿",
                value: "1",
              },
              {
                label: "不能完成",
                value: "2",
              },
            ],
          },
          {
            label: "右侧",
            value: "",
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "可以摸到但是有身体代偿",
                value: "1",
              },
              {
                label: "不能完成",
                value: "2",
              },
            ],
          },
        ],
      },
      {
        label: "站立摸背后",
        value: "",
        children: [
          {
            label: "左侧",
            value: "",
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "可以摸到对侧肩胛骨下角但是有身体代偿",
                value: "1",
              },
              {
                label: "不能完成",
                value: "2",
              },
            ],
          },
          {
            label: "右侧",
            value: "",
            children: [
              {
                label: "正常",
                value: "0",
              },
              {
                label: "可以摸到对侧肩胛骨下角但是有身体代偿",
                value: "1",
              },
              {
                label: "不能完成",
                value: "2",
              },
            ],
          },
        ],
      },
      {
        label: "卧位肩内旋",
        value: "",
        children: [
          {
            label: "左侧",
            type: "input",
            value: "",
          },
          {
            label: "右侧",
            type: "input",
            value: "",
          },
        ],
      },
      {
        label: "卧位肩外旋",
        value: "",
        children: [
          {
            label: "左侧",
            type: "input",
            value: "",
          },
          {
            label: "右侧",
            type: "input",
            value: "",
          },
        ],
      },
      {
        label: "屈肘前臂旋前",
        value: "",
        children: [
          {
            label: "左侧",
            value: "",
            children: [
              {
                label: "90°",
                value: "0",
              },
              {
                label: "不足90°",
                value: "1",
              },
            ],
          },
          {
            label: "右侧",
            value: "",
            children: [
              {
                label: "90°",
                value: "0",
              },
              {
                label: "不足90°",
                value: "1",
              },
            ],
          },
        ],
      },
      {
        label: "屈肘前臂旋后",
        value: "",
        children: [
          {
            label: "左侧",
            value: "",
            children: [
              {
                label: "90°",
                value: "0",
              },
              {
                label: "不足90°",
                value: "1",
              },
            ],
          },
          {
            label: "右侧",
            value: "",
            children: [
              {
                label: "90°",
                value: "0",
              },
              {
                label: "不足90°",
                value: "1",
              },
            ],
          },
        ],
      },
    ],
  },
]);

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

const addReport = () => {

  console.log("report",formSetting.value);

  /*sendPost("/medical/manage/report/add", addReportForm.value)
    .then((res) => {
      ElMessage.success("提交成功");
      hideDialog();
    })
    .catch((err) => {
      ElMessage.error("提交失败：" + err.message);
    });*/
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

onMounted(() => {
});
</script>

<template>
  <MainTable
    :row-btns="columnBtns"
  >

  </MainTable>
  <el-drawer
    v-model="displayControl.checkDialog"
    title="骨科康复体格检查评估表"
    direction="rtl"
    :before-close="hideDialog"
    size="800"
  >
    <el-descriptions
      :column="4"
      class="queue_info_desc"
    >
      <el-descriptions-item label="姓名">{{ currentView["patientName"] }}</el-descriptions-item>
      <el-descriptions-item label="性别">{{ currentView["patientGenderStr"] }}</el-descriptions-item>
      <el-descriptions-item label="年龄">{{ currentView["patientAge"] }}</el-descriptions-item>
      <el-descriptions-item label="手机号">{{ currentView["patientPhone"] }}</el-descriptions-item>
    </el-descriptions>
    <el-form
      ref="formRef"
      :model="formSetting"
      label-width="200px"
      class="examine-form"
    >
      <template v-for="group in formSetting" key="id">
        <el-divider>{{ group.title }}</el-divider>
        <el-form-item :label="item['label']" v-for="item in group.children" key="id">
          <div class="joint-movement" v-for="detail in item.children">
            <div class="movement-label">{{ detail['label'] }}</div>
            <el-input v-if="detail['type']==='input'" v-model="detail['value']" />
            <el-row v-else-if="detail['type']==='inputGroup'">
              <template v-for="opt in detail['children']">
                <el-col :span="4">{{ opt.label }}</el-col>
                <el-col :span="8">
                  <el-input v-model="opt.value" />
                </el-col>
              </template>
            </el-row>
            <el-radio-group v-else v-model="detail['value']">
              <el-radio :value="opt.value" v-for="opt in detail['children']">{{ opt.label }}</el-radio>
            </el-radio-group>
          </div>
        </el-form-item>
      </template>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="hideDialog">关闭</el-button>
        <el-button @click="addReport" type="primary">提交</el-button>
      </div>
    </template>
  </el-drawer>
</template>

<style scoped lang="scss">
.examine-form {
  padding: 20px;

  .joint-movement {
    width: 100%;
    display: flex;
    align-items: center;
    gap: 10px;
    margin-bottom: 10px;

    .movement-label {
      width: 100px;
      text-align: right;
    }

    .test-description {
      color: #666;
      font-size: 12px;
      margin-left: 10px;
    }
  }
}

.dialog-footer {
  padding: 20px;
  text-align: right;
}
</style>