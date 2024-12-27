package com.kelaker.kcommon.common.tools.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 根据IP地址转换的地址
 */
@Data
public class IpLocationVo {

    private String ip;

    private String address;

    private Content content;

    /**
     * 内容
     */
    @Data
    public static class Content {
        /**
         * 地址
         */
        private String address;

        /**
         * 地址详情
         */
        @JsonProperty("address_detail")
        private AddressDetail addressDetail;

        private Point point;
    }

    /**
     * 地址详情
     */
    @Data
    public static class AddressDetail {
        /**
         * 地址编码
         */
        private String adcode;

        /**
         * 城市
         */
        private String city;

        /**
         * 城市代码
         */
        @JsonProperty("city_code")
        private int cityCode;

        /**
         * 省
         */
        private String province;
    }

    /**
     * 经纬度
     */
    @Data
    public static class Point {
        /**
         * 经
         */
        private String x;

        /**
         * 纬
         */
        private String y;

    }


}
