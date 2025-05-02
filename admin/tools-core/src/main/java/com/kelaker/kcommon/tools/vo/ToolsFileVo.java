package com.kelaker.kcommon.tools.vo;

import java.util.Date;

import lombok.Data;

/**
 * 文件上传记录(ToolsFileUpload)表实体类
 *
 * @author Felix Huang
 * @since 2025-04-30 03:44:50
 */
@Data
public class ToolsFileVo {

    /**
     * id
     */
    private Integer id;

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
    private Integer fileSize;

    /**
     * 创建时间
     */
    private Date createDatetime;

    /**
     * 平台
     */
    private String platform;

    /**
     * 状态
     */
    private String status;
}

