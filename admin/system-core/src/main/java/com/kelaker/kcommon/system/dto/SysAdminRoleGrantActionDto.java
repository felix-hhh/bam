package com.kelaker.kcommon.system.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.util.Set;

/**
 * 角色授权功能Dto
 * @author chym
 * @date 2020/3/31 13:42
 */
@Data
public class SysAdminRoleGrantActionDto {

    /**
     * 角色代码
     */
    @NotBlank(message = "角色ID不能为空")
    private Long id;

    /**
     * 功能代码集合
     */
    private Set<String> actionCodes;
}
