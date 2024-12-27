package com.kelaker.kcommon.system.api.front;

import com.kelaker.kcommon.system.service.SysAdminInfoService;
import com.kelaker.kcommon.system.vo.AdminInfoBaseVo;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/system/front/admin/info")
public class SysAdminInfoFrontApi {

    @Resource
    private SysAdminInfoService sysAdminInfoService;

    /**
     * 显示管理员基础信息
     *
     * @param id
     */
    @GetMapping("/show/{id}")
    public AdminInfoBaseVo showAdminInfo(@Validated @NotNull(message = "管理员ID不能为空") @PathVariable("id") Long id) {
        return this.sysAdminInfoService.getAdminInfoBaseById(id);
    }
}
