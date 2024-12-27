package com.kelaker.kcommon.common.ws.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MessageContentResponse<T extends MessageBody> {

    private String type = "ACK";

    /**
     * 消息ID
     */
    private String id;

    /**
     * 创建时间
     */
    private Date createDatetime;

    /**
     * 错误消息
     */
    private String errMsg;

    /**
     * 内容
     */
    private T body;
}
