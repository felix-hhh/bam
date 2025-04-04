package com.kelaker.kcommon.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class UserRelactionshipQueryDto {

    @NotNull(message = "用户id不能为空")
    private Set<String> userIds;

}
