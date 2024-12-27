package com.kelaker.kcommon.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.system.constant.SystemConstant;
import com.kelaker.kcommon.system.dao.SysActionDao;
import com.kelaker.kcommon.system.dto.SysActionDto;
import com.kelaker.kcommon.system.dto.SysActionSearchDto;
import com.kelaker.kcommon.system.entity.SysAction;
import com.kelaker.kcommon.system.entity.SysAdminInfo;
import com.kelaker.kcommon.system.manager.SysCacheManager;
import com.kelaker.kcommon.system.vo.SysActionVo;
import com.kelaker.kcommon.system.vo.SysModuleTreeVo;
import com.kelaker.ktools.cache.annotation.CacheExpire;
import com.kelaker.ktools.common.exception.BusinessException;
import com.kelaker.ktools.common.populator.ConvertUtils;
import com.kelaker.ktools.common.utils.ValidateUtil;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统功能表(SysAction)表服务实现类
 *
 * @author felix huang
 * @since 2020-03-27 11:42:57
 */
@Slf4j
@Service
public class SysActionService extends BaseService<SysActionDao, SysAction> {

    @Resource
    private SysModuleService sysModuleService;

    @Resource
    private SysCacheManager sysCacheManager;

    /**
     * 添加系统功能
     *
     * @param dto {@link SysActionDto}
     * @throws BusinessException 如果功能模块不存在 | 系统功能已存在
     */
    @CacheExpire(key = SystemConstant.SYS_ACTION_LIST_CACHE_KEY)
    public void addSystemAction(SysActionDto dto) {
        sysModuleService.findByModuleCode(dto.getModuleCode())
                .orElseThrow(() -> new BusinessException("系统模块不存在"));

        findByActionCode(dto.getActionCode()).ifPresent((sysAction -> {
            log.error("======系统功能已存在{}", sysAction.getActionCode());
            throw new BusinessException("系统功能已存在");
        }));

        // save
        SysAction sysAction = super.objectConvert(dto, SysAction.class);

        super.save(sysAction);
    }

    /**
     * 分页查询系统功能
     *
     * @param dto dto
     */
    public IPage<SysActionVo> querySystemActionPage(RequestPage<SysActionSearchDto> dto) {
        SysActionSearchDto searchDto = dto.getData();

        IPage<SysAction> sourcePage = super.lambdaQuery()
                .like(ValidateUtil.isNotBlank(searchDto.getActionCode()), SysAction::getActionCode, searchDto.getActionCode())
                .like(ValidateUtil.isNotBlank(searchDto.getUrl()), SysAction::getUrl, searchDto.getUrl())
                .eq(ValidateUtil.isNotBlank(searchDto.getModuleCode()), SysAction::getModuleCode, searchDto.getModuleCode())
                .page(super.createPage(dto));
        return super.mapPageToTarget(sourcePage, this::entityToVo);
    }

    /**
     * entity to Vo
     *
     * @param entity entity
     * @return Vo
     */
    public SysActionVo entityToVo(SysAction entity) {
        return super.objectConvert(entity, SysActionVo.class);
    }

    /**
     * 根据功能代码查找系统功能
     *
     * @param actionCode 功能代码
     * @return {@link Optional}
     */
    public Optional<SysAction> findByActionCode(String actionCode) {
        return sysCacheManager.getActionListFromCache().stream()
                .filter(sysAction -> sysAction.getActionCode().equals(actionCode))
                .findFirst();
    }

    public List<SysModuleTreeVo> treeAdminModules(Map<String, List<SysAction>> moduleActionsMap) {
        List<SysModuleTreeVo> topTreeNodeList = new ArrayList<>(moduleActionsMap.size());
        moduleActionsMap.forEach((moduleCode, actionList) -> {
            SysModuleTreeVo node = new SysModuleTreeVo();
            node.setModuleCode(moduleCode);
            node.setActionCodes(actionList.stream().map(SysAction::getActionCode).collect(Collectors.toSet()));
            topTreeNodeList.add(node);
        });
        return topTreeNodeList;
    }

    /**
     * 根据功能代码批量查询功能权限
     *
     * @param actionCodes 功能代码
     * @return 功能权限
     */
    public Set<SysAction> listByActionCodes(Set<String> actionCodes) {
        return sysCacheManager.getActionListFromCache().stream()
                .filter((sysAction ->
                        actionCodes.contains(sysAction.getActionCode())
                )).collect(Collectors.toSet());
    }

    /**
     * 根据功能代码批量查询功能权限
     * 转换实体
     *
     * @param actionCodes 功能代码
     * @return 功能权限
     */
    public Set<SysActionVo> listVoByActionCodes(Set<String> actionCodes) {
        return this.sysCacheManager.getActionListFromCache().stream()
                .filter((sysAction ->
                        actionCodes.contains(sysAction.getActionCode())
                ))
                .map(this::entityToVo)
                .collect(Collectors.toSet());
    }

    /**
     * 根据模块代码获取功能列表
     *
     * @param moduleCode 模块代码
     * @return 功能列表
     */
    public List<SysActionVo> listByModuleCode(String moduleCode) {
        return sysCacheManager.getActionListFromCache().stream()
                .filter(sysAction ->
                        sysAction.getModuleCode().equals(moduleCode)
                )
                .map(this::entityToVo)
                .collect(Collectors.toList());
    }

    /**
     * 获取系统功能Vo列表
     *
     * @return 系统功能Vo列表
     */
    public List<SysActionVo> getActionVoList() {
        return sysCacheManager.getActionListFromCache().stream()
                .map(this::entityToVo)
                .collect(Collectors.toList());
    }

    /**
     * 查询所有系统功能
     */
    public List<SysAction> getAllActionList() {
        return super.list();
    }

    /**
     * 更新系统功能
     *
     * @param dto dto
     */
    @CacheExpire(key = SystemConstant.SYS_ACTION_LIST_CACHE_KEY)
    public void updateSysAction(SysActionDto dto) {
        sysModuleService.findByModuleCode(dto.getModuleCode())
                .orElseThrow(() -> new BusinessException("系统模块不存在"));

        final SysAction sysAction = findByActionCode(dto.getActionCode())
                .orElseThrow(() -> new BusinessException("系统功能不存在"));

//        sysAction.setActionName(dto.getActionName());
//        sysAction.setModuleCode(dto.getModuleCode());
//        sysAction.setRemark(dto.getRemark());
        ConvertUtils.copyProperties(dto, sysAction);
        super.updateById(sysAction);
    }

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     */
    @Override
    public boolean saveBatch(Collection<SysAction> entityList) {
        return super.saveBatch(entityList);
    }

    /**
     * 删除功能
     *
     * @param actionCodes
     */
    @CacheExpire(key = SystemConstant.SYS_ACTION_LIST_CACHE_KEY)
    public void deleteByActionCode(String actionCodes) {
        super.removeById(actionCodes);
    }

    /**
     * 批量插入或更新
     *
     * @param sysActions
     */
    public boolean saveOrUpdateBatch(List<SysAction> sysActions) {
        return super.saveOrUpdateBatch(sysActions);
    }

//--------------------------- helper functions ---------------------------//

    @CacheExpire(key = SystemConstant.SYS_ACTION_LIST_CACHE_KEY)
    public void deleteByActionCodes(Set<String> actionCodes) {
        lambdaUpdate().in(!actionCodes.isEmpty(), SysAction::getActionCode, actionCodes)
                .remove();
    }


}