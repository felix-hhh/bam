package com.kelaker.kcommon.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kelaker.kcommon.medical.entity.MedicalPatient;
import org.springframework.stereotype.Repository;

/**
 * 病人信息(MedicalPatient)表数据库访问层
 *
 * @author Felix Huang
 * @since 2025-04-09 10:21:51
 */
@Repository
public interface MedicalPatientDao extends BaseMapper<MedicalPatient> {

}

