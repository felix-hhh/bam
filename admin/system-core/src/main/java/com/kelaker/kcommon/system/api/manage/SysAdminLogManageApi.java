package com.kelaker.kcommon.system.api.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.system.dto.SysAdminLogRequestDto;
import com.kelaker.kcommon.system.service.SysAdminLogService;
import com.kelaker.kcommon.system.vo.SysAdminLogVo;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.annotation.HasAction;
import com.kelaker.ktools.web.annotation.InModule;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

/**
 * 管理员操作日志
 *
 * @author felix huang
 * @since 2020-03-27 11:42:56
 */
@RestController
@RequestMapping("/system/manage/log")
@InModule(moduleCode = "SYSTEM")
public class SysAdminLogManageApi {
    /**
     * 服务对象
     */
    @Resource
    private SysAdminLogService sysAdminLogService;

    /**
     * 分页查找管理员日志
     */
    @HasAction(actionCode = "SYSTEM_LOG:PAGE",actionName = "操作日志")
    @PostMapping("/page")
    public IPage<SysAdminLogVo> pageAdminLog(@Validated @RequestBody RequestPage<SysAdminLogRequestDto> params){
        return sysAdminLogService.queryPage(params);
    }
}