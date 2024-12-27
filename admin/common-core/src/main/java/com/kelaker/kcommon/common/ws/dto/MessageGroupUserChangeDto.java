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
public class MessageGroupUserChangeDto {

    /**
     * 群ID
     */
    private String groupId;

    /**
     * 用户ID
     */
    private Set<String> users;
}

