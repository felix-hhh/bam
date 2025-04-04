package com.kelaker.kcommon.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 用户修改拓展资料
 *
 * @author felix huang
 * @since 2021-12-16
 */
@Data
public class UserInfoExtendsChangeDto {
    /**
     * 值
     */
    @NotBlank(message = "值不能为空")
    private String value;

    /**
     * 变更类型
     */
    @NotNull(message = "类型不能为空")
    private Type type;

    public enum Type {
        /**
         * 头像
         */
        HEAD_IMG,
        /**
         * 性别
         */
        GENDER,
        /**
         * 签名
         */
        SIGNATURE
    }

}
