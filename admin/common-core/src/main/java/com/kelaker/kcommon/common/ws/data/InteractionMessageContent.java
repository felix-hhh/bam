package com.kelaker.kcommon.common.ws.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 互动消息
 *
 * @author felix huang
 * @since 2022-08-23
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InteractionMessageContent extends MessageContent<InteractionMessageContent.InteractionMessageBody> {
    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class InteractionMessageBody extends MessageBody {
        /**
         * 类型：拍一拍，送礼物，视频，语音
         */
        private InteractionType interactionType;

        /**
         * 动作类型
         */
        private ActionType actionType;

        /**
         * 信令类型
         */
        private SignalType signalType;

        /**
         * 互动对象
         */
        private String userId;

        /**
         * 拓展数据
         */
        private Map<String, Object> extendsData = new ConcurrentHashMap<>();

        /**
         * 添加扩展数据
         *
         * @param key
         * @param value
         */
        public void pushExtendsData(String key, Object value) {
            extendsData.put(key, value);
        }

    }

    /**
     * 视呼叫动作类型
     */
    public enum ActionType {
        /**
         * 发起
         */
        OFFER,
        /**
         * 应答
         */
        ANSWER
    }

    public enum SignalType {
        /**
         * 发起
         */
        SDP,
        /**
         * 应答
         */
        ICE
    }

    /**
     * 互动类型
     */
    public enum InteractionType {
        /**
         * 打招呼
         */
        HI,
        /**
         * 视频
         */
        VIDEO,
        /**
         * 音频
         */
        AUDIO,
        /**
         * 礼物
         */
        GIFT,
        /**
         * 正在输入
         */
        TYPING,
        /**
         * 点赞
         */
        LIKE,
        /**
         * 分享
         */
        SHARE
    }

}
