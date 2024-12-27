package com.kelaker.kcommon.system.service;

import com.kelaker.kcommon.system.constant.SystemConstant;
import com.kelaker.kcommon.system.dao.SysModuleDao;
import com.kelaker.kcommon.system.dto.SysModuleDto;
import com.kelaker.kcommon.system.entity.SysModule;
import com.kelaker.kcommon.system.manager.SysCacheManager;
import com.kelaker.kcommon.system.vo.SysModuleVo;
import com.kelaker.ktools.cache.annotation.CacheExpire;
import com.kelaker.ktools.common.exception.BusinessException;
import com.kelaker.ktools.web.base.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 系统模块(SysModule)表服务实现类
 *
 * @author felix huang
 * @since 2020-03-27 11:42:57
 */
@Slf4j
@Service
public class SysModuleService extends BaseService<SysModuleDao, SysModule> {

    @Resource
    private SysCacheManager sysCacheManager;

    /**
     * 添加系统模块
     *
     * @param dto {@link SysModuleDto}
     * @throws BusinessException 如果系统模块已经存在
     */
    @CacheExpire(key = SystemConstant.SYS_MODULE_LIST_CACHE_KEY)
    public void addSystemModule(SysModuleDto dto) {
        this.findByModuleCode(dto.getModuleCode()).ifPresent((sysModule -> {
            throw new BusinessException("该系统模块已经存在");
        }));

        // save
        SysModule sysModule = super.objectConvert(dto, SysModule.class);
        super.save(sysModule);
    }

    /**
     * 更新系统模块
     *
     * @param dto dto
     */
    @CacheExpire(key = SystemConstant.SYS_MODULE_LIST_CACHE_KEY)
    public void updateSystemModule(SysModuleDto dto) {
        final SysModule sysModule = findByModuleCode(dto.getModuleCode())
                .orElseThrow(() -> new BusinessException("模块不存在"));
        sysModule.setModuleName(dto.getModuleName());
        sysModule.setRemark(dto.getRemark());
        super.updateById(sysModule);
    }

    /**
     * 根据模块代码查找系统模块
     *
     * @param moduleCode 模块代码
     * @return {@link Optional}
     */
    public Optional<SysModule> findByModuleCode(String moduleCode) {
        return sysCacheManager.getModuleAllFromCache().stream()
                .filter(sysModule -> sysModule.getModuleCode().equals(moduleCode))
                .findFirst();
    }

    /**
     * 获取所有系统模块Vo列表
     *
     * @return 系统模块Vo列表
     */
    public List<SysModuleVo> getModuleVoList() {
        return sysCacheManager.getModuleAllFromCache().stream()
                .map(this::entityToVo)
                .collect(Collectors.toList());
    }

    /**
     * 健康检查
     */
    public void healthyCheck() {
        List<SysModule> sysModules = this.sysCacheManager.getModuleAllFromCache();
    }



    /**
     * entity to Vo
     *
     * @param entity entity
     * @return Vo
     */
    protected SysModuleVo entityToVo(SysModule entity) {
        SysModuleVo sysModuleVo = super.objectConvert(entity, SysModuleVo.class);
        String moduleCode = sysModuleVo.getModuleCode();

        return sysModuleVo;
    }

}