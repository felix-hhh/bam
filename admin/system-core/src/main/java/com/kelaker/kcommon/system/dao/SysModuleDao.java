package com.kelaker.kcommon.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kelaker.kcommon.system.entity.SysModule;
import org.springframework.stereotype.Repository;

/**
 * 系统模块(SysModule)表数据库访问层
 *
 * @author felix huang
 * @since 2020-03-27 11:42:57
 */
@Repository
public interface SysModuleDao extends BaseMapper<SysModule> {

}