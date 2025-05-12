package com.kelaker.kcommon.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kelaker.kcommon.system.entity.SysViewSearch;
import org.springframework.stereotype.Repository;

/**
 * 页面搜索功能(SysViewSearch)表数据库访问层
 *
 * @author Felix Huang
 * @since 2025-05-12 15:45:05
 */
@Repository
public interface SysViewSearchDao extends BaseMapper<SysViewSearch> {

}

