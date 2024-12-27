package com.kelaker.kcommon.system.dto;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * 超级管理员重置用户密码Dto
 * @author chym
 * @date 2020/3/30 16:31
 */
@Data
public class SysAdminResetPasswordDto {

    /**
     * 管理员ID
     */
    @NotNull(message = "管理员ID不能为空")
    private Long userId;

    /**
     * 新密码
     */
    private String newPassword;
}
