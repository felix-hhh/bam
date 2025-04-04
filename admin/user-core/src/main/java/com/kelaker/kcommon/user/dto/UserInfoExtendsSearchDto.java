package com.kelaker.kcommon.user.dto;

import lombok.Data;

/**
 * 查询用户拓展信息DTO
 * @author felix huang
 * @since 2021-12-18
 */
@Data
public class UserInfoExtendsSearchDto {

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户名
     */
    private String username;

    /**
     * 标签
     */
    private String tag;
}
