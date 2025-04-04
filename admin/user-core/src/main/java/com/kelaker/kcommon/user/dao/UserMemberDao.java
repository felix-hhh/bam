package com.kelaker.kcommon.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kelaker.kcommon.user.entity.UserMember;
import org.springframework.stereotype.Repository;

/**
 * 用户会员(UserMember)表数据库访问层
 *
 * @author Felix Huang
 * @since 2023-05-02 22:42:01
 */
@Repository
public interface UserMemberDao extends BaseMapper<UserMember> {

}

