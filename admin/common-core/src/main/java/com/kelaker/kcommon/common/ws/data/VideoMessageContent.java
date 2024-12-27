package com.kelaker.kcommon.common.ws.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 视频处理数据
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VideoMessageContent extends MessageContent<VideoMessageContent.VideoMessageBody> {
    @Data
    public static class VideoMessageBody extends MessageBody {
        /**
         * 文件路径
         */
        private String path;

        /**
         * 时长
         */
        private int time;

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
