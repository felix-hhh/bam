package com.kelaker.kcommon.system.api.front;

import com.kelaker.kcommon.system.service.SysConfigService;
import com.kelaker.kcommon.system.vo.SysConfigFrontVo;
import com.kelaker.ktools.web.annotation.ApiRestController;
import com.kelaker.ktools.web.base.api.BaseApi;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * 系统设置后台接口
 *
 * @author felix
 * @since 2021/1/31
 */
@ApiRestController
@RestController
@RequestMapping("/system/front/config")
public class SysConfigFrontApi extends BaseApi {

    @Resource
    private SysConfigService sysConfigService;

    /**
     * 前端根据配置key，取回配置（HTML）
     *
     * @param key
     */
    @GetMapping("/show/{key}")
    public void showFrontConfig(@Validated @NotBlank(message = "key不能为空") @PathVariable("key") String key) {
        SysConfigFrontVo frontConfig = this.sysConfigService.getFrontConfig(key);
        super.renderHTML(frontConfig.getConfigValue());
    }

    /**
     * 前端根据配置key，取回配置
     *
     * @param key
     */
    @GetMapping("/get/{key}")
    public SysConfigFrontVo getFrontConfig(@Validated @NotBlank(message = "key不能为空") @PathVariable("key") String key) {
        return this.sysConfigService.getFrontConfig(key);
    }

    /**
     * 前端根据配置key的分组，取回配置
     *
     * @param groupCode 配置组
     */
    @GetMapping("/group/{groupCode}")
    public List<SysConfigFrontVo> getFrontConfigByGroup(@Validated @NotBlank(message = "配置组") @PathVariable("groupCode") String groupCode) {
        return this.sysConfigService.getFrontConfigByGroup(groupCode);
    }

}
