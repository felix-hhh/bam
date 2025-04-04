package com.kelaker.kcommon.user.vo;

import lombok.Data;

/**
 * 注销用户缓存vo
 */
@Data
public class UserCancelCacheVo {
    /**
     * 手机号
     */
    private String phone;

    /**
     * wx openid
     */
    private String wxOpenId;

    /**
     * apple openid
     */
    private String appleOpenId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 授权码
     */
    private int authCode;

}
