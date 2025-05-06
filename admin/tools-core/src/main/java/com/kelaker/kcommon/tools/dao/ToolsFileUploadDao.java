package com.kelaker.kcommon.tools.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kelaker.kcommon.tools.entity.ToolsFile;
import org.springframework.stereotype.Repository;

/**
 * 文件上传记录(ToolsFileUpload)表数据库访问层
 *
 * @author Felix Huang
 * @since 2025-04-30 03:44:50
 */
@Repository
public interface ToolsFileUploadDao extends BaseMapper<ToolsFile> {

}

