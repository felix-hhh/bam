package com.kelaker.kcommon.system.vo;

import lombok.Data;

@Data
public class SysModuleInstanceVo {

    private String Id;

    /**
     * 实例IP
     */
    private String ip;

    /**
     * 实例端口.
     */
    private int port;

    /**
     * 实例健康状态
     */
    private boolean healthy;
}
