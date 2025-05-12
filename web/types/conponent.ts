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
  dataType: "text" | "textarea" | "password" | "button" | "checkbox" | "file" | "number" | "switch" | "select" | "image";
  editHandle: boolean;
  dataSource?: string;
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

export interface ViewSearchConfig {
  id: number;
  viewId: number;
  searchLabel: string;
  searchKey: string;
  searchValue: string;
  dataType: "input" | "select";
}

/**
 * 页面配置
 */
export interface ViewConfig {
  id: number;
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
  optView: boolean;
  optViewLabel: string;
  optViewShowRegion: string;
  optViewUrl: string;
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
  type?: "handle" | "image";
  format?: (row: any, column: any, cellValue: any, index?: number) => VNode | string;
  handles?: TableColumnHandle[];
  showOverflowTooltip?: boolean;
  sortable?: "custom";
}

export interface TableColumnHandle {
  label: string;
  handleFun: (index: number, data: any) => void;
  format?: (data: any) => void;
  type: "group" | "primary" | "success" | "warning" | "danger";
  items?: TableColumnHandle[];
}

/**
 * 表格操作按钮
 */
export interface TableOptButton {
  label: string;
  type: "group" | "primary" | "success" | "warning" | "danger";
  selectHandler: boolean;
  handle?: () => void;
  items?: TableOptButton[];
}

/**
 * 表格配置
 */
export interface FormColumn {
  label: string;
  prop: string;
  type: "text" | "textarea" | "password" | "button" | "checkbox" | "file" | "number" | "switch" | "select" | "image";
  addHandle: boolean;
  editHandle: boolean;
  dataSource?: string;
  selectData?: object[];
}

export interface SearchData {
  limit: number;
  page: number;
  data: object;
  dir: "ASC" | "DESC";
}

export interface ColumnData {
  column: any;
  prop: string;
  order: any;
}

/**
 * 下拉菜单选项
 */
export interface SelectItem {
  key: string;
  value: string;
}