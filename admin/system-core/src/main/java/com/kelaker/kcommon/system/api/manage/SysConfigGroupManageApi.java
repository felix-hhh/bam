package com.kelaker.kcommon.system.api.manage;

import com.kelaker.kcommon.system.dto.SysConfigGroupDto;
import com.kelaker.kcommon.system.service.SysConfigGroupService;
import com.kelaker.kcommon.system.vo.SysConfigGroupVo;
import com.kelaker.ktools.web.annotation.InModule;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 系统设置分组
 */
@RestController
@RequestMapping("/system/manage/config/group")
@InModule(moduleCode = "SYSTEM")
public class SysConfigGroupManageApi {

    @Resource
    private SysConfigGroupService sysConfigGroupService;

    /**
     * 添加系统分组设置
     *
     * @param dto
     */
    @PostMapping("/add")
    public void addSysConfigGroup(@Validated @RequestBody SysConfigGroupDto dto) {
        this.sysConfigGroupService.addSysConfigGroup(dto);
        
    }

    /**
     * 根据系统类型查询所有分组
     *
     * @param typeCode
     */
    @GetMapping("/all/{typeCode}")
    public List<SysConfigGroupVo> getSysConfigGroup(@PathVariable String typeCode) {
        return this.sysConfigGroupService.getAllGroup(typeCode);
    }

    /**
     * 查询所有分组
     * @return
     */
    @GetMapping("/all")
    public List<SysConfigGroupVo> getAllSysConfigGroup() {
        return this.sysConfigGroupService.getAllGroup();
    }
}
