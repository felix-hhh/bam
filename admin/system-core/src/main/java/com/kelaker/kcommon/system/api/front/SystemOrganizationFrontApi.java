package com.kelaker.kcommon.system.api.front;

import com.kelaker.ktools.web.base.api.BaseApi;

import jakarta.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.kelaker.kcommon.system.entity.SystemOrganization;
import com.kelaker.kcommon.system.service.SystemOrganizationService;

/**
 * 组织架构(SystemOrganization)表控制层
 *
 * @author Felix Huang
 * @since 2024-09-02 12:41:00
 */
@RestController
@RequestMapping("/front/systemOrganization")
public class SystemOrganizationFrontApi extends BaseApi {
    /**
     * 服务对象
     */
    @Resource
    private SystemOrganizationService systemOrganizationService;

}

