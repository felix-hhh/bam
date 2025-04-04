package com.kelaker.kcommon.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * 好友关系(UserRelactionship)实体类
 *
 * @author felix huang
 * @since 2021-12-14 21:26:51
 */
@Data
@TableName("user_relactionship")
public class UserRelactionship extends Model<UserRelactionship> {

    @TableId(type= IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private String userId;


    /**
     * 好友id
     */
    private String friendId;

    /**
     * 关注来源
     */
    private String source;

    /**
     * 是否双向关注
     */
    private boolean bothway;

    /**
     * userid对friendid的备注
     */
    private String aliasUser;

    /**
     * friend对userid的备注
     */
    private String aliasFriend;

    /**
     * 好友验证
     */
    private String reqMsg;

    /**
     * 创建时间
     */
    private Date createDatetime;

    /**
     * 创建好友时间
     */
    private Date friendsCreateDatetime;
}
