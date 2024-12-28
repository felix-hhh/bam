package com.kelaker.kcommon.system.dto;

import lombok.Data;

/**
 * 系统页面(SysView)查询实体类
 *
 * @author Felix Huang
 * @since 2024-12-16 17:45:56
 */
@Data
public class SysViewSearchDto {

    /**
     * 页面名字
     */
    private String name;

    private String path;
}

