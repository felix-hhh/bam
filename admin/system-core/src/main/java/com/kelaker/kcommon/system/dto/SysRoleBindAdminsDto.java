package com.kelaker.kcommon.system.dto;

import lombok.Data;

import java.util.Set;

/**
 * 角色批量绑定管理员Dto
 * @author chym
 * @since 2020/3/31 13:40
 */
@Data
public class SysRoleBindAdminsDto {

    /**
     * 管理员ID集合
     */
    private Set<Long> adminIds;

    /**
     * 角色代码
     */
    private Long roleId;
}
