package com.kelaker.kcommon.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BindPhoneDto {

    /**
     * 手机号码
     */
    @NotBlank(message = "新手机号不能为空")
    private String phone;

    /**
     * 短信验证码
     */
    @NotBlank(message = "手机验证码不能为控")
    private String smsAuthCode;

    /**
     * 原手验证结果
     */
    private String sourcePhoneValidateCode;

}
