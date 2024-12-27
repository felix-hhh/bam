package com.kelaker.kcommon.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.common.system.vo.SysConfigVo;
import com.kelaker.kcommon.system.constant.SystemConstant;
import com.kelaker.kcommon.system.dao.SysConfigDao;
import com.kelaker.kcommon.system.dto.SysConfigDto;
import com.kelaker.kcommon.system.dto.SysConfigSearchDto;
import com.kelaker.kcommon.system.entity.SysConfig;
import com.kelaker.kcommon.system.manager.SysCacheManager;
import com.kelaker.kcommon.system.vo.SysConfigFrontVo;
import com.kelaker.ktools.cache.annotation.CacheExpire;
import com.kelaker.ktools.common.exception.BusinessException;
import com.kelaker.ktools.common.populator.ConvertUtils;
import com.kelaker.ktools.common.utils.ValidateUtil;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import com.kelaker.ktools.web.configs.WebConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 系统配置表(SysConfig)表服务实现类
 *
 * @author felix huang
 * @since 2020-03-27 11:42:57
 */
@Slf4j
@Service
public class SysConfigService extends BaseService<SysConfigDao, SysConfig> {

    @Resource
    private SysCacheManager sysCacheManager;

    @Resource
    private WebConfigProperties configProperties;

    /**
     * 分页查询
     *
     * @param request
     */
    public IPage<SysConfigVo> pageSysConfig(RequestPage<SysConfigSearchDto> request) {
        SysConfigSearchDto dto = request.getData();
        if (ValidateUtil.isBlank(dto)) {
            dto = new SysConfigSearchDto();
        }
        SysConfig sysConfig = super.objectConvert(dto, SysConfig.class);
        sysConfig.setStatus(SysConfig.Status.toEnum(dto.getStatus()));

        IPage<SysConfig> page = super.page(super.createPage(request), super.createWrapper(sysConfig));
        return super.mapPageToTarget(page, this::convertToVo);
    }

    /**
     * 添加系统配置
     *
     * @param dto 配置实体
     */
    @CacheExpire(key = SystemConstant.SYS_CONFIG_LIST_CACHE_KEY)
    public void saveSysConfig(SysConfigDto dto) {
        String configKey = dto.getConfigKey();
        if (this.getById(configKey).isPresent()) {
            throw new BusinessException("配置key已存在");
        }
        SysConfig.Type type = this.validaDataType(dto.getType(), dto.getConfigValue());
        SysConfig sysConfig = super.objectConvert(dto, SysConfig.class);
        sysConfig.setType(type);
        sysConfig.setStatus(SysConfig.Status.NORMAL);
        super.save(sysConfig);
    }

    /**
     * 根据configKey查询配置信息
     *
     * @param configKey 配置的key
     * @return 配置实体
     */
    public SysConfigVo getSysConfigByKey(String configKey) {
        SysConfig config = this.findCacheByConfigKey(configKey).orElseThrow(() -> new BusinessException("指定Key的配置不存在"));
        return this.convertToVo(config);
    }

    /**
     * 按分组查询配置信息
     * @param groupCode
     * @return
     */
    public List<SysConfigVo> getByGroupCode(String groupCode) {
        Map<String, List<SysConfig>> map = this.findByGroupCode();
        if (ValidateUtil.isNotBlank(groupCode)) {
            List<SysConfig> sysConfigs = (List)map.get(groupCode);
            return this.mapListToTarget(sysConfigs, this::convertToVo);
        } else {
            return null;
        }
    }

    /**
     * 免登录根据configKey查询配置信息
     *
     * @param configKey 配置的key
     * @return 配置实体
     */
    public SysConfigVo getPublicConfig(String configKey) {
        SysConfigVo configVo = this.getSysConfigByKey(configKey);
        if (!configVo.isPublicKey()) {
            throw new BusinessException("指定Key的配置不存在");
        }
        return configVo;
    }

    /**
     * 前端根据configKey查询配置信息
     *
     * @param configKey 配置的key
     * @return 配置实体
     */
    public SysConfigFrontVo getFrontConfig(String configKey) {
        SysConfigVo configVo = this.getSysConfigByKey(configKey);
        if (!configVo.isFrontView()) {
            throw new BusinessException("指定Key的配置不存在");
        }

        return objectConvert(configVo, SysConfigFrontVo.class);
    }

    /**
     * 根据configKey编辑字典
     *
     * @param dto 实体
     */
    @CacheExpire(key = SystemConstant.SYS_CONFIG_LIST_CACHE_KEY)
    public void updateSysConfig(SysConfigDto dto) {
        String configKey = dto.getConfigKey();
        SysConfig config = this.getById(configKey).orElseThrow(() -> new BusinessException("指定Key的配置不存在"));
        this.validaDataType(dto.getType(), dto.getConfigValue());
        ConvertUtils.copyProperties(dto, config);

        super.updateById(config);
    }

