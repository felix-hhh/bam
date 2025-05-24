package com.kelaker.kcommon.app.api.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.app.dto.MedicalHospitalDto;
import com.kelaker.kcommon.app.dto.MedicalHospitalSearchDto;
import com.kelaker.kcommon.app.service.MedicalHospitalService;
import com.kelaker.kcommon.app.vo.MedicalHospitalVo;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.annotation.NoLogin;
import com.kelaker.ktools.web.base.api.BaseApi;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 医院信息(MedicalHospital)表控制层
 *
 * @author Felix Huang
 * @since 2025-04-09 10:39:07
 */
@RestController
@RequestMapping("/medical/front/hospital")
@Validated
public class MedicalHospitalFrontApi extends BaseApi {

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
    @PostMapping("/page")
    public IPage<MedicalHospitalVo> pageMedicalHospital(@RequestBody RequestPage<MedicalHospitalSearchDto> searchDto) {
        return this.medicalHospitalService.queryPage(searchDto);
    }

    @NoLogin
    /**
     * 获取所有启用的医院列表
     *
     * @return 医院列表
     */
    @GetMapping("/list")
    public List<MedicalHospitalVo> listEnabledHospitals() {
        return this.medicalHospitalService.listHospitals();
    }

    /**
     * 添加医院信息
     *
     * @param dto 医院信息
     */
    @PostMapping("/add")
    public void addMedicalHospital(@RequestBody @Valid MedicalHospitalDto dto) {
        this.medicalHospitalService.addMedicalHospital(dto);
    }

    /**
     * 删除医院信息
     *
     * @param id 医院ID
     */
    @DeleteMapping("/del/{id}")
    public void deleteMedicalHospital(@PathVariable("id") @NotNull(message = "医院ID不能为空") Long id) {
        this.medicalHospitalService.deleteMedicalHospital(id);
    }

    /**
     * 更新医院信息
     *
     * @param dto 医院信息
     */
    @PostMapping("/update")
    public void updateMedicalHospital(@RequestBody @Valid MedicalHospitalDto dto) {
        this.medicalHospitalService.updateMedicalHospital(dto);
    }

    /**
     * 获取医院信息
     *
     * @param id 医院ID
     * @return 医院信息
     */
    @GetMapping("/get/{id}")
    public MedicalHospitalVo getMedicalHospital(@PathVariable("id") @NotNull(message = "医院ID不能为空") Long id) {
        return this.medicalHospitalService.getMedicalHospital(id);
    }
}
