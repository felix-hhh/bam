package com.kelaker.kcommon.tools.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
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
import com.kelaker.kcommon.tools.entity.ToolsFile;
import com.kelaker.kcommon.tools.vo.ToolsFileStsVo;
import com.kelaker.kcommon.tools.vo.ToolsFileVo;
import com.kelaker.ktools.common.exception.BusinessException;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * 文件上传记录(ToolsFileUpload)表服务
 *
 * @author Felix Huang
 * @since 2025-04-30 03:44:50
 */
@Slf4j
@Service
public class ToolsFileService extends BaseService<ToolsFileUploadDao, ToolsFile> {

    public static final String ROLE_SESSION_NAME = "sts_temp";

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
        ToolsFile empty = super.objectConvert(searchDtoData, ToolsFile.class);
        IPage<ToolsFile> page = super.page(super.createPage(searchDto), super.createWrapper(empty));
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
            ToolsFileStsVo toolsFileStsVo = super.objectConvert(credentials, ToolsFileStsVo.class);
            toolsFileStsVo.setRegion(this.toolsConfigProperties.getAliyunOssRegion());
            toolsFileStsVo.setBucketName(this.toolsConfigProperties.getAliyunOssBucketName());

            return toolsFileStsVo;
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
     * 文件路径签名
     *
     * @param filePath 文件路径
     */
    public String getSignatureUrl(String filePath) {
        OSS ossClient = this.getOssClient();
        Date expiration = new Date(new Date().getTime() + 3600000);
        URL signedUrl = ossClient.generatePresignedUrl(
                this.toolsConfigProperties.getAliyunOssBucketName(),
                filePath,
                expiration);
        return signedUrl.toString();
    }

    /**
     * 取回对象
     *
     * @param id 对象ID
     */
    public ToolsFileVo getToolsFile(Long id) {
        ToolsFile toolsFile = super.getById(id);
        return this.convertToVo(toolsFile);
    }

    /**
     * 添加对象
     *
     * @param dto 对象
     */
    @Transactional(rollbackOn = Exception.class)
    public ToolsFileVo addToolsFile(ToolsFileDto dto) {
        moveFile(dto);

        ToolsFile toolsFile = super.objectConvert(dto, ToolsFile.class);
        toolsFile.setPlatform(ToolsFile.Platform.ALIYUN);
        toolsFile.setStatus(ToolsFile.Status.NORMAL);
        super.save(toolsFile);
        return this.convertToVo(toolsFile);
    }

    private void moveFile(ToolsFileDto dto) {
        String filename = dto.getFilePath();
        String aliyunOssBucketName = toolsConfigProperties.getAliyunOssBucketName();
        String suffix = filename.substring(filename.lastIndexOf("."));
        try {
            OSS ossClient = this.getOssClient();
            //文件信息
            OSSObject clientObject = ossClient.getObject(aliyunOssBucketName, filename);
            long fileSize = clientObject.getObjectMetadata().getContentLength();
            String contentType = clientObject.getObjectMetadata().getContentType();
            dto.setFileSize(fileSize);
            dto.setFileType(contentType);

            String fileType = contentType.substring(0, contentType.indexOf("/"));
            String newPath = fileType + "/" + UUID.randomUUID() + suffix;
            dto.setFilePath(newPath);

            //移动
            ossClient.copyObject(aliyunOssBucketName, filename, aliyunOssBucketName, newPath);
            ossClient.deleteObject(aliyunOssBucketName, filename);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException("文件上传失败");
        } finally {
            oss.shutdown();
        }

    }

    /**
     * 对象转换
     */
    private ToolsFileVo convertToVo(ToolsFile toolsFile) {
        return super.objectConvert(toolsFile, ToolsFileVo.class);
    }

    private OSS getOssClient() {
        return new OSSClientBuilder().build(
                toolsConfigProperties.getAliyunOssEndpoint(),
                toolsConfigProperties.getAliyunOssAccessKeyId(),
                toolsConfigProperties.getAliyunOssAccessKeySecret()
        );
    }
}

