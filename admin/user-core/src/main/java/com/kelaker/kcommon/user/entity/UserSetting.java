package com.kelaker.kcommon.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.kelaker.kcommon.user.constant.UserSettingKey;
import lombok.Data;

/**
 * 用户设置(UserSetting)表实体类
 *
 * @author lfz
 * @since 2022-07-28 14:28:03
 */

@Data
@TableName("user_setting")
public class UserSetting extends Model<UserSetting> {
    //主键ID
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    //用户ID
    private Long userId;

    //配置key
    private UserSettingKey configKey;

    //配置value
    private String configValue;

}

