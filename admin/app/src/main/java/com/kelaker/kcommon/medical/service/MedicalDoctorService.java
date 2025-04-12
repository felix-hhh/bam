package com.kelaker.kcommon.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.medical.dao.MedicalDoctorDao;
import com.kelaker.kcommon.medical.dto.MedicalDoctorDto;
import com.kelaker.kcommon.medical.dto.MedicalDoctorSearchDto;
import com.kelaker.kcommon.medical.entity.MedicalDoctor;
import com.kelaker.kcommon.medical.vo.MedicalDoctorVo;
import com.kelaker.kcommon.medical.vo.MedicalHospitalVo;
import com.kelaker.ktools.common.exception.BusinessException;
import com.kelaker.ktools.common.utils.ValidateUtil;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 医生信息(MedicalDoctor)表服务
 *
 * @author Felix Huang
 * @since 2025-04-09 10:39:06
 */
@Service
public class MedicalDoctorService extends BaseService<MedicalDoctorDao, MedicalDoctor> {

    @Resource
    private MedicalHospitalService medicalHospitalService;

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 分页结果
     */
    public IPage<MedicalDoctorVo> queryPage(RequestPage<MedicalDoctorSearchDto> searchDto) {
        MedicalDoctorSearchDto searchDtoData = searchDto.getData();
        MedicalDoctor empty = super.objectConvert(searchDtoData, MedicalDoctor.class);
        IPage<MedicalDoctor> page = super.page(super.createPage(searchDto), super.createWrapper(empty));
        return mapPageToTarget(page, this::convertToVo);
    }

    /**
     * 取回对象
     *
     * @param id 对象ID
     */
    public MedicalDoctorVo getMedicalDoctor(Long id) {
        MedicalDoctor medicalDoctor = super.getById(id);
        if (ValidateUtil.isBlank(medicalDoctor)) {
            throw new BusinessException("医生信息不存在");
        }
        return this.convertToVo(medicalDoctor);
    }

    /**
     * 添加对象
     *
     * @param dto 对象
     */
    public void addMedicalDoctor(MedicalDoctorDto dto) {
        MedicalDoctor medicalDoctor = super.objectConvert(dto, MedicalDoctor.class);
        super.save(medicalDoctor);
    }

    /**
     * 更新对象
     *
     * @param dto 对象
     */
    public void updateMedicalDoctor(MedicalDoctorDto dto) {
        if (dto.getId() == null) {
            throw new BusinessException("医生ID不能为空");
        }
        MedicalDoctor existingDoctor = super.getById(dto.getId());
        if (existingDoctor == null) {
            throw new BusinessException("医生信息不存在");
        }
        MedicalDoctor medicalDoctor = super.objectConvert(dto, MedicalDoctor.class);
        super.updateById(medicalDoctor);
    }

    /**
     * 删除对象
     *
     * @param id 对象ID
     */
    public void deleteMedicalDoctor(Long id) {
        MedicalDoctor existingDoctor = super.getById(id);
        if (existingDoctor == null) {
            throw new BusinessException("医生信息不存在");
        }
        super.removeById(id);
    }

    /**
     * 对象转换
     */
    private MedicalDoctorVo convertToVo(MedicalDoctor medicalDoctor) {
        MedicalDoctorVo vo = super.objectConvert(medicalDoctor, MedicalDoctorVo.class);
        MedicalHospitalVo medicalHospital = medicalHospitalService.getMedicalHospital(medicalDoctor.getHospitalId());
        vo.setHospitalName(medicalHospital.getName());
        return vo;
    }
}
