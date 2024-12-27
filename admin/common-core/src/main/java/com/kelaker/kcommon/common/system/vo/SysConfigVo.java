package com.kelaker.kcommon.common.system.vo;

import lombok.Data;

/**
 * 系统设置Vo
 *
 * @author Felix Huang
 * @since 2021-01-31
 */
@Data
public class SysConfigVo {

    /**
     * 配置key
     */
    private String configKey;

    /**
     * 配置名称
     */
    private String configKeyName;

    /**
     * 配置值
     */
    private String configValue;

    /**
     * 备注
     */
    private String remark;

    /**
     * 启用:S_C_S_ENABLE 禁用:S_C_S_DISABLE
     */
    private String status;

    /**
     * 启用:S_C_S_ENABLE 禁用:S_C_S_DISABLE
     */
    private String statusStr;

    /**
     * 分组
     */
    private String groupCode;

    /**
     * 是否允许前端调用
     */
    private boolean frontView;

    /**
     * 是否免登录获取
     */
    private boolean publicKey;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 类型(string-字符串 | boolean-布尔 | number-数值)
     */
    private String type;

    /**
     * 类型
     */
    private String typeStr;

}