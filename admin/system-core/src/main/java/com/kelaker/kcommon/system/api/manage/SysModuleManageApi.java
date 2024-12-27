package com.kelaker.kcommon.system.api.manage;

import com.kelaker.kcommon.system.dto.SysModuleDto;
import com.kelaker.kcommon.system.service.SysModuleService;
import com.kelaker.kcommon.system.vo.SysModuleVo;
import com.kelaker.ktools.web.annotation.HasAction;
import com.kelaker.ktools.web.annotation.InModule;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 系统模块
 *
 * @author felix huang
 * @since 2020-03-27 11:42:57
 */
@RestController
@RequestMapping("/system/manage/module")
@InModule(moduleCode = "SYSTEM")
public class SysModuleManageApi {

    /**
     * 服务对象
     */
    @Resource
    private SysModuleService sysModuleService;

    /**
     * 添加系统模块
     *
     * @param dto 系统模块信息
     */
    @HasAction(actionCode = "SYS_MODULE:ADD", actionName = "添加系统模块")
    @PostMapping("/add")
    public void addModule(@Validated @RequestBody SysModuleDto dto) {
        sysModuleService.addSystemModule(dto);

    }

    /**
     * 获取模块列表
     */
    @HasAction(actionCode = "SYS_MODULE:LIST", actionName = "系统模块列表")
    @GetMapping("/list")
    public List<SysModuleVo> getModuleList() {
        return sysModuleService.getModuleVoList();
    }

    /**
     * 更新模块
     *
     * @param dto 请求信息
     */
    @HasAction(actionCode = "SYS_MODULE:UPDATE", actionName = "修改系统模块")
    @PutMapping("/update")
    public void updateSysModule(@Validated @RequestBody SysModuleDto dto) {
        sysModuleService.updateSystemModule(dto);

    }
}