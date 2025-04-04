package com.kelaker.kcommon.user.constant;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 会员类型枚举
 */
@Getter
@AllArgsConstructor
public enum MemberType implements IEnum<String> {

    /**
     * VIP
     */
    VIP("M_T_VIP", "会员"),
    /**
     * SVIP
     */
    SVIP("M_T_SVIP", "黄金会员");

    private final String value;

    private final String remark;

    public static MemberType toEnum(String value) {
        for (MemberType memberType : values()) {
            if (value.equals(memberType.getValue())) {
                return memberType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}
