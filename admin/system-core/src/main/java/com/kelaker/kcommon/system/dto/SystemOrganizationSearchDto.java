package com.kelaker.kcommon.system.dto;

import lombok.Data;

/**
 * 组织架构(SystemOrganization)查询实体类
 *
 * @author Felix Huang
 * @since 2024-09-02 12:41:01
 */
@Data
public class SystemOrganizationSearchDto {
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 组织代码
     */
    private String code;

    /**
     * 组织类型
     */
    private String type;

    /**
     * 排序
     */
    private Long orderNum;

    /**
     * 状态
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 父级部门ID
     */
    private Long parentId;
}

