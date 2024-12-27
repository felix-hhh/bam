package com.kelaker.kcommon.system.dto;

import lombok.Data;

import java.util.Date;

/**
 * 管理员日志请求
 */
@Data
public class SysAdminLogRequestDto {

    /**
     * 查询关键字(操作员，角色，操作)
     */
    private String key;

    /**
     * 开始日期
     */
    private Date startDate;

    /**
     * 结束日期
     */
    private Date endDate;

    /**
     * 请求参数
     */
    private String requestData;

}