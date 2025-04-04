package com.kelaker.kcommon.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPhoneDto {

    /**
     * 短信验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String authCode;

    /**
     * 验证ID
     */
    @NotBlank(message = "活体验证ID不能为空")
    private String validateId;
}
