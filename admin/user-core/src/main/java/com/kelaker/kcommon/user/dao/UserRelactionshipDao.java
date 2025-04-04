package com.kelaker.kcommon.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kelaker.kcommon.user.entity.UserRelactionship;
import org.springframework.stereotype.Repository;

/**
 * 好友关系(UserRelactionship)表数据库访问层
 *
 * @author felix huang
 * @since 2021-12-14 21:26:51
 */
@Repository
public interface UserRelactionshipDao extends BaseMapper<UserRelactionship> {

}
