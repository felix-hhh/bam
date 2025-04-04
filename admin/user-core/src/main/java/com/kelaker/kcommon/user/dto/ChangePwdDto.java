package com.kelaker.kcommon.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 更换密码DTO
 *
 * @author felix huang
 * @since 2021-12-13
 */
@Data
public class ChangePwdDto {

    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空")
    private String newPwd;

    private String authCode;
}
