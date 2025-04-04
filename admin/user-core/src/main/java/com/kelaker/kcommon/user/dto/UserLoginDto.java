package com.kelaker.kcommon.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 用户登录DTO
 *
 * @author felix huang
 * @since 2021-12-10
 */
@Data
public class UserLoginDto {

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 短线验证码
     */
    private String smsAuthCode;

    /**
     * 密码
     */
    private String passwd;

    /**
     * 登录类型
     */
    private LoginType loginType;

    /**
     * 微信登录code/苹果一键登录authorizationCode/谷歌一键登录code/Facebook一键登录Code
     */
    private String code;

    /**
     * 手机号码一键登录token
     */
    private String token;

    /**
     * 手机号码一键登录accessToken
     */
    private String accessToken;

    /**
     * 设备信息
     */
    @NotNull(message = "设备信息不能为空")
    private DeviceInfoDto deviceInfo;

    /**
     * 登录类型
     */
    public enum LoginType {
        /**
         * 短信
         */
        SMS,
        /**
         * 密码
         */
        PWD,
        /**
         * 微信
         */
        WX,
        /**
         * 手机号一键登录
         */
        ONECLICK,
        /**
         * 苹果账号一键登录
         */
        APPLEID,
        /**
         * 微信小程序Openid
         */
        WXM_OPENID,
        /**
         * 微信小程序手机号码
         */
        WXM_PHONE
    }
}
