package com.kelaker.kcommon.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户注销DTO
 * @author felix huang
 * @since 2021-12-17
 */
@Data
public class UserCancelDto {

    /**
     * 手机验证码
     */
    @NotBlank(message = "手机验证码不能为空")
    private String smsAuthCode;

}
