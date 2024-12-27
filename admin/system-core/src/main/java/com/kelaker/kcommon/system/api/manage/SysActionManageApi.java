package com.kelaker.kcommon.system.api.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.system.dto.SysActionDto;
import com.kelaker.kcommon.system.dto.SysActionSearchDto;
import com.kelaker.kcommon.system.dto.SysAdminInfoSearchDto;
import com.kelaker.kcommon.system.service.SysActionService;
import com.kelaker.kcommon.system.vo.SysActionVo;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.annotation.HasAction;
import com.kelaker.ktools.web.annotation.InModule;
import com.kelaker.ktools.web.annotation.RecordLog;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * 系统功能
 *
 * @author felix huang
 * @since 2020-03-27 11:42:57
 */
@RestController
@RequestMapping("/system/manage/action")
@InModule(moduleCode = "SYSTEM")
public class SysActionManageApi {
    /**
     * 服务对象
     */
    @Resource
    private SysActionService sysActionService;

    /**
     * 添加系统功能
     *
     * @param dto 需要添加的系统功能信息
     */
    @RecordLog(type = "INSERT", description = "添加系统功能")
    @HasAction(actionCode = "SYS_ACTION:ADD", actionName = "添加系统功能")
    @PostMapping("/add")
    public void addSystemAction(@Validated @RequestBody SysActionDto dto) {
        sysActionService.addSystemAction(dto);
    }

    /**
     * 删除功能
     *
     * @param actionCode 功能代码
     */
    @HasAction(actionCode = "SYS_ACTION:DEL", actionName = "删除系统功能")
    @DeleteMapping("/del/{actionCode}")
    public void delSystemAction(@PathVariable("actionCode") @Validated @NotBlank(message = "功能代码不能为空") String actionCode) {
        this.sysActionService.deleteByActionCode(actionCode);
    }

    /**
     * 根据系统模块代码获取系统功能
     *
     * @param moduleCode 功能代码
     */
    @HasAction(actionCode = "SYS_ACTION:MODULE", actionName = "模块功能列表")
    @GetMapping("/query/{moduleCode}")
    public List<SysActionVo> listByModuleCode(@Validated @NotBlank(message = "模块代码不能为空") @PathVariable("moduleCode") String moduleCode) {
        return sysActionService.listByModuleCode(moduleCode);
    }

    /**
     * 获取系统功能列表
     */
    @HasAction(actionCode = "SYS_ACTION:PAGE", actionName = "系统功能列表")
    @PostMapping("/page")
    public IPage<SysActionVo> querySystemActionPage(@Validated @RequestBody RequestPage<SysActionSearchDto> dto) {
        return sysActionService.querySystemActionPage(dto);
    }

    /**
     * 更新系统功能
     *
     * @param dto 请求信息
     */
    @HasAction(actionCode = "SYS_ACTION:UPDATE", actionName = "修改系统功能")
    @PutMapping("/update")
    public void updateSysModule(@Validated @RequestBody SysActionDto dto) {
        sysActionService.updateSysAction(dto);
    }
}