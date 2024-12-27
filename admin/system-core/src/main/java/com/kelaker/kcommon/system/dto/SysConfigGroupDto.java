package com.kelaker.kcommon.system.dto;

import lombok.Data;

/**
 * 系统配置分组
 */
@Data
public class SysConfigGroupDto {

    /**
     * 系统类型
     */
    private String systemType;

    /**
     * 分组代码
     */
    private String groupCode;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 备注
     */
    private String remark;
}
