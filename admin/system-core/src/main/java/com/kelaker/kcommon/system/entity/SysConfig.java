package com.kelaker.kcommon.system.entity;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 系统配置表(SysConfig)表实体类
 *
 * @author felix huang
 * @since 2020-03-27 11:42:57
 */
@Data
@TableName(value = "sys_config")
public class SysConfig extends Model<SysConfig> {

    /**
     * 配置值
     */
    @TableId(type = IdType.INPUT)
    private String configKey;

    /**
     * 配置名称
     */
    private String configKeyName;


    /**
     * 配置值
     */
    private String configValue;

    /**
     * 备注
     */
    private String remark;

    /**
     * 启用/禁用状态
     */
    private Status status;

    /**
     * 分组
     */
    private String groupCode;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 是否允许前端调用
     */
    private Boolean frontView;

    /**
     * 是否免登录
     */
    private Boolean publicKey;

    /**
     * 类型
     */
    private Type type;

    /**
     * 系统ID
     */
    private Long systemId;

    @Getter
    @AllArgsConstructor
    public enum Status implements IEnum<String> {
        /**
         * 禁用
         */
        DISABLE("S_C_S_DISABLE", "禁用"),
        /**
         * 启用
         */
        NORMAL("S_C_S_NORMAL", "启用");

        private final String value;

        private final String remark;


        @Override
        public String toString() {
            return this.value;
        }

        public static Status toEnum(String value) {
            for (Status status : values()) {
                if (status.getValue().equals(value)) {
                    return status;
                }
            }
            return null;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum Type implements IEnum<String> {

        /**
         * 数值
         */
        NUMBER("number", "数值"),
        /**
         * 布尔型
         */
        BOOLEAN("boolean", "布尔"),
        /**
         * 字符串型
         */
        STRING("string", "字符串");

        private final String value;

        private final String remark;

        public static Type toEnum(String value) {
            for (Type type : values()) {
                if (type.getValue().equals(value)) {
                    return type;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }


}