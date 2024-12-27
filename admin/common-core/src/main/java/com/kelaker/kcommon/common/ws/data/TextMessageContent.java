package com.kelaker.kcommon.common.ws.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Set;

/**
 * 文本消息
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TextMessageContent extends MessageContent<TextMessageContent.TextMessageBody> {
    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class TextMessageBody extends MessageBody {
        /**
         * 消息内容
         */
        private String text;

        /**
         * 联系人
         */
        private Set<String> contacts;

        /**
         * 圈所有人
         */
        private boolean contactAll;

    }

}
