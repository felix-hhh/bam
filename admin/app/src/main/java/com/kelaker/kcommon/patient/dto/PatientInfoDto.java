package com.kelaker.kcommon.patient.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

/**
 * 就诊人信息表(PatientInfo)数据传输对象
 *
 * @author felix huang
 * @since 2024-04-04
 */
@Data
public class PatientInfoDto {

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
    @NotBlank(message = "姓名不能为空")
    private String name;

    /**
     * 身份证号
     */
    @NotBlank(message = "身份证号不能为空")
    private String idCard;

    /**
     * 手机号码
     */
    @NotBlank(message = "手机号码不能为空")
    private String phone;

    /**
     * 性别：0-未知，1-男，2-女
     */
    @NotNull(message = "性别不能为空")
    private Integer gender;

    /**
     * 出生日期
     */
    @NotNull(message = "出生日期不能为空")
    private Date birthDate;

    /**
     * 是否默认就诊人
     */
    private Boolean isDefault;
}