    /**
     * 禁用配置
     *
     * @param configKey 配置的key
     */
    @CacheExpire(key = SystemConstant.SYS_CONFIG_LIST_CACHE_KEY)
    public void disableSysConfig(String configKey) {
        super.checkNullParameter(configKey);
        SysConfig sysConfig = super.getById(configKey);
        if (ValidateUtil.isBlank(sysConfig)) {
            throw new BusinessException("配置不存在");
        }
        sysConfig.setStatus(SysConfig.Status.DISABLE);
        super.updateById(sysConfig);
    }

    /**
     * 启用系统设置
     *
     * @param configKey 系统设置
     */
    @CacheExpire(key = SystemConstant.SYS_CONFIG_LIST_CACHE_KEY)
    public void enableSysConfig(String configKey) {
        super.checkNullParameter(configKey);
        SysConfig config = super.getById(configKey);
        if (ValidateUtil.isBlank(config)) {
            throw new BusinessException("配置不存在");
        }
        config.setStatus(SysConfig.Status.NORMAL);
        super.updateById(config);
    }

    /**
     * 根据配置key查询系统配置信息
     *
     * @param configKey 配置的key
     */
    private Optional<SysConfig> findCacheByConfigKey(String configKey) {
        return sysCacheManager.getConfigAllFromCache()
                .stream()
                .filter(sysConfig -> sysConfig.getConfigKey().equals(configKey))
                .findFirst();
    }

    /**
     * 分组查询所有实体类
     *
     * @param groupCode 分组代码
     * @return 实体类列表
     */
    protected List<SysConfig> listSysConfigByGroupCode(String groupCode) {
        super.checkNullParameter(groupCode);
        List<SysConfig> configAllFromCache = this.sysCacheManager.getConfigAllFromCache();
        return configAllFromCache
                .stream()
                .filter(sysConfig -> groupCode.equals(sysConfig.getGroupCode()))
                .collect(Collectors.toList());
    }


    /**
     * 从缓存中分组查询
     */
    public Map<String, List<SysConfig>> findByGroupCode() {
        List<SysConfig> configList = sysCacheManager.getConfigAllFromCache();
        Map<String, List<SysConfig>> map = configList.stream().collect(Collectors.groupingBy(SysConfig::getGroupCode));
        return map;
    }

    private Optional<SysConfig> getById(String configKey) {
        return super.lambdaQuery().eq(SysConfig::getConfigKey, configKey).oneOpt();
    }

    /**
     * 数据转换
     *
     * @param sysConfig
     */
    private SysConfigVo convertToVo(SysConfig sysConfig) {
        SysConfigVo sysConfigVo = super.objectConvert(sysConfig, SysConfigVo.class);
        sysConfigVo.setStatus(sysConfig.getStatus().getValue());
        sysConfigVo.setStatusStr(sysConfig.getStatus().getRemark());
        sysConfigVo.setTypeStr(sysConfig.getType().getRemark());
        return sysConfigVo;
    }

    /**
     * 按照配置的key从列表中取回配置
     *
     * @param configs 配置列表
     * @param key     key
     * @return 配置
     */
    private SysConfig getSysConfigByKey(List<SysConfig> configs, String key) {
        for (SysConfig config : configs) {
            if (key.equals(config.getConfigKey())) {
                configs.remove(config);
                return config;
            }
        }
        return null;
    }

    /**
     * 重载下面的方法
     *
     * @param type
     * @param value
     */
    private SysConfig.Type validaDataType(String type, String value) {
        SysConfig.Type sysConfigType = SysConfig.Type.toEnum(type);
        if (ValidateUtil.isBlank(type)) {
            throw new BusinessException("无效的类型");
        }

        this.validaDataType(sysConfigType, value);

        return sysConfigType;
    }

    /**
     * 根据类型校验填写值
     *
     * @param type
     * @param value
     */
    private void validaDataType(SysConfig.Type type, String value) {
        switch (type) {
            case NUMBER:
                if (!ValidateUtil.isNumber(value)) {
                    throw new BusinessException("填写值必须为数字");
                }
                break;
            case BOOLEAN:
                if (!("true".equals(value) || "false".equals(value))) {
                    throw new BusinessException("填写值必须为true | false");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 关闭站点
     */
    public void closeSite() {
        configProperties.setAllowFrontAccess(false);
    }

    /**
     * 开启站点
     */
    public void openSite() {
        configProperties.setAllowFrontAccess(true);
    }

    /**
     * 获取站点当前状态
     */
    public boolean getSiteStatus() {
        return configProperties.isAllowFrontAccess();
    }

    /**
     * 分组取回前端配置
     *
     * @param groupCode 分组代码
     */
    public List<SysConfigFrontVo> getFrontConfigByGroup(String groupCode) {
        return this.listSysConfigByGroupCode(groupCode)
                .stream()
                .filter(sysConfig -> sysConfig.getFrontView() && SysConfig.Status.NORMAL.equals(sysConfig.getStatus()))
                .map(sysConfig -> super.objectConvert(sysConfig, SysConfigFrontVo.class))
                .collect(Collectors.toList());
    }
}