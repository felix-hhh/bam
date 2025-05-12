package com.kelaker.kcommon.medical.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.kelaker.ktools.common.utils.ValidateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

/**
 * 员工信息(MedicalDoctor)表实体类
 *
 * @author Felix Huang
 * @since 2025-04-09 10:39:06
 */
@Data
@TableName("medical_doctor")
public class MedicalDoctor extends Model<MedicalDoctor> {

    /**
     * 序号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 医院ID
     */
    private Long hospitalId;

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
    private Status status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDatetime;

    /**
     * 服务次数
     */
    private Integer serviceCount;

    @Getter
    @AllArgsConstructor
    public enum Status implements IEnum<String> {

        ENABLE("M_D_S_NORMAL", "启用"),
        DISABLE("M_D_S_DISABLE", "禁用"),
        DELETE("M_D_S_DELETE", "删除");

        private final String value;

        private final String remark;

        public static Status toEnum(String value) {
            if (ValidateUtil.isBlank(value)) {
                return null;
            }
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
