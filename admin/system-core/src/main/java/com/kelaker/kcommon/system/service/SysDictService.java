package com.kelaker.kcommon.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.system.constant.SystemConstant;
import com.kelaker.kcommon.system.dao.SysDictDao;
import com.kelaker.kcommon.system.dto.SysDictDto;
import com.kelaker.kcommon.system.entity.SysDict;
import com.kelaker.kcommon.system.manager.SysCacheManager;
import com.kelaker.kcommon.system.vo.SysDictVo;
import com.kelaker.ktools.cache.annotation.CacheExpire;
import com.kelaker.ktools.cache.manager.CacheManager;
import com.kelaker.ktools.common.exception.BusinessException;
import com.kelaker.ktools.common.populator.ConvertUtils;
import com.kelaker.ktools.common.utils.SecurityUtil;
import com.kelaker.ktools.common.utils.ValidateUtil;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统字典表(SysDict)表服务实现类
 *
 * @author felix huang
 * @since 2020-03-27 11:42:57
 */
@Service
@Slf4j
public class SysDictService extends BaseService<SysDictDao, SysDict> {

    @Resource
    private SysCacheManager sysCacheManager;

    @Resource
    private CacheManager cacheManager;

    /**
     * 分页查询字典
     *
     * @param request 分页查询请求
     * @return 分页数据
     */
    public IPage<SysDictVo> selectListByDictPage(RequestPage<SysDictDto> request) {
        IPage<SysDict> page = super.createPage(request);

        SysDict sysDict = super.objectConvert(request.getData(), SysDict.class);
        IPage<SysDict> resultPage = super.page(page, super.createWrapper(sysDict));

        return mapPageToTarget(resultPage, SysDictVo.class);
    }

    /**
     * 根据dictCode查询， 如果不存在，插入
     *
     * @param dictCode 字典代码
     * @return
     */
    public SysDictVo getSysDictByCodeAndInst(String dictCode) {
        String dictCodeMd5 = SecurityUtil.md5Encrypt(dictCode);
        SysDict sysDict = this.findByDictCode(dictCode).orElseGet(() -> {
            SysDictDto dto = new SysDictDto();
            dto.setDictCode(dictCodeMd5);
            dto.setDictValue(dictCode);
            SysDict saveObj = super.objectConvert(dto, SysDict.class);
            super.save(saveObj);
            this.cacheManager.removeValue(SystemConstant.SYS_DICT_LIST_CACHE_KEY);
            return saveObj;
        });
        return super.objectConvert(sysDict, SysDictVo.class);
    }

    /**
     * 根据dictCode查询
     *
     * @param dictCode
     * @return
     */
    public SysDictVo getSysDictByCode(String dictCode) {
        SysDict sysDict = this.findByDictCode(dictCode).orElse(null);
        return super.objectConvert(sysDict, SysDictVo.class);
    }


    /**
     * 添加系统字典
     *
     * @param sysDictDto 字典数据
     * @return
     */
    @CacheExpire(key = SystemConstant.SYS_DICT_LIST_CACHE_KEY)
    public void addSysDict(SysDictDto sysDictDto) {
        findByDictCode(sysDictDto.getDictCode()).ifPresent((sysDict) -> {
            throw new BusinessException("字典代码已存在");
        });
        SysDict sysDict = super.objectConvert(sysDictDto, SysDict.class);
        super.save(sysDict);
    }

    /**
     * 根据dictCode编辑字典
     *
     * @param sysDictDto 变更对象
     * @return
     */
    @CacheExpire(key = SystemConstant.SYS_DICT_LIST_CACHE_KEY)
    public void updateSysDict(SysDictDto sysDictDto) {
        sysDictDto.setDictCode(sysDictDto.getDictCode());
        SysDict sysDict = super.getById(sysDictDto.getDictCode());
        ConvertUtils.copyProperties(sysDictDto, sysDict);
        this.updateById(sysDict);
    }

    /**
     * 根据字典代码查询系统字典信息
     *
     * @param dictCode 字典代码
     * @return 字典
     */
    private Optional<SysDict> findByDictCode(String dictCode) {
        return sysCacheManager.getDictAllFromCache().stream()
                .filter(sysDict -> sysDict.getDictCode().equals(dictCode))
                .findFirst();
    }


    /**
     * 根据分组代码查询系统字典信息
     *
     * @param dictGroupCode
     * @return
     */
    public List<SysDict> findByDictGroupCode(String dictGroupCode) {
        if (ValidateUtil.isBlank(dictGroupCode)) {
            return Collections.emptyList();
        }
        return sysCacheManager.getDictAllFromCache().stream().filter(sysDict ->
                dictGroupCode.equals(sysDict.getDictGroupCode())
        ).collect(Collectors.toList());
    }

    /**
     * 根据字典CODE代码查询系统字典信息
     *
     * @return
     */
    public Map<String, SysDict> findSysDict() {
        return this.sysCacheManager.getDictAllFromCache().stream()
                .collect(Collectors.toMap(SysDict::getDictCode, dict -> dict));
    }

    /**
     * 查询所有字典信息列表
     * @return
     */
    public List<SysDictVo> findAllSysDict() {
        return super.mapListToTarget(this.sysCacheManager.getDictAllFromCache(), SysDictVo.class);
    }

    /**
     * 获取所有字典分组代码
     * @return
     */
    public Set<String> getAllGroupCode() {
        List<SysDict> sysDictList = this.sysCacheManager.getDictAllFromCache();
        return sysDictList.stream().map(SysDict::getDictGroupCode).collect(Collectors.toSet());
    }

    /**
     * 根据字典分组代码查询列表-非树状结构
     * @param groupCode
     * @return
     */
    public List<SysDictVo> findVoListByDictGroupCode(String groupCode) {
        return mapListToTarget(this.findByDictGroupCode(groupCode), SysDictVo.class);
    }

    /**
     * 查询字典-树状结构
     *
     * @param groupCode
     * @return
     */
    public List<SysDictVo> listDict(String groupCode){
        List<SysDict> sysDicts = super.lambdaQuery().eq(SysDict::getDictGroupCode, groupCode).list();
        List<SysDictVo> sysDictVos = super.mapListToTarget(sysDicts, SysDictVo.class);

        List<SysDictVo> resultList = new ArrayList<>();
        for (SysDictVo sysDictVo : getRootDicts(sysDictVos)) {
            buildChildTree(sysDictVo, sysDictVos);
            resultList.add(sysDictVo);
        }
        return resultList;
    }

    /**
     * 递归，建立子树形结构
     */
     private SysDictVo buildChildTree(SysDictVo sysDictVo, List<SysDictVo> sysDicts){
         List<SysDictVo> childMenus = new ArrayList<>();
         for (SysDictVo sysDictVo1 : sysDicts) {
             if (ValidateUtil.isNotBlank(sysDictVo1.getParentCode())) {
                 if (sysDictVo1.getParentCode().equals(sysDictVo.getDictCode())) {
                     childMenus.add(buildChildTree(sysDictVo1, sysDicts));
                 }
             }
         }
         sysDictVo.setChildren(childMenus);
         return sysDictVo;
    }

    /**
     * 获取根结点
     *
     * @param sysDictVos
     * @return
     */
    private List<SysDictVo> getRootDicts(List<SysDictVo> sysDictVos) {
        List<SysDictVo> rootDicts = new ArrayList<>();
        for (SysDictVo sysDictVo : sysDictVos) {
            if (ValidateUtil.isBlank(sysDictVo.getParentCode())) {
                rootDicts.add(sysDictVo);
            }
        }
        return rootDicts;
    }
}