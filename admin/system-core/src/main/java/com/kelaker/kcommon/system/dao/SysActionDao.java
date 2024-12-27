package com.kelaker.kcommon.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kelaker.kcommon.system.entity.SysAction;
import org.springframework.stereotype.Repository;

/**
 * 系统功能表(SysAction)表数据库访问层
 *
 * @author felix huang
 * @since 2020-03-27 11:42:57
 */
@Repository
public interface SysActionDao extends BaseMapper<SysAction> {

}