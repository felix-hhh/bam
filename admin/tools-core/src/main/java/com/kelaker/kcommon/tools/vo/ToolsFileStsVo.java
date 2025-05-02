package com.kelaker.kcommon.tools.vo;

import lombok.Data;

@Data
public class ToolsFileStsVo {
    private String securityToken;
    private String accessKeySecret;
    private String accessKeyId;
    private String expiration;
}
