package com.kelaker.kcommon.user.vo;

import lombok.Data;

import java.util.Date;

/**
 * 用户会员(UserMember)表实体类
 *
 * @author Felix Huang
 * @since 2023-05-02 22:42:01
 */
@Data
public class UserMemberVo {
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 会员类型
     */
    private String memberType;

    /**
     * 会员时间
     */
    private Long memberTime;

    /**
     * 创建时间
     */
    private Date createDatetime;

    /**
     * 花费
     */
    private Double cost;

    /**
     * 状态
     */
    private String status;

    /**
     * 来源
     */
    private String source;

    /**
     * 开始时间
     */
    private Date startDatetime;

    /**
     * 结束时间
     */
    private Date endDatetime;

    /**
     * 支付ID
     */
    private String payId;

    /**
     * 会员商品类型
     */
    private String memberTitle;
}

