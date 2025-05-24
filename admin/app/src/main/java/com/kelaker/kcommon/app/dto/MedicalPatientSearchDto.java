package com.kelaker.kcommon.app.dto;

import lombok.Data;

/**
 * 病人信息(MedicalPatient)查询实体类
 *
 * @author Felix Huang
 * @since 2025-04-09 10:39:07
 */
@Data
public class MedicalPatientSearchDto {

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 状态
     */
    private String status;
}

