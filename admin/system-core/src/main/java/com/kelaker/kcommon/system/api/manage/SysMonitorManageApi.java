package com.kelaker.kcommon.system.api.manage;

import com.kelaker.kcommon.system.service.SysMonitorService;
import com.kelaker.kcommon.system.vo.SysMonitorVo;
import com.kelaker.kcommon.system.vo.SysRedisMonitorVo;
import com.kelaker.ktools.web.annotation.HasAction;
import com.kelaker.ktools.web.annotation.InModule;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

/**
 * 系统功能
 *
 * @author felix huang
 * @since 2020-03-27 11:42:57
 */
@RestController
@RequestMapping("/system/manage/monitor")
@InModule(moduleCode = "SYSTEM")
public class SysMonitorManageApi {
    /**
     * 服务对象
     */
    @Resource
    private SysMonitorService  sysMonitorService;

    @HasAction(actionCode = "SYSTEM_MONITOR:SYS",actionName = "系统监控")
    @GetMapping("/sys")
    public SysMonitorVo getSysMonitor() {
        return this.sysMonitorService.jvmRunMonitor();
    }
}