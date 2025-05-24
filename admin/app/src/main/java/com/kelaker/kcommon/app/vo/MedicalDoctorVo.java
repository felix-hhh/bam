package com.kelaker.kcommon.app.vo;

import lombok.Data;

import java.util.Date;

/**
 * 员工信息(MedicalDoctor)表实体类
 *
 * @author Felix Huang
 * @since 2025-04-09 10:39:06
 */
@Data
public class MedicalDoctorVo {

    /**
     * 序号
     */
    private Integer id;

    /**
     * 医院ID
     */
    private Integer hospitalId;

    /**
     * 医院名称
     */
    private String hospitalName;

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

    private String statusStr;

    /**
     * 创建时间
     */
    private Date createDatetime;

    /**
     * 服务次数
     */
    private Integer serviceCount;
}

