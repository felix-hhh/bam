//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.kelaker.kcommon.system.api.feign;

import com.kelaker.kcommon.system.service.SysAdminRoleActionLinkService;
import com.kelaker.kcommon.system.service.SysAdminRoleService;
import com.kelaker.kcommon.system.vo.SysAdminRoleVo;
import com.kelaker.ktools.common.vo.R;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping({"/system/feign/role"})
public class SystemRoleFeignApi {

    @Resource
    private SysAdminRoleService sysAdminRoleService;

    @Resource
    private SysAdminRoleActionLinkService sysAdminRoleActionLinkService;

    @PostMapping({"/action/url/list"})
    R<Set<String>> listUserActionUrls(@RequestBody Set<Long> roles) {
        Set<String> sysActions = this.sysAdminRoleActionLinkService.listGrantedActionsUrlByRoleCodes(roles);
        return R.success(sysActions);
    }

    /**
     * 取回角色分组
     */
    @GetMapping("/list")
    public R<List<SysAdminRoleVo>> listAdminRole() {
        return R.success(sysAdminRoleService.listRoleVos());
    }


}
