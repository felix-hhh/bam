package com.kelaker.kcommon.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * 系统菜单(SysMenu)表实体类
 *
 * @author Felix Huang
 * @since 2024-12-06 15:49:10
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SysMenuVo {

    /**
     * id
     */
    private Long id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单类型（S_M_FIRST_MENU,S_M_CHILD_MENU,S_M_BUTTON）
     */
    private String type;

    private String typeStr;

    /**
     * 图标
     */
    private String icon;

    /**
     * 级别
     */
    private String level;

    /**
     * 排序
     */
    private Long orderNum;

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
     * 子菜单
     */
    private List<SysMenuVo> children;

    /**
     * 是否隐藏
     */
    private boolean hide;

    /**
     * 菜单权限代码
     */
    private String actionCode;
}

