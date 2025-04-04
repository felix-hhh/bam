package com.kelaker.kcommon.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kelaker.kcommon.user.entity.UserBlacklist;
import org.springframework.stereotype.Repository;

/**
 * 用户黑名单(UserBlacklist)表数据库访问层
 *
 * @author lfz
 * @since 2022-07-28 09:15:25
 */
@Repository
public interface UserBlacklistDao extends BaseMapper<UserBlacklist> {

}

