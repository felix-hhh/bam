package com.kelaker.kcommon.user.vo;

import lombok.Data;

import java.util.Date;

/**
 * 用户设置(UserSetting)表实体类
 *
 * @author lfz
 * @since 2022-07-28 14:28:03
 */

@Data
public class UserSettingVo {
    //主键ID
    private String id;
    //用户ID
    private String userId;
    //名称
    private String name;
    //配置key
    private String configKey;
    //配置value
    private String configValue;
    //创建时间
    private Date createDatetime;
    //更新时间
    private Date updateDatetime;

}

