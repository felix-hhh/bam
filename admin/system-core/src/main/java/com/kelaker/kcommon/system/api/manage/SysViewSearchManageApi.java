package com.kelaker.kcommon.system.api.manage;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.system.dto.SysViewSearchSearchDto;
import com.kelaker.kcommon.system.service.SysViewSearchService;
import com.kelaker.kcommon.system.vo.SysViewSearchVo;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.api.BaseApi;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 页面搜索功能(SysViewSearch)表控制层
 *
 * @author Felix Huang
 * @since 2025-05-12 15:45:04
 */
@RestController
@RequestMapping("/system/manage/view/search")
public class SysViewSearchManageApi extends BaseApi {

    /**
     * 服务对象
     */
    @Resource
    private SysViewSearchService sysViewSearchService;

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 查询结果
     */
    @PostMapping("/page")
    public IPage<SysViewSearchVo> pageSysViewSearch(@RequestBody RequestPage<SysViewSearchSearchDto> searchDto) {
        return this.sysViewSearchService.queryPage(searchDto);
    }

    @GetMapping("/get/{viewId}")
    public List<SysViewSearchVo> getSysViewSearchByView( @PathVariable("viewId") Long viewId) {
        return this.sysViewSearchService.getSysViewSearchByView(viewId);
    }
}

