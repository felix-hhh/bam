package com.kelaker.kcommon.user.dto;

import lombok.Data;

/**
 * 用户登录日志(UserLoginLog)查询实体类
 *
 * @author Felix Huang
 * @since 2022-08-23 14:23:29
 */
@Data
public class UserLoginLogDto {

    /**
     * ip地址
     */
    private String ip;

    private String userId;

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
     * 登录类型
     */
    private String type;

}

