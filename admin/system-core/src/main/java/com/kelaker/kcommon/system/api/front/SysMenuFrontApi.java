package com.kelaker.kcommon.system.api.front;


import com.kelaker.kcommon.system.service.SysMenuService;
import com.kelaker.ktools.web.base.api.BaseApi;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 系统菜单(SysMenu)表控制层
 *
 * @author Felix Huang
 * @since 2024-12-06 15:49:09
 */
@RestController
@RequestMapping("/front/sysMenu")
public class SysMenuFrontApi extends BaseApi {

    /**
     * 服务对象
     */
    @Resource
    private SysMenuService sysMenuService;


}

