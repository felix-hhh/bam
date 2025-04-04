package com.kelaker.kcommon.user.dto;

import lombok.Data;

/**
 * 用户归属地
 *
 * @author felix huang
 * @since 2021-12-10
 */
@Data
public class UserLocationDto {

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
    private String ip;

    private String localProvince;

    private String localCode;

    private String localCity;
}
