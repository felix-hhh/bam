package com.kelaker.kcommon.common.ws.dto;

import lombok.Data;

import java.util.Date;

/**
 * 推送token
 */
@Data
public class PushTokenDto {
    /**
     * 用户token
     */
    private String userToken;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 推送token
     */
    private String pushToken;
}
