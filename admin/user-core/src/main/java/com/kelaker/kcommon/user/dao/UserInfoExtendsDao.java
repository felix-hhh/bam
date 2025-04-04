package com.kelaker.kcommon.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kelaker.kcommon.user.entity.UserInfoExtends;
import org.springframework.stereotype.Repository;

/**
 * 用户拓展(UserInfoExtends)表数据库访问层
 *
 * @author felix huang
 * @since 2021-12-14 21:26:35
 */
@Repository
public interface UserInfoExtendsDao extends BaseMapper<UserInfoExtends> {

}
