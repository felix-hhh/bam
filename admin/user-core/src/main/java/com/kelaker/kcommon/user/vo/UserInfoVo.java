package com.kelaker.kcommon.user.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * 用户信息对象
 *
 * @author felix huang
 * @since 2021-12-13
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoVo {

    /**
     * id
     */
    private String id;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 最后登录时间
     */
    private Date lastLoginDatetime;

    /**
     * 删除时间
     */
    private Date deleteDatetime;

    /**
     * 创建时间
     */
    private Date createDatetime;

    /**
     * 是否完善数据
     */
    private boolean extendsData;

    /**
     * 状态
     */
    private String status;

    /**
     * 状态说明
     */
    private String statusStr;

    /**
     * 更改用户名状态
     */
    private boolean usernameStatus;

}
