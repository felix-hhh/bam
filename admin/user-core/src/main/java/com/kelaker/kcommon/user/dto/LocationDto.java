package com.kelaker.kcommon.user.dto;

import lombok.Data;

/**
 * 定位信息
 */
@Data
public class LocationDto {

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

}

