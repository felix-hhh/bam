package com.kelaker.kcommon.tools.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 微信tokenvo
 */
@Data
public class WxTokenVo {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private int expiresIn;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("openid")
    private String openid;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("unionid")
    private String unionid;

    private int errcode;
}
