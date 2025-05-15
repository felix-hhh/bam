package com.kelaker.kcommon.medical.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * 队列(MedicalQueue)查询实体类
 *
 * @author Felix Huang
 * @since 2025-04-09 10:39:08
 */
@Data
public class MedicalQueueDto {

    /**
     * 序号
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 医生ID
     */
    @NotNull(message = "医生ID不能为空")
    private Long doctorId;

    /**
     * 患者ID
     */
    @NotNull(message = "患者ID不能为空")
    private Long patientId;

    /**
     * 患者信息
     */
    private Map<String,String> patientInfo;

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

