package com.kelaker.kcommon.system.api.manage;


import com.kelaker.ktools.web.base.api.BaseApi;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.kcommon.system.vo.SysViewVo;
import com.kelaker.kcommon.system.dto.SysViewSearchDto;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import com.kelaker.kcommon.system.entity.SysView;
import com.kelaker.kcommon.system.service.SysViewService;

/**
 * 系统页面(SysView)表控制层
 *
 * @author Felix Huang
 * @since 2024-12-16 17:45:55
 */
@RestController
@RequestMapping("/system/manage/view")
public class SysViewManageApi extends BaseApi {

    /**
     * 服务对象
     */
    @Resource
    private SysViewService sysViewService;

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 查询结果
     */
    @PostMapping("/page")
    public IPage<SysViewVo> pageSysView(@RequestBody RequestPage<SysViewSearchDto> searchDto) {
        return this.sysViewService.queryPage(searchDto);
    }

    @GetMapping("/{id}")
    public SysViewVo getSysView(@PathVariable("id") Long id) {
        return this.sysViewService.getSystemViewById(id);
    }

    /**
     * 根据页面路径查找页面配置
     *
     * @param path 页面路径
     */
    @GetMapping("/path/{path}")
    public SysViewVo getSysViewByPath(@PathVariable("path") String path) {
        return this.sysViewService.getSystemViewByPath(path);
    }
}

