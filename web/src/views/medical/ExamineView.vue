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
    title: "损伤相关",
    children: [
      {
        label: "手术情况",
        children: [
          {
            children: [
              {
                label: "无手术史",
                value: "",
              },
              {
                label: "无支具",
                value: "",
              },
              {
                label: "内固定",
                value: "",
              },
              {
                label: "外固定",
                value: "",
              },
            ],
          },
        ],
      },
      {
        label: "肿胀程度",
        children: [
          {
            children: [
              {
                label: "无",
                value: "",
              },
              {
                label: "轻度",
                value: "",
              },
              {
                label: "中度",
                value: "",
              },
              {
                label: "重度",
                value: "",
              },
            ],
          },
        ],
      },
      {
        label: "瘢痕情况",
        children: [
          {
            children: [
              {
                label: "无",
                value: "",
              },
              {
                label: "有（红肿/硬结/疼痛）",
                value: "",
              },
            ],
          },
        ],
      },
    ],
  },
  {
    title: "关节活动度ROM",
    children: [
      {
        label: "肩关节",
        children: [
          {
            label: "屈曲",
            type: "inputGroup",
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
        children: [
          {
            label: "屈曲",
            type: "inputGroup",
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
        children: [
          {
            label: "掌屈",
            type: "inputGroup",
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
        children: [
          {
            label: "屈曲",
            type: "inputGroup",
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
        children: [
          {
            label: "屈曲",
            type: "inputGroup",
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
        children: [
          {
            label: "背伸",
            type: "inputGroup",
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
    children: [
      {
        label: "肩关节",
        children: [
          {
            label: "前侧",
            children: [
              {
                label: "阳性",
                value: "",
              },
              {
                label: "阴性",
                value: "",
              },
            ],
          },
          {
            label: "下侧",
            children: [
              {
                label: "阳性",
                value: "",
              },
              {
                label: "阴性",
                value: "",
              },
            ],
          },
          {
            label: "后侧",
            children: [
              {
                label: "阳性",
                value: "",
              },
              {
                label: "阴性",
                value: "",
              },
            ],
          },
        ],
      },
      {
        label: "肘关节",
        children: [
          {
            label: "内侧稳定试验",
            children: [
              {
                label: "阳性",
                value: "",
              },
              {
                label: "阴性",
                value: "",
              },
            ],
          },
          {
            label: "外侧稳定试验",
            children: [
              {
                label: "阳性",
                value: "",
              },
              {
                label: "阴性",
                value: "",
              },
            ],
          },

        ],
      },
      {
        label: "腕关节",
        children: [
          {
            label: "琴键试验",
            children: [
              {
                label: "阳性",
                value: "",
              },
              {
                label: "阴性",
                value: "",
              },
            ],
          },
          {
            label: "尺腕应力试验",
            children: [
              {
                label: "阳性",
                value: "",
              },
              {
                label: "阴性",
                value: "",
              },
            ],
          },

        ],
      },
      {
        label: "髋关节",
        children: [
          {
            label: "长腿牵拉试验",
            children: [
              {
                label: "阳性",
                value: "",
              },
              {
                label: "阴性",
                value: "",
              },
            ],
          },
          {
            label: "关节面擦抹测试",
            children: [
              {
                label: "阳性",
                value: "",
              },
              {
                label: "阴性",
                value: "",
              },
            ],
          },

        ],
      },
      {
        label: "膝关节",
        children: [
          {
            label: "前抽屉试验",
            children: [
              {
                label: "阳性",
                value: "",
              },
              {
                label: "阴性",
                value: "",
              },
            ],
          },
          {
            label: "后抽屉试验",
            children: [
              {
                label: "阳性",
                value: "",
              },
              {
                label: "阴性",
                value: "",
              },
            ],
          },
          {
            label: "内侧稳定试验",
            children: [
              {
                label: "阳性",
                value: "",
              },
              {
                label: "阴性",
                value: "",
              },
            ],
          },
          {
            label: "外侧稳定试验",
            children: [
              {
                label: "阳性",
                value: "",
              },
              {
                label: "阴性",
                value: "",
              },
            ],
          },
        ],
      },
      {
        label: "踝关节",
        children: [
          {
            label: "前抽屉试验",
            children: [
              {
                label: "阳性",
                value: "",
              },
              {
                label: "阴性",
                value: "",
              },
            ],
          },
          {
            label: "距骨倾斜试验",
            children: [
              {
                label: "阳性",
                value: "",
              },
              {
                label: "阴性",
                value: "",
              },
            ],
          },

        ],
      },
    ],
  },
  {
    title: "静态姿势（背面）",
    children: [
      {
        label: "跟骨内外侧是否平均着地",
        children: [
          {
            label: "左侧",
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "外",
                value: "",
              },
              {
                label: "内",
                value: "",
              },
            ],
          },
          {
            label: "右侧",
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "外",
                value: "",
              },
              {
                label: "内",
                value: "",
              },
            ],
          },
        ],
      },
      {
        label: "脚掌有无外旋",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "有（右侧/左侧/双侧）",
                value: "",
              },
            ],
          },
        ],
      },
      {
        label: "双侧臀大肌宽度是否对称",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "左宽",
                value: "",
              },
              {
                label: "右宽",
                value: "",
              },

            ],
          },
        ],
      },
      {
        label: "左右肋骨下缘是否一致",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "左侧低",
                value: "",
              },
              {
                label: "右侧低",
                value: "",
              },
              {
                label: "双侧",
                value: "",
              },
            ],
          },
        ],
      },
      {
        label: "体前屈下观看脊柱有没有正常弧度",
        children: [
          {
            label: "颈椎",
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "变曲",
                value: "",
              },
              {
                label: "变直",
                value: "",
              },
              {
                label: "左凸",
                value: "",
              },
              {
                label: "凸",
                value: "",
              },
            ],
          },
          {
            label: "胸椎",
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "变曲",
                value: "",
              },
              {
                label: "变直",
                value: "",
              },
              {
                label: "左凸",
                value: "",
              },
              {
                label: "凸",
                value: "",
              },
            ],
          },
          {
            label: "腰椎",
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "变曲",
                value: "",
              },
              {
                label: "变直",
                value: "",
              },
              {
                label: "左凸",
                value: "",
              },
              {
                label: "凸",
                value: "",
              },
            ],
          },
        ],
      },
      {
        label: "脊柱有无异常弧度",
        children: [
          {
            children: [
              {
                label: "无",
                value: "",
              },
              {
                label: "颈胸交界处过曲",
                value: "",
              },
              {
                label: "胸腰交界处过曲",
                value: "",
              },
            ],
          },
        ],
      },
    ],
  },
  {
    title: "静态姿势（侧面）",
    children: [
      {
        label: "肋骨有没有前倾",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "有",
                value: "",
              },
            ],
          },
        ],
      },
      {
        label: "有没有大肚腩",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "有",
                value: "",
              },
            ],
          },
        ],
      },
    ],
  },
  {
    title: "呼吸方式",
    children: [
      {
        children: [
          {
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "无明显起伏",
                value: "",
              },
              {
                label: "单腹部起伏",
                value: "",
              },
              {
                label: "耸肩呼吸",
                value: "",
              },
            ],
          },
        ],
      },
    ],
  },
  {
    title: "动态评估（站立体前屈）",
    children: [
      {
        label: "下弯时上半身有无偏离中线",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "偏左",
                value: "",
              },
              {
                label: "偏右",
                value: "",
              },

            ],
          },
        ],
      },
      {
        label: "下弯时骨盆有无变宽",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "左变宽",
                value: "",
              },
              {
                label: "右变宽",
                value: "",
              },

            ],
          },
        ],
      },
      {
        label: "颈胸腰椎是否有联动",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "无",
                value: "",
              },
            ],
          },
        ],
      },
      {
        label: "下弯时有无过凸的部分",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "颈胸交界",
                value: "",
              },
              {
                label: "胸椎",
                value: "",
              },
              {
                label: "胸腰交界",
                value: "",
              },
              {
                label: "腰椎",
                value: "",
              },
            ],
          },
        ],
      },
      {
        label: "下弯时双手指间可触及部分",
        children: [
          {
            children: [
              {
                label: "大腿中段",
                value: "",
              },
              {
                label: "髌骨",
                value: "",
              },
              {
                label: "胫骨粗隆",
                value: "",
              },
              {
                label: "胫骨一半",
                value: "",
              },
              {
                label: "踝关节",
                value: "",
              },
              {
                label: "脚趾",
                value: "",
              },
              {
                label: "地面",
                value: "",
              },
            ],
          },
        ],
      },
      {
        label: "下弯时肋骨是否能靠近大腿",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "不能",
                value: "",
              },
            ],
          },
        ],
      },
    ],
  },
  {
    title: "动态评估（正面）",
    children: [
      {
        label: "有无膝内/外翻",
        children: [
          {
            label: "左侧",
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "内翻",
                value: "",
              },
              {
                label: "外翻",
                value: "",
              },
            ],
          },
          {
            label: "右侧",
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "内翻",
                value: "",
              },
              {
                label: "外翻",
                value: "",
              },
            ],
          },
        ],
      },
      {
        label: "有无足外翻",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "左侧外翻",
                value: "",
              },
              {
                label: "右侧外翻",
                value: "",
              },
              {
                label: "双侧外翻",
                value: "",
              },
            ],
          },
        ],
      },
    ],
  },
  {
    title: "动态评估（侧面）",
    children: [
      {
        label: "能否双侧足跟着地蹲到底",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "不能",
                value: "",
              },
            ],
          },
        ],
      },
      {
        label: "有没有上半身前倾",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "有在髋屈30°时",
                value: "",
              },
              {
                label: "有在髋屈60°时",
                value: "",
              },
              {
                label: "有在髋屈90°时",
                value: "",
              },
            ],
          },
        ],
      },
    ],
  },
  {
    title: "动态评估（背面）",
    children: [
      {
        label: "跟骨是否垂直",
        children: [
          {
            label: "左侧",
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "内翻",
                value: "",
              },
              {
                label: "外翻",
                value: "",
              },
            ],
          },
          {
            label: "右侧",
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "内翻",
                value: "",
              },
              {
                label: "外翻",
                value: "",
              },
            ],
          },
        ],
      },
      {
        label: "蹲到底时骨盆有没有横移",
        children: [
          {
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "向左",
                value: "",
              },
              {
                label: "向左右",
                value: "",
              },
            ],
          },
        ],
      },
    ],
  },
  {
    title: "下肢",
    children: [
      {
        label: "直腿上提",
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
        children: [
          {
            label: "左侧",
            children: [
              {
                label: "45°",
                value: "",
              },
              {
                label: "不足45°",
                value: "",
              },

            ],
          },
          {
            label: "右侧",
            children: [
              {
                label: "45°",
                value: "",
              },
              {
                label: "不足45°",
                value: "",
              },

            ],
          },
        ],
      },
    ],
  },
  {
    title: "上肢",
    children: [
      {
        label: "站立摸大椎",
        children: [
          {
            label: "左侧",
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "可以摸到但是有身体代偿",
                value: "",
              },
              {
                label: "不能完成",
                value: "",
              },
            ],
          },
          {
            label: "右侧",
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "可以摸到但是有身体代偿",
                value: "",
              },
              {
                label: "不能完成",
                value: "",
              },
            ],
          },
        ],
      },
      {
        label: "站立摸背后",
        children: [
          {
            label: "左侧",
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "可以摸到对侧肩胛骨下角但是有身体代偿",
                value: "",
              },
              {
                label: "不能完成",
                value: "",
              },
            ],
          },
          {
            label: "右侧",
            children: [
              {
                label: "正常",
                value: "",
              },
              {
                label: "可以摸到对侧肩胛骨下角但是有身体代偿",
                value: "",
              },
              {
                label: "不能完成",
                value: "",
              },
            ],
          },
        ],
      },
      {
        label: "卧位肩内旋",
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
        children: [
          {
            label: "左侧",
            children: [
              {
                label: "90°",
                value: "",
              },
              {
                label: "不足90°",
                value: "",
              },
            ],
          },
          {
            label: "右侧",
            children: [
              {
                label: "90°",
                value: "",
              },
              {
                label: "不足90°",
                value: "",
              },
            ],
          },
        ],
      },
      {
        label: "屈肘前臂旋后",
        children: [
          {
            label: "左侧",
            children: [
              {
                label: "90°",
                value: "",
              },
              {
                label: "不足90°",
                value: "",
              },
            ],
          },
          {
            label: "右侧",
            children: [
              {
                label: "90°",
                value: "",
              },
              {
                label: "不足90°",
                value: "",
              },
            ],
          },
        ],
      },
    ],
  },
]);

