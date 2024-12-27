package com.kelaker.kcommon.system.dto;

import lombok.Data;

/**
 * 添加管理员角色Dto
 *
 * @author chym
 * @since 2020/3/31 9:21
 */
@Data
public class SysAdminRoleDto {
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 备注
     */
    private String remark;
}
