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
 * 用户登录日志(UserLoginLog)表实体类
 *
 * @author Felix Huang
 * @since 2022-08-23 14:23:29
 */
@Data
@TableName("user_login_log")
public class UserLoginLog extends Model<UserLoginLog> {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * ip地址
     */
    @TableField(typeHandler = SecurityTypeHelper.class)
    private String ip;

    private String localProvince;

    private String localCode;

    private String localCity;

    /**
     * 备注
     */
    private String remark;

    /**
     * 设备品牌
     */
    private String deviceBrand;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 设备型号
     */
    private String deviceModel;

    /**
     * 登录类型
     */
    private Type type;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date createDatetime;

    @Getter
    @AllArgsConstructor
    public enum Type implements IEnum<String> {

        WXMIN("U_L_L_T_WXMIN", "微信小程序登录"),
        WX("U_L_L_T_WX", "微信一键登录"),
        SMS("U_L_L_T_SMS", "短信登录"),
        ONECLICK("U_L_L_T_ONECLICK", "手机号一键登录"),
        APPLEID("U_L_L_T_APPLEID", "苹果ID一键登录"),
        PWD("U_L_L_T_PWD", "密码登录");

        private final String value;

        private final String remark;

        public static Type toEnum(String value) {
            if (ValidateUtil.isBlank(value)) {
                return null;
            }
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
