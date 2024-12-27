package com.kelaker.kcommon.system.dto;

import lombok.Data;

import java.util.Date;

/**
 * 联系人组关联管理员(SysLinkmanGroupInfoLink)查询实体类
 *
 * @author Felix Huang
 * @since 2023-04-04 16:15:43
 */
@Data
public class SysLinkmanGroupInfoLinkSearchDto {
    private Long id;

    private String groupId;

    private Long infoId;

    private Date createDatetime;
}

