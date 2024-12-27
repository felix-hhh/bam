package com.kelaker.kcommon.system.vo;

import lombok.Data;

import java.util.Date;

/**
 * 管理员日志请求
 */
@Data
public class SysAdminLogVo {

    private Long id;

    /**
     * 操作人
     */
    private Long infoId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 操作
     */
    private String type;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createDatetime;

    /**
     * 请求地址
     */
    private String requestUri;

    /**
     * 请求数据
     */
    private String requestData;

    /**
     * 响应数据
     */
    private String responseData;

}