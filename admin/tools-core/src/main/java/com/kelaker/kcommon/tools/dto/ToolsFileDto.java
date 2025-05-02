package com.kelaker.kcommon.tools.dto;

import lombok.Data;

/**
 * 文件上传记录(ToolsFileUpload)查询实体类
 *
 * @author Felix Huang
 * @since 2025-04-30 03:44:50
 */
@Data
public class ToolsFileDto {

    /**
     * id
     */
    private Long id;

    /**
     * 真实文件名
     */
    private String realFileName;

    /**
     * 原文件名
     */
    private String origFileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件大小
     */
    private Long fileSize;

}

