package com.kelaker.kcommon.medical.dto;

import java.util.Date;

import lombok.Data;

/**
 * 队列(MedicalQueue)查询实体类
 *
 * @author Felix Huang
 * @since 2025-04-09 10:21:51
 */
@Data
public class MedicalQueueDto {

    /**
     * 序号
     */
    private Integer id;

    /**
     * 医院ID
     */
    private Integer hospitalId;

    /**
     * 医生ID
     */
    private Integer doctorId;

    /**
     * 患者ID
     */
    private Integer patientId;

    /**
     * 患者信息
     */
    private Object patientInfo;

    /**
     * 检查项目
     */
    private String checkItem;

    /**
     * 创建时间
     */
    private Date createDatetime;

    /**
     * 状态
     */
    private String status;

    /**
     * 队列号码
     */
    private String queueNum;

    /**
     * 当前号码
     */
    private Integer currentNum;

    /**
     * 诊断时间
     */
    private Date diagnosticTime;
}

