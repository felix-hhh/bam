package com.kelaker.kcommon.user.dto;

import lombok.Data;

/**
 * 第三方登录用户信息实体
 */
@Data
public class OtherLoginUserInfoDto {

    private String name;

    private String headImage;

    private String email;

    private String openId;
}