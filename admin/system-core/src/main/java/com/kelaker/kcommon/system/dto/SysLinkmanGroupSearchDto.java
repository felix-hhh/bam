package com.kelaker.kcommon.system.dto;

import lombok.Data;

/**
 * 系统联系人组(SysLinkmanGroup)查询实体类
 *
 * @author Felix Huang
 * @since 2023-04-04 16:15:43
 */
@Data
public class SysLinkmanGroupSearchDto {
    /**
     * id
     */
    private String groupCode;

    /**
     * 组名称
     */
    private String groupName;

    /**
     * 备注
     */
    private String remark;
}
