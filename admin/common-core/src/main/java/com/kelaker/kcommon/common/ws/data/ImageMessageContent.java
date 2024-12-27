package com.kelaker.kcommon.common.ws.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 动作消息
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ImageMessageContent extends MessageContent<ImageMessageContent.ImageMessageBody> {
    @Data
    public static class ImageMessageBody extends MessageBody {
        /**
         * 路径
         */
        private String path;

        /**
         * 文件类型
         */
        private String type;

        /**
         * 宽
         */
        private String width;

        /**
         * 高
         */
        private String height;
    }
}
