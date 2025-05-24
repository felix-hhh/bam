package com.kelaker.kcommon.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kelaker.kcommon.app.entity.MedicalDoctor;
import org.springframework.stereotype.Repository;

/**
 * 员工信息(MedicalDoctor)表数据库访问层
 *
 * @author Felix Huang
 * @since 2025-04-09 10:39:06
 */
@Repository
public interface MedicalDoctorDao extends BaseMapper<MedicalDoctor> {

}

