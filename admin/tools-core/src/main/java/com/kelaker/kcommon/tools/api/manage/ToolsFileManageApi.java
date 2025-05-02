package com.kelaker.kcommon.tools.api.manage;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.tools.dto.ToolsFileSearchDto;
import com.kelaker.kcommon.tools.service.ToolsFileService;
import com.kelaker.kcommon.tools.vo.ToolsFileVo;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.api.BaseApi;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传记录(ToolsFileUpload)表控制层
 *
 * @author Felix Huang
 * @since 2025-04-30 03:44:50
 */
@RestController
@RequestMapping("/tools/manage/file")
public class ToolsFileManageApi extends BaseApi {

    /**
     * 服务对象
     */
    @Resource
    private ToolsFileService toolsFileService;

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 查询结果
     */
    @PostMapping("/page")
    public IPage<ToolsFileVo> pageToolsFileUpload(@RequestBody RequestPage<ToolsFileSearchDto> searchDto) {
        return this.toolsFileService.queryPage(searchDto);
    }

    @PostMapping("/upload")
    public void fileUpload(MultipartFile file) {
        this.toolsFileService.uploadFile(file);
    }
}

