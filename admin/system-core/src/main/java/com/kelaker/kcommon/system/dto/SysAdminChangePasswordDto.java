package com.kelaker.kcommon.system.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 管理员修改密码Dto
 * @author chym
 * @date 2020/3/30 16:42
 */
@Data
public class SysAdminChangePasswordDto {

    /**
     * 当前密码
     */
    @NotBlank(message = "当前密码不能为空")
    private String currentPassword;

    /**
     * 新密码
     */
    private String newPassword;
}
