package com.kelaker.kcommon.system.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 页面搜索功能(SysViewSearch)表实体类
 *
 * @author Felix Huang
 * @since 2025-05-12 15:45:05
 */
@Data
public class SysViewSearchVo {

    private Long id;

    /**
     * 页面ID
     */
    private Long viewId;

    /**
     * 检索label
     */
    private String searchLabel;

    /**
     * 检索key
     */
    private String searchKey;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 数据源
     */
    private List<Map<String,String>> dataSource;

}

