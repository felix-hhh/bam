package com.kelaker.kcommon.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 初始化密码DTO
 *
 * @author felix huang
 * @since 2021-12-13
 */
@Data
public class InitPwdDto {

    /**
     * 密码，MD5加密后传输
     */
    @NotBlank(message = "密码不能位空")
    public String pwd;

    /**
     * 手机验证码
     */
    @NotBlank(message = "手机验证码不能为空")
    public String authCode;
}
