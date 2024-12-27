package com.kelaker.kcommon.system.vo;

import lombok.Data;

import java.util.Date;

/**
 * 联系人组关联管理员(SysLinkmanGroupInfoLink)表实体类
 *
 * @author Felix Huang
 * @since 2023-04-04 16:15:43
 */
@Data
public class SysLinkmanGroupInfoLinkVo {
    private Long id;

    private String groupId;

    private Long infoId;

    private Date createDatetime;
}

