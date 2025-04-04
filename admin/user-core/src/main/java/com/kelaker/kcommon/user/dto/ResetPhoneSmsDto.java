package com.kelaker.kcommon.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 重设手机号
 */
@Data
public class ResetPhoneSmsDto {
    /**
     * 旧手机号
     */
    @NotBlank(message = "旧手机号码不能为空")
    private String oldPhone;

    /**
     * 新手机号码
     */
    @NotBlank(message = "新手机号码不能为空")
    private String newPhone;
}
