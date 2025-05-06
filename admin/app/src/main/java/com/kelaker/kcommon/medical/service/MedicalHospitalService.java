package com.kelaker.kcommon.medical.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.medical.dao.MedicalHospitalDao;
import com.kelaker.kcommon.medical.dto.MedicalHospitalDto;
import com.kelaker.kcommon.medical.dto.MedicalHospitalSearchDto;
import com.kelaker.kcommon.medical.entity.MedicalHospital;
import com.kelaker.kcommon.medical.vo.MedicalHospitalVo;
import com.kelaker.kcommon.tools.service.ToolsFileService;
import com.kelaker.ktools.cache.annotation.CacheIt;
import com.kelaker.ktools.common.exception.BusinessException;
import com.kelaker.ktools.common.utils.ValidateUtil;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 医院信息(MedicalHospital)表服务
 *
 * @author Felix Huang
 * @since 2025-04-09 10:39:07
 */
@Service
public class MedicalHospitalService extends BaseService<MedicalHospitalDao, MedicalHospital> {
    @Resource
    private ToolsFileService toolsFileService;

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 分页结果
     */
    public IPage<MedicalHospitalVo> queryPage(RequestPage<MedicalHospitalSearchDto> searchDto) {
        MedicalHospitalSearchDto searchDtoData = searchDto.getData();
        MedicalHospital empty = super.objectConvert(searchDtoData, MedicalHospital.class);
        IPage<MedicalHospital> page = super.page(super.createPage(searchDto), super.createWrapper(empty));
        return mapPageToTarget(page, this::convertToVo);
    }

    /**
     * 获取所有启用的医院列表
     *
     * @return 医院列表
     */
    public List<MedicalHospitalVo> listHospitals() {
        LambdaQueryWrapper<MedicalHospital> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MedicalHospital::getStatus, MedicalHospital.Status.ENABLE);
        queryWrapper.orderByDesc(MedicalHospital::getCreateDatetime);

        List<MedicalHospital> hospitals = super.list(queryWrapper);
        return super.mapListToTarget(hospitals, this::convertToVo);
    }

    /**
     * 取回对象
     *
     * @param id 对象ID
     */
    @CacheIt(key = "MEDICAL_HOSPITAL", paramKey = true)
    public MedicalHospitalVo getMedicalHospital(Long id) {
        MedicalHospital medicalHospital = super.getById(id);
        if (medicalHospital == null) {
            throw new BusinessException("医院信息不存在");
        }
        return this.convertToVo(medicalHospital);
    }

    /**
     * 添加对象
     *
     * @param dto 对象
     */
    public void addMedicalHospital(MedicalHospitalDto dto) {
        MedicalHospital medicalHospital = super.objectConvert(dto, MedicalHospital.class);
        medicalHospital.setStatus(MedicalHospital.Status.ENABLE);
        super.save(medicalHospital);
    }

    /**
     * 更新对象
     *
     * @param dto 对象
     */
    public void updateMedicalHospital(MedicalHospitalDto dto) {
        if (dto.getId() == null) {
            throw new BusinessException("医院ID不能为空");
        }
        MedicalHospital existingHospital = super.getById(dto.getId());
        if (existingHospital == null) {
            throw new BusinessException("医院信息不存在");
        }
        MedicalHospital medicalHospital = super.objectConvert(dto, MedicalHospital.class);
        super.updateById(medicalHospital);
    }

    /**
     * 删除对象
     *
     * @param id 对象ID
     */
    public void deleteMedicalHospital(Long id) {
        MedicalHospital existingHospital = super.getById(id);
        if (existingHospital == null) {
            throw new BusinessException("医院信息不存在");
        }
        super.removeById(id);
    }

    /**
     * 对象转换
     */
    private MedicalHospitalVo convertToVo(MedicalHospital medicalHospital) {
        MedicalHospitalVo vo = super.objectConvert(medicalHospital, MedicalHospitalVo.class);
        vo.setStatusStr(medicalHospital.getStatus().getRemark());
        if (ValidateUtil.isNotBlank(medicalHospital.getLogo())) {
           vo.setLogo(this.toolsFileService.getSignatureUrl(medicalHospital.getLogo()));
        }
        return vo;
    }
}
