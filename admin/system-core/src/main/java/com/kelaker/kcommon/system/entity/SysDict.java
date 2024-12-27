package com.kelaker.kcommon.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * 系统字典表(SysDict)表实体类
 *
 * @author felix huang
 * @since 2020-03-27 11:42:57
 */
@Data
@TableName(value = "sys_dict")
public class SysDict extends Model<SysDict> {

    /**
     * 字典代码
     */
    @TableId(type = IdType.INPUT)
    private String dictCode;

    /**
     * 默认语言解释
     */
    private String dictValue;

    /**
     * 中文
     */
    private String dictValueZhCn;

    /**
     * 英文
     */
    private String dictValueEn;

    /**
     * 马来
     */
    private String dictValueMs;

    /**
     * 泰国
     */
    private String dictValueTh;

    /**
     * 韩国
     */
    private String dictValueKo;

    /**
     * 印度尼西亚
     */
    private String dictValueId;

    /**
     * 日本
     */
    private String dictValueJp;

    /**
     * 分组
     */
    private String dictGroupCode;

    /**
     * 父级
     */
    private String parentCode;
}