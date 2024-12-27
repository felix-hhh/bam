package com.kelaker.kcommon.system.configs;

import com.kelaker.kcommon.system.service.SysModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.Resource;

@Slf4j
@Configuration("SystemScheduleConfig")
public class ScheduleConfig {

    @Resource
    private SysModuleService sysModuleService;


    /**
     * 检查系统健康，每分钟一次
     */
    public void checkHealth() {

    }
}
