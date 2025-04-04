package com.kelaker.kcommon.tools.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WxPhoneVo {

    /**
     * 错误消息
     */
    private String errmsg;

    /**
     * 错误代码
     */
    private int errcode;

    /**
     * 手机信息
     */
    @JsonProperty("phone_info")
    private PhoneInfo phoneInfo;

    /**
     * 手机号码信息
     */
    @Data
    public static class PhoneInfo{
        /**
         * 有区号手机号码
         */
        private String phoneNumber;

        /**
         * 无区号手机号码
         */
        private String purePhoneNumber;

        /**
         * 区号
         */
        private String countryCode;
    }
}
