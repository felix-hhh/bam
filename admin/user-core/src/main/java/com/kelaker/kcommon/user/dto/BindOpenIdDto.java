package com.kelaker.kcommon.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 绑定OpenIDDto
 *
 * @author felix huang
 * @since 2021-12-17
 */
@Data
public class BindOpenIdDto {

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String authCode;

    /**
     * OpenId
     */
    @NotBlank(message = "openId不能为空")
    private String openId;

    /**
     * openId类型
     */
    @NotNull(message = "绑定类型不能为空")
    private BindType bindType;

    public enum BindType {
        /**
         * 微信
         */
        WECHAT,
        /**
         * QQ
         */
        QQ,
        /**
         * 苹果
         */
        APPLE
    }
}
