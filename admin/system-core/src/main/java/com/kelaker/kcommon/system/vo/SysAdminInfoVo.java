package com.kelaker.kcommon.system.vo;

import lombok.Data;

import java.util.Date;

/**
 * 管理员信息Vo
 *
 * @author chym
 * @since  2020/4/10 14:57
 */
@Data
public class SysAdminInfoVo {

    /**
     * 用户Id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 用户状态
     */
    private String status;

    /**
     * 用户状态字符串
     */
    private String statusStr;

    /**
     * 用户角色
     */
    private String roles;

    /**
     * 所属部门
     */
    private String department;

    /**
     * 所属公司
     */
    private String company;

    /**
     * 注册时间
     */
    private Date createDatetime;

    /**
     * 最后一次登录时间
     */
    private Date lastLoginDatetime;
}
