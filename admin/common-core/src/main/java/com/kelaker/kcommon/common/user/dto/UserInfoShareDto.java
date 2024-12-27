package com.kelaker.kcommon.common.user.dto;

import lombok.Data;

import java.util.Set;

/**
 * 批量查询用户资料请求DTO
 */
@Data
public class UserInfoShareDto {

    /**
     * 用户ID数组
     */
    Set<Long> userIds;
}
