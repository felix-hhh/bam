package com.kelaker.kcommon.system.service;

import com.kelaker.kcommon.system.constant.SystemConstant;
import com.kelaker.kcommon.system.dao.SysConfigGroupDao;
import com.kelaker.kcommon.system.dto.SysConfigGroupDto;
import com.kelaker.kcommon.system.entity.SysConfigGroup;
import com.kelaker.kcommon.system.manager.SysCacheManager;
import com.kelaker.kcommon.system.vo.SysConfigGroupVo;
import com.kelaker.ktools.cache.annotation.CacheExpire;
import com.kelaker.ktools.web.base.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统配置分组
 */
@Slf4j
@Service
public class SysConfigGroupService extends BaseService<SysConfigGroupDao, SysConfigGroup> {

    @Resource
    private SysCacheManager sysCacheManager;

    /**
     * 按系统类型取回所有系统分组
     *
     * @param systemType
     */
    public List<SysConfigGroupVo> getAllGroup(String systemType) {
        List<SysConfigGroup> configGroupAllList = this.sysCacheManager.getConfigGroupAllFromCache()
                .stream()
                .filter(sysConfigGroup -> systemType.equals(sysConfigGroup.getSystemType()))
                .collect(Collectors.toList());
        return super.mapListToTarget(configGroupAllList, SysConfigGroupVo.class);

    }

    /**
     * 取回全部分组
     */
    public List<SysConfigGroupVo> getAllGroup() {
        List<SysConfigGroup> configGroupAllList = this.sysCacheManager.getConfigGroupAllFromCache();
        return super.mapListToTarget(configGroupAllList, SysConfigGroupVo.class);
    }

    /**
     * 添加系统分组
     *
     * @param dto
     */
    @CacheExpire(key = SystemConstant.SYS_CONFIG_GROUP_LIST_CACHE_KEY)
    public void addSysConfigGroup(SysConfigGroupDto dto) {
        SysConfigGroup sysConfigGroup = super.objectConvert(dto, SysConfigGroup.class);
        super.save(sysConfigGroup);
    }
}
