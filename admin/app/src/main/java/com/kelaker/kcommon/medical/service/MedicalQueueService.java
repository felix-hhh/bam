package com.kelaker.kcommon.medical.service;

import com.kelaker.kcommon.medical.entity.MedicalQueue;
import com.kelaker.kcommon.medical.dao.MedicalQueueDao;
import com.kelaker.kcommon.medical.vo.MedicalQueueVo;
import com.kelaker.kcommon.medical.dto.MedicalQueueSearchDto;
import com.kelaker.kcommon.medical.dto.MedicalQueueDto;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 队列(MedicalQueue)表服务
 *
 * @author Felix Huang
 * @since 2025-04-09 10:21:51
 */
@Service
public class MedicalQueueService extends BaseService<MedicalQueueDao, MedicalQueue> {

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 分页结果
     */
    public IPage<MedicalQueueVo> queryPage(RequestPage<MedicalQueueSearchDto> searchDto) {
        MedicalQueueSearchDto searchDtoData = searchDto.getData();
        MedicalQueue empty = super.objectConvert(searchDtoData, MedicalQueue.class);
        IPage<MedicalQueue> page = super.page(super.createPage(searchDto), super.createWrapper(empty));
        return mapPageToTarget(page, this::convertToVo);
    }

    /**
     * 取回对象
     *
     * @param id 对象ID
     */
    public MedicalQueueVo getMedicalQueue(Long id) {
        MedicalQueue medicalQueue = super.getById(id);
        return this.convertToVo(medicalQueue);
    }

    /**
     * 添加对象
     *
     * @param dto 对象
     */
    public void addMedicalQueue(MedicalQueueDto dto) {
        MedicalQueue medicalQueue = super.objectConvert(dto, MedicalQueue.class);
        super.save(medicalQueue);
    }

    /**
     * 对象转换
     */
    private MedicalQueueVo convertToVo(MedicalQueue medicalQueue) {
        return super.objectConvert(medicalQueue, MedicalQueueVo.class);
    }
}

