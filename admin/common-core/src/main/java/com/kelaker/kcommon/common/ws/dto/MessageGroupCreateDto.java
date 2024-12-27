package com.kelaker.kcommon.common.ws.dto;

import lombok.Data;

import java.util.Set;

/**
 * 群信息(MessageGroup)查询实体类
 *
 * @author Felix Huang
 * @since 2022-08-15 16:29:04
 */
@Data
public class MessageGroupCreateDto {

    /**
     * 群名称
     */
    private String groupName;

    /**
     * 公告
     */
    private String notice;

    /**
     * 房间类型
     */
    private String type;

    /**
     * 群成员
     */
    private Set<String> users;

    /**
     * 管理员
     */
    private Set<String> managers;

}

