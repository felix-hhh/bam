package com.kelaker.kcommon.system.api.front;


import com.kelaker.ktools.web.base.api.BaseApi;


import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.kelaker.kcommon.system.entity.SysView;
import com.kelaker.kcommon.system.service.SysViewService;

/**
 * 系统页面(SysView)表控制层
 *
 * @author Felix Huang
 * @since 2024-12-16 17:45:55
 */
@RestController
@RequestMapping("/front/sysView")
public class SysViewFrontApi extends BaseApi {

    /**
     * 服务对象
     */
    @Resource
    private SysViewService sysViewService;


}

