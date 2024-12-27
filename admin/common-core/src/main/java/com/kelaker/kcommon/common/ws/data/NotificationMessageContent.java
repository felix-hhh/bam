package com.kelaker.kcommon.common.ws.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

/**
 * 通知消息
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NotificationMessageContent extends MessageContent<NotificationMessageContent.NotificationMessageBody> {
    @Data
    public static class NotificationMessageBody extends MessageBody {

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
