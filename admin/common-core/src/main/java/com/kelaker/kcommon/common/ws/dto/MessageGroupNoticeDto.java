package com.kelaker.kcommon.common.ws.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 修改群名称
 */
@Data
public class MessageGroupNoticeDto {

    /**
     * 群ID
     */
    @NotBlank(message = "群ID不能为空")
    private String groupId;

    /**
     * 群名称
     */
    @NotBlank(message = "群公告不能为空")
    private String notice;
}
