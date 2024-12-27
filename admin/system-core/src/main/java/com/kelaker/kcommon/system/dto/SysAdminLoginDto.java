package com.kelaker.kcommon.system.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 管理员登陆DTO
 *
 * @author chym
 * @date 2020/3/30 16:00
 */
@Data
public class SysAdminLoginDto {
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 登陆密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String verifyCode;

    /**
     * 验证码唯一标识
     */
    @NotBlank(message = "验证码唯一标识不能为空")
    private String identity;
}
