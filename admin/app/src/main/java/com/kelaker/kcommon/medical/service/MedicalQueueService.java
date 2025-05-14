package com.kelaker.kcommon.medical.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kelaker.kcommon.medical.dao.MedicalQueueDao;
import com.kelaker.kcommon.medical.dto.MedicalPatientSearchDto;
import com.kelaker.kcommon.medical.dto.MedicalQueueDto;
import com.kelaker.kcommon.medical.dto.MedicalQueueSearchDto;
import com.kelaker.kcommon.medical.entity.MedicalQueue;
import com.kelaker.kcommon.medical.vo.MedicalDoctorVo;
import com.kelaker.kcommon.medical.vo.MedicalHospitalVo;
import com.kelaker.kcommon.medical.vo.MedicalPatientVo;
import com.kelaker.kcommon.medical.vo.MedicalQueueVo;
import com.kelaker.ktools.cache.manager.CacheManager;
import com.kelaker.ktools.common.exception.BusinessException;
import com.kelaker.ktools.common.populator.ConvertUtils;
import com.kelaker.ktools.common.utils.StringUtil;
import com.kelaker.ktools.common.utils.ValidateUtil;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 队列(MedicalQueue)表服务
 *
 * @author Felix Huang
 * @since 2025-04-09 10:39:07
 */
@Service
public class MedicalQueueService extends BaseService<MedicalQueueDao, MedicalQueue> {

    @Resource
    private MedicalPatientService medicalPatientService;

    @Resource
    private MedicalHospitalService medicalHospitalService;

    @Resource
    private MedicalDoctorService medicalDoctorService;

    @Resource
    private CacheManager cacheManager;

    /**
     * 自增ID时间
     */
    public static final String INCREMENT_DATE_KEY = "INCREMENT_DATE";

    /**
     * 当前num
     */
    public static final String CURRENT_NUM_KEY = "CURRENT_NUM";

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
     * 分页查询排队的患者
     *
     * @param searchDto 查询DTO
     */
    public IPage<MedicalQueueVo> queryPatientPage(RequestPage<MedicalPatientSearchDto> searchDto) {
        IPage<MedicalPatientVo> medicalPatientPage = this.medicalPatientService.queryPage(searchDto);
        IPage<MedicalQueueVo> resultPage = new Page<>(medicalPatientPage.getCurrent(), medicalPatientPage.getSize(), medicalPatientPage.getTotal(), medicalPatientPage.searchCount());
        List<MedicalQueueVo> list = medicalPatientPage.getRecords().stream().map(this::convertToVo).toList();
        resultPage.setRecords(list);
        return resultPage;
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

        if (ValidateUtil.isNotBlank(searchDtoData.getStatus())) {
            empty.setStatus(MedicalQueue.Status.toEnum(searchDtoData.getStatus()));
        }

        // 创建查询条件，只查询当前用户的数据
        LambdaQueryWrapper<MedicalQueue> queryWrapper = super.createWrapper(empty);
        queryWrapper.eq(MedicalQueue::getUserId, super.getUserId());

        IPage<MedicalQueue> page = super.page(super.createPage(searchDto), queryWrapper);
        return mapPageToTarget(page, this::convertToVo);
    }

    /**
     * 列表查询当前登录用户的订单
     */
    public List<MedicalQueueVo> listMyQueue() {
        List<MedicalQueue> medicalQueueList = super.lambdaQuery().eq(MedicalQueue::getUserId, super.getUserId())
                .orderByDesc(MedicalQueue::getCreateDatetime).list();
        return super.mapListToTarget(medicalQueueList, this::convertToVo);
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
        medicalQueue.setCurrentNum(this.getCurrentNum());
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
     * 取回当前排号
     */
    public Long getCurrentNum() {
        this.checkIncrementId();
        return cacheManager.incr(CURRENT_NUM_KEY);
    }

    private void checkIncrementId() {
        Integer incrementDate = (Integer) cacheManager.getValue(INCREMENT_DATE_KEY);
        Integer currentDate = ConvertUtils.convertToInteger(StringUtil.getCurrentDate("yyyyMMdd"));
        if (ValidateUtil.isBlank(incrementDate) || incrementDate < currentDate) {
            cacheManager.cacheValue(INCREMENT_DATE_KEY, currentDate, -1);
            cacheManager.cacheValue(CURRENT_NUM_KEY, 0, -1);
        }
    }

    /**
     * 对象转换
     */
    private MedicalQueueVo convertToVo(MedicalQueue medicalQueue) {
        MedicalQueueVo vo = super.objectConvert(medicalQueue, MedicalQueueVo.class);
        vo.setStatusStr(medicalQueue.getStatus().getRemark());
        if (medicalQueue.getCheckItem() != null) {
            vo.setCheckItemStr(medicalQueue.getCheckItem().getRemark());
        }
        MedicalPatientVo medicalPatient = medicalPatientService.getMedicalPatient(medicalQueue.getPatientId());
        vo.setPatientName(medicalPatient.getName());
        vo.setPatientGender(medicalPatient.getGender());
        vo.setPatientGenderStr(medicalPatient.getGender() == 1 ? "男" : "女");
        vo.setPatientPhone(medicalPatient.getPhone());
        vo.setPatientRelation(medicalPatient.getRelation());
        vo.setPatientRelationStr(medicalPatient.getRelationStr());
        MedicalHospitalVo medicalHospitalVo = this.medicalHospitalService
                .getMedicalHospital(medicalQueue.getHospitalId());
        vo.setHospitalName(medicalHospitalVo.getName());
        vo.setHospitalPhone(medicalHospitalVo.getPhone());
        MedicalDoctorVo medicalDoctorVo = this.medicalDoctorService.getMedicalDoctor(medicalQueue.getDoctorId());
        vo.setDoctorName(medicalDoctorVo.getName());
        return vo;
    }

    private MedicalQueueVo convertToVo(MedicalPatientVo medicalPatientVo) {
        Long queueNum = medicalPatientVo.getQueueNum();
        MedicalQueueVo vo = new MedicalQueueVo();
        vo.setId(medicalPatientVo.getId());
        vo.setPatientName(medicalPatientVo.getName());
        vo.setPatientPhone(medicalPatientVo.getPhone());
        vo.setPatientGender(medicalPatientVo.getGender());
        vo.setPatientRelation(medicalPatientVo.getRelation());
        vo.setPatientRelationStr(medicalPatientVo.getRelationStr());
        vo.setMedicalNum(medicalPatientVo.getMedicalNum());
        if (ValidateUtil.isBlank(queueNum)) {
            vo.setStatus(MedicalQueue.Status.WAIT.getValue());
            vo.setStatusStr(MedicalQueue.Status.WAIT.getRemark());
            return vo;
        } else {
            MedicalQueue medicalQueue = this.getById(queueNum);
            vo.setStatus(medicalQueue.getStatus().getValue());
            vo.setStatus(medicalQueue.getStatus().getRemark());
            vo.setCreateDatetime(medicalQueue.getCreateDatetime());
        }
        return null;
    }

    public List<MedicalQueueVo> listMyQueueCompleted() {
        List<MedicalQueue> medicalQueueList = super.lambdaQuery()
                .eq(MedicalQueue::getUserId, super.getUserId())
                .eq(MedicalQueue::getStatus, MedicalQueue.Status.COMPLETED)
                .orderByDesc(MedicalQueue::getCreateDatetime)
                .list();
        return super.mapListToTarget(medicalQueueList, this::convertToVo);
    }
}
