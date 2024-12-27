package com.kelaker.kcommon.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kelaker.kcommon.system.entity.SysAdminLog;
import org.springframework.stereotype.Repository;

/**
 * 管理员日志(AdminLog)表数据库访问层
 *
 * @author felix huang
 * @since 2020-03-27 11:42:56
 */
@Repository
public interface SysAdminLogDao extends BaseMapper<SysAdminLog> {

}