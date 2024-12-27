package com.kelaker.kcommon.common.ws.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 动作消息
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AudioMessageContent extends MessageContent<AudioMessageContent.AudioMessageBody> {
    @Data
    public static class AudioMessageBody extends MessageBody {
        /**
         * 文件路径
         */
        private String path;

        /**
         * 时长
         */
        private int time;
    }

}
