package com.kelaker.kcommon.user.dto;

import lombok.Data;

import java.util.Date;

/**
 * 用户查询对象
 *
 * @author felix huang
 * @since 2021-12-13
 */
@Data
public class UserInfoSearchDto {

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * id
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 创建时间
     */
    private Date startCreateDatetime;

    /**
     * 结束时间
     */
    private Date endCreateDatetime;

    /**
     * 关键字：手机号码，昵称，用户名
     */
    private String keyword;
}
