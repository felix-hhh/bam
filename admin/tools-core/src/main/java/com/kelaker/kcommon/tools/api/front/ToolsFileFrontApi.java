package com.kelaker.kcommon.tools.api.front;


import com.kelaker.kcommon.tools.vo.ToolsFileStsVo;
import com.kelaker.ktools.web.base.api.BaseApi;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.kelaker.kcommon.tools.service.ToolsFileService;
import com.kelaker.kcommon.tools.vo.ToolsFileVo;

/**
 * 文件上传记录(ToolsFileUpload)表控制层
 *
 * @author Felix Huang
 * @since 2025-04-30 03:44:50
 */
@RestController
@RequestMapping("/tools/front/file")
public class ToolsFileFrontApi extends BaseApi {

    /**
     * 服务对象
     */
    @Resource
    private ToolsFileService toolsFileService;

    @GetMapping("/get/{id}")
    public ToolsFileVo getToolsFileUpload(@Validated @NotNull(message = "ID不能为空") @PathVariable("id") Long id) {

        return this.toolsFileService.getToolsFileUpload(id);
    }

    @PostMapping("/upload")
    public void fileUpload(){
        this.toolsFileService.getSTSCredentials();
    }

    @GetMapping("/sts")
    public ToolsFileStsVo getToolsFileSts(){
        return this.toolsFileService.getSTSCredentials();
    }
}

