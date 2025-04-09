package com.kelaker.kcommon.medical.dto;

import java.util.Date;

import lombok.Data;

/**
 * 员工信息(MedicalDoctor)查询实体类
 *
 * @author Felix Huang
 * @since 2025-04-09 10:21:50
 */
@Data
public class MedicalDoctorDto {

    /**
     * 序号
     */
    private Integer id;

    /**
     * 医院ID
     */
    private Integer hospitalId;

    /**
     * 医生姓名
     */
    private String name;

    /**
     * 照片
     */
    private String photo;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 角色
     */
    private String roleName;

    /**
     * 管理员ID
     */
    private Integer adminId;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createDatetime;

    /**
     * 服务次数
     */
    private Integer serviceCount;
}

