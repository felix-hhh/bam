package com.kelaker.kcommon.system.api.manage;

import com.kelaker.kcommon.system.dto.SysTypeDto;
import com.kelaker.kcommon.system.service.SysTypeService;
import com.kelaker.kcommon.system.vo.SysTypeVo;
import com.kelaker.ktools.web.annotation.InModule;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 系统类型后台
 *
 * @author felix huang
 * @since 2021/2/1
 */
@RestController
@RequestMapping("/system/manage//type")
@InModule(moduleCode = "SYSTEM")
public class SysTypeManageApi {

    @Resource
    private SysTypeService sysTypeService;

    /**
     * 查询所有系统类型数据
     */
    @GetMapping("/all")
    public List<SysTypeVo> getAllSysType() {
        return this.sysTypeService.getAllType();
    }

    /**
     * 查询所有系统类型数据（无备注），用于选项
     */
    @GetMapping("/all/simple")
    public List<SysTypeVo> getAllSysTypeSimple() {
        return this.sysTypeService.getAllSysTypeSimple();
    }

    /**
     * 添加系统类型
     *
     * @param sysTypeDto
     */
    @PostMapping("/add")
    public void addSysType(@Validated @RequestBody SysTypeDto sysTypeDto) {
        this.sysTypeService.addSystemType(sysTypeDto);
        
    }
}
