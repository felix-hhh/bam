package com.kelaker.kcommon.user.vo;

import lombok.Data;

/**
 * 加好友
 * 查询用户
 */
@Data
public class SearchUserVo {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 用户名
     */
    private String username;

    /**
     * 头像地址
     */
    private String headImgPath;
}
