package com.kelaker.kcommon.system.helper;

import com.kelaker.kcommon.system.service.SysAdminInfoService;
import com.kelaker.kcommon.system.service.SysAdminRoleActionLinkService;
import com.kelaker.ktools.common.utils.ValidateUtil;
import com.kelaker.ktools.web.helper.AuthHelper;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.util.Set;

/**
 * @author chym
 * @ 2020/4/7 8:49
 */
@Component
public class AuthHelperImpl implements AuthHelper {

    @Resource
    private SysAdminRoleActionLinkService roleActionLinkService;

    @Resource
    private SysAdminInfoService sysAdminInfoService;

    @Override
    public boolean checkHasAction(Set<Long> roles, String actionCode) {
        if (ValidateUtil.isBlank(roles)) {
            return false;
        }
        return roleActionLinkService.listGrantedActionsCodeByRoleCodes(roles).contains(actionCode);
    }

    @Override
    public boolean checkHasAction(Long userTag, String actionCode) {
        //TODO 检查拥有的权限

        return false;
    }
}
