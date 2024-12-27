package com.kelaker.kcommon.common.assets.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class AssetsRechargePayDto {
    /**
     * 商品ID
     */
    @NotBlank(message = "订单ID不能为空")
    private String orderId;

    @NotNull(message = "价值")
    private BigDecimal price;

    /**
     * 支付币种
     */
    private String coinId;

    /**
     * 订单备注信息
     */
    private String remark;

    /**
     * 其他参数
     */
    private Map<String, Object> orderParam;
}
