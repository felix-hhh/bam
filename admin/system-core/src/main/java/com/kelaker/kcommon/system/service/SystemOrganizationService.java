package com.kelaker.kcommon.system.service;

import com.kelaker.kcommon.system.entity.SystemOrganization;
import com.kelaker.kcommon.system.dao.SystemOrganizationDao;
import com.kelaker.kcommon.system.vo.SystemOrganizationVo;
import com.kelaker.kcommon.system.dto.SystemOrganizationSearchDto;
import com.kelaker.kcommon.system.dto.SystemOrganizationDto;
import com.kelaker.ktools.common.exception.BusinessException;
import com.kelaker.ktools.common.utils.ValidateUtil;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 组织架构(SystemOrganization)表服务
 *
 * @author Felix Huang
 * @since 2024-09-02 12:41:01
 */
@Service
public class SystemOrganizationService extends BaseService<SystemOrganizationDao, SystemOrganization> {

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 分页结果
     */
    public IPage<SystemOrganizationVo> queryPage(RequestPage<SystemOrganizationSearchDto> searchDto) {
        SystemOrganizationSearchDto searchDtoData = searchDto.getData();
        SystemOrganization empty = super.objectConvert(searchDtoData, SystemOrganization.class);
        IPage<SystemOrganization> page = super.page(super.createPage(searchDto), super.createWrapper(empty));
        return mapPageToTarget(page, this::convertToVo);
    }

    /**
     * 添加对象
     *
     * @param dto 对象
     */
    public void addSystemOrganization(SystemOrganizationDto dto) {
        SystemOrganization systemOrganization = super.objectConvert(dto, SystemOrganization.class);
        systemOrganization.setStatus(SystemOrganization.Status.NORMAL);
        systemOrganization.setType(SystemOrganization.Type.toEnum(dto.getType()));
        Long parentId = dto.getParentId();
        if (ValidateUtil.isNotBlank(parentId)) {
            SystemOrganization organization = this.getById(parentId);
            if (ValidateUtil.isBlank(organization)) {
                throw new BusinessException("上级部门数据异常");
            }
        }
        super.save(systemOrganization);
    }

    /**
     * 对象转换
     */
    private SystemOrganizationVo convertToVo(SystemOrganization systemOrganization) {
        return super.objectConvert(systemOrganization, SystemOrganizationVo.class);
    }
}

