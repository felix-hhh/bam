package com.kelaker.kcommon.system.vo;

import lombok.Data;

/**
 * 系统配置分组信息
 *
 * @author Felix Huang
 * @since 2020/10/19 16:53
 */
@Data
public class SysConfigGroupVo {

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
