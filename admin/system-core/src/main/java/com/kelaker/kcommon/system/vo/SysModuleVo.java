package com.kelaker.kcommon.system.vo;

import lombok.Data;

/**
 * 系统模块Vo
 *
 * @author chym
 * @date 2020/4/1 9:44
 */
@Data
public class SysModuleVo {
    /**
     * 模块代码
     */
    private String moduleCode;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 健康检查地址
     */
    private String healthCheckUrl;

    private int instanceCount;

    /**
     * 模块备注
     */
    private String remark;
}
