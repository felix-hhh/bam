package com.kelaker.kcommon.system.vo;

import lombok.Data;

/**
 * 系统配置前端VO
 *
 * @author felix huang
 * @since 2021-12-16
 */
@Data
public class SysConfigFrontVo {

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
     * 分组代码
     */
    private String groupCode;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 类型(string-字符串 | boolean-布尔 | number-数值)
     */
    private String type;
}
