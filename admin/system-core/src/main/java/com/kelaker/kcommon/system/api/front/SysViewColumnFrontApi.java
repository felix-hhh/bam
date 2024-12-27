package com.kelaker.kcommon.system.api.front;


import com.kelaker.ktools.web.base.api.BaseApi;


import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.kelaker.kcommon.system.entity.SysViewColumn;
import com.kelaker.kcommon.system.service.SysViewColumnService;

/**
 * 页面显示列(SysViewColumn)表控制层
 *
 * @author Felix Huang
 * @since 2024-12-16 17:47:24
 */
@RestController
@RequestMapping("/front/sysViewColumn")
public class SysViewColumnFrontApi extends BaseApi {

    /**
     * 服务对象
     */
    @Resource
    private SysViewColumnService sysViewColumnService;


}

