package com.kelaker.kcommon.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * 页面搜索功能(SysViewSearch)表实体类
 *
 * @author Felix Huang
 * @since 2025-05-12 15:45:05
 */
@Data
@TableName("sys_view_search")
public class SysViewSearch extends Model<SysViewSearch> {

    @TableId(type = IdType.AUTO)
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

