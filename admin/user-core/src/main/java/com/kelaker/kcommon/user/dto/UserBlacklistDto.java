package com.kelaker.kcommon.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserBlacklistDto {
    @NotBlank(message = "拉黑用户不能为空")
    private String blackUserId;
}

