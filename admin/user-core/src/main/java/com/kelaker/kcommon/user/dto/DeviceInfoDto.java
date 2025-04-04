package com.kelaker.kcommon.user.dto;

import lombok.Data;

/**
 * 用户登录日志(UserLoginLog)表实体类
 *
 * @author lfz
 * @since 2022-08-08 16:03:28
 */
@Data
public class DeviceInfoDto {
    /**
     * ip地址
     */
    private String ip;

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
     * 渠道
     */
    private String channel;

    /**
     * 版本
     */
    private String version;

    /**
     * 设备token
     */
    private String deviceToken;

}

