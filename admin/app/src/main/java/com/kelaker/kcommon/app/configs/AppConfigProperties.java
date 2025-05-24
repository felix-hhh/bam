package com.kelaker.kcommon.app.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "bam.app")
public class AppConfigProperties {

    /**
     * 小程序状态
     */
    private String miniProgramState;
}
