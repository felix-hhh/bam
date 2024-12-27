package com.kelaker.kcommon.system.vo;

import lombok.Data;

import java.util.List;

/**
 * 数据字典
 *
 * @author Felix Huang
 * @since 2020-01-06
 */
@Data
public class SysDictVo {

    /**
     * 字典代码
     */
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

    /**
     * 子集
     */
    private List<SysDictVo> children;
}