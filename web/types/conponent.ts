import { VNode } from "vue";


/**
 * 分页查询结果
 */
export interface PageResult {
  /**
   * 每页大小
   */
  size: number;

  /**
   * 数据总数
   */
  total: number;
  /**
   * 数据集
   */
  records: object[];

  /**
   * 总页数
   */
  pages: number;
  /**
   * 当前页
   */
  current: number;
}

/**
 * 页面列配置
 */
export interface ViewColumnConfig {
  addHandle: boolean;
  columnLabel: string;
  columnName: string;
  columnType: "text" | "textarea" | "password" | "button" | "checkbox" | "file" | "number" | "radio";
  editHandle: boolean;
  id: number;
  ruleRegular: string;
  ruleRequired: boolean;
  showFixed?: "left" | "right";
  showFormat?: string;
  showWidth?: number;
  viewId: number;
  sortable?: "custom";
  showColumn: boolean;
}

/**
 * 页面配置
 */
export interface ViewConfig {
  /**
   * 页面名称
   */
  name: string;
  /**
   * 是否为分页查询
   */
  pageQuery: boolean;
  /**
   * 初始化数据
   */
  initData: boolean;
  /**
   * 初始化数据字符串
   */
  initDataUrl: string;
  optAdd: boolean;
  optAddLabel: string;
  optAddShowRegion: string;
  optAddUrl: string;
  optDelete: boolean;
  optDeleteLabel: string;
  optDeleteShowRegion: string;
  optDeleteUrl: string;
  optEdit: boolean;
  optEditLabel: string;
  optEditShowRegion: string;
  optEditUrl: string;
  remark: string;
  tips: string;
}


export interface ResponseData<T = any> {
  success: boolean;
  data?: T;
}

/**
 * 表格列配置
 */
export interface TableColumn {
  label: string;
  width?: number;
  prop?: string;
  fixed?: "left" | "right" | boolean;
  type?: "handle";
  format?: (row: any, column: any, cellValue: any, index?: number) => VNode | string;
  handles?: TableColumnHandle[];
  showOverflowTooltip?: boolean;
  sortable?: "custom";
}

export interface TableColumnHandle {
  label: string;
  handleFun: () => void;
  format?: () => void;
  type: "group" | "primary" | "success" | "warning" | "danger";
  items?: TableColumnHandle[];
}

/**
 * 表格操作按钮
 */
export interface TableOptButton {
  label: string;
  type: "primary";
  selectHandler: boolean;
  handle?: () => void;
  items?: TableOptButton[];
}

/**
 * 表格搜索
 */
export interface TableSearch {
  label: string;
  key: string;
  value: string;
}

/**
 * 表格配置
 */
export interface FormColumn {
  label: string;
  prop: string;
  type: "text" | "textarea" | "password" | "button" | "checkbox" | "file" | "number" | "radio";
  addHandle: boolean;
  editHandle: boolean;
}