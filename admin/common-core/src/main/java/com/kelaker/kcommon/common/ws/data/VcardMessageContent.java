package com.kelaker.kcommon.common.ws.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 动作消息
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VcardMessageContent extends MessageContent<VcardMessageContent.VcardMessageBody> {
    @Data
    public static class VcardMessageBody extends MessageBody {

        /**
         * 昵称
         */
        private String nickname;
        /**
         * 头像
         */
        private String header;

        /**
         * 用户ID
         */
        private String userId;

        /**
         * 来源
         */
        private String origin;
    }

}
