package com.kelaker.kcommon.common.ws.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class MessageGroupChangeDto {
    /**
     * 群ID
     */
    @NotBlank(message = "群ID不能为空")
    private String groupId;

    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private String userId;
}
