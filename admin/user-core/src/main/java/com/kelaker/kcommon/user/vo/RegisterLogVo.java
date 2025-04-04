package com.kelaker.kcommon.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * 注册日记
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterLogVo {

    /**
     * ip
     */
    private String ip;
    /**
     * 渠道
     */
    private String channel;
    /**
     * 版本
     */
    private String version;
    
}
