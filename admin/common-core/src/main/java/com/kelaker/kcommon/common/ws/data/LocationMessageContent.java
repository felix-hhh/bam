package com.kelaker.kcommon.common.ws.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 动作消息
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LocationMessageContent extends MessageContent<LocationMessageContent.LocationMessageBody> {
    @Data
    public static class LocationMessageBody extends MessageBody {

        /**
         * 地点
         */
        private String site;
        /**
         * 地址
         */
        private String address;

        /**
         * 经度
         */
        private double longitude;

        /**
         * 纬度
         */
        private double latitude;
    }

}
