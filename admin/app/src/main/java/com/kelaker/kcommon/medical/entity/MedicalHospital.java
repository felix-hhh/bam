package com.kelaker.kcommon.medical.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * 医院信息(MedicalHospital)表实体类
 *
 * @author Felix Huang
 * @since 2025-04-09 10:21:50
 */
@Data
@TableName("medical_hospital")
public class MedicalHospital extends Model<MedicalHospital> {

    /**
     * 序号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 地址
     */
    private String address;

    /**
     * 定位
     */
    private String location;

    /**
     * 图标
     */
    private String logo;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 医生数量
     */
    private Integer doctorCount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 地图数据
     */
    private String mapData;

    /**
     * 科室
     */
    private Integer room;

    /**
     * 创建时间
     */
    private Date createDatetime;

    /**
     * 状态
     */
    private String status;
}

