package com.kelaker.kcommon.medical.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 病人信息(MedicalPatient)查询实体类
 *
 * @author Felix Huang
 * @since 2025-04-09 10:39:07
 */
@Data
public class MedicalPatientDto {

    /**
     * 序号
     */
    private Long id;

    @NotBlank(message ="姓名不能为空")
    /**
     * 姓名
     */
    private String name;

    @NotBlank(message = "身份证号不能为空")
    /**
     * 身份证
     */
    private String idCard;

    /**
     * 性别
     */
    private int gender;

    @NotBlank(message = "手机号不能为空")
    /**
     * 手机号码
     */
    private String phone;

    /**
     * 默认就诊人
     */
    private Boolean defaultPatient;

    /**
     * 生日
     */
    private int age;

    /**
     * 就诊人关系
     */
    @NotBlank(message = "就诊人关系不能为空")
    private String relation;

}

