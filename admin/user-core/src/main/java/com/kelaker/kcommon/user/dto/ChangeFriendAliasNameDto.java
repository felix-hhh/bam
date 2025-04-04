package com.kelaker.kcommon.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 更换好友备注名称DTO
 * @author felix huang
 * @since 2021-12-18
 */
@Data
public class ChangeFriendAliasNameDto {

    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private String userId;

    /**
     * 备注
     */
    @NotBlank(message = "备注不能为空")
    private String aliasName;
}
