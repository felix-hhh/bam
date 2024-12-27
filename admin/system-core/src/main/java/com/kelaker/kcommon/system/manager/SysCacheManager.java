package com.kelaker.kcommon.system.manager;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kelaker.kcommon.system.constant.SystemConstant;
import com.kelaker.kcommon.system.dao.*;
import com.kelaker.kcommon.system.entity.*;
import com.kelaker.ktools.cache.annotation.CacheIt;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 数据缓存管理器
 *
 * @author Felix Huang
 * @date 2020/4/1 16:32
 */
@Component
public class SysCacheManager {

    @Resource
    private SysActionDao sysActionDao;

    @Resource
    private SysModuleDao sysModuleDao;

    @Resource
    private SysConfigDao sysConfigDao;

    @Resource
    private SysConfigGroupDao sysConfigGroupDao;

    @Resource
    private SysDictDao sysDictDao;

    @Resource
    private SysTypeDao sysTypeDao;

    /**
     * 从缓存中拿到功能列表
     *
     * @return 功能列表
     */
    @CacheIt(key = SystemConstant.SYS_ACTION_LIST_CACHE_KEY)
    public List<SysAction> getActionListFromCache() {
        return sysActionDao.selectList(Wrappers.emptyWrapper());
    }

    /**
     * 从缓存中拿到模块列表
     *
     * @return 模块列表
     */
    @CacheIt(key = SystemConstant.SYS_MODULE_LIST_CACHE_KEY)
    public List<SysModule> getModuleAllFromCache() {
        return sysModuleDao.selectList(Wrappers.emptyWrapper());
    }

    /**
     * 从缓存中拿到系统配置
     */
    @CacheIt(key = SystemConstant.SYS_CONFIG_LIST_CACHE_KEY)
    public List<SysConfig> getConfigAllFromCache() {
        return sysConfigDao.selectList(Wrappers.emptyWrapper());
    }

    /**
     * 从缓存种拿到所有系统配置组
     */
    @CacheIt(key = SystemConstant.SYS_CONFIG_GROUP_LIST_CACHE_KEY)
    public List<SysConfigGroup> getConfigGroupAllFromCache() {
        return this.sysConfigGroupDao.selectList(Wrappers.emptyWrapper());
    }

    @CacheIt(key = SystemConstant.SYS_TYPE_LIST_CACHE_KEY)
    public List<SysType> getSysTypeAllFromCache() {
        return this.sysTypeDao.selectList(Wrappers.emptyWrapper());
    }

    /**
     * 从缓存中拿到系统字典
     */
    @CacheIt(key = SystemConstant.SYS_DICT_LIST_CACHE_KEY)
    public List<SysDict> getDictAllFromCache() {
        return sysDictDao.selectList(Wrappers.emptyWrapper());
    }

}
