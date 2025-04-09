package com.kelaker.kcommon.medical.api.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.medical.dto.MedicalHospitalDto;
import com.kelaker.kcommon.medical.dto.MedicalHospitalSearchDto;
import com.kelaker.kcommon.medical.service.MedicalHospitalService;
import com.kelaker.kcommon.medical.vo.MedicalHospitalVo;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.annotation.HasAction;
import com.kelaker.ktools.web.annotation.InModule;
import com.kelaker.ktools.web.base.api.BaseApi;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 医院信息(MedicalHospital)表控制层
 *
 * @author Felix Huang
 * @since 2025-04-09 10:39:07
 */
@RestController
@RequestMapping("/medical/manage/hospital")
@InModule(moduleCode = "MEDICAL")
@Validated
public class MedicalHospitalManageApi extends BaseApi {

    /**
     * 服务对象
     */
    @Resource
    private MedicalHospitalService medicalHospitalService;

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 查询结果
     */
    @HasAction(actionCode = "MEDICAL_HOSPITAL:PAGE", actionName = "医院信息列表")
    @PostMapping("/page")
    public IPage<MedicalHospitalVo> pageMedicalHospital(@RequestBody RequestPage<MedicalHospitalSearchDto> searchDto) {
        return this.medicalHospitalService.queryPage(searchDto);
    }

    /**
     * 添加医院信息
     *
     * @param dto 医院信息
     */
    @HasAction(actionCode = "MEDICAL_HOSPITAL:ADD", actionName = "添加医院信息")
    @PostMapping("/add")
    public void addMedicalHospital(@RequestBody MedicalHospitalDto dto) {
        this.medicalHospitalService.addMedicalHospital(dto);
    }

    /**
     * 删除医院信息
     *
     * @param id 医院ID
     */
    @HasAction(actionCode = "MEDICAL_HOSPITAL:DELETE", actionName = "删除医院信息")
    @DeleteMapping("/del/{id}")
    public void deleteMedicalHospital(@PathVariable("id") @NotNull(message = "医院ID不能为空") Long id) {
        this.medicalHospitalService.deleteMedicalHospital(id);
    }

    /**
     * 更新医院信息
     *
     * @param dto 医院信息
     */
    @HasAction(actionCode = "MEDICAL_HOSPITAL:UPDATE", actionName = "更新医院信息")
    @PostMapping("/update")
    public void updateMedicalHospital(@RequestBody MedicalHospitalDto dto) {
        this.medicalHospitalService.updateMedicalHospital(dto);
    }

    /**
     * 获取医院信息
     *
     * @param id 医院ID
     * @return 医院信息
     */
    @HasAction(actionCode = "MEDICAL_HOSPITAL:GET", actionName = "获取医院信息")
    @GetMapping("/get/{id}")
    public MedicalHospitalVo getMedicalHospital(@PathVariable("id") @NotNull(message = "医院ID不能为空") Long id) {
        return this.medicalHospitalService.getMedicalHospital(id);
    }
}
