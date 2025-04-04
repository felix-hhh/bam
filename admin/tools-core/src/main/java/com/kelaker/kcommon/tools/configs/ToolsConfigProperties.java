package com.kelaker.kcommon.tools.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "bam.tools")
public class ToolsConfigProperties {

    /**
     * 微信appid
     */
    private String wxMicAppId;

    /**
     * 微信小程序密钥
     */
    private String wxMicSecret;
}
