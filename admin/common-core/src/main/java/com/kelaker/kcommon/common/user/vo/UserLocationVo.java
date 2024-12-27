package com.kelaker.kcommon.common.user.vo;

import lombok.Data;

@Data
public class UserLocationVo {
    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * IP归属地
     */
    private String ipLocation;
}
