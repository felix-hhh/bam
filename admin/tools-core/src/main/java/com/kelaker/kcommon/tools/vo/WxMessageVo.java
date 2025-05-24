package com.kelaker.kcommon.tools.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WxMessageVo {
    /**
     * 错误消息
     */
    @JsonProperty("errmsg")
    private String errMsg;

    /**
     * 错误代码
     */
    @JsonProperty("errcode")
    private int errCode;

    @JsonProperty("msgid")
    private Long msgId;
}
