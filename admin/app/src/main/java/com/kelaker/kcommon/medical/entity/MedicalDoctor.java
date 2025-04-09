package com.kelaker.kcommon.medical.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * 员工信息(MedicalDoctor)表实体类
 *
 * @author Felix Huang
 * @since 2025-04-09 10:21:50
 */
@Data
@TableName("medical_doctor")
public class MedicalDoctor extends Model<MedicalDoctor> {

    /**
     * 序号
     */
    @TableId(type = IdType.AUTO)
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

