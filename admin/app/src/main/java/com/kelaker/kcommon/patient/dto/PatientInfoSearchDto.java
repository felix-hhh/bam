package com.kelaker.kcommon.patient.dto;

import lombok.Data;

import java.util.Date;

/**
 * 就诊人信息表(PatientInfo)查询实体类
 *
 * @author felix huang
 * @since 2024-04-04
 */
@Data
public class PatientInfoSearchDto {

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 性别：0-未知，1-男，2-女
     */
    private Integer gender;

    /**
     * 出生日期
     */
    private Date birthDate;

    /**
     * 是否默认就诊人
     */
    private Boolean isDefault;

    /**
     * 状态
     */
    private String status;
}