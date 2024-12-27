package com.kelaker.kcommon.system.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;


/**
 * 系统配置
 * @author felix huang
 * @since 2020/6/28
 */
@Data
public class SysConfigDto {

    /**
     * 配置key
     */
    @NotBlank(message = "key值不能为空")
    private String configKey;

    /**
     * 配置名称
     */
    private String configKeyName;

    /**
     * 配置值
     */
    @NotBlank(message = "值不能为空")
    private String configValue;

    /**
     * 备注
     */
    private String remark;

    /**
     * 分组
     */
    @NotBlank(message = "分组不能为空")
    private String groupCode;

    /**
     * 是否允许前端调用
     */
    private boolean frontView;

    /**
     * 是否免登录
     */
    private boolean publicKey;

    /**
     * 配置类型(string-字符串 | boolean-布尔 | number-数值)
     */
    @NotBlank(message = "类型不能为空")
    private String type;
}