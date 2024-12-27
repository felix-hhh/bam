package com.kelaker.kcommon.common.ws.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;
import java.util.Set;

/**
 * 动作消息
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ActionMessageContent extends MessageContent<ActionMessageContent.ActionMessageBody> {

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class ActionMessageBody extends MessageBody {
        /**
         * 动作类型
         */
        private ActionType actionType;

        /**
         * 消息ID
         */
        private String messageId;

        /**
         * 动作文本
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

        /**
         * 接收用户
         */
        private String to;

        /**
         * 内容
         */
        private Map<String,Object> messageBody;

        /**
         * 动作类型
         */
        public enum ActionType {
            /**
             * 撤回
             */
            REVOKE,
            /**
             * 删除
             */
            DELETE,
            /**
             * 已读
             */
            READ,
            /**
             * 引用消息
             */
            QUOTE
        }
    }
}
