package com.kelaker.kcommon.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kelaker.kcommon.system.entity.SysMenu;
import org.springframework.stereotype.Repository;

/**
 * 系统菜单(SysMenu)表数据库访问层
 *
 * @author Felix Huang
 * @since 2024-12-06 15:49:10
 */
@Repository
public interface SysMenuDao extends BaseMapper<SysMenu> {

}

