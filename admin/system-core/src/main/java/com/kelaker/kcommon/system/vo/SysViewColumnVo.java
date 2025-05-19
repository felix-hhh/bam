package com.kelaker.kcommon.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 页面显示列(SysViewColumn)表实体类
 *
 * @author Felix Huang
 * @since 2024-12-17 11:42:01
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SysViewColumnVo {

    /**
     * id
     */
    private Long id;

    /**
     * 页面ID
     */
    private Long viewId;

    /**
     * 列表名
     */
    private String columnName;

    /**
     * 显示名字
     */
    private String columnLabel;

    /**
     * 字段排序
     */
    private Integer columnOrder;

    /**
     * 字段排序
     */
    private Boolean columnSortable;

    /**
     * 是否必填
     */
    private Boolean ruleRequired;

    /**
     * 正则表达式
     */
    private String ruleRegular;

    /**
     * 格式化
     */
    private String showFormat;

    /**
     * 列宽
     */
    private Integer showWidth;

    /**
     * 固定
     */
    private String showFixed;

    /**
     * 是否在表格中显示
     */
    private Boolean showColumn;

    /**
     * 支持添加
     */
    private Boolean addHandle;

    /**
     * 支持编辑
     */
    private Boolean editHandle;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 数据来源，当data_type为droplist的时候，从数据字典取值
     */
    private String dataSource;
}

