package com.kelaker.kcommon.system.api.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.system.dto.SystemOrganizationDto;
import com.kelaker.kcommon.system.dto.SystemOrganizationSearchDto;
import com.kelaker.kcommon.system.service.SystemOrganizationService;
import com.kelaker.kcommon.system.vo.SystemOrganizationVo;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.api.BaseApi;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

/**
 * 组织架构(SystemOrganization)表控制层
 *
 * @author Felix Huang
 * @since 2024-09-02 12:41:00
 */
@RestController
@RequestMapping("/system/manage/organization")
public class SystemOrganizationManageApi extends BaseApi {
    /**
     * 服务对象
     */
    @Resource
    private SystemOrganizationService systemOrganizationService;

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 查询结果
     */
    @PostMapping("/page")
    public IPage<SystemOrganizationVo> pageSystemOrganization(@RequestBody RequestPage<SystemOrganizationSearchDto> searchDto) {
        return this.systemOrganizationService.queryPage(searchDto);
    }

    @PostMapping("/add")
    public void addSystemOrganization(@RequestBody SystemOrganizationDto systemOrganizationDto) {
        this.systemOrganizationService.addSystemOrganization(systemOrganizationDto);
    }
}

