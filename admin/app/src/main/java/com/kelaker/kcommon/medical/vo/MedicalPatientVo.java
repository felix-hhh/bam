package com.kelaker.kcommon.medical.vo;

import lombok.Data;

import java.util.Date;

/**
 * 病人信息(MedicalPatient)表实体类
 *
 * @author Felix Huang
 * @since 2025-04-09 10:39:07
 */
@Data
public class MedicalPatientVo {

    /**
     * 序号
     */
    private Long id;

    /**
     * 问诊编号
     */
    private String medicalNum;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 性别
     */
    private int gender;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 默认就诊人
     */
    private Boolean defaultPatient;

    /**
     * 数据归属用户ID
     */
    private Long userId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 生日
     */
    private int age;

    /**
     * 上一次就诊时间
     */
    private Date lastTreatDatetime;

    /**
     * 就诊次数
     */
    private int treatCount;

    /**
     * 与账户人关系
     */
    private String relation;

    /**
     * 关系名称
     */
    private String relationStr;

    /**
     * 数据创建时间
     */
    private Date createDatetime;

    /**
     * 状态
     */
    private String status;

    /**
     * 状态名称
     */
    private String statusStr;
}
