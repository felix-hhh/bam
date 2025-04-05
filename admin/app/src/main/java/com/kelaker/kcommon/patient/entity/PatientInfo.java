package com.kelaker.kcommon.patient.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.kelaker.ktools.web.helper.SecurityTypeHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

/**
 * 就诊人信息表(Patient)实体类
 *
 * @author felix huang
 * @since 2024-04-04
 */
@Data
@TableName(value = "patien_info", autoResultMap = true)
public class PatientInfo extends Model<PatientInfo> {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号
     */
    @TableField(typeHandler = SecurityTypeHelper.class)
    private String idCard;

    /**
     * 手机号码
     */
    @TableField(typeHandler = SecurityTypeHelper.class)
    private String phone;

    /**
     * 性别：0-未知，1-男，2-女
     */
    private Integer gender;

    /**
     * 出生日期
     */
    private Date birthDate;

    /**
     * 是否默认就诊人
     */
    private Boolean isDefault;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDatetime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDatetime;

    /**
     * 状态
     */
    private Status status;

    @Getter
    @AllArgsConstructor
    public enum Status implements IEnum<String> {

        ENABLE("P_S_NORMAL", "启用"),
        DISABLE("P_S_DISABLE", "禁用"),
        DELETE("P_S_DELETE", "删除");

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