const addReportForm = ref<MedicalReport>({
  patientId: "",
  name: "",
  gender: "",
  age: "",
  phone: "",
  occupation: "",
  exerciseHabits: [] as string[],
  surgeryHistory: "",
  medicalHistory: [] as string[],
  symptomLocation: [] as string[],
  duration: "",
  painVAS: "",
  currentHistory: "",
  doctorId: "",
  examineDate: "",
  // 关节活动度
  shoulder: {
    flexion: { left: "", right: "" },
    extension: { left: "", right: "" },
    internalRotation: { left: "", right: "" },
    externalRotation: { left: "", right: "" },
    adduction: { left: "", right: "" },
    abduction: { left: "", right: "" },
  },
  elbow: {
    flexion: { left: "", right: "" },
    extension: { left: "", right: "" },
  },
  wrist: {
    palmarFlexion: { left: "", right: "" },
    dorsiflexion: { left: "", right: "" },
    radialDeviation: { left: "", right: "" },
    ulnarDeviation: { left: "", right: "" },
    pronation: { left: "", right: "" },
    supination: { left: "", right: "" },
  },
  hip: {
    flexion: { left: "", right: "" },
    extension: { left: "", right: "" },
    abduction: { left: "", right: "" },
    adduction: { left: "", right: "" },
    internalRotation: { left: "", right: "" },
    externalRotation: { left: "", right: "" },
  },
  knee: {
    flexion: { left: "", right: "" },
    extension: { left: "", right: "" },
  },
  ankle: {
    dorsiflexion: { left: "", right: "" },
    plantarflexion: { left: "", right: "" },
    inversion: { left: "", right: "" },
    eversion: { left: "", right: "" },
  },
  // 关节稳定性测试
  shoulderStability: {
    anterior: "",
    posterior: "",
    inferior: "",
  },
  elbowStability: {
    valgus: "",
    varus: "",
  },
  wristStability: {
    ulnar: "",
    radial: "",
  },
  hipStability: {
    anterior: "",
    posterior: "",
  },
  kneeStability: {
    anteriorDrawer: "",
    posteriorDrawer: "",
    varus: "",
    valgus: "",
  },
  ankleStability: {
    anteriorDrawer: "",
    inversion: "",
    eversion: "",
  },
} as MedicalReport);

