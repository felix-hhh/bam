//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.kelaker.kcommon.system.api.feign;

import com.kelaker.kcommon.system.service.SysAdminInfoService;
import com.kelaker.kcommon.system.vo.SysAdminInfoVo;
import com.kelaker.ktools.common.utils.ValidateUtil;
import com.kelaker.ktools.common.vo.R;
import com.kelaker.ktools.common.vo.TokenVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping({"/system/feign/info"})
public class SystemInfoFeignApi {
    @Resource
    private SysAdminInfoService sysAdminInfoService;


    @PostMapping({"/check/token/{token}"})
    public R<TokenVo> checkToken(@PathVariable("token") String token) {
        TokenVo tokenVo = this.sysAdminInfoService.getUserToken(token);
        return ValidateUtil.isBlank(tokenVo) ? R.fail() : R.success(tokenVo);
    }

    /**
     * 根据角色代码查询管理员信息
     * @param roleCode 角色代码
     * @return
     */
    @GetMapping("/list/query/{roleCode}")
    public R<List<SysAdminInfoVo>> listBindAdminVosByRoleCode(@Validated @NotNull(message = "角色代码不能为空") @PathVariable("roleCode") Long roleCode) {
        return R.success(sysAdminInfoService.listBindAdminVosByRoleCode(roleCode));
    }
}
