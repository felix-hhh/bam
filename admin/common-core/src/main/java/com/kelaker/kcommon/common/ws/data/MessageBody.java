package com.kelaker.kcommon.common.ws.data;

import lombok.Data;

/**
 * 消息体
 */
@Data
public abstract class MessageBody {
    /**
     * 临时消息ID
     */
    private String id;
}
