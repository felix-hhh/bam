package com.kelaker.kcommon.common.ws.data;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.util.Map;
import java.util.Set;

@Data
public class MessageContentRequest {

    /**
     * id
     */
    private String id;

    /**
     * 消息类型：SINGLE,GROUP,SYSTEM,ADMIN,CUSTOMER,OFFLINE
     */
    @NotBlank(message = "消息类型不能为空")
    private String type;

    /**
     * 媒体类型：TEXT,AUDIO,VIDEO,IMAGE,FILE,ACTION
     */
    @NotBlank(message = "媒体类型不能为空")
    private String mimeType;

    /**
     * 内容
     */
    private Map<String, Object> body;

    /**
     * 接收对象
     */
    private String to;

    /**
     * 发送对象
     */
    private String from;

    /**
     * 发送过滤器
     */
    private Set<String> filterTo;

    /**
     * 消息类型
     */
    private String msgType;

}
