package com.kelaker.kcommon.user.vo;

import lombok.Data;

@Data
public class UserInfoShareVo {

    /**
     * id
     */
    private String id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像地址
     */
    private String headImgPath;
    /**
     * 签名
     */
    private String signature;

}
