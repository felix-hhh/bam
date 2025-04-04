package com.kelaker.kcommon.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kelaker.kcommon.user.entity.UserInfo;
import org.springframework.stereotype.Repository;

/**
 * 用户信息表(UserInfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-12-10 10:59:46
 */
@Repository
public interface UserInfoDao extends BaseMapper<UserInfo> {

}
