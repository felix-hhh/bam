package com.kelaker.kcommon.system.dto;

import lombok.Data;

/**
 * 系统菜单(SysMenu)查询实体类
 *
 * @author Felix Huang
 * @since 2024-12-06 15:49:10
 */
@Data
public class SysMenuSearchDto {

    /**
     * id
     */
    private Integer id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单类型（S_M_FIRST_MENU,S_M_CHILD_MENU,S_M_BUTTON）
     */
    private String type;

    /**
     * 级别
     */
    private String level;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 组件
     */
    private String component;

    /**
     * 菜单路径
     */
    private String path;

    /**
     * 父菜单ID
     */
    private Integer parentId;
}

