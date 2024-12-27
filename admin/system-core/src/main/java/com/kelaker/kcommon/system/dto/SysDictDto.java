package com.kelaker.kcommon.system.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;


/**
 * 系统字典Request
 * @author Felix Huang
 * @since 2020-01-06
 */
@Data
public class SysDictDto {

    /**
     * 字典代码
     */
    @NotBlank(message = "字典代码不能为空")
    private String dictCode;

    /**
     * 默认语言解释
     */
    @NotBlank(message = "默认语言不能为空")
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