package com.kelaker.kcommon.app.api.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.app.dto.MedicalQueueSearchDto;
import com.kelaker.kcommon.app.service.MedicalQueueService;
import com.kelaker.kcommon.app.vo.MedicalQueueVo;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.api.BaseApi;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 队列(MedicalQueue)表控制层
 *
 * @author Felix Huang
 * @since 2025-04-09 10:39:07
 */
@RestController
@RequestMapping("/medical/front/queue")
@Validated
public class MedicalQueueFrontApi extends BaseApi {

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
        return this.medicalQueueService.queryPageFront(searchDto);
    }

    @PostMapping("/page/completed")
    public IPage<MedicalQueueVo> pageMedicalQueueCompleted(@RequestBody RequestPage<MedicalQueueSearchDto> searchDto) {
        searchDto.getData().setStatus("M_Q_S_COMPLETED");
        return this.medicalQueueService.queryPageFront(searchDto);
    }

    /**
     * 列表查询当前登录用户的订单
     */
    @GetMapping("/list")
    public List<MedicalQueueVo> listMedicalQueue() {
        return this.medicalQueueService.listMyQueue();
    }

    /**
     * 列表查询当前登录用户的订单
     */
    @GetMapping("/list/completed")
    public List<MedicalQueueVo> listMedicalQueueCompleted() {
        return this.medicalQueueService.listMyQueueCompleted();
    }

    /**
     * 获取队列信息
     *
     * @param id 队列ID
     * @return 队列信息
     */
    @GetMapping("/get/{id}")
    public MedicalQueueVo getMedicalQueue(@PathVariable("id") @NotNull(message = "队列ID不能为空") Long id) {
        return this.medicalQueueService.getMedicalQueueFront(id);
    }

}
