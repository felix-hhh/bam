package com.kelaker.kcommon.common.ws.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 文件消息
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FileMessageContent extends MessageContent<FileMessageContent.FileMessageBody> {
    @Data
    public static class FileMessageBody extends MessageBody {

        /**
         * 文件名
         */
        private String fileName;

        /**
         * 文件路径
         */
        private String path;

        /**
         * 后缀
         */
        private String postfix;

        /**
         * 文件大小（字节）
         */
        private Long size;
    }
}
