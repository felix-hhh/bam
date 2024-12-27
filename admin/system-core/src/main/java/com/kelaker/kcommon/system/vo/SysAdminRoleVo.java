package com.kelaker.kcommon.system.vo;

import lombok.Data;

import java.util.Date;

/**
 * 管理员角色Vo
 * @author chym
 * @date 2020/4/1 14:27
 */
@Data
public class SysAdminRoleVo {

    /**
     * 角色代码
     */
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色状态
     */
    private String status;

    /**
     * 角色状态说明
     */
    private String statusStr;

    /**
     * 角色备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createDatetime;

    /**
     * 人数
     */
    private int userCount;
}
