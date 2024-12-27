package com.kelaker.kcommon.common.user.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * 用户完整资料
 *
 * @author felix huang
 * @since 2021-12-15
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserInfoAllVo {

    private Long id;

    private String phone;

    private String username;

    private String nickname;

    /**
     * 头像图片路径
     */
    private String headImgPath;

    /**
     * 性别
     */
    private Integer gender;


    /**
     * 最后登录时间
     */
    private Date lastLoginDatetime;

    /**
     * 创建时间
     */
    private Date createDatetime;

    /**
     * 是否绑定微信
     */
    private boolean bindWxOpenId;

    /**
     * 是否绑定苹果ID
     */
    private boolean bindAppleOpenId;

    /**
     * 是否绑定QQ
     */
    private boolean bindQqOpenId;

    /**
     * 是否绑定手机
     */
    private boolean bindPhone;


    /**
     * 修改过用户名
     */
    private boolean changedUsername;

    /**
     * 是否在线
     */
    private boolean online;

    /**
     * 签名
     */
    private String signature;

    /**
     * 用户角色
     */
    private String userRole;

    /**
     * 用户角色字符串
     */
    private String userRoleStr;

    /**
     * 状态
     */
    private String status;

    /**
     * 状态字符串
     */
    private String statusStr;

    /**
     * 认证类型
     */
    private String validateType;

    /**
     * 认证类型字符串
     */
    private String validateTypeStr;

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
     * 是否开通直播
     */
    private boolean live;

    /**
     * 有店铺
     */
    private boolean hasShop;

    /**
     * 认证等级
     */
    private int authLevel;

    /**
     * 会员类型
     */
    private String memberType;

    /**
     * 会员到期时间
     */
    private Date memberTime;

    /**
     * 拓展资料
     */
    private boolean extendsData;
}
