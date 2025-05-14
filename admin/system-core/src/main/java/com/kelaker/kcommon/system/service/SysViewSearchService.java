package com.kelaker.kcommon.system.service;

import com.kelaker.kcommon.system.entity.SysViewSearch;
import com.kelaker.kcommon.system.dao.SysViewSearchDao;
import com.kelaker.kcommon.system.vo.SysViewSearchVo;
import com.kelaker.kcommon.system.dto.SysViewSearchSearchDto;
import com.kelaker.kcommon.system.dto.SysViewSearchDto;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 页面搜索功能(SysViewSearch)表服务
 *
 * @author Felix Huang
 * @since 2025-05-12 15:45:05
 */
@Service
public class SysViewSearchService extends BaseService<SysViewSearchDao, SysViewSearch> {

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 分页结果
     */
    public IPage<SysViewSearchVo> queryPage(RequestPage<SysViewSearchSearchDto> searchDto) {
        SysViewSearchSearchDto searchDtoData = searchDto.getData();
        SysViewSearch empty = super.objectConvert(searchDtoData, SysViewSearch.class);
        IPage<SysViewSearch> page = super.page(super.createPage(searchDto), super.createWrapper(empty));
        return mapPageToTarget(page, this::convertToVo);
    }

    /**
     * 取回对象
     *
     * @param id 对象ID
     */
    public SysViewSearchVo getSysViewSearch(Long id) {
        SysViewSearch sysViewSearch = super.getById(id);
        return this.convertToVo(sysViewSearch);
    }

    /**
     * 添加对象
     *
     * @param dto 对象
     */
    public void addSysViewSearch(SysViewSearchDto dto) {
        SysViewSearch sysViewSearch = super.objectConvert(dto, SysViewSearch.class);
        super.save(sysViewSearch);
    }

    /**
     * 对象转换
     */
    private SysViewSearchVo convertToVo(SysViewSearch sysViewSearch) {
        SysViewSearchVo sysViewSearchVo = super.objectConvert(sysViewSearch, SysViewSearchVo.class);
        sysViewSearchVo.setDataSource(sysViewSearch.getDataSource());
        return sysViewSearchVo;
    }

    public List<SysViewSearchVo> getSysViewSearchByView(Long viewId) {
        List<SysViewSearch> list = super.lambdaQuery().eq(SysViewSearch::getViewId, viewId).list();
        return super.mapListToTarget(list, this::convertToVo);
    }
}

