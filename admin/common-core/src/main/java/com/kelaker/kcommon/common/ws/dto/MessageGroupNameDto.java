package com.kelaker.kcommon.common.ws.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

/**
 * 修改群名称
 */
@Data
public class MessageGroupNameDto {

    /**
     * 群ID
     */
    @NotBlank(message = "群ID不能为空")
    private String groupId;

    /**
     * 群名称
     */
    @NotBlank(message = "群名称不能为空")
    @Pattern(regexp = "^\\w*|\\W*|[\\u4e00-\\u9fa5]{3,10}$",message = "群名称长度为3-10个字符")
    private String groupName;
}
