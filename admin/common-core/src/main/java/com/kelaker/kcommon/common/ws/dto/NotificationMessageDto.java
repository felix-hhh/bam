package com.kelaker.kcommon.common.ws.dto;

import lombok.Data;

import java.util.Map;

@Data
public class NotificationMessageDto extends MessageDto {

    /**
     * 内容
     */
    private MessageBody body;

    /**
     * 消息体
     */
    @Data
    public static class MessageBody {
        /**
         * 消息
         */
        private String message;

        /**
         * 通知类型
         */
        private String type;

        /**
         * 拓展信息
         */
        private Map<String, Object> extendsData;
    }
}
