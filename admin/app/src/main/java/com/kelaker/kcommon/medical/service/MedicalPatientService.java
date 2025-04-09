package com.kelaker.kcommon.medical.service;

import com.kelaker.kcommon.medical.entity.MedicalPatient;
import com.kelaker.kcommon.medical.dao.MedicalPatientDao;
import com.kelaker.kcommon.medical.vo.MedicalPatientVo;
import com.kelaker.kcommon.medical.dto.MedicalPatientSearchDto;
import com.kelaker.kcommon.medical.dto.MedicalPatientDto;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 病人信息(MedicalPatient)表服务
 *
 * @author Felix Huang
 * @since 2025-04-09 10:21:51
 */
@Service
public class MedicalPatientService extends BaseService<MedicalPatientDao, MedicalPatient> {

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 分页结果
     */
    public IPage<MedicalPatientVo> queryPage(RequestPage<MedicalPatientSearchDto> searchDto) {
        MedicalPatientSearchDto searchDtoData = searchDto.getData();
        MedicalPatient empty = super.objectConvert(searchDtoData, MedicalPatient.class);
        IPage<MedicalPatient> page = super.page(super.createPage(searchDto), super.createWrapper(empty));
        return mapPageToTarget(page, this::convertToVo);
    }

    /**
     * 取回对象
     *
     * @param id 对象ID
     */
    public MedicalPatientVo getMedicalPatient(Long id) {
        MedicalPatient medicalPatient = super.getById(id);
        return this.convertToVo(medicalPatient);
    }

    /**
     * 添加对象
     *
     * @param dto 对象
     */
    public void addMedicalPatient(MedicalPatientDto dto) {
        MedicalPatient medicalPatient = super.objectConvert(dto, MedicalPatient.class);
        super.save(medicalPatient);
    }

    /**
     * 对象转换
     */
    private MedicalPatientVo convertToVo(MedicalPatient medicalPatient) {
        return super.objectConvert(medicalPatient, MedicalPatientVo.class);
    }
}

