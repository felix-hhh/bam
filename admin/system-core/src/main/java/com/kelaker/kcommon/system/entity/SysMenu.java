package com.kelaker.kcommon.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.Getter;

/**
 * 系统菜单(SysMenu)表实体类
 *
 * @author Felix Huang
 * @since 2024-12-06 15:49:09
 */
@Data
@TableName("sys_menu")
public class SysMenu extends Model<SysMenu> {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单类型（S_M_FIRST_MENU,S_M_CHILD_MENU,S_M_BUTTON）
     */
    private Type type;

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

    @Getter
    public enum Type implements IEnum<String> {
        FIRST("S_M_T_FIRST","一级菜单"),
        CHILD("S_M_T_CHILD", "子菜单"),
        BUTTON("S_M_T_CHILD","按钮");

        Type(String value,String remark) {
            this.value = value;
            this.remark = remark;
        }

        private final String value;
        private final String remark;

        @Override
        public String toString() {
            return value;
        }
    }
}

