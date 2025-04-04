package com.kelaker.kcommon.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 忘记密码DTO
 *
 * @author felix huang
 * @since 2021-12-13
 */
@Data
public class ForgotPwdDto {

    /**
     * 手机号码
     */
    @NotBlank(message = "手机号码不能为空")
    private String phone;

    /**
     * 短信验证码
     */
    @NotBlank(message = "短信验证码不能为空")
    private String smsAuthCode;

    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空")
    private String newPwd;
}
