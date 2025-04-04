package com.kelaker.kcommon.user.dto;

import lombok.Data;

/**
 * 更换用户密码DTO
 * @author felix huang
 * @since 2021-12-17
 */
@Data
public class ChangeUserPwdDto {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 密码
     */
    private String passwd;
}
