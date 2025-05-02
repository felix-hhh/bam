package com.kelaker.kcommon.tools.configs;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolsConfigs {

    @Resource
    private ToolsConfigProperties toolsConfigProperties;

    @Bean
    public OSS getOSS() {
        return new OSSClientBuilder().build(
                toolsConfigProperties.getAliyunOssEndpoint(),
                toolsConfigProperties.getAliyunOssAccessKeyId(),
                toolsConfigProperties.getAliyunOssAccessKeySecret()
        );
    }
}
