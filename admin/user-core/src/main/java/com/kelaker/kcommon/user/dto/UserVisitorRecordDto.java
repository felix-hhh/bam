package com.kelaker.kcommon.user.dto;

import lombok.Data;

import java.util.Date;

/**
 * 访客记录表(UserVisitorRecord)查询实体类
 *
 * @author makejava
 * @since 2022-08-10 10:03:49
 */
@Data
public class UserVisitorRecordDto {
    private String id;
    /**
     * 查看人ID
     */
    private String watchId;
    /**
     * 被查看人ID
     */
    private String beVisitedId;
    /**
     * 创建时间
     */
    private Date createDatetime;
}

