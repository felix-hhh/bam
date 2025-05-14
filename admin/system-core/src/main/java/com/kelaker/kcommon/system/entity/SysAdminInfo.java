package com.kelaker.kcommon.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.Getter;

import java.util.Date;
import java.util.Map;

/**
 * 管理员信息(AdminInfo)表实体类
 *
 * @author felix huang
 * @since 2020-03-27 11:42:56
 */
@Data
@TableName(value = "sys_admin_info", autoResultMap = true)
public class SysAdminInfo extends Model<SysAdminInfo> {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    private String passwd;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDatetime;

    /**
     * 状态（A_I_S_NORMAL = 正常，A_I_S_DISABLE = 禁用）
     */
    private Status status;

    /**
     * 最后登录时间
     */
    private Date lastLoginDatetime;

    /**
     * 微信openId
     */
    private String openId;

    /**
     * 拓展信息
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> extendData;

    @Getter
    public enum Status implements IEnum<String> {
        /**
         * 管理员状态枚举
         */
        DISABLE("A_I_S_DISABLE", "禁用"),
        NORMAL("A_I_S_NORMAL", "启用");

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