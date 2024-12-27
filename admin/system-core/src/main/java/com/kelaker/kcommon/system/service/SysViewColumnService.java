package com.kelaker.kcommon.system.service;

import com.kelaker.kcommon.system.entity.SysViewColumn;
import com.kelaker.kcommon.system.dao.SysViewColumnDao;
import com.kelaker.kcommon.system.vo.SysViewColumnVo;
import com.kelaker.kcommon.system.dto.SysViewColumnSearchDto;
import com.kelaker.kcommon.system.dto.SysViewColumnDto;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 页面显示列(SysViewColumn)表服务
 *
 * @author Felix Huang
 * @since 2024-12-16 17:47:24
 */
@Service
public class SysViewColumnService extends BaseService<SysViewColumnDao, SysViewColumn> {

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 分页结果
     */
    public IPage<SysViewColumnVo> queryPage(RequestPage<SysViewColumnSearchDto> searchDto) {
        SysViewColumnSearchDto searchDtoData = searchDto.getData();
        SysViewColumn empty = super.objectConvert(searchDtoData, SysViewColumn.class);
        IPage<SysViewColumn> page = super.page(super.createPage(searchDto), super.createWrapper(empty));
        return mapPageToTarget(page, this::convertToVo);
    }

    /**
     * 取回视图列配置
     *
     * @param viewId 视图ID
     */
    public List<SysViewColumnVo> getViewColumnByViewId(Long viewId) {
        List<SysViewColumn> sysViewColumns = super.lambdaQuery().eq(SysViewColumn::getViewId, viewId).list();
        return mapListToTarget(sysViewColumns, this::convertToVo);
    }

    /**
     * 添加对象
     *
     * @param dto 对象
     */
    public void addSysViewColumn(SysViewColumnDto dto) {
        SysViewColumn sysViewColumn = super.objectConvert(dto, SysViewColumn.class);
        super.save(sysViewColumn);
    }

    /**
     * 对象转换
     */
    private SysViewColumnVo convertToVo(SysViewColumn sysViewColumn) {
        return super.objectConvert(sysViewColumn, SysViewColumnVo.class);
    }
}

