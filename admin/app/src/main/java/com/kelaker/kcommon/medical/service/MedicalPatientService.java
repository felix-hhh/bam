package com.kelaker.kcommon.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.kelaker.kcommon.medical.dao.MedicalPatientDao;
import com.kelaker.kcommon.medical.dto.MedicalPatientDto;
import com.kelaker.kcommon.medical.dto.MedicalPatientSearchDto;
import com.kelaker.kcommon.medical.entity.MedicalPatient;
import com.kelaker.kcommon.medical.vo.MedicalPatientVo;
import com.kelaker.ktools.cache.annotation.CacheIt;
import com.kelaker.ktools.common.exception.BusinessException;
import com.kelaker.ktools.common.utils.ValidateUtil;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 病人信息(MedicalPatient)表服务
 *
 * @author Felix Huang
 * @since 2025-04-09 10:39:07
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
        empty.setStatus(MedicalPatient.Status.toEnum(searchDtoData.getStatus()));
        IPage<MedicalPatient> page = super.page(super.createPage(searchDto), super.createWrapper(empty));
        return mapPageToTarget(page, this::convertToVo);
    }

    /**
     * 列表查询当前登录用户的就诊人
     */
    public List<MedicalPatientVo> listMyMedicalPatient() {
        List<MedicalPatient> patientList = super.lambdaQuery()
                .eq(MedicalPatient::getUserId, super.getUserId())
                .eq(MedicalPatient::getStatus, MedicalPatient.Status.ENABLE)
                .orderByDesc(MedicalPatient::getCreateDatetime)
                .list();
        return super.mapListToTarget(patientList, this::convertToVo);
    }

    /**
     * 取回对象
     *
     * @param id 对象ID
     */
    @CacheIt(key = "MEDICAL_PATIENT", paramKey = true)
    public MedicalPatientVo getMedicalPatient(Long id) {
        MedicalPatient medicalPatient = super.getById(id);
        if (medicalPatient == null) {
            throw new BusinessException("病人信息不存在");
        }
        return this.convertToVo(medicalPatient);
    }

    /**
     * 添加对象
     *
     * @param dto 对象
     */
    public void addMedicalPatient(MedicalPatientDto dto) {
        MedicalPatient medicalPatient = super.objectConvert(dto, MedicalPatient.class);
        medicalPatient.setUserId(super.getUserId());
        medicalPatient.setStatus(MedicalPatient.Status.ENABLE);
        medicalPatient.setRelation(MedicalPatient.Relation.toEnum(dto.getRelation()));
        medicalPatient.setMedicalNum(IdWorker.getIdStr());
        super.save(medicalPatient);
    }

    /**
     * 更新对象
     *
     * @param dto 对象
     */
    public void updateMedicalPatient(MedicalPatientDto dto) {
        if (dto.getId() == null) {
            throw new BusinessException("病人ID不能为空");
        }
        MedicalPatient existingPatient = super.getById(dto.getId());
        if (existingPatient == null) {
            throw new BusinessException("病人信息不存在");
        }
        MedicalPatient medicalPatient = super.objectConvert(dto, MedicalPatient.class);
        super.updateById(medicalPatient);
    }

    /**
     * 删除对象
     *
     * @param id 对象ID
     */
    public void deleteMedicalPatient(Long id) {
        MedicalPatient existingPatient = super.getById(id);
        if (existingPatient == null) {
            throw new BusinessException("病人信息不存在");
        }
        existingPatient.setStatus(MedicalPatient.Status.DELETE);
        super.updateById(existingPatient);
    }

    /**
     * 变更状态
     * @param id 数据ID
     */
    public void changeMedicalPatientStatus(@NotNull(message = "病人ID不能为空") Long id) {
        MedicalPatient existingPatient = super.getById(id);
        if (ValidateUtil.isBlank(existingPatient)) {
            throw new BusinessException("病人信息不存在");
        }
        existingPatient.setStatus(MedicalPatient.Status.ENABLE.equals(existingPatient.getStatus()) ? MedicalPatient.Status.DISABLE : MedicalPatient.Status.ENABLE);
        super.updateById(existingPatient);
    }

    /**
     * 对象转换
     */
    private MedicalPatientVo convertToVo(MedicalPatient medicalPatient) {
        MedicalPatientVo vo = super.objectConvert(medicalPatient, MedicalPatientVo.class);
        vo.setStatusStr(medicalPatient.getStatus().getRemark());
        vo.setRelationStr(medicalPatient.getRelation().getRemark());
        return vo;
    }


}
