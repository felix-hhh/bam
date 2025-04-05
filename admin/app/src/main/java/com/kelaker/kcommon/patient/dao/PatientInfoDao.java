package com.kelaker.kcommon.patient.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kelaker.kcommon.patient.entity.PatientInfo;
import org.springframework.stereotype.Repository;

/**
 * 患者信息(PatientInfo)表数据库访问层
 *
 * @author felix huang
 * @since 2024-03-27 11:42:56
 */
@Repository
public interface PatientInfoDao extends BaseMapper<PatientInfo> {

}