package com.kelaker.kcommon.system.api.manage;


import com.kelaker.ktools.web.base.api.BaseApi;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.kcommon.system.vo.SysViewColumnVo;
import com.kelaker.kcommon.system.dto.SysViewColumnSearchDto;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import com.kelaker.kcommon.system.entity.SysViewColumn;
import com.kelaker.kcommon.system.service.SysViewColumnService;

import java.util.List;

/**
 * 页面显示列(SysViewColumn)表控制层
 *
 * @author Felix Huang
 * @since 2024-12-16 17:47:24
 */
@RestController
@RequestMapping("/system/manage/view/column")
public class SysViewColumnManageApi extends BaseApi {

    /**
     * 服务对象
     */
    @Resource
    private SysViewColumnService sysViewColumnService;

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 查询结果
     */
    @PostMapping("/page")
    public IPage<SysViewColumnVo> pageSysViewColumn(@RequestBody RequestPage<SysViewColumnSearchDto> searchDto) {
        return this.sysViewColumnService.queryPage(searchDto);
    }

    @GetMapping("/list/{viewId}")
    public List<SysViewColumnVo> listSysViewColumn(@PathVariable("viewId") Long viewId) {
        return this.sysViewColumnService.getViewColumnByViewId(viewId);
    }
}

