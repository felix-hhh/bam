package com.kelaker.kcommon.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * 管理员日志(AdminLog)表实体类
 *
 * @author felix huang
 * @since 2020-03-27 11:42:56
 */
@Data
@TableName("sys_admin_log")
public class SysAdminLog extends Model<SysAdminLog> {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
    * 操作人
    */
    @JsonProperty
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
    @TableField(fill = FieldFill.INSERT)
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