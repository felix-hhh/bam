package com.kelaker.kcommon.common.ws.data;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class MessageGroupInfoVo {
    /**
     * ID
     */
    private String id;

    /**
     * 群聊名称
     */
    private String groupName;

    /**
     * 群用户总数
     */
    private Integer groupUserCount;

    /**
     * 群主
     */
    private String groupMaster;

    /**
     * 群管理员
     */
    private Set<String> groupAdmins;

    /**
     * 群成员
     */
    private Set<String> users;

    /**
     * 禁言用户
     */
    private Set<String> shutups;

    /**
     * 群公告
     */
    private String notice;

    /**
     * 公告作者
     */
    private String noticeAuthor;

    /**
     * 公告时间
     */
    private Date noticeDatetime;

    private String headerPath;

    /**
     * 确认邀请开关
     */
    private boolean invite;

    /**
     * 群类型
     */
    private String type;

    /**
     * 点赞数
     */
    private int like;
}
