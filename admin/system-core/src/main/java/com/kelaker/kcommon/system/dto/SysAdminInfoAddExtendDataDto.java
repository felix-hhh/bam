package com.kelaker.kcommon.system.dto;

import lombok.Data;

import java.util.Map;

/**
 * 管理员拓展参数
 */
@Data
public class SysAdminInfoAddExtendDataDto {
    /**
     * 管理员ID
     */
    private Long adminId;

    /**
     * 拓展参数
     */
    private Map<String, Object> extendData;
}
