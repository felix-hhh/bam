package com.kelaker.kcommon.medical.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

/**
 * 医院信息(MedicalHospital)表实体类
 *
 * @author Felix Huang
 * @since 2025-04-09 10:39:07
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
    @TableField(fill = FieldFill.INSERT)
    private Date createDatetime;

    /**
     * 状态
     */
    private Status status;

    @Getter
    @AllArgsConstructor
    public enum Status implements IEnum<String> {

        ENABLE("M_H_S_NORMAL", "启用"),
        DISABLE("M_H_S_DISABLE", "禁用"),
        DELETE("M_H_S_DELETE", "删除");

        private final String value;

        private final String remark;

        public static Status toEnum(String value) {
            for (Status status : values()) {
                if (value.equals(status.getValue())) {
                    return status;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
