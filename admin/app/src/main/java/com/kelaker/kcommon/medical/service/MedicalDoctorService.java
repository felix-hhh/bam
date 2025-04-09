package com.kelaker.kcommon.medical.service;

import com.kelaker.kcommon.medical.entity.MedicalDoctor;
import com.kelaker.kcommon.medical.dao.MedicalDoctorDao;
import com.kelaker.kcommon.medical.vo.MedicalDoctorVo;
import com.kelaker.kcommon.medical.dto.MedicalDoctorSearchDto;
import com.kelaker.kcommon.medical.dto.MedicalDoctorDto;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 员工信息(MedicalDoctor)表服务
 *
 * @author Felix Huang
 * @since 2025-04-09 10:21:50
 */
@Service
public class MedicalDoctorService extends BaseService<MedicalDoctorDao, MedicalDoctor> {

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
     * 对象转换
     */
    private MedicalDoctorVo convertToVo(MedicalDoctor medicalDoctor) {
        return super.objectConvert(medicalDoctor, MedicalDoctorVo.class);
    }
}

