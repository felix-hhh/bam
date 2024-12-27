package com.kelaker.kcommon.common.assets.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 变更指定资产到指定账户
 */
@Data
public class AssetsChangeToDto {

    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private String userId;

    /**
     * 变动金额
     */
    @NotNull(message = "变动金额不能为空")
    private BigDecimal amount;

    /**
     * 币种ID
     */
    @NotBlank(message = "币种ID不能为空")
    private String coinId;

    /**
     * 出入帐（true-入账，false-出账
     */
    private boolean income;

    /**
     * 业务代码
     */
    @NotBlank(message = "交易代码不能为空")
    private String dealCode;

    /**
     * 业务ID
     */
    @NotBlank(message = "业务ID不能为空")
    private String businessId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 目标备注
     */
    private String targetRemark;

    /**
     * 目标用户ID
     */
    @NotBlank(message = "目标用户ID")
    private String targetUserId;

    /**
     * 目标金额
     */
    @NotNull(message = "目标金额不能为空")
    private BigDecimal targetAmount;

    /**
     * 目标币种
     */
    @NotBlank(message = "目标币种不能为空")
    private String targetCoinId;

    private String extendsData;
}
