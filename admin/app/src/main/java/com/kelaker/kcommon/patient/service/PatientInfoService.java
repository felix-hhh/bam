package com.kelaker.kcommon.patient.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.patient.dao.PatientInfoDao;
import com.kelaker.kcommon.patient.dto.PatientInfoDto;
import com.kelaker.kcommon.patient.dto.PatientInfoSearchDto;
import com.kelaker.kcommon.patient.entity.PatientInfo;
import com.kelaker.kcommon.patient.vo.PatientInfoVo;
import com.kelaker.ktools.common.exception.BusinessException;
import com.kelaker.ktools.common.utils.ValidateUtil;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 患者信息(PatientInfo)表服务
 *
 * @author felix huang
 * @since 2024-03-27 11:42:56
 */
@Service
public class PatientInfoService extends BaseService<PatientInfoDao, PatientInfo> {

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 分页结果
     */
    public IPage<PatientInfoVo> queryPage(RequestPage<PatientInfoSearchDto> searchDto) {
        PatientInfoSearchDto searchDtoData = searchDto.getData();
        PatientInfo empty = super.objectConvert(searchDtoData, PatientInfo.class);
        IPage<PatientInfo> page = super.page(super.createPage(searchDto), super.createWrapper(empty));
        return mapPageToTarget(page, this::convertToVo);
    }

    /**
     * 添加对象
     *
     * @param dto 对象
     */
    public void addPatientInfo(PatientInfoDto dto) {
        PatientInfo patientInfo = super.objectConvert(dto, PatientInfo.class);
        // 设置当前登录用户ID
        patientInfo.setUserId(super.getUserId());
        super.save(patientInfo);
    }

    /**
     * 前端删除患者信息
     *
     * @param id 患者ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void deletePatientInfoFront(Long id) {
        PatientInfo patientInfo = getPatientInfoById(id);
        // 验证当前用户是否有权限删除该患者信息
        checkUserPermission(patientInfo, "删除");

        deletePatientInfo(patientInfo);
    }

    /**
     * 管理端删除患者信息
     *
     * @param id 患者ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void deletePatientInfoManage(Long id) {
        PatientInfo patientInfo = getPatientInfoById(id);
        deletePatientInfo(patientInfo);
    }

    /**
     * 前端更新患者信息
     *
     * @param dto 患者信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void updatePatientInfoFront(PatientInfoDto dto) {
        validatePatientInfoDto(dto);

        PatientInfo patientInfo = getPatientInfoById(dto.getId());
        // 验证当前用户是否有权限更新该患者信息
        checkUserPermission(patientInfo, "更新");

        updatePatientInfo(dto);
    }

    /**
     * 管理端更新患者信息
     *
     * @param dto 患者信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void updatePatientInfoManage(PatientInfoDto dto) {
        validatePatientInfoDto(dto);

        getPatientInfoById(dto.getId());
        updatePatientInfo(dto);
    }

    /**
     * 前端获取患者信息
     *
     * @param id 患者ID
     * @return 患者信息
     */
    public PatientInfoVo getPatientInfoFront(Long id) {
        PatientInfo patientInfo = getPatientInfoById(id);
        // 验证当前用户是否有权限查看该患者信息
        checkUserPermission(patientInfo, "查看");

        return convertToVo(patientInfo);
    }

    /**
     * 管理端获取患者信息
     *
     * @param id 患者ID
     * @return 患者信息
     */
    public PatientInfoVo getPatientInfoManage(Long id) {
        PatientInfo patientInfo = getPatientInfoById(id);
        return convertToVo(patientInfo);
    }

    /**
     * 根据ID获取患者信息
     *
     * @param id 患者ID
     * @return 患者信息
     */
    private PatientInfo getPatientInfoById(Long id) {
        PatientInfo patientInfo = super.getById(id);
        if (ValidateUtil.isBlank(patientInfo)) {
            throw new BusinessException("患者信息不存在");
        }
        return patientInfo;
    }

    /**
     * 验证患者信息DTO
     *
     * @param dto 患者信息DTO
     */
    private void validatePatientInfoDto(PatientInfoDto dto) {
        if (ValidateUtil.isBlank(dto.getId())) {
            throw new BusinessException("患者ID不能为空");
        }
    }

    /**
     * 检查用户权限
     *
     * @param patientInfo 患者信息
     * @param operation   操作类型
     */
    private void checkUserPermission(PatientInfo patientInfo, String operation) {
        if (!patientInfo.getUserId().equals(super.getUserId())) {
            throw new BusinessException("无权" + operation + "该患者信息");
        }
    }

    /**
     * 删除患者信息
     *
     * @param patientInfo 患者信息
     */
    private void deletePatientInfo(PatientInfo patientInfo) {
        patientInfo.setStatus(PatientInfo.Status.DELETE);
        super.updateById(patientInfo);
    }

    /**
     * 更新患者信息
     *
     * @param dto 患者信息
     */
    private void updatePatientInfo(PatientInfoDto dto) {
        PatientInfo updateInfo = super.objectConvert(dto, PatientInfo.class);
        super.updateById(updateInfo);
    }

    /**
     * 对象转换
     */
    private PatientInfoVo convertToVo(PatientInfo patientInfo) {
        PatientInfoVo vo = super.objectConvert(patientInfo, PatientInfoVo.class);

        // 转换状态名称
        if (ValidateUtil.isNotBlank(patientInfo.getStatus())) {
            vo.setStatusName(patientInfo.getStatus().getRemark());
        }

        // 转换性别名称
        if (patientInfo.getGender() != null) {
            switch (patientInfo.getGender()) {
                case 1 -> vo.setGenderName("男");
                case 2 -> vo.setGenderName("女");
                default -> vo.setGenderName("未知");
            }
        }

        return vo;
    }
}