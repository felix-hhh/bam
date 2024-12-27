package com.kelaker.kcommon.common.ws.dto;

import lombok.Data;

import java.util.Map;

@Data
public class InteractionMessageDto extends MessageDto {
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
         * 拓展信息
         */
        private Map<String, Object> extendsData;

        /**
         * 互动类型
         */
        private String type;

        /**
         * 互动消息目标用户
         */
        private String userId;
    }
}
