package com.kelaker.kcommon.common.ws.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 * 消息数据
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MessageContent<T extends MessageBody> {

    /**
     * 消息ID
     */
    private String id;

    /**
     * 消息类型：SINGLE,GROUP,SYSTEM,ADMIN,CUSTOMER,OFFLINE
     */
    private MessageType type;

    /**
     * 类型拓展
     */
    private String typeExtend;

    /**
     * 媒体类型：TEXT,AUDIO,VIDEO,IMAGE,FILE,ACTION
     */
    private MimeType mimeType;

    /**
     * 内容
     */
    private T body;

    /**
     * 接收对象
     */
    private String to;

    /**
     * 发送过滤器
     */
    private Set<String> filterTo;

    /**
     * 发送对象Id
     */
    private String from;

    /**
     * 创建耗时间
     */
    private Date createDatetime;

    /**
     * 消息类型
     */
    private MsgType msgType;

    /**
     * 是否是离线消息
     */
    private Boolean offline;

    /**
     * 要过滤的token
     */
    private Set<String> filterToken;

    public enum MsgType {
        /**
         * 普通消息
         */
        NORMAL,
        /**
         * 阅后即焚
         */
        BURN
    }
}
