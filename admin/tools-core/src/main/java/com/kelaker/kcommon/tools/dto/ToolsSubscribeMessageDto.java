package com.kelaker.kcommon.tools.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ToolsSubscribeMessageDto {

    /**
     * 模版ID
     */
    private String templateId;

    /**
     * 点击消息后跳转的页面
     */
    private String page;

    /**
     * 接收用户
     */
    private String toUser;

    /**
     * 模版数据
     */
    private Map<String, SubscribeMessageData> data;

    /**
     * 小程序状态
     */
    private MiniProgramState miniProgramState;

    public enum MiniProgramState {
        /**
         * 开发版
         */
        DEVELOPER,
        /**
         * 体验版
         */
        TRIAL,
        /**
         * 正式版
         */
        FORMAL
    }

    @Data
    public static class SubscribeMessageData {

        public SubscribeMessageData(String value) {
            this.value = value;
        }

        String value;
    }
}
