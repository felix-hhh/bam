package com.kelaker.kcommon.system.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 系统功能Dto
 *
 * @author Felix Huang
 * @since 2020/3/31 10:17
 */
@Data
public class SysActionDto {

    /**
     * 功能代码
     */
    @NotBlank(message = "权限代码不能为空")
    private String actionCode;

    /**
     * 功能名称
     */
    @NotBlank(message = "权限名称不能为空")
    private String actionName;

    /**
     * 功能代码
     */
    @NotBlank(message = "模块代码不能为空")
    private String moduleCode;

    /**
     * 备注
     */
    private String remark;

    /**
     * 请求地址
     */
    @NotBlank(message = "请求地址不能为空")
    private String url;
}