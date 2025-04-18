package com.kelaker.kcommon.medical.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

/**
 * 病人信息(MedicalPatient)表实体类
 *
 * @author Felix Huang
 * @since 2025-04-09 10:39:07
 */
@Data
@TableName("medical_patient")
public class MedicalPatient extends Model<MedicalPatient> {

    /**
     * 序号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 性别
     */
    private int gender;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 默认就诊人
     */
    private Boolean defaultPatient;

    /**
     * 数据归属用户ID
     */
    private Long userId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 上一次就诊时间
     */
    private Date lastTreatDatetime;

    /**
     * 就诊次数
     */
    private int treatCount;

    /**
     * 与账户人关系
     */
    private Relation relation;

    /**
     * 数据创建时间
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

        ENABLE("M_P_S_NORMAL", "启用"),
        DISABLE("M_P_S_DISABLE", "禁用"),
        DELETE("M_P_S_DELETE", "删除");

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

    @Getter
    @AllArgsConstructor
    public enum Relation implements IEnum<String> {

        SELF("M_P_R_SELF", "本人"),
        PARENT("M_P_R_PARENT", "父母"),
        CHILD("M_P_R_CHILD", "子女"),
        FRIEND("M_P_R_FRIEND", "朋友"),
        RELATIVE("M_P_R_RELATIVE", "亲属"),
        OTHER("M_P_R_OTHER", "其他");

        private final String value;

        private final String remark;

        public static Relation toEnum(String value) {
            for (Relation relation : values()) {
                if (value.equals(relation.getValue())) {
                    return relation;
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
