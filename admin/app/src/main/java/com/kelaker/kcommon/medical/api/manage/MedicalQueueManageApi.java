package com.kelaker.kcommon.medical.api.manage;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.medical.dto.MedicalQueueSearchDto;
import com.kelaker.kcommon.medical.service.MedicalQueueService;
import com.kelaker.kcommon.medical.vo.MedicalQueueVo;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.api.BaseApi;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 队列(MedicalQueue)表控制层
 *
 * @author Felix Huang
 * @since 2025-04-09 10:21:51
 */
@RestController
@RequestMapping("/medical/manage/queue")
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
    @PostMapping("/page")
    public IPage<MedicalQueueVo> pageMedicalQueue(@RequestBody RequestPage<MedicalQueueSearchDto> searchDto) {
        return this.medicalQueueService.queryPage(searchDto);
    }
}

