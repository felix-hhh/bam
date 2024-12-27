package com.kelaker.kcommon.common.assets.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ChangeAssetsVo {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 变动金额
     */
    private BigDecimal amount;

    /**
     * 币种ID
     */
    private String coinId;

    /**
     * 业务代码
     */
    private String dealCode;

    /**
     * 业务ID
     */
    private String businessId;

    /**
     * 目标用户ID
     */
    private String targetUserId;

    /**
     * 目标金额
     */
    private BigDecimal targetAmount;

    /**
     * 目标币种
     */
    private String targetCoinId;

    private Map<String,Object> productData;

}
