package com.kelaker.kcommon.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

/**
 * 管理员角色表(AdminRole)表实体类
 *
 * @author felix huang
 * @since 2020-03-27 11:42:56
 */
@Data
@TableName(value = "sys_admin_role")
public class SysAdminRole extends Model<SysAdminRole> {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String roleName;

    private Status status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDatetime;

    /**
     * 人数
     */
    private int userCount;

    @Getter
    public enum Status implements IEnum<String> {
        /**
         * 角色状态枚举
         */
        DISABLE("A_R_S_DISABLE","禁用"),
        NORMAL("A_R_S_NORMAL", "启用");

        Status(String value, String remark) {
            this.value = value;
            this.remark = remark;
        }

        private final String value;
        private final String remark;
        @Override
        public String toString() {
            return value;
        }
    }

}