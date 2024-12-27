//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.kelaker.kcommon.system.api.feign;

import com.kelaker.kcommon.common.system.vo.SysConfigVo;
import com.kelaker.kcommon.system.service.SysConfigService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping({"/system/feign/config"})
public class SystemConfigFeignApi {
    @Resource
    private SysConfigService sysConfigService;

    /**
     * 前端根据配置key的分组，取回配置
     *
     * @param configKey
     */
    @GetMapping({"/get/{configKey}"})
    public SysConfigVo getConfigByKey(@Validated @NotBlank(message = "配置值不能为空") @PathVariable("configKey") String configKey) {
        SysConfigVo sysConfigVo = this.sysConfigService.getSysConfigByKey(configKey);
        return sysConfigVo;
    }

    @GetMapping({"/list/groupCode/{groupCode}"})
    public List<SysConfigVo> getConfigByGroupCode(@Validated @NotBlank(message = "分组代码不能为空") @PathVariable("groupCode") String groupCode) {
        return this.sysConfigService.getByGroupCode(groupCode);
    }
}
