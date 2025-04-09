package com.kelaker.kcommon.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kelaker.kcommon.medical.entity.MedicalHospital;
import org.springframework.stereotype.Repository;

/**
 * 医院信息(MedicalHospital)表数据库访问层
 *
 * @author Felix Huang
 * @since 2025-04-09 10:21:50
 */
@Repository
public interface MedicalHospitalDao extends BaseMapper<MedicalHospital> {

}

