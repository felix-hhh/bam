package com.kelaker.kcommon.medical.dto;

import java.util.Date;

import lombok.Data;

/**
 * 病人信息(MedicalPatient)查询实体类
 *
 * @author Felix Huang
 * @since 2025-04-09 10:21:51
 */
@Data
public class MedicalPatientDto {

    /**
     * 序号
     */
    private Integer id;

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
    private Boolean gender;

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
    private Integer userId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 数据创建时间
     */
    private Date createDatetime;

    /**
     * 状态
     */
    private String status;
}

