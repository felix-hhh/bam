package com.kelaker.kcommon.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.kelaker.ktools.web.helper.SecurityTypeHelper;
import lombok.Data;

import java.util.Date;

/**
 * 用户注册信息表(UserRegisterLog)表实体类
 *
 * @author Felix Huang
 * @since 2022-08-23 14:43:54
 */
@Data
@TableName("user_register_log")
public class UserRegisterLog extends Model<UserRegisterLog> {

    /**
     * 用户id
     */
    @TableId(type = IdType.INPUT)
    private String userId;

    /**
     * 注册ip
     */
    @TableField(typeHandler = SecurityTypeHelper.class)
    private String ip;

    private String localProvince;

    private String localCode;

    private String localCity;

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
    @TableField(fill = FieldFill.INSERT)
    private Date createDatetime;
}

