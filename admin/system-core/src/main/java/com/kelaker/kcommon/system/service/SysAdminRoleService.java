package com.kelaker.kcommon.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.system.dao.SysAdminRoleDao;
import com.kelaker.kcommon.system.dto.SysAdminRoleDto;
import com.kelaker.kcommon.system.dto.SysAdminRoleGrantActionDto;
import com.kelaker.kcommon.system.dto.SysAdminRoleSearchDto;
import com.kelaker.kcommon.system.entity.SysAdminRole;
import com.kelaker.kcommon.system.vo.SysAdminRoleVo;
import com.kelaker.ktools.common.exception.BusinessException;
import com.kelaker.ktools.common.populator.ConvertUtils;
import com.kelaker.ktools.common.utils.ValidateUtil;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

/**
 * 管理员角色表(AdminRole)表服务实现类
 *
 * @author felix huang
 * @since 2020-03-27 11:42:57
 */
@Service
public class SysAdminRoleService extends BaseService<SysAdminRoleDao, SysAdminRole> {

    @Resource
    private SysAdminRoleActionLinkService sysAdminRoleActionLinkService;

    @Resource
    private SysActionService sysActionService;


    /**
     * 添加管理员角色
     *
     * @param dto {@link SysAdminRoleDto}
     */
    public void addAdminRole(SysAdminRoleDto dto) {
        findByRoleName(dto.getRoleName())
                .ifPresent((adminRole -> {
                    throw new BusinessException("该角色名称已经存在");
                }));
        // save
        SysAdminRole needToSave = new SysAdminRole();
        needToSave.setRemark(dto.getRemark());
        needToSave.setRoleName(dto.getRoleName());
        needToSave.setStatus(SysAdminRole.Status.NORMAL);

        super.save(needToSave);
    }

    public void changeAdminRole(SysAdminRoleDto dto) {
        SysAdminRole sysAdminRole = super.getOptById(dto.getId()).orElseThrow(() -> new BusinessException("角色不存在"));
        ConvertUtils.copyProperties(dto, sysAdminRole);
        super.updateById(sysAdminRole);
    }

    /**
     * 角色授予功能
     *
     * @param dto {@link SysAdminRoleGrantActionDto}
     */
    public void grantActions(SysAdminRoleGrantActionDto dto) {
        // 确保角色存在
        findByRoleCodeThrowExpIfNotExist(dto.getId());

        if (!CollectionUtils.isEmpty(dto.getActionCodes())) {
            // 确保功能存在
            if (sysActionService.listByIds(dto.getActionCodes()).size() != dto.getActionCodes().size()) {
                throw new BusinessException("功能代码列表数据不正确");
            }
        }

        // grant actions
        sysAdminRoleActionLinkService.grantActionsUnSafe(dto.getId(),
                dto.getActionCodes(),
                super.getUserId());
    }

    /**
     * 分页查询管理员角色
     *
     * @param searchDto 查询dto
     */
    public IPage<SysAdminRoleVo> pageSysAdminRole(RequestPage<SysAdminRoleSearchDto> searchDto) {
        SysAdminRoleSearchDto dto = searchDto.getData();
        if (ValidateUtil.isBlank(dto)) {
            dto = new SysAdminRoleSearchDto();
        }
        SysAdminRole sysAdminRole = super.objectConvert(dto, SysAdminRole.class);


        IPage<SysAdminRole> page = super.page(super.createPage(searchDto), super.createWrapper(sysAdminRole));
        return super.mapPageToTarget(page, this::convertToVo);
    }

    /**
     * 获取管理员角色Vo
     *
     * @return 角色Vo列表
     */
    public List<SysAdminRoleVo> listRoleVos() {
        List<SysAdminRole> list = super.lambdaQuery()
                .list();
        return super.mapListToTarget(list, this::convertToVo);
    }

    /**
     * 根据角色代码禁用角色
     *
     * @param id 角色代码
     * @throws BusinessException 如果角色不存在
     */
    public void disableByRoleCode(Long id) {
        updateStatusByRoleCode(id, SysAdminRole.Status.DISABLE);
    }

    /**
     * 根据角色代码启用角色
     *
     * @param roleId 角色代码
     * @throws BusinessException 如果角色不存在
     */
    public void enableByRoleCode(Long roleId) {
        updateStatusByRoleCode(roleId, SysAdminRole.Status.NORMAL);
    }

    /**
     * 根据角色代码更新角色状态
     *
     * @param roleId 角色id
     * @param status {@link SysAdminRole.Status}
     * @throws BusinessException 如果角色不存在
     */
    public void updateStatusByRoleCode(Long roleId, SysAdminRole.Status status) {
        final SysAdminRole theSysAdminRole = findByRoleCodeThrowExpIfNotExist(roleId);
        theSysAdminRole.setStatus(status);
        updateById(theSysAdminRole);
    }

    /**
     * @param id 角色代码
     * @return {@link SysAdminRole}
     * @throws BusinessException 如果角色不存在
     */
    public SysAdminRole findByRoleCodeThrowExpIfNotExist(Long id) {
        return Optional.of(super.getById(id))
                .orElseThrow(() -> new BusinessException("角色不存在"));
    }

    /**
     * 根据角色代码查找角色信息
     *
     * @param roleName 角色名称
     * @return {@link Optional}
     */
    public Optional<SysAdminRole> findByRoleName(String roleName) {
        return lambdaQuery()
                .eq(SysAdminRole::getRoleName, roleName)
                .oneOpt();
    }

    //--------------------------- helper functions ---------------------------//

    /**
     * entity to Vo
     *
     * @param entity entity
     * @return Vo
     */
    protected SysAdminRoleVo convertToVo(SysAdminRole entity) {
        SysAdminRoleVo sysAdminRoleVo = super.objectConvert(entity, SysAdminRoleVo.class);
        sysAdminRoleVo.setStatusStr(entity.getStatus().getRemark());
        return sysAdminRoleVo;
    }

}