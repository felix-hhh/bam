package com.kelaker.kcommon.system.dto;

import lombok.Data;

/**
 * 页面搜索功能(SysViewSearch)查询实体类
 *
 * @author Felix Huang
 * @since 2025-05-12 15:45:05
 */
@Data
public class SysViewSearchSearchDto {

    private Integer id;

    /**
     * 页面ID
     */
    private Integer viewId;

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
}

