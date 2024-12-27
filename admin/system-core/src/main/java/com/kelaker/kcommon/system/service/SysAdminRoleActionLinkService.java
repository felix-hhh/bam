package com.kelaker.kcommon.system.service;

import com.kelaker.kcommon.system.dao.SysAdminRoleActionLinkDao;
import com.kelaker.kcommon.system.entity.SysAction;
import com.kelaker.kcommon.system.entity.SysAdminRoleActionLink;
import com.kelaker.kcommon.system.vo.SysActionVo;
import com.kelaker.ktools.common.utils.DateUtil;
import com.kelaker.ktools.common.utils.ValidateUtil;
import com.kelaker.ktools.web.base.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import jakarta.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author chym
 * @since 2020/3/31 13:58
 */
@Service
public class SysAdminRoleActionLinkService extends BaseService<SysAdminRoleActionLinkDao, SysAdminRoleActionLink> {

    @Resource
    private SysActionService sysActionService;

    /**
     * 角色授予功能列表（功能列表未检查）
     *
     * @param roleId      角色Id
     * @param actionCodes 功能列表
     * @param operatorId  操作员ID
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void grantActionsUnSafe(Long roleId,
                                   Set<String> actionCodes,
                                   long operatorId) {
        // 清除之前授权信息
        deleteByRoleCode(roleId);

        if (!CollectionUtils.isEmpty(actionCodes)) {
            // grant
            super.saveBatch(
                    actionCodes.stream()
                            .map((actionCode) -> {
                                SysAdminRoleActionLink sysAdminRoleActionLink = new SysAdminRoleActionLink();
                                sysAdminRoleActionLink.setActionCode(actionCode);
                                sysAdminRoleActionLink.setCreateDatetime(DateUtil.getCurrentDate());
                                sysAdminRoleActionLink.setOperateId(operatorId);
                                sysAdminRoleActionLink.setRoleId(roleId);
                                return sysAdminRoleActionLink;
                            }).collect(Collectors.toList())
            );
        }
    }

    /**
     * 根据角色代码删除授权信息
     *
     * @param roleId 角色代码
     */
    public void deleteByRoleCode(Long roleId) {
        lambdaUpdate().eq(SysAdminRoleActionLink::getRoleId, roleId)
                .remove();
    }

    /**
     * 根据角色代码获取已经授权的功能列表
     *
     * @param roleCode 角色代码
     * @return 功能列表
     */
    public Set<SysAction> listGrantedActionsByRoleCode(String roleCode) {
        // 1.根据角色代码获取已经授权的功能代码列表
        final Set<String> actionCodes = listByRoleCode(roleCode).stream()
                .map(SysAdminRoleActionLink::getActionCode)
                .collect(Collectors.toSet());
        if (actionCodes.isEmpty()) {
            return Collections.emptySet();
        }

        // 2.根据功能代码批量获取功能信息列表
        return sysActionService.listByActionCodes(actionCodes);
    }

    /**
     * 根据角色代码集合获取已经授权的功能集合
     *
     * @param roleCodes 角色代码集合
     * @return 功能集合
     */
    public Set<SysAction> listGrantedActionsByRoleCodes(Set<Long> roleCodes) {
        // 1.根据角色代码获取已经授权的功能代码列表
        final Set<String> actionCodes = listGrantedActionsCodeByRoleCodes(roleCodes);

        if (actionCodes.isEmpty()) {
            return Collections.emptySet();
        }

        // 2.根据功能代码批量获取功能信息列表
        return sysActionService.listByActionCodes(actionCodes);
    }

    /**
     * 根据角色代码集合获取已经授权的功能集合
     *
     * @param roleCodes 角色代码集合
     * @return 功能集合
     */
    public Set<String> listGrantedActionsUrlByRoleCodes(Set<Long> roleCodes) {
        // 1.根据角色代码获取已经授权的功能代码列表
        final Set<String> actionCodes = listGrantedActionsCodeByRoleCodes(roleCodes);

        if (actionCodes.isEmpty()) {
            return Collections.emptySet();
        }

        // 2.根据功能代码批量获取功能信息列表
        return sysActionService.listByActionCodes(actionCodes)
                .stream()
                .map(SysAction::getUrl)
                .collect(Collectors.toSet());
    }

    /**
     * 根据角色代码集合获取已经授权的功能代码集合
     *
     * @param roleCodes 角色代码集合
     * @return 功能集合
     */
    public Set<String> listGrantedActionsCodeByRoleCodes(Set<Long> roleCodes) {
        return batchListByRoleCodes(roleCodes).stream()
                .map(SysAdminRoleActionLink::getActionCode)
                .collect(Collectors.toSet());
    }

    /**
     * 根据角色代码获取已经授权的功能VO列表
     *
     * @param roleCode 角色代码
     * @return 功能列表
     */
    public List<SysActionVo> listGrantedActionVosByRoleCode(String roleCode) {
        return listGrantedActionsByRoleCode(roleCode).stream()
                .map(sysActionService::entityToVo)
                .collect(Collectors.toList());
    }

    /**
     * 根据角色代码获取所有授权信息列表
     *
     * @param roleId 角色ID
     * @return 授权信息列表
     */
    public List<SysAdminRoleActionLink> listByRoleCode(String roleId) {
        return lambdaQuery()
                .eq(SysAdminRoleActionLink::getRoleId, roleId)
                .list();
    }

    /**
     * 根据角色代码集合获取所有授权信息列表
     *
     * @param roleId 角色代码集合
     * @return 授权信息列表
     */
    public List<SysAdminRoleActionLink> batchListByRoleCodes(Set<Long> roleId) {
        if (ValidateUtil.isBlank(roleId)) {
            return Collections.emptyList();
        }
        return lambdaQuery()
                .in(SysAdminRoleActionLink::getRoleId, roleId)
                .list();
    }

    public void batchDeleteByActionCodes(Set<String> actionCodes) {
        lambdaUpdate().in(!actionCodes.isEmpty(), SysAdminRoleActionLink::getActionCode, actionCodes)
                .remove();
    }
}
