package com.kelaker.kcommon.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kelaker.kcommon.user.entity.UserRegisterLog;
import org.springframework.stereotype.Repository;

/**
 * 用户注册信息表(UserRegisterLog)表数据库访问层
 *
 * @author Felix Huang
 * @since 2022-08-23 14:43:55
 */
@Repository
public interface UserRegisterLogDao extends BaseMapper<UserRegisterLog> {

}

