package com.kelaker.kcommon.app.api.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.app.dto.*;
import com.kelaker.kcommon.app.service.MedicalQueueService;
import com.kelaker.kcommon.app.vo.MedicalQueueVo;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.annotation.HasAction;
import com.kelaker.ktools.web.annotation.InModule;
import com.kelaker.ktools.web.base.api.BaseApi;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

/**
 * 队列(MedicalQueue)表控制层
 *
 * @author Felix Huang
 * @since 2025-04-09 10:39:07
 */
@RestController
@RequestMapping("/medical/manage/queue")
@InModule(moduleCode = "MEDICAL")
public class MedicalQueueManageApi extends BaseApi {

    /**
     * 服务对象
     */
    @Resource
    private MedicalQueueService medicalQueueService;

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 查询结果
     */
    @HasAction(actionCode = "MEDICAL_QUEUE:PAGE", actionName = "队列信息列表")
    @PostMapping("/page")
    public IPage<MedicalQueueVo> pageMedicalQueue(@RequestBody RequestPage<MedicalQueueSearchDto> searchDto) {
        return this.medicalQueueService.queryPage(searchDto);
    }

    @PutMapping("/check/add")
    public void addCheckData(@RequestBody MedicalQueueCheckDataDto dto) {
        this.medicalQueueService.addCheckData(dto);
    }

    @HasAction(actionCode = "MEDICAL_QUEUE:MY_PAGE", actionName = "主治医生列表")
    @PostMapping("/page/my")
    public IPage<MedicalQueueVo> pageMyMedicalQueue(@RequestBody RequestPage<MedicalQueueSearchDto> searchDto) {
        return this.medicalQueueService.queryMyPage(searchDto);
    }

    @HasAction(actionCode = "MEDICAL_QUEUE:PATIENT_PAGE", actionName = "患者队列信息列表")
    @PostMapping("/patient/page")
    public IPage<MedicalQueueVo> pageMedicalPatientQueue(@RequestBody RequestPage<MedicalPatientSearchDto> searchDto) {
        return this.medicalQueueService.queryPatientPage(searchDto);
    }

    /**
     * 添加队列信息
     *
     * @param dto 队列信息
     */
    @HasAction(actionCode = "MEDICAL_QUEUE:ADD", actionName = "添加队列信息")
    @PostMapping("/add")
    public void addMedicalQueue(@RequestBody @Valid MedicalQueueDto dto) {
        this.medicalQueueService.addMedicalQueue(dto);
    }

    /**
     * 删除队列信息
     *
     * @param id 队列ID
     */
    @HasAction(actionCode = "MEDICAL_QUEUE:DELETE", actionName = "删除队列信息")
    @DeleteMapping("/del/{id}")
    public void deleteMedicalQueue(@PathVariable("id") @NotNull(message = "队列ID不能为空") Long id) {
        this.medicalQueueService.deleteMedicalQueue(id);
    }

    /**
     * 更新队列信息
     *
     * @param dto 队列信息
     */
    @HasAction(actionCode = "MEDICAL_QUEUE:UPDATE", actionName = "更新队列信息")
    @PutMapping("/update")
    public void updateMedicalQueue(@RequestBody @Valid MedicalQueueDto dto) {
        this.medicalQueueService.updateMedicalQueue(dto);
    }

    /**
     * 取消订单
     *
     * @param id 订单ID
     */
    @HasAction(actionCode = "MEDICAL_QUEUE:CANCEL", actionName = "取消队列")
    @PutMapping("/cancel/{id}")
    public void cancelMedicalQueue(@PathVariable("id") String id) {
        this.medicalQueueService.cancelMedicalQueue(id);
    }

    /**
     * 获取队列信息
     *
     * @param id 队列ID
     * @return 队列信息
     */
    @HasAction(actionCode = "MEDICAL_QUEUE:GET", actionName = "获取队列信息")
    @GetMapping("/get/{id}")
    public MedicalQueueVo getMedicalQueue(@PathVariable("id") @NotNull(message = "队列ID不能为空") Long id) {
        return this.medicalQueueService.getMedicalQueue(id);
    }
}
