package com.kelaker.kcommon.system.api.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.common.system.vo.SysConfigVo;
import com.kelaker.kcommon.system.dto.SysConfigDto;
import com.kelaker.kcommon.system.dto.SysConfigSearchDto;
import com.kelaker.kcommon.system.service.SysConfigService;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.annotation.HasAction;
import com.kelaker.ktools.web.annotation.InModule;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * 系统设置
 *
 * @author felix
 * @since 2021/1/31
 */
@RestController
@RequestMapping("/system/manage/config")
@InModule(moduleCode = "SYSTEM")
public class SysConfigManageApi {

    @Resource
    private SysConfigService sysConfigService;

    /**
     * 分页查询所有配置
     * @param params
     * @return
     */
    @HasAction(actionCode = "SYS_CONFIG:PAGE",actionName = "系统配置列表")
    @PostMapping("/page")
    public IPage<SysConfigVo> selectListByConfigPage(@RequestBody RequestPage<SysConfigSearchDto> params) {
        return sysConfigService.pageSysConfig(params);
    }

    /**
     * 根据configKey查询配置信息
     * @param configKey
     * @return
     */
    @HasAction(actionCode = "SYS_CONFIG:GET",actionName = "取回系统配置")
    @GetMapping("/get/{key}")
    public SysConfigVo findSysConfigByKey(@Validated @NotBlank(message = "配置Key不能为空") @PathVariable("key") String configKey) {
        return sysConfigService.getSysConfigByKey(configKey);
    }

    /**
     * 添加配置
     * @param dto
     * @return
     */
    @HasAction(actionCode = "SYS_CONFIG:ADD",actionName = "添加系统配置")
    @PostMapping("/add")
    public void saveConfigKey(@Validated @RequestBody SysConfigDto dto) {
        this.sysConfigService.saveSysConfig(dto);
        
    }

    /**
     * 根据configKey编辑配置
     * @param dto
     * @return
     */
    @HasAction(actionCode = "SYS_CONFIG:UPDATE",actionName = "修改系统配置")
    @PutMapping("/update")
    public void updateSysConfig(@Validated @RequestBody SysConfigDto dto) {
        this.sysConfigService.updateSysConfig(dto);
        
    }

    /**
     * 禁用配置
     * @param configKey
     * @return
     */
    @HasAction(actionCode = "SYS_CONFIG:DISABLE",actionName = "禁用系统配置")
    @PutMapping("/disable/{key}")
    public void disableByConfigKey(@Validated @NotBlank(message = "配置Key不能为空") @PathVariable("key") String configKey) {
        this.sysConfigService.disableSysConfig(configKey);
        
    }

    /**
     * 启用配置
     * @param configKey
     * @return
     */
    @HasAction(actionCode = "SYS_CONFIG:ENABLE",actionName = "启用系统配置")
    @PutMapping("/enable/{key}")
    public void enableByConfigKey(@Validated @NotBlank(message = "配置Key不能为空") @PathVariable("key") String configKey) {
        this.sysConfigService.enableSysConfig(configKey);
        
    }

    /**
     * 根据分组查询分组下的配置
     * @param groupCode
     * @return
     */
    @HasAction(actionCode = "SYS_CONFIG:GROUP",actionName = "分组系统配置")
    @GetMapping("/list/groupCode/{groupCode}")
    public List<SysConfigVo> getByGroupCode(@PathVariable("groupCode") String groupCode) {
        return sysConfigService.getByGroupCode(groupCode);
    }


    /**
     * 关闭站点
     *
     * @param remark
     */
    @HasAction(actionCode = "SYS_CONFIG:CLOSE_SITE",actionName = "关闭系统")
    @GetMapping("/close/site")
    public void closeSite(@Validated @NotBlank(message = "关闭站点理由不能为空") @RequestParam String remark) {
        sysConfigService.closeSite();
        
    }

    /**
     * 开启站点
     */
    @HasAction(actionCode = "SYS_CONFIG:OPEN_SITE",actionName = "打开系统")
    @GetMapping("/open/site")
    public void openSite() {
        sysConfigService.openSite();
        
    }

    /**
     * 获取当前站点状态
     */
    @GetMapping("/get/site/status")
    public Boolean getSiteStatus() {
        return sysConfigService.getSiteStatus();
    }

}
