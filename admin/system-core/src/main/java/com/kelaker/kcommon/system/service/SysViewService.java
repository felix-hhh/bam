package com.kelaker.kcommon.system.service;

import com.kelaker.kcommon.system.entity.SysView;
import com.kelaker.kcommon.system.dao.SysViewDao;
import com.kelaker.kcommon.system.vo.SysViewVo;
import com.kelaker.kcommon.system.dto.SysViewSearchDto;
import com.kelaker.kcommon.system.dto.SysViewDto;
import com.kelaker.ktools.common.exception.BusinessException;
import com.kelaker.ktools.common.utils.ValidateUtil;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 系统页面(SysView)表服务
 *
 * @author Felix Huang
 * @since 2024-12-16 17:45:55
 */
@Service
public class SysViewService extends BaseService<SysViewDao, SysView> {

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 分页结果
     */
    public IPage<SysViewVo> queryPage(RequestPage<SysViewSearchDto> searchDto) {
        SysViewSearchDto searchDtoData = searchDto.getData();
        SysView empty = super.objectConvert(searchDtoData, SysView.class);
        IPage<SysView> page = super.page(super.createPage(searchDto), super.createWrapper(empty));
        return mapPageToTarget(page, this::convertToVo);
    }

    /**
     * 添加对象
     *
     * @param dto 对象
     */
    public void addSysView(SysViewDto dto) {
        SysView sysView = super.lambdaQuery().eq(SysView::getPath, dto.getPath()).one();
        if(ValidateUtil.isNotBlank(sysView)) {
            throw new BusinessException("页面配置中路径已存在");
        }
        sysView = super.objectConvert(dto, SysView.class);
        super.save(sysView);
    }

    /**
     * 通过页面路径查询页面配置
     *
     * @param path 页面路径
     */
    public SysViewVo getSystemViewByPath(String path) {
        SysView sysView = super.lambdaQuery()
                .eq(SysView::getPath, path)
                .one();
        return convertToVo(sysView);
    }

    /**
     * 根据页面ID取回页面配置
     *
     * @param id 页面ID
     */
    public SysViewVo getSystemViewById(Long id) {
        SysView sysView = super.getById(id);
        return convertToVo(sysView);
    }

    /**
     * 对象转换
     */
    private SysViewVo convertToVo(SysView sysView) {
        return super.objectConvert(sysView, SysViewVo.class);
    }


}

