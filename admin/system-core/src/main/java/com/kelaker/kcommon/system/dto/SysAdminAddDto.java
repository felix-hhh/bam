package com.kelaker.kcommon.system.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 添加管理员信息请求
 *
 * @author felix huang
 * @date 2020/10/27 17:40
 */
@Data
public class SysAdminAddDto {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 密码
     */
    private String password;
}
