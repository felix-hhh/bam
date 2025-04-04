package com.kelaker.kcommon.user.dto;

import com.kelaker.kcommon.user.constant.MemberType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户会员(UserMember)查询实体类
 *
 * @author Felix Huang
 * @since 2023-05-02 22:42:01
 */
@Data
public class UserMemberDto {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 会员类型
     */
    private MemberType memberType;

    /**
     * 会员时间
     */
    private int memberTime;

    /**
     * 花费
     */
    private BigDecimal cost;

    /**
     * 来源
     */
    private String source;

    /**
     * 支付ID
     */
    private String payId;

    /**
     * 会员商品类型
     */
    private String memberTitle;
}

