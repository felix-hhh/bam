package com.kelaker.kcommon.system.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 系统类型
 */
@Data
public class SysTypeDto {

    /**
     * 类型代码
     */
    @NotBlank(message = "类型代码不能为空")
    private String typeCode;

    /**
     * 类型名称
     */
    @NotBlank(message = "类型名称不能为空")
    private String typeName;

    /**
     * 备注
     */
    private String remark;

}
