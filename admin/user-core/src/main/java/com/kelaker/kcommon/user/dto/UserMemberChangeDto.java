package com.kelaker.kcommon.user.dto;

import com.kelaker.kcommon.user.constant.MemberType;
import lombok.Data;

@Data
public class UserMemberChangeDto {
    /**
     * 会员类型
     */
    private MemberType memberType;

    /**
     * 增加时间
     */
    private int memberTime;
}