const doctorOptions = ref<SelectItem[]>([]);

const exerciseHabitsOptions = [
  { label: "规律运动", value: "regular" },
  { label: "偶尔运动", value: "occasional" },
  { label: "基本不动", value: "sedentary" },
];

const medicalHistoryOptions = [
  { label: "心脏病", value: "heart" },
  { label: "高血压", value: "hypertension" },
  { label: "哮喘", value: "asthma" },
  { label: "慢性肺病", value: "lung" },
  { label: "骨质疏松", value: "osteoporosis" },
  { label: "糖尿病", value: "diabetes" },
  { label: "甲亢", value: "hyperthyroidism" },
  { label: "甲减", value: "hypothyroidism" },
  { label: "贫血", value: "anemia" },
];

const symptomLocationOptions = [
  { label: "头", value: "head" },
  { label: "颈", value: "neck" },
  { label: "胸", value: "chest" },
  { label: "肩", value: "shoulder" },
  { label: "肘", value: "elbow" },
  { label: "腕", value: "wrist" },
  { label: "手", value: "hand" },
  { label: "腰", value: "waist" },
  { label: "骨盆", value: "pelvis" },
  { label: "髋", value: "hip" },
  { label: "膝", value: "knee" },
  { label: "踝", value: "ankle" },
  { label: "脚", value: "foot" },
];

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
  if (!addReportForm.value.patientId) {
    ElMessage.error("请选择患者");
    return;
  }
  if (!addReportForm.value.doctorId) {
    ElMessage.error("请选择医生");
    return;
  }
  if (!addReportForm.value.examineDate) {
    ElMessage.error("请选择检查日期");
    return;
  }

  sendPost("/medical/manage/report/add", addReportForm.value)
    .then((res) => {
      ElMessage.success("提交成功");
      hideDialog();
    })
    .catch((err) => {
      ElMessage.error("提交失败：" + err.message);
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
    <el-form
      ref="formRef"
      :model="addReportForm"
      label-width="200px"
      class="examine-form"
    >
      <template v-for="group in formSetting" key="id">
        <el-divider>{{ group.title }}</el-divider>
        <el-form-item :label="item.label" v-for="item in group.children" key="id">
          <div class="joint-movement" v-for="detail in item.children">
            <div class="movement-label">{{ detail.label }}</div>
            <el-input v-if="detail.type==='input'" />
            <el-row v-else-if="detail.type==='inputGroup'">
              <template v-for="opt in detail.children">
               <el-col :span="4">{{opt.label}}</el-col>
                <el-col :span="8"><el-input /></el-col>
              </template>
            </el-row>
            <el-radio-group v-else v-model="addReportForm.elbowStability.varus">
              <el-radio label="positive" v-for="opt in detail.children">{{ opt.label }}</el-radio>
            </el-radio-group>
          </div>
        </el-form-item>
      </template>
    </el-form>
    <!--    <el-form
          ref="formRef"
          :model="addReportForm"
          label-width="200px"
          class="examine-form"
        >
          &lt;!&ndash; 基本信息 &ndash;&gt;
          <el-divider>基本信息</el-divider>
          <el-form-item label="姓名">
            <el-input v-model="addReportForm.name" />
          </el-form-item>
          <el-form-item label="性别">
            <el-select v-model="addReportForm.gender">
              <el-option label="男" value="male" />
              <el-option label="女" value="female" />
            </el-select>
          </el-form-item>
          <el-form-item label="年龄">
            <el-input v-model="addReportForm.age" type="number" />
          </el-form-item>
          <el-form-item label="手机号">
            <el-input v-model="addReportForm.phone" />
          </el-form-item>
          <el-form-item label="职业">
            <el-input v-model="addReportForm.occupation" />
          </el-form-item>
          <el-form-item label="运动习惯">
            <el-checkbox-group v-model="addReportForm.exerciseHabits">
              <el-checkbox
                v-for="item in exerciseHabitsOptions"
                :key="item.value"
                :label="item.value"
              >
                {{ item.label }}
              </el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item label="手术史">
            <el-input v-model="addReportForm.surgeryHistory" type="textarea" />
          </el-form-item>
          <el-form-item label="既往史">
            <el-checkbox-group v-model="addReportForm.medicalHistory">
              <el-checkbox
                v-for="item in medicalHistoryOptions"
                :key="item.value"
                :label="item.value"
              >
                {{ item.label }}
              </el-checkbox>
            </el-checkbox-group>
          </el-form-item>

          &lt;!&ndash; 症状信息 &ndash;&gt;
          <el-divider>症状信息</el-divider>
          <el-form-item label="症状部位">
            <el-checkbox-group v-model="addReportForm.symptomLocation">
              <el-checkbox
                v-for="item in symptomLocationOptions"
                :key="item.value"
                :label="item.value"
              >
                {{ item.label }}
              </el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item label="持续时间">
            <div>
              <el-input v-model="addReportForm.duration" />
            </div>
          </el-form-item>
          <el-form-item label="疼痛VAS评分">
            <div>
              <el-input-number
                v-model="addReportForm.painVAS"
                :min="0"
                :max="10"
                :step="1"
              />
            </div>
          </el-form-item>
          <el-form-item label="现病史">
            <div>
              <el-input
                v-model="addReportForm.currentHistory"
                type="textarea"
                :rows="4"
              />
            </div>
          </el-form-item>

          &lt;!&ndash; 关节活动度 &ndash;&gt;
          <el-divider>关节活动度(ROM)</el-divider>

          &lt;!&ndash; 肩关节 &ndash;&gt;
          <el-form-item label="肩关节">
            <div class="joint-movement">
              <div class="movement-label">屈曲</div>
              <div>
                <el-input v-model="addReportForm.shoulder.flexion.left" placeholder="左" />
              </div>
              <div>
                <el-input v-model="addReportForm.shoulder.flexion.right" placeholder="右" />
              </div>
            </div>
            <div class="joint-movement">
              <div class="movement-label">伸展</div>
              <div>
                <el-input v-model="addReportForm.shoulder.extension.left" placeholder="左" />
              </div>
              <div>
                <el-input v-model="addReportForm.shoulder.extension.right" placeholder="右" />
              </div>
            </div>
            <div class="joint-movement">
              <div class="movement-label">内旋</div>
              <div>
                <el-input v-model="addReportForm.shoulder.internalRotation.left" placeholder="左" />
              </div>
              <div>
                <el-input v-model="addReportForm.shoulder.internalRotation.right" placeholder="右" />
              </div>
            </div>
            <div class="joint-movement">
              <div class="movement-label">外旋</div>
              <div>
                <el-input v-model="addReportForm.shoulder.externalRotation.left" placeholder="左" />
              </div>
              <div>
                <el-input v-model="addReportForm.shoulder.externalRotation.right" placeholder="右" />
              </div>
            </div>
            <div class="joint-movement">
              <div class="movement-label">内收</div>
              <div>
                <el-input v-model="addReportForm.shoulder.adduction.left" placeholder="左" />
              </div>
              <div>
                <el-input v-model="addReportForm.shoulder.adduction.right" placeholder="右" />
              </div>
            </div>
            <div class="joint-movement">
              <div class="movement-label">外展</div>
              <div>
                <el-input v-model="addReportForm.shoulder.abduction.left" placeholder="左" />
              </div>
              <div>
                <el-input v-model="addReportForm.shoulder.abduction.right" placeholder="右" />
              </div>
            </div>
          </el-form-item>

          &lt;!&ndash; 肘关节 &ndash;&gt;
          <el-form-item label="肘关节">
            <div class="joint-movement">
              <div class="movement-label">屈曲</div>
              <div>
                <el-input v-model="addReportForm.elbow.flexion.left" placeholder="左" />
              </div>
              <div>
                <el-input v-model="addReportForm.elbow.flexion.right" placeholder="右" />
              </div>
            </div>
            <div class="joint-movement">
              <div class="movement-label">伸展</div>
              <div>
                <el-input v-model="addReportForm.elbow.extension.left" placeholder="左" />
              </div>
              <div>
                <el-input v-model="addReportForm.elbow.extension.right" placeholder="右" />
              </div>
            </div>
          </el-form-item>

          &lt;!&ndash; 腕关节 &ndash;&gt;
          <el-form-item label="腕关节">
            <div class="joint-movement">
              <div class="movement-label">掌屈</div>
              <div>
                <el-input v-model="addReportForm.wrist.palmarFlexion.left" placeholder="左" />
              </div>
              <div>
                <el-input v-model="addReportForm.wrist.palmarFlexion.right" placeholder="右" />
              </div>
            </div>
            <div class="joint-movement">
              <div class="movement-label">背伸</div>
              <div>
                <el-input v-model="addReportForm.wrist.dorsiflexion.left" placeholder="左" />
              </div>
              <div>
                <el-input v-model="addReportForm.wrist.dorsiflexion.right" placeholder="右" />
              </div>
            </div>
            <div class="joint-movement">
              <div class="movement-label">桡偏</div>
              <div>
                <el-input v-model="addReportForm.wrist.radialDeviation.left" placeholder="左" />
              </div>
              <div>
                <el-input v-model="addReportForm.wrist.radialDeviation.right" placeholder="右" />
              </div>
            </div>
            <div class="joint-movement">
              <div class="movement-label">尺偏</div>
              <div>
                <el-input v-model="addReportForm.wrist.ulnarDeviation.left" placeholder="左" />
              </div>
              <div>
                <el-input v-model="addReportForm.wrist.ulnarDeviation.right" placeholder="右" />
              </div>
            </div>
            <div class="joint-movement">
              <div class="movement-label">旋前</div>
              <div>
                <el-input v-model="addReportForm.wrist.pronation.left" placeholder="左" />
              </div>
              <div>
                <el-input v-model="addReportForm.wrist.pronation.right" placeholder="右" />
              </div>
            </div>
            <div class="joint-movement">
              <div class="movement-label">旋后</div>
              <div>
                <el-input v-model="addReportForm.wrist.supination.left" placeholder="左" />
              </div>
              <div>
                <el-input v-model="addReportForm.wrist.supination.right" placeholder="右" />
              </div>
            </div>
          </el-form-item>

          &lt;!&ndash; 髋关节 &ndash;&gt;
          <el-form-item label="髋关节">
            <div class="joint-movement">
              <div class="movement-label">屈曲</div>
              <div>
                <el-input v-model="addReportForm.hip.flexion.left" placeholder="左" />
              </div>
              <div>
                <el-input v-model="addReportForm.hip.flexion.right" placeholder="右" />
              </div>
            </div>
            <div class="joint-movement">
              <div class="movement-label">伸展</div>
              <div>
                <el-input v-model="addReportForm.hip.extension.left" placeholder="左" />
              </div>
              <div>
                <el-input v-model="addReportForm.hip.extension.right" placeholder="右" />
              </div>
            </div>
            <div class="joint-movement">
              <div class="movement-label">内旋</div>
              <div>
                <el-input v-model="addReportForm.hip.internalRotation.left" placeholder="左" />
              </div>
              <div>
                <el-input v-model="addReportForm.hip.internalRotation.right" placeholder="右" />
              </div>
            </div>
            <div class="joint-movement">
              <div class="movement-label">外旋</div>
              <div>
                <el-input v-model="addReportForm.hip.externalRotation.left" placeholder="左" />
              </div>
              <div>
                <el-input v-model="addReportForm.hip.externalRotation.right" placeholder="右" />
              </div>
            </div>
            <div class="joint-movement">
              <div class="movement-label">内收</div>
              <div>
                <el-input v-model="addReportForm.hip.adduction.left" placeholder="左" />
              </div>
              <div>
                <el-input v-model="addReportForm.hip.adduction.right" placeholder="右" />
              </div>
            </div>
            <div class="joint-movement">
              <div class="movement-label">外展</div>
              <div>
                <el-input v-model="addReportForm.hip.abduction.left" placeholder="左" />
              </div>
              <div>
                <el-input v-model="addReportForm.hip.abduction.right" placeholder="右" />
              </div>
            </div>
          </el-form-item>

          &lt;!&ndash; 膝关节 &ndash;&gt;
          <el-form-item label="膝关节">
            <div class="joint-movement">
              <div class="movement-label">屈曲</div>
              <div>
                <el-input v-model="addReportForm.knee.flexion.left" placeholder="左" />
              </div>
              <div>
                <el-input v-model="addReportForm.knee.flexion.right" placeholder="右" />
              </div>
            </div>
            <div class="joint-movement">
              <div class="movement-label">伸展</div>
              <div>
                <el-input v-model="addReportForm.knee.extension.left" placeholder="左" />
              </div>
              <div>
                <el-input v-model="addReportForm.knee.extension.right" placeholder="右" />
              </div>
            </div>
          </el-form-item>

          &lt;!&ndash; 踝关节 &ndash;&gt;
          <el-form-item label="踝关节">
            <div class="joint-movement">
              <div class="movement-label">背屈</div>
              <div>
                <el-input v-model="addReportForm.ankle.dorsiflexion.left" placeholder="左" />
              </div>
              <div>
                <el-input v-model="addReportForm.ankle.dorsiflexion.right" placeholder="右" />
              </div>
            </div>
            <div class="joint-movement">
              <div class="movement-label">跖屈</div>
              <div>
                <el-input v-model="addReportForm.ankle.plantarflexion.left" placeholder="左" />
              </div>
              <div>
                <el-input v-model="addReportForm.ankle.plantarflexion.right" placeholder="右" />
              </div>
            </div>
            <div class="joint-movement">
              <div class="movement-label">内翻</div>
              <div>
                <el-input v-model="addReportForm.ankle.inversion.left" placeholder="左" />
              </div>
              <div>
                <el-input v-model="addReportForm.ankle.inversion.right" placeholder="右" />
              </div>
            </div>
            <div class="joint-movement">
              <div class="movement-label">外翻</div>
              <div>
                <el-input v-model="addReportForm.ankle.eversion.left" placeholder="左" />
              </div>
              <div>
                <el-input v-model="addReportForm.ankle.eversion.right" placeholder="右" />
              </div>
            </div>
          </el-form-item>

          <el-divider>关节稳定性测试</el-divider>

          &lt;!&ndash; 肩关节稳定性测试 &ndash;&gt;
          <el-form-item label="肩关节">
            <div class="joint-movement">
              <div class="movement-label">前侧</div>
              <div>
                <el-radio-group v-model="addReportForm.shoulderStability.anterior">
                  <el-radio label="positive">阳性</el-radio>
                  <el-radio label="negative">阴性</el-radio>
                </el-radio-group>
              </div>
            </div>
            <div class="joint-movement">
              <div class="movement-label">后侧</div>
              <el-radio-group v-model="addReportForm.shoulderStability.posterior">
                <el-radio label="positive">阳性</el-radio>
                <el-radio label="negative">阴性</el-radio>
              </el-radio-group>
            </div>
            <div class="joint-movement">
              <div class="movement-label">下侧</div>
              <el-radio-group v-model="addReportForm.shoulderStability.inferior">
                <el-radio label="positive">阳性</el-radio>
                <el-radio label="negative">阴性</el-radio>
              </el-radio-group>
            </div>
          </el-form-item>

          &lt;!&ndash; 肘关节稳定性测试 &ndash;&gt;
          <el-form-item label="肘关节">
            <div class="joint-movement">
              <div class="movement-label">外翻应力</div>
              <el-radio-group v-model="addReportForm.elbowStability.valgus">
                <el-radio label="positive">阳性</el-radio>
                <el-radio label="negative">阴性</el-radio>
              </el-radio-group>
            </div>
            <div class="joint-movement">
              <div class="movement-label">内翻应力</div>
              <el-radio-group v-model="addReportForm.elbowStability.varus">
                <el-radio label="positive">阳性</el-radio>
                <el-radio label="negative">阴性</el-radio>
              </el-radio-group>
            </div>
          </el-form-item>

          &lt;!&ndash; 腕关节稳定性测试 &ndash;&gt;
          <el-form-item label="腕关节">
            <div class="joint-movement">
              <div class="movement-label">尺侧应力</div>
              <el-radio-group v-model="addReportForm.wristStability.ulnar">
                <el-radio label="positive">阳性</el-radio>
                <el-radio label="negative">阴性</el-radio>
              </el-radio-group>
            </div>
            <div class="joint-movement">
              <div class="movement-label">桡侧应力</div>
              <el-radio-group v-model="addReportForm.wristStability.radial">
                <el-radio label="positive">阳性</el-radio>
                <el-radio label="negative">阴性</el-radio>
              </el-radio-group>
            </div>
          </el-form-item>

          &lt;!&ndash; 髋关节稳定性测试 &ndash;&gt;
          <el-form-item label="髋关节">
            <div class="joint-movement">
              <div class="movement-label">前侧</div>
              <el-radio-group v-model="addReportForm.hipStability.anterior">
                <el-radio label="positive">阳性</el-radio>
                <el-radio label="negative">阴性</el-radio>
              </el-radio-group>
            </div>
            <div class="joint-movement">
              <div class="movement-label">后侧</div>
              <el-radio-group v-model="addReportForm.hipStability.posterior">
                <el-radio label="positive">阳性</el-radio>
                <el-radio label="negative">阴性</el-radio>
              </el-radio-group>
            </div>
          </el-form-item>

          &lt;!&ndash; 膝关节稳定性测试 &ndash;&gt;
          <el-form-item label="膝关节">
            <div class="joint-movement">
              <div class="movement-label">前抽屉试验</div>
              <el-radio-group v-model="addReportForm.kneeStability.anteriorDrawer">
                <el-radio label="positive">阳性</el-radio>
                <el-radio label="negative">阴性</el-radio>
              </el-radio-group>
            </div>
            <div class="joint-movement">
              <div class="movement-label">后抽屉试验</div>
              <el-radio-group v-model="addReportForm.kneeStability.posteriorDrawer">
                <el-radio label="positive">阳性</el-radio>
                <el-radio label="negative">阴性</el-radio>
              </el-radio-group>
            </div>
            <div class="joint-movement">
              <div class="movement-label">内翻应力</div>
              <el-radio-group v-model="addReportForm.kneeStability.varus">
                <el-radio label="positive">阳性</el-radio>
                <el-radio label="negative">阴性</el-radio>
              </el-radio-group>
            </div>
            <div class="joint-movement">
              <div class="movement-label">外翻应力</div>
              <el-radio-group v-model="addReportForm.kneeStability.valgus">
                <el-radio label="positive">阳性</el-radio>
                <el-radio label="negative">阴性</el-radio>
              </el-radio-group>
            </div>
          </el-form-item>

          &lt;!&ndash; 踝关节稳定性测试 &ndash;&gt;
          <el-form-item label="踝关节">
            <div class="joint-movement">
              <div class="movement-label">前抽屉试验</div>
              <el-radio-group v-model="addReportForm.ankleStability.anteriorDrawer">
                <el-radio label="positive">阳性</el-radio>
                <el-radio label="negative">阴性</el-radio>
              </el-radio-group>
            </div>
            <div class="joint-movement">
              <div class="movement-label">内翻应力</div>
              <el-radio-group v-model="addReportForm.ankleStability.inversion">
                <el-radio label="positive">阳性</el-radio>
                <el-radio label="negative">阴性</el-radio>
              </el-radio-group>
            </div>
            <div class="joint-movement">
              <div class="movement-label">外翻应力</div>
              <el-radio-group v-model="addReportForm.ankleStability.eversion">
                <el-radio label="positive">阳性</el-radio>
                <el-radio label="negative">阴性</el-radio>
              </el-radio-group>
            </div>
          </el-form-item>

          <el-divider>静态姿势（背面）</el-divider>
          <el-form-item label="跟骨内外侧是否平均着地">
            <div class="joint-movement">
              <div class="movement-label">左侧</div>
              <el-radio-group>
                <el-radio>正常</el-radio>
                <el-radio>外</el-radio>
                <el-radio>内</el-radio>
              </el-radio-group>
            </div>
            <div class="joint-movement">
              <div class="movement-label">右侧</div>
              <el-radio-group>
                <el-radio>正常</el-radio>
                <el-radio>外</el-radio>
                <el-radio>内</el-radio>
              </el-radio-group>
            </div>
          </el-form-item>
          <el-form-item label="脚掌有无外旋">
            <div class="joint-movement">
              <div class="movement-label"></div>
              <el-radio-group>
                <el-radio>正常</el-radio>
                <el-radio>左侧</el-radio>
                <el-radio>右侧</el-radio>
                <el-radio>双侧</el-radio>
              </el-radio-group>
            </div>
          </el-form-item>
          <el-form-item label="双侧臀大肌宽度是否对称">
            <div class="joint-movement">
              <div class="movement-label"></div>
              <el-radio-group>
                <el-radio>正常</el-radio>
                <el-radio>左宽</el-radio>
                <el-radio>右宽</el-radio>
              </el-radio-group>
            </div>
          </el-form-item>
          <el-form-item label="左右肋骨下缘是否一致">
            <div class="joint-movement">
              <div class="movement-label"></div>
              <el-radio-group>
                <el-radio>正常</el-radio>
                <el-radio>左侧低</el-radio>
                <el-radio>右侧低</el-radio>
              </el-radio-group>
            </div>
          </el-form-item>
          <el-form-item label="体前屈下观看脊柱有没有正常弧度">
            <div class="joint-movement">
              <div class="movement-label">颈椎</div>
              <el-radio-group>
                <el-radio>正常</el-radio>
                <el-radio>变曲</el-radio>
                <el-radio>变直</el-radio>
                <el-radio>左凸</el-radio>
                <el-radio>右凸</el-radio>
              </el-radio-group>
            </div>
            <div class="joint-movement">
              <div class="movement-label">胸椎</div>
              <el-radio-group>
                <el-radio>正常</el-radio>
                <el-radio>变曲</el-radio>
                <el-radio>变直</el-radio>
                <el-radio>左凸</el-radio>
                <el-radio>右凸</el-radio>
              </el-radio-group>
            </div>
            <div class="joint-movement">
              <div class="movement-label">腰椎</div>
              <el-radio-group>
                <el-radio>正常</el-radio>
                <el-radio>变曲</el-radio>
                <el-radio>变直</el-radio>
                <el-radio>左凸</el-radio>
                <el-radio>右凸</el-radio>
              </el-radio-group>
            </div>
          </el-form-item>
          <el-form-item label="脊柱有无异常弧度">
            <div class="joint-movement">
              <div class="movement-label"></div>
              <el-radio-group>
                <el-radio>正常</el-radio>
                <el-radio>颈胸交界处过曲</el-radio>
                <el-radio>胸腰交界处过曲</el-radio>
              </el-radio-group>
            </div>
          </el-form-item>

          <el-divider>静态姿势（侧面）</el-divider>
          <el-form-item label="肋骨有没有前倾">
            <div class="joint-movement">
              <div class="movement-label"></div>
              <el-radio-group>
                <el-radio>正常</el-radio>
                <el-radio>有</el-radio>
              </el-radio-group>
            </div>
          </el-form-item>
          <el-form-item label="肋骨有没有前倾">
            <div class="joint-movement">
              <div class="movement-label"></div>
              <el-radio-group>
                <el-radio>正常</el-radio>
                <el-radio>有</el-radio>
              </el-radio-group>
            </div>
          </el-form-item>

          <el-divider>关节稳定性测试</el-divider>
          <el-form-item label="呼吸方式">
            <div class="joint-movement">
              <div class="movement-label"></div>
              <el-radio-group>
                <el-radio>正常</el-radio>
                <el-radio>无明显起伏</el-radio>
                <el-radio>单腹部起伏</el-radio>
                <el-radio>耸肩呼吸</el-radio>
              </el-radio-group>
            </div>
          </el-form-item>

          <el-divider>动态评估（站立体前屈）</el-divider>
          <el-form-item label="呼吸方式">
            <div class="joint-movement">
              <div class="movement-label"></div>
              <el-radio-group>
                <el-radio>正常</el-radio>
                <el-radio>无明显起伏</el-radio>
                <el-radio>单腹部起伏</el-radio>
                <el-radio>耸肩呼吸</el-radio>
              </el-radio-group>
            </div>
          </el-form-item>
          <el-form-item label="呼吸方式">
            <div class="joint-movement">
              <div class="movement-label"></div>
              <el-radio-group>
                <el-radio>正常</el-radio>
                <el-radio>无明显起伏</el-radio>
                <el-radio>单腹部起伏</el-radio>
                <el-radio>耸肩呼吸</el-radio>
              </el-radio-group>
            </div>
          </el-form-item>
          <el-form-item label="呼吸方式">
            <div class="joint-movement">
              <div class="movement-label"></div>
              <el-radio-group>
                <el-radio>正常</el-radio>
                <el-radio>无明显起伏</el-radio>
                <el-radio>单腹部起伏</el-radio>
                <el-radio>耸肩呼吸</el-radio>
              </el-radio-group>
            </div>
          </el-form-item>
          <el-form-item label="呼吸方式">
            <div class="joint-movement">
              <div class="movement-label"></div>
              <el-radio-group>
                <el-radio>正常</el-radio>
                <el-radio>无明显起伏</el-radio>
                <el-radio>单腹部起伏</el-radio>
                <el-radio>耸肩呼吸</el-radio>
              </el-radio-group>
            </div>
          </el-form-item>
          <el-form-item label="呼吸方式">
            <div class="joint-movement">
              <div class="movement-label"></div>
              <el-radio-group>
                <el-radio>正常</el-radio>
                <el-radio>无明显起伏</el-radio>
                <el-radio>单腹部起伏</el-radio>
                <el-radio>耸肩呼吸</el-radio>
              </el-radio-group>
            </div>
          </el-form-item>
          <el-form-item label="呼吸方式">
            <div class="joint-movement">
              <div class="movement-label"></div>
              <el-radio-group>
                <el-radio>正常</el-radio>
                <el-radio>无明显起伏</el-radio>
                <el-radio>单腹部起伏</el-radio>
                <el-radio>耸肩呼吸</el-radio>
              </el-radio-group>
            </div>
          </el-form-item>
        </el-form>-->

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