package com.kelaker.kcommon.user.dto;

import lombok.Data;

import java.util.Date;

/**
 * 用户注册信息表(UserRegisterLog)查询实体类
 *
 * @author Felix Huang
 * @since 2022-08-23 14:43:55
 */
@Data
public class UserRegisterLogSearchDto {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 注册ip
     */
    private String ip;

    /**
     * 渠道
     */
    private String channel;

    /**
     * 版本
     */
    private String version;

    /**
     * 注册方式：U_R_L_R_M_SMS 短信验证码注册，U_R_L_R_M_ONE_CLICK 一键登录注册
     */
    private String registerMode;

    /**
     * 注册时间
     */
    private Date registerDatetime;
}

