package com.kelaker.kcommon.system.dto;

import lombok.Data;

/**
 * 系统配置分页查询请求信息
 *
 * @author Felix Huang
 * @since 2021-01-29
 */
@Data
public class SysConfigSearchDto {

    /**
     * 配置key
     */
    private String configKey;

    /**
     * 配置名称
     */
    private String configKeyName;

    /**
     * 状态
     */
    private String status;

    /**
     * 分组
     */
    private String groupCode;
}