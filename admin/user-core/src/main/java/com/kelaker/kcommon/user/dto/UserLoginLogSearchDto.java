package com.kelaker.kcommon.user.dto;

import lombok.Data;

import java.util.Date;

/**
 * 用户登录日志(UserLoginLog)查询实体类
 *
 * @author Felix Huang
 * @since 2022-08-23 14:23:29
 */
@Data
public class UserLoginLogSearchDto {
    /**
     * id
     */
    private Integer id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * ip地址
     */
    private String ipAddr;

    /**
     * 备注
     */
    private String remark;

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

