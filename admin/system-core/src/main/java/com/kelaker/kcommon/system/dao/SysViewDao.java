package com.kelaker.kcommon.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kelaker.kcommon.system.entity.SysView;
import org.springframework.stereotype.Repository;

/**
 * 系统页面(SysView)表数据库访问层
 *
 * @author Felix Huang
 * @since 2024-12-16 17:45:55
 */
@Repository
public interface SysViewDao extends BaseMapper<SysView> {

}

