package com.kelaker.kcommon.user.vo;

import lombok.Data;

import java.util.Date;

/**
 * 用户登录日志(UserLoginLog)表实体类
 *
 * @author Felix Huang
 * @since 2022-08-23 14:23:29
 */
@Data
public class UserLoginLogVo {
    /**
     * id
     */
    private Long id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 归属省
     */
    private String localProvince;

    /**
     * 属地代码
     */
    private String localCode;

    /**
     * 归属市
     */
    private String localCity;

    /**
     * 备注
     */
    private String remark;

    /**
     * 登录类型
     */
    private String type;

    /**
     * 登录类型字符串
     */
    private String typeStr;

    /**
     * 设备品牌
     */
    private String deviceBrand;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 设备型号
     */
    private String deviceModel;

    /**
     * 创建时间
     */
    private Date createDatetime;
}

