package com.kelaker.kcommon.medical.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.medical.dao.MedicalQueueDao;
import com.kelaker.kcommon.medical.dto.MedicalQueueDto;
import com.kelaker.kcommon.medical.dto.MedicalQueueSearchDto;
import com.kelaker.kcommon.medical.entity.MedicalQueue;
import com.kelaker.kcommon.medical.vo.MedicalQueueVo;
import com.kelaker.ktools.common.exception.BusinessException;
import com.kelaker.ktools.common.utils.ValidateUtil;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import org.springframework.stereotype.Service;

/**
 * 队列(MedicalQueue)表服务
 *
 * @author Felix Huang
 * @since 2025-04-09 10:39:07
 */
@Service
public class MedicalQueueService extends BaseService<MedicalQueueDao, MedicalQueue> {

    /**
     * 分页查询（管理端）
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
     * 分页查询（前端）
     * 只能查询当前用户的数据
     *
     * @param searchDto 查询对象
     * @return 分页结果
     */
    public IPage<MedicalQueueVo> queryPageFront(RequestPage<MedicalQueueSearchDto> searchDto) {
        MedicalQueueSearchDto searchDtoData = searchDto.getData();
        MedicalQueue empty = super.objectConvert(searchDtoData, MedicalQueue.class);

        // 创建查询条件，只查询当前用户的数据
        LambdaQueryWrapper<MedicalQueue> queryWrapper = super.createWrapper(empty);
        queryWrapper.eq(MedicalQueue::getUserId, super.getUserId());

        IPage<MedicalQueue> page = super.page(super.createPage(searchDto), queryWrapper);
        return mapPageToTarget(page, this::convertToVo);
    }

    /**
     * 取回对象（管理端）
     *
     * @param id 对象ID
     */
    public MedicalQueueVo getMedicalQueue(Long id) {
        MedicalQueue medicalQueue = super.getById(id);
        if (medicalQueue == null) {
            throw new BusinessException("队列信息不存在");
        }
        return this.convertToVo(medicalQueue);
    }

    /**
     * 取回对象（前端）
     * 只能查询当前用户的数据
     *
     * @param id 对象ID
     */
    public MedicalQueueVo getMedicalQueueFront(Long id) {
        MedicalQueue medicalQueue = super.getById(id);
        if (ValidateUtil.isBlank(medicalQueue)) {
            throw new BusinessException("队列信息不存在");
        }

        // 验证是否为当前用户的数据
        if (!super.getUserId().equals(medicalQueue.getUserId())) {
            throw new BusinessException("无权限查看此队列信息");
        }

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
     * 更新对象
     *
     * @param dto 对象
     */
    public void updateMedicalQueue(MedicalQueueDto dto) {
        if (dto.getId() == null) {
            throw new BusinessException("队列ID不能为空");
        }
        MedicalQueue existingQueue = super.getById(dto.getId());
        if (existingQueue == null) {
            throw new BusinessException("队列信息不存在");
        }
        MedicalQueue medicalQueue = super.objectConvert(dto, MedicalQueue.class);
        super.updateById(medicalQueue);
    }

    /**
     * 删除对象
     *
     * @param id 对象ID
     */
    public void deleteMedicalQueue(Long id) {
        MedicalQueue existingQueue = super.getById(id);
        if (existingQueue == null) {
            throw new BusinessException("队列信息不存在");
        }
        super.removeById(id);
    }

    /**
     * 对象转换
     */
    private MedicalQueueVo convertToVo(MedicalQueue medicalQueue) {
        return super.objectConvert(medicalQueue, MedicalQueueVo.class);
    }
}
