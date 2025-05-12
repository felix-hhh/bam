package com.kelaker.kcommon.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.kelaker.ktools.web.helper.SecurityTypeHelper;
import com.kelaker.ktools.common.utils.ValidateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

/**
 * 用户信息表(UserInfo)实体类
 *
 * @author makejava
 * @since 2021-12-10 10:59:46
 */
@Data
@TableName(value = "user_info", autoResultMap = true)
public class UserInfo extends Model<UserInfo> {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 手机号码
     */
    @TableField(typeHandler = SecurityTypeHelper.class)
    private String phone;

    /**
     * 用户名
     */
    private String username;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String passwd;

    /**
     * 微信openid
     */
    private String wxOpenId;

    /**
     * 苹果OpenId
     */
    private String appleOpenId;

    /**
     * 企鹅OpenId
     */
    private String qqOpenId;

    /**
     * 最后登录时间
     */
    private Date lastLoginDatetime;

    /**
     * 注销时间
     */
    private Date deleteDatetime;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDatetime;

    /**
     * 拓展资料
     */
    private boolean extendsData;

    @TableField(exist = false)
    private String clientType;

    /**
     * 修改用户名时间
     */
    private Date changeUsernameDatetime;

    /**
     * 验证类型
     */
    private ValidateType validateType;

    /**
     * 状态
     */
    private Status status;

    @Getter
    @AllArgsConstructor
    public enum ValidateType implements IEnum<String> {

        REAL_NAME("U_I_V_T_REAL_NAME", "实名认证"),
        REAL_PERSON("U_I_V_T_REAL_PERSON", "实人认证");

        private final String value;

        private final String remark;

        public static ValidateType toEnum(String value) {
            if (ValidateUtil.isBlank(value)) {
                return null;
            }
            for (ValidateType validateType : values()) {
                if (value.equals(validateType.getValue())) {
                    return validateType;
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
    public enum Status implements IEnum<String> {

        ENABLE("U_I_S_NORMAL", "启用"),
        DISABLE("U_I_S_DISABLE", "禁用"),
        DELETE("U_I_S_DELETE", "删除");

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
