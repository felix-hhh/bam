package com.kelaker.kcommon.system.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 系统菜单(SysMenu)查询实体类
 *
 * @author Felix Huang
 * @since 2024-12-06 15:49:10
 */
@Data
public class SysMenuDto {

    /**
     * id
     */
    private Long id;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    /**
     * 菜单类型（S_M_FIRST_MENU,S_M_CHILD_MENU,S_M_BUTTON）
     */
    @NotBlank(message = "菜单类型不能为空")
    private String type;

    private String icon;

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
    private Long parentId;

    /**
     * 是否隐藏
     */
    private boolean hide;
}

