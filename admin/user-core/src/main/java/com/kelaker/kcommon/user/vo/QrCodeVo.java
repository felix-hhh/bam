package com.kelaker.kcommon.user.vo;

import lombok.Data;

import java.util.Date;

/**
 *  二维码
 * @author lfz
 * @since 2022-07-28 14:28:03
 */

@Data
public class QrCodeVo {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 验证码
     */
    private String authCode;

    /**
     * 创建时间
     */
    private Date createDatetime;
}

