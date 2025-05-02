package com.kelaker.kcommon.tools.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.tools.configs.ToolsConfigProperties;
import com.kelaker.kcommon.tools.dao.ToolsFileUploadDao;
import com.kelaker.kcommon.tools.dto.ToolsFileDto;
import com.kelaker.kcommon.tools.dto.ToolsFileSearchDto;
import com.kelaker.kcommon.tools.entity.ToolsFileUpload;
import com.kelaker.kcommon.tools.vo.ToolsFileStsVo;
import com.kelaker.kcommon.tools.vo.ToolsFileVo;
import com.kelaker.ktools.common.exception.BusinessException;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传记录(ToolsFileUpload)表服务
 *
 * @author Felix Huang
 * @since 2025-04-30 03:44:50
 */
@Slf4j
@Service
public class ToolsFileService extends BaseService<ToolsFileUploadDao, ToolsFileUpload> {

    public static final String ROLE_SESSION_NAME="sts_temp";



    @Resource
    private ToolsConfigProperties toolsConfigProperties;

    @Resource
    private OSS oss;

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 分页结果
     */
    public IPage<ToolsFileVo> queryPage(RequestPage<ToolsFileSearchDto> searchDto) {
        ToolsFileSearchDto searchDtoData = searchDto.getData();
        ToolsFileUpload empty = super.objectConvert(searchDtoData, ToolsFileUpload.class);
        IPage<ToolsFileUpload> page = super.page(super.createPage(searchDto), super.createWrapper(empty));
        return mapPageToTarget(page, this::convertToVo);
    }

    /**
     * 创建sts凭证
     */
    public ToolsFileStsVo getSTSCredentials() {
        DefaultProfile profile = DefaultProfile.getProfile(
                this.toolsConfigProperties.getAliyunOssRegionId(),
                this.toolsConfigProperties.getAliyunOssAccessKeyId(),
                this.toolsConfigProperties.getAliyunOssAccessKeySecret()
        );
        IAcsClient client = new DefaultAcsClient(profile);

        AssumeRoleRequest request = new AssumeRoleRequest();
        request.setRoleSessionName(ROLE_SESSION_NAME);
        request.setRoleArn(this.toolsConfigProperties.getAliyunOssArn());
        request.setDurationSeconds(3600L);
        try {
            AssumeRoleResponse response = client.getAcsResponse(request);
            AssumeRoleResponse.Credentials credentials = response.getCredentials();
            return super.objectConvert(credentials, ToolsFileStsVo.class);
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * 上传文件到OSS
     *
     * @param file 文件
     * @return 文件URL
     */
    public String uploadFile(MultipartFile file) {
        //生成随机文件名
        String fileName = file.getOriginalFilename();
        String rename = UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));
        try {
            oss.putObject(toolsConfigProperties.getAliyunOssBucketName(), rename, new ByteArrayInputStream(file.getBytes()));
            return fileName;
        } catch (IOException e) {
            log.error("上传文件失败", e);
            throw new BusinessException("上传文件失败", e);
        } finally {
            oss.shutdown();
        }
    }

    /**
     * 取回对象
     *
     * @param id 对象ID
     */
    public ToolsFileVo getToolsFileUpload(Long id) {
        ToolsFileUpload toolsFileUpload = super.getById(id);
        return this.convertToVo(toolsFileUpload);
    }

    /**
     * 添加对象
     *
     * @param dto 对象
     */
    public void addToolsFileUpload(ToolsFileDto dto) {
        ToolsFileUpload toolsFileUpload = super.objectConvert(dto, ToolsFileUpload.class);
        super.save(toolsFileUpload);
    }

    /**
     * 对象转换
     */
    private ToolsFileVo convertToVo(ToolsFileUpload toolsFileUpload) {
        return super.objectConvert(toolsFileUpload, ToolsFileVo.class);
    }

    private OSS getOssClient() {
        return new OSSClientBuilder().build(
                toolsConfigProperties.getAliyunOssEndpoint(),
                toolsConfigProperties.getAliyunOssAccessKeyId(),
                toolsConfigProperties.getAliyunOssAccessKeySecret()
        );
    }
}

