package com.kelaker.kcommon.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
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
@TableName(value = "sys_view_search",autoResultMap = true)
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

    /**
     * 数据源
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Map<String,String>> dataSource;
}

