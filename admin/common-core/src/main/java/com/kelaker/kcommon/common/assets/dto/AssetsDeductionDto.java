package com.kelaker.kcommon.common.assets.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class AssetsDeductionDto {

    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private String userId;

    /**
     * 币种ID
     */
    private String coinId;

    /**
     * 变动金额
     */
    @NotNull(message = "变动金额不能为空")
    private BigDecimal amount;

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
    private String businessId;

    /**
     * 备注
     */
    private String remark;
}
