package com.kelaker.kcommon.medical.api.manage;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.medical.dto.MedicalPatientSearchDto;
import com.kelaker.kcommon.medical.service.MedicalPatientService;
import com.kelaker.kcommon.medical.vo.MedicalPatientVo;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.api.BaseApi;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 病人信息(MedicalPatient)表控制层
 *
 * @author Felix Huang
 * @since 2025-04-09 10:21:50
 */
@RestController
@RequestMapping("/medical/manage/patient")
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
    @PostMapping("/page")
    public IPage<MedicalPatientVo> pageMedicalPatient(@RequestBody RequestPage<MedicalPatientSearchDto> searchDto) {
        return this.medicalPatientService.queryPage(searchDto);
    }
}

