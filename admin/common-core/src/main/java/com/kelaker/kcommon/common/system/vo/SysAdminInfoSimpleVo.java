package com.kelaker.kcommon.common.system.vo;

import lombok.Data;

@Data
public class SysAdminInfoSimpleVo {

    /**
     * id
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
     * 密码
     */
    private String password;

}
