/**
 * 登录数据
 */
export interface LoginData {
  /**
   * 用户吗
   */
  username: string;

  /**
   * 密码
   */
  password: string;

  /**
   * 验证码
   */
  verifyCode: string;

  /**
   * 随机码
   */
  identity: string;
}

export interface ResponseData<T> {
  /**
   * 是否处理成功
   */
  success: boolean;
  /**
   * 响应内容
   */
  data?: T;
  /**
   * 错误消息
   */
  errMsg: string;
}

/**
 * token信息
 */
export interface TokenInfo {
  /**
   * 创建时间
   */
  createDatetime: number;
  /**
   * 超时时间
   */
  exp: number;
  /**
   * 有效时长
   */
  expireSeconds: number;
  /**
   * 角色
   */
  roles: string;
  /**
   * 用户数据
   */
  userData: UserData;
  /**
   * 用户标识
   */
  userTag: string;
}

export interface UserData {
  nickname: string;
  username: string;
}

export interface OssSts {
  securityToken: string;
  accessKeySecret: string;
  accessKeyId: string;
  expiration: number;
}

/**
 * 系统菜单信息
 */
export interface SysMenu {
  /**
   * 子菜单
   */
  children?: SysMenu[];
  /**
   * 是否隐藏
   */
  hide: boolean;
  /**
   * 序号
   */
  id: number;
  /**
   * 菜单名字
   */
  name: string;
  /**
   * 排序
   */
  orderNum: number;
  /**
   * 路径
   */
  path: string;
  /**
   * 类型
   */
  type: "S_M_T_FIRST" | "S_M_T_CHILD" | "S_M_T_BUTTON";
  /**
   * 类型字符串
   */
  typeStr: string;

  component?: "MainFrame" | "TableView";
}




