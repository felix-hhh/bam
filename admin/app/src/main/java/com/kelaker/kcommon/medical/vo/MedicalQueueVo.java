package com.kelaker.kcommon.medical.vo;

import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * 队列(MedicalQueue)表实体类
 *
 * @author Felix Huang
 * @since 2025-04-09 10:39:08
 */
@Data
public class MedicalQueueVo {

    /**
     * 序号
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 医院ID
     */
    private Long hospitalId;

    /**
     * 医院名字
     */
    private String hospitalName;

    /**
     * 医院电话
     */
    private String hospitalPhone;

    /**
     * 医生ID
     */
    private Long doctorId;

    /**
     * 医生名字
     */
    private String doctorName;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 患者名字
     */
    private String patientName;

    /**
     * 患者性别
     */
    private int patientGender;

    /**
     * 患者性别说明
     */
    private String patientGenderStr;

    /**
     * 与账户人关系
     */
    private String patientRelation;

    /**
     * 与账户人关系说明
     */
    private String patientRelationStr;

    /**
     * 患者信息
     */
    private Map<String, String> patientInfo;

    /**
     * 检查项目
     */
    private String checkItem;

    /**
     * 检查项目说明
     */
    private String checkItemStr;

    /**
     * 创建时间
     */
    private Date createDatetime;

    /**
     * 状态
     */
    private String status;

    /**
     * 状态说明
     */
    private String statusStr;

    /**
     * 队列号码
     */
    private String queueNum;

    /**
     * 当前号码
     */
    private Long currentNum;

    /**
     * 诊断时间
     */
    private Date diagnosticTime;

    /**
     * 是否有诊断报告
     */
    private boolean record;

    /**
     * 是否有评估报告
     */
    private boolean report;

    /**
     * 是否有运动视频
     */
    private boolean video;
}
