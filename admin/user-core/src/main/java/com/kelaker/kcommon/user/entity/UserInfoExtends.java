package com.kelaker.kcommon.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.kelaker.kcommon.user.constant.MemberType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

/**
 * 用户拓展(UserInfoExtends)实体类
 *
 * @author felix huang
 * @since 2021-12-14 21:26:35
 */
@Data
@TableName("user_info_extends")
public class UserInfoExtends extends Model<UserInfoExtends> {

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private Long id;

    /**
     * 头像图片路径
     */
    private String headImgPath;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDatetime;

    /**
     * 修改时间
     */
    private Date modifyDatetime;

    /**
     * 管理员标签
     */
    private String adminTag;

    /**
     * 机器标签
     */
    private String machineTag;

    /**
     * 签名
     */
    private String signature;


    /**
     * 性别
     */
    private Integer gender;

    /**
     * 是否开通直播
     */
    private boolean live;

    /**
     * 有店铺
     */
    private boolean hasShop;

    /**
     * 用户角色
     */
    private UserRole userRole;

    /**
     * 城市
     */
    private String localCity;

    /**
     * 城市代码
     */
    private String localCode;

    /**
     * 省份
     */
    private String localProvince;

    /**
     * 认证等级
     */
    private int authLevel;

    /**
     * 实名认证
     */
    private String realName;

    /**
     * 身份证号码
     */
    private String idCardNo;

    /**
     * 会员类型
     */
    private MemberType memberType;

    /**
     * 会员到期时间
     */
    private Date memberTime;


    @Getter
    @AllArgsConstructor
    public enum UserRole implements IEnum<String> {

        NORMAL("U_I_U_R_NORMAL", "普通"),
        TEST("U_I_U_R_TEST", "测试用户"),
        ROBOT("U_I_U_R_ROBOT", "机器人"),
        SYSTEM("U_I_U_R_SYSTEM", "系统用户");

        private final String value;

        private final String remark;

        public static UserRole toEnum(String value) {
            for (UserRole userRole : values()) {
                if (value.equals(userRole.getValue())) {
                    return userRole;
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
