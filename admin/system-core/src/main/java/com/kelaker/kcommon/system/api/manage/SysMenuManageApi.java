package com.kelaker.kcommon.system.api.manage;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.system.dto.SysMenuDto;
import com.kelaker.kcommon.system.dto.SysMenuSearchDto;
import com.kelaker.kcommon.system.service.SysMenuService;
import com.kelaker.kcommon.system.vo.SysMenuVo;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.api.BaseApi;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 系统菜单(SysMenu)表控制层
 *
 * @author Felix Huang
 * @since 2024-12-06 15:49:09
 */
@RestController
@RequestMapping("/system/manage/menu")
public class SysMenuManageApi extends BaseApi {

    /**
     * 服务对象
     */
    @Resource
    private SysMenuService sysMenuService;

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 查询结果
     */
    @PostMapping("/page")
    public IPage<SysMenuVo> pageSysMenu(@RequestBody RequestPage<SysMenuSearchDto> searchDto) {
        return this.sysMenuService.queryPage(searchDto);
    }

    /**
     * 取回系统菜单数据
     */
    @GetMapping("/list")
    public List<SysMenuVo> listSysMenu() {
        return this.sysMenuService.listAll();
    }

    /**
     * 添加菜单
     *
     * @param sysMenuDto 系统菜单DTO
     */
    @PostMapping("/add")
    public void addSysMenu(@RequestBody SysMenuDto sysMenuDto) {
        this.sysMenuService.addSysMenu(sysMenuDto);
    }

}

