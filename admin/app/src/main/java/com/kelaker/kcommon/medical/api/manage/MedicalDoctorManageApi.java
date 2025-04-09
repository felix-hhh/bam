package com.kelaker.kcommon.medical.api.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.medical.dto.MedicalDoctorDto;
import com.kelaker.kcommon.medical.dto.MedicalDoctorSearchDto;
import com.kelaker.kcommon.medical.service.MedicalDoctorService;
import com.kelaker.kcommon.medical.vo.MedicalDoctorVo;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.annotation.HasAction;
import com.kelaker.ktools.web.annotation.InModule;
import com.kelaker.ktools.web.base.api.BaseApi;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 医生信息(MedicalDoctor)表控制层
 *
 * @author Felix Huang
 * @since 2025-04-09 10:39:07
 */
@RestController
@RequestMapping("/medical/manage/doctor")
@InModule(moduleCode = "MEDICAL")
@Validated
public class MedicalDoctorManageApi extends BaseApi {

    /**
     * 服务对象
     */
    @Resource
    private MedicalDoctorService medicalDoctorService;

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 查询结果
     */
    @HasAction(actionCode = "MEDICAL_DOCTOR:PAGE", actionName = "医生信息列表")
    @PostMapping("/page")
    public IPage<MedicalDoctorVo> pageMedicalDoctor(@RequestBody RequestPage<MedicalDoctorSearchDto> searchDto) {
        return this.medicalDoctorService.queryPage(searchDto);
    }

    /**
     * 添加医生信息
     *
     * @param dto 医生信息
     */
    @HasAction(actionCode = "MEDICAL_DOCTOR:ADD", actionName = "添加医生信息")
    @PostMapping("/add")
    public void addMedicalDoctor(@RequestBody MedicalDoctorDto dto) {
        this.medicalDoctorService.addMedicalDoctor(dto);
    }

    /**
     * 删除医生信息
     *
     * @param id 医生ID
     */
    @HasAction(actionCode = "MEDICAL_DOCTOR:DELETE", actionName = "删除医生信息")
    @DeleteMapping("/del/{id}")
    public void deleteMedicalDoctor(@PathVariable("id") @NotNull(message = "医生ID不能为空") Long id) {
        this.medicalDoctorService.deleteMedicalDoctor(id);
    }

    /**
     * 更新医生信息
     *
     * @param dto 医生信息
     */
    @HasAction(actionCode = "MEDICAL_DOCTOR:UPDATE", actionName = "更新医生信息")
    @PostMapping("/update")
    public void updateMedicalDoctor(@RequestBody MedicalDoctorDto dto) {
        this.medicalDoctorService.updateMedicalDoctor(dto);
    }

    /**
     * 获取医生信息
     *
     * @param id 医生ID
     * @return 医生信息
     */
    @HasAction(actionCode = "MEDICAL_DOCTOR:GET", actionName = "获取医生信息")
    @GetMapping("/get/{id}")
    public MedicalDoctorVo getMedicalDoctor(@PathVariable("id") @NotNull(message = "医生ID不能为空") Long id) {
        return this.medicalDoctorService.getMedicalDoctor(id);
    }
}
