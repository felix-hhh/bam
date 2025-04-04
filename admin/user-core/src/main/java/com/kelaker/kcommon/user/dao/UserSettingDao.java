package com.kelaker.kcommon.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kelaker.kcommon.user.entity.UserSetting;
import org.springframework.stereotype.Repository;

/**
 * 用户设置(UserSetting)表数据库访问层
 *
 * @author lfz
 * @since 2022-07-28 14:28:02
 */
@Repository
public interface UserSettingDao extends BaseMapper<UserSetting> {

}

