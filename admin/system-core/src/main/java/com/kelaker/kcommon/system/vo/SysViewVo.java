package com.kelaker.kcommon.system.vo;

import lombok.Data;

/**
 * 系统页面(SysView)表实体类
 *
 * @author Felix Huang
 * @since 2024-12-16 17:45:55
 */
@Data
public class SysViewVo {

    /**
     * 序号
     */
    private Integer id;

    /**
     * 页面名字
     */
    private String name;

    /**
     * 页面小注
     */
    private String tips;

    /**
     * 是否分页请求
     */
    private Integer pageQuery;

    /**
     * 初始化请求数据
     */
    private Integer initData;

    /**
     * 初始化数据请求地址
     */
    private String initDataUrl;

    /**
     * 添加功能
     */
    private Boolean optAdd;

    /**
     * 添加地址
     */
    private String optAddUrl;

    /**
     * 添加按钮显示内容
     */
    private String optAddLabel;

    /**
     * 按钮显示方式（O_S_R_OPT 操作区, O_S_R_LINE 行）
     */
    private String optAddShowRegion;

    /**
     * 编辑功能
     */
    private Boolean optEdit;

    /**
     * 编辑url
     */
    private String optEditUrl;

    /**
     * 编辑按钮显示内容
     */
    private String optEditLabel;

    /**
     * 按钮显示方式（O_S_R_OPT 操作区, O_S_R_LINE 行）
     */
    private String optEditShowRegion;

    /**
     * 删除功能
     */
    private Boolean optDelete;

    /**
     * 删除地址
     */
    private String optDeleteUrl;

    /**
     * 删除按钮显示内容
     */
    private String optDeleteLabel;

    /**
     * 按钮显示方式（O_S_R_OPT 操作区, O_S_R_LINE 行）
     */
    private String optDeleteShowRegion;

    /**
     * 备注
     */
    private String remark;
}

