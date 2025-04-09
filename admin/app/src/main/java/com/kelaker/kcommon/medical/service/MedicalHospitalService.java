package com.kelaker.kcommon.medical.service;

import com.kelaker.kcommon.medical.entity.MedicalHospital;
import com.kelaker.kcommon.medical.dao.MedicalHospitalDao;
import com.kelaker.kcommon.medical.vo.MedicalHospitalVo;
import com.kelaker.kcommon.medical.dto.MedicalHospitalSearchDto;
import com.kelaker.kcommon.medical.dto.MedicalHospitalDto;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 医院信息(MedicalHospital)表服务
 *
 * @author Felix Huang
 * @since 2025-04-09 10:21:50
 */
@Service
public class MedicalHospitalService extends BaseService<MedicalHospitalDao, MedicalHospital> {

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
     * 取回对象
     *
     * @param id 对象ID
     */
    public MedicalHospitalVo getMedicalHospital(Long id) {
        MedicalHospital medicalHospital = super.getById(id);
        return this.convertToVo(medicalHospital);
    }

    /**
     * 添加对象
     *
     * @param dto 对象
     */
    public void addMedicalHospital(MedicalHospitalDto dto) {
        MedicalHospital medicalHospital = super.objectConvert(dto, MedicalHospital.class);
        super.save(medicalHospital);
    }

    /**
     * 对象转换
     */
    private MedicalHospitalVo convertToVo(MedicalHospital medicalHospital) {
        return super.objectConvert(medicalHospital, MedicalHospitalVo.class);
    }
}

