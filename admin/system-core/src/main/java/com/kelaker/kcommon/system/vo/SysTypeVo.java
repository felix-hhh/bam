package com.kelaker.kcommon.system.vo;

import lombok.Data;

/**
 * 系统类型
 */
@Data
public class SysTypeVo {

    /**
     * 类型代码
     */
    private String typeCode;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 备注
     */
    private String remark;
}
