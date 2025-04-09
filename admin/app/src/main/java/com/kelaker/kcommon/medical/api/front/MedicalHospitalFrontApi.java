package com.kelaker.kcommon.medical.api.front;


import com.kelaker.ktools.web.base.api.BaseApi;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import com.kelaker.kcommon.medical.service.MedicalHospitalService;
import com.kelaker.kcommon.medical.vo.MedicalHospitalVo;

/**
 * 医院信息(MedicalHospital)表控制层
 *
 * @author Felix Huang
 * @since 2025-04-09 10:21:50
 */
@RestController
@RequestMapping("/medical/front/hospital")
public class MedicalHospitalFrontApi extends BaseApi {

    /**
     * 服务对象
     */
    @Resource
    private MedicalHospitalService medicalHospitalService;

    @GetMapping("/get/{id}")
    public MedicalHospitalVo getMedicalHospital(@Validated @NotNull(message = "ID不能为空") @PathVariable("id") Long id) {
        return this.medicalHospitalService.getMedicalHospital(id);
    }
}

