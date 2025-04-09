package com.kelaker.kcommon.medical.api.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.medical.dto.MedicalPatientDto;
import com.kelaker.kcommon.medical.dto.MedicalPatientSearchDto;
import com.kelaker.kcommon.medical.service.MedicalPatientService;
import com.kelaker.kcommon.medical.vo.MedicalPatientVo;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.annotation.HasAction;
import com.kelaker.ktools.web.annotation.InModule;
import com.kelaker.ktools.web.base.api.BaseApi;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 病人信息(MedicalPatient)表控制层
 *
 * @author Felix Huang
 * @since 2025-04-09 10:39:07
 */
@RestController
@RequestMapping("/medical/manage/patient")
@InModule(moduleCode = "MEDICAL")
@Validated
public class MedicalPatientManageApi extends BaseApi {

    /**
     * 服务对象
     */
    @Resource
    private MedicalPatientService medicalPatientService;

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 查询结果
     */
    @HasAction(actionCode = "MEDICAL_PATIENT:PAGE", actionName = "病人信息列表")
    @PostMapping("/page")
    public IPage<MedicalPatientVo> pageMedicalPatient(@RequestBody RequestPage<MedicalPatientSearchDto> searchDto) {
        return this.medicalPatientService.queryPage(searchDto);
    }

    /**
     * 添加病人信息
     *
     * @param dto 病人信息
     */
    @HasAction(actionCode = "MEDICAL_PATIENT:ADD", actionName = "添加病人信息")
    @PostMapping("/add")
    public void addMedicalPatient(@RequestBody MedicalPatientDto dto) {
        this.medicalPatientService.addMedicalPatient(dto);
    }

    /**
     * 删除病人信息
     *
     * @param id 病人ID
     */
    @HasAction(actionCode = "MEDICAL_PATIENT:DELETE", actionName = "删除病人信息")
    @DeleteMapping("/del/{id}")
    public void deleteMedicalPatient(@PathVariable("id") @NotNull(message = "病人ID不能为空") Long id) {
        this.medicalPatientService.deleteMedicalPatient(id);
    }

    /**
     * 更新病人信息
     *
     * @param dto 病人信息
     */
    @HasAction(actionCode = "MEDICAL_PATIENT:UPDATE", actionName = "更新病人信息")
    @PostMapping("/update")
    public void updateMedicalPatient(@RequestBody MedicalPatientDto dto) {
        this.medicalPatientService.updateMedicalPatient(dto);
    }

    /**
     * 获取病人信息
     *
     * @param id 病人ID
     * @return 病人信息
     */
    @HasAction(actionCode = "MEDICAL_PATIENT:GET", actionName = "获取病人信息")
    @GetMapping("/get/{id}")
    public MedicalPatientVo getMedicalPatient(@PathVariable("id") @NotNull(message = "病人ID不能为空") Long id) {
        return this.medicalPatientService.getMedicalPatient(id);
    }
}
