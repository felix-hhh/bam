package com.kelaker.kcommon.system.dto;

import lombok.Data;

/**
 * 系统模块更新D
 * @author chym
 * @date 2020/4/18 10:43
 */
@Data
public class SysModuleUpdateDto {
    /**
     * 模块代码
     */
    private String moduleCode;

    /**
     * 新的模块名称
     */
    private String newModuleName;

    /**
     * 新的备注
     */
    private String newRemark;
}
