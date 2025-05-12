package com.kelaker.kcommon.system.api.front;


import com.kelaker.ktools.web.base.api.BaseApi;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import com.kelaker.kcommon.system.service.SysViewSearchService;
import com.kelaker.kcommon.system.vo.SysViewSearchVo;

/**
 * 页面搜索功能(SysViewSearch)表控制层
 *
 * @author Felix Huang
 * @since 2025-05-12 15:45:04
 */
@RestController
@RequestMapping("/sysViewSearch/front")
public class SysViewSearchFrontApi extends BaseApi {

    /**
     * 服务对象
     */
    @Resource
    private SysViewSearchService sysViewSearchService;

    @GetMapping("/get/{id}")
    public SysViewSearchVo getSysViewSearch(@Validated @NotNull(message = "ID不能为空") @PathVariable("id") Long id) {
        return this.sysViewSearchService.getSysViewSearch(id);
    }
}

