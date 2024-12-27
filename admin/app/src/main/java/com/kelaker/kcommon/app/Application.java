package com.kelaker.kcommon.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Repository;

/**
 * 应用启动入口
 *
 * @author felix huang
 * @since 2021-12-11
 */
@SpringBootApplication(
        scanBasePackages = {
                "com.kelaker.kcommon",
                "com.kelaker.ktools"
        }
)
@MapperScan(basePackages = {"com.kelaker",}, annotationClass = Repository.class)
@EnableConfigurationProperties({})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
