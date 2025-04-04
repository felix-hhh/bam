package com.kelaker.kcommon.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "user_invite_record", autoResultMap = true)
public class UserInviteRecord extends Model<UserInviteRecord> {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 邀请人id
     */
    private Long userId;

    /**
     * 被邀请人id
     */
    private Long inviteeId;


    @TableField(fill = FieldFill.INSERT)
    private Date createDatetime;


}
