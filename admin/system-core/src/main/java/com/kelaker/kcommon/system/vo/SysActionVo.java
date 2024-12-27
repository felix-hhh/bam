package com.kelaker.kcommon.system.vo;

import lombok.Data;

/**
 * 系统功能Vo
 * @author Felix Huang
 * @since 2020/4/1 9:41
 */
@Data
public class SysActionVo {

    /**
     * 功能代码
     */
    private String actionCode;

    /**
     * 功能名称
     */
    private String actionName;

    /**
     * 模块代码
     */
    private String moduleCode;

    /**
     * 功能备注
     */
    private String remark;

    /**
     * 权限url
     */
    private String url;
}
