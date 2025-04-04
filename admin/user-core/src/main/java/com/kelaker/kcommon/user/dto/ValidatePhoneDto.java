package com.kelaker.kcommon.user.dto;

import com.kelaker.kcommon.user.constant.BusinessType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 验证手机号码dto
 *
 * @author felix huang
 * @since 2022-07-26
 */
@Data
public class ValidatePhoneDto {
    /**
     * 短信验证码
     */
    @NotBlank(message = "短信验证码不能为空")
    private String smsAuthCode;

    /**
     * 业务类型
     */
    @NotNull(message = "业务类型不能为空")
    private BusinessType businessType;
}
