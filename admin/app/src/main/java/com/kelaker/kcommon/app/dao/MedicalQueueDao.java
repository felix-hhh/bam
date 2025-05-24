package com.kelaker.kcommon.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kelaker.kcommon.app.entity.MedicalQueue;
import org.springframework.stereotype.Repository;

/**
 * 队列(MedicalQueue)表数据库访问层
 *
 * @author Felix Huang
 * @since 2025-04-09 10:39:08
 */
@Repository
public interface MedicalQueueDao extends BaseMapper<MedicalQueue> {

}

