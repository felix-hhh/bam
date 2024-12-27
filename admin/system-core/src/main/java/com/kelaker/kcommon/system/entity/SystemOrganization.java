package com.kelaker.kcommon.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 组织架构(SystemOrganization)表实体类
 *
 * @author Felix Huang
 * @since 2024-09-02 12:41:01
 */
@Data
@TableName("system_organization")
public class SystemOrganization extends Model<SystemOrganization> {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 组织代码
     */
    private String code;

    /**
     * 组织类型
     */
    private Type type;

    /**
     * 排序
     */
    private Long orderNum;

    /**
     * 状态
     */
    private Status status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 父级部门ID
     */
    private Long parentId;

    @Getter
    @AllArgsConstructor
    public enum Status implements IEnum<String> {

        NORMAL("S_O_S_NORMAL", "启用"),
        DISABLE("S_O_S_DISABLE", "禁用");

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
    public enum Type implements IEnum<String> {

        ENABLE("S_O_T_ORGANIZATION", "组织"),
        DISABLE("S_O_T_DEPARTMENT", "部门");

        private final String value;

        private final String remark;

        public static Type toEnum(String value) {
            for (Type type : values()) {
                if (value.equals(type.getValue())) {
                    return type;
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

