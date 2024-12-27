package com.kelaker.kcommon.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kelaker.kcommon.system.entity.SystemOrganization;
import org.springframework.stereotype.Repository;

/**
 * 组织架构(SystemOrganization)表数据库访问层
 *
 * @author Felix Huang
 * @since 2024-09-02 12:41:01
 */
@Repository
public interface SystemOrganizationDao extends BaseMapper<SystemOrganization> {

}

