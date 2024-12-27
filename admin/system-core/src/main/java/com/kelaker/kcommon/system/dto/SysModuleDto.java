package com.kelaker.kcommon.system.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 系统模块Dto
 *
 * @author Felix Huang
 * @since 2020/4/18 10:43
 */
@Data
public class SysModuleDto {

    /**
     * 模块代码
     */
    @NotBlank(message = "模块代码不能为空")
    private String moduleCode;

    /**
     * 模块名称
     */
    @NotBlank(message = "模块名称不能为空")
    private String moduleName;

    /**
     * 备注
     */
    private String remark;
}
