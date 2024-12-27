package com.kelaker.kcommon.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kelaker.kcommon.system.entity.SysViewColumn;
import org.springframework.stereotype.Repository;

/**
 * 页面显示列(SysViewColumn)表数据库访问层
 *
 * @author Felix Huang
 * @since 2024-12-16 17:47:24
 */
@Repository
public interface SysViewColumnDao extends BaseMapper<SysViewColumn> {

}

