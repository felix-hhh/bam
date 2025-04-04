package com.kelaker.kcommon.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 初始化用户资料
 */
@Data
public class UserInfoDefaultDataDto {
    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    /**
     * 性别
     */
    @NotNull(message = "性别不能为空")
    private Integer gender;

    /**
     * 头像
     */
    @NotBlank(message = "头像不能为空")
    private String headerImg;

    /**
     * 签名
     */
    private String signature;

    /**
     * 用户ID
     */
    private Long userId;

}
