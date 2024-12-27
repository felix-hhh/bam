package com.kelaker.kcommon.common.assets.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 赠送礼物DTO
 *
 * @author felix huang
 * @since 2021-02-09
 */
@Data
public class AssetsProductSendDto {
    /**
     * 商品ID
     */
    @NotBlank(message = "商品ID不能为空")
    private String productId;

    /**
     * 发送用户ID
     */
    @NotBlank(message = "发送用户ID不能为空")
    private String userId;

    private String username;

    /**
     * 接收用户ID
     */
    @NotBlank(message = "接收用户ID不能为空")
    private String receiveUserId;

    private String receiveUsername;


    private String dealCode;

    private String remark;

    /**
     * 送礼数量
     */
    private int count;
}
