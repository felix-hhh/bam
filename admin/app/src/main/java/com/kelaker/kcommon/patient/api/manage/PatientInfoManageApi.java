package com.kelaker.kcommon.patient.api.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.patient.dto.PatientInfoDto;
import com.kelaker.kcommon.patient.dto.PatientInfoSearchDto;
import com.kelaker.kcommon.patient.service.PatientInfoService;
import com.kelaker.kcommon.patient.vo.PatientInfoVo;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.annotation.HasAction;
import com.kelaker.ktools.web.annotation.InModule;
import com.kelaker.ktools.web.base.api.BaseApi;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

/**
 * 患者信息(PatientInfo)表控制层
 *
 * @author felix huang
 * @since 2024-03-27 11:42:56
 */
@RestController
@RequestMapping("/patient/manage/info")
@InModule(moduleCode = "PATIENT")
public class PatientInfoManageApi extends BaseApi {

    /**
     * 服务对象
     */
    @Resource
    private PatientInfoService patientInfoService;

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 查询结果
     */
    @HasAction(actionCode = "PATIENT_INFO:PAGE", actionName = "患者信息列表")
    @PostMapping("/page")
    public IPage<PatientInfoVo> pagePatientInfo(@RequestBody RequestPage<PatientInfoSearchDto> searchDto) {
        return this.patientInfoService.queryPage(searchDto);
    }

    /**
     * 添加患者信息
     *
     * @param dto 患者信息
     */
    @HasAction(actionCode = "PATIENT_INFO:ADD", actionName = "添加患者信息")
    @PostMapping("/add")
    public void addPatientInfo(@RequestBody @Valid PatientInfoDto dto) {
        this.patientInfoService.addPatientInfo(dto);
    }

    /**
     * 删除患者信息
     *
     * @param id 患者ID
     */
    @HasAction(actionCode = "PATIENT_INFO:DELETE", actionName = "删除患者信息")
    @DeleteMapping("/del/{id}")
    public void deletePatientInfo(@PathVariable("id") @NotNull Long id) {
        this.patientInfoService.deletePatientInfoManage(id);
    }

    /**
     * 更新患者信息
     *
     * @param dto 患者信息
     */
    @HasAction(actionCode = "PATIENT_INFO:UPDATE", actionName = "更新患者信息")
    @PostMapping("/update")
    public void updatePatientInfo(@RequestBody @Valid PatientInfoDto dto) {
        this.patientInfoService.updatePatientInfoManage(dto);
    }

    /**
     * 获取患者信息
     *
     * @param id 患者ID
     * @return 患者信息
     */
    @HasAction(actionCode = "PATIENT_INFO:GET", actionName = "获取患者信息")
    @GetMapping("/get/{id}")
    public PatientInfoVo getPatientInfo(@PathVariable("id") @NotNull Long id) {
        return this.patientInfoService.getPatientInfoManage(id);
    }
}