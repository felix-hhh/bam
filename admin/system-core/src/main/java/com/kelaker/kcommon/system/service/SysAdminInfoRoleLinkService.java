package com.kelaker.kcommon.system.service;

import com.kelaker.kcommon.system.dao.SysAdminInfoRoleLinkDao;
import com.kelaker.kcommon.system.dto.SysRoleBindAdminsDto;
import com.kelaker.kcommon.system.entity.SysAdminInfoRoleLink;
import com.kelaker.kcommon.system.entity.SysAdminRole;
import com.kelaker.kcommon.system.vo.SysAdminRoleVo;
import com.kelaker.ktools.common.exception.BusinessException;
import com.kelaker.ktools.web.base.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author chym
 * @since  2020/3/31 13:58
 */
@Service
public class SysAdminInfoRoleLinkService extends BaseService<SysAdminInfoRoleLinkDao, SysAdminInfoRoleLink> {

    @Resource
    private SysAdminRoleService sysAdminRoleService;

    /**
     * 管理员授予角色列表（角色列表未经检查）
     *
     * @param adminIds   管理员ID集合
     * @param roleId     角色列表
     * @param operatorId 操作人ID
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void batchGrantRole(Set<Long> adminIds,
                               Long roleId,
                               long operatorId) {

        if (!adminIds.isEmpty()) {
            Set<Long> roleAdminIds = listAdminIdByRoleCode(roleId);
            // grant
            saveBatch(
                    adminIds.stream()
                            .filter(adminId -> !roleAdminIds.contains(adminId))
                            .map((adminId) -> {
                                SysAdminInfoRoleLink sysAdminInfoRoleLink = new SysAdminInfoRoleLink();
                                sysAdminInfoRoleLink.setOperateId(operatorId);
                                sysAdminInfoRoleLink.setRoleCode(roleId);
                                sysAdminInfoRoleLink.setInfoId(adminId);
                                return sysAdminInfoRoleLink;
                            }).collect(Collectors.toList())
            );
        }
    }

    /**
     * 根据管理员ID获取已经授予的角色列表
     *
     * @param adminId 管理员ID
     * @return 角色列表
     */
    public List<SysAdminRole> listGrantedRolesByAdminId(Long adminId) {
        if (adminId == 0) {
            //超级管理员
            List<SysAdminRole> roles = new ArrayList<>();
            SysAdminRole sysAdminRole = new SysAdminRole();
            sysAdminRole.setRoleName("超级管理员");
            roles.add(sysAdminRole);

            return roles;
        }
        // 1.先获取该管理员的授权信息列表 & 角色代码
        final Set<Long> roleCodes = listByAdminId(adminId).stream()
                .map(SysAdminInfoRoleLink::getRoleCode)
                .collect(Collectors.toSet());
        if (roleCodes.isEmpty()) {
            return Collections.emptyList();
        }
        // 2.根据角色代码批量获取角色信息

        return sysAdminRoleService.listByIds(roleCodes);
    }

    /**
     * 根据管理员ID获取已经授予的角色VO列表
     *
     * @param adminId 管理员ID
     * @return 角色列表
     */
    public Set<SysAdminRoleVo> listGrantedRoleVosByAdminId(long adminId) {
        return listGrantedRolesByAdminId(adminId).stream()
                .map(sysAdminRoleService::convertToVo)
                .collect(Collectors.toSet());
    }

    /**
     * 根据管理员ID获取授权信息列表
     *
     * @param adminId 管理员ID
     * @return 授权信息列表
     */
    public List<SysAdminInfoRoleLink> listByAdminId(long adminId) {
        return lambdaQuery()
                .eq(SysAdminInfoRoleLink::getInfoId, adminId)
                .list();
    }

    /**
     * 根据角色其授权信息列表
     *
     * @param roleCode 角色代码
     * @return 授权信息列表
     */
    public List<SysAdminInfoRoleLink> listByRoleCode(Long roleCode) {
        return lambdaQuery()
                .eq(SysAdminInfoRoleLink::getRoleCode, roleCode)
                .list();
    }

    private Set<Long> listAdminIdByRoleCode(Long roleCode) {
        return this.listByRoleCode(roleCode)
                .stream()
                .map(SysAdminInfoRoleLink::getInfoId)
                .collect(Collectors.toSet());
    }

    /**
     * 删除角色中制定管理员
     *
     * @param dto dto
     */
    public void deleteRoleUser(SysRoleBindAdminsDto dto) {
        Long roleId = dto.getRoleId();
        Set<Long> adminIds = dto.getAdminIds();

        sysAdminRoleService.getOptById(roleId).orElseThrow(() -> new BusinessException("角色不存在"));
        super.lambdaUpdate()
                .in(SysAdminInfoRoleLink::getInfoId, adminIds)
                .eq(SysAdminInfoRoleLink::getRoleCode, roleId)
                .remove();
    }

    /**
     * 根绝角色代码删除所有授权信息
     *
     * @param roleId 角色代码
     */
    public void deleteByRoleCode(Long roleId) {
        lambdaUpdate()
                .eq(SysAdminInfoRoleLink::getRoleCode, roleId)
                .remove();
    }
}
