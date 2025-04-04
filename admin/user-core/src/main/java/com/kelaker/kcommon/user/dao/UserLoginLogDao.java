package com.kelaker.kcommon.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kelaker.kcommon.user.entity.UserLoginLog;
import org.springframework.stereotype.Repository;

/**
 * 用户登录日志(UserLoginLog)表数据库访问层
 *
 * @author Felix Huang
 * @since 2022-08-23 14:23:29
 */
@Repository
public interface UserLoginLogDao extends BaseMapper<UserLoginLog> {

}

