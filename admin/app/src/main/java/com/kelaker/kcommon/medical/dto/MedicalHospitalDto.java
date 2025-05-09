package com.kelaker.kcommon.medical.dto;

import java.util.Date;

import lombok.Data;

/**
 * 医院信息(MedicalHospital)查询实体类
 *
 * @author Felix Huang
 * @since 2025-04-09 10:39:07
 */
@Data
public class MedicalHospitalDto {

    /**
     * 序号
     */
    private Long id;

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
    private Long doctorCount;

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
    private Long room;

    /**
     * 创建时间
     */
    private Date createDatetime;

    /**
     * 状态
     */
    private String status;
}

