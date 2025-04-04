package com.kelaker.kcommon.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * 用户黑名单(UserBlacklist)表实体类
 *
 * @author lfz
 * @since 2022-07-28 09:15:26
 */
@Data
@TableName("user_blacklist")
public class UserBlacklist extends Model<UserBlacklist> {
    //主键ID
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 黑名单用户
     */
    private String blackUserId;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createDatetime;
}

