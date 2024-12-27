package com.kelaker.kcommon.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * (AdminRoleActionLink)实体类
 *
 * @author chym
 * @since 2020-03-31 13:56:46
 */
@Data
@TableName(value = "sys_admin_role_action_link")
public class SysAdminRoleActionLink extends Model<SysAdminRoleActionLink> {

    /**
     * 角色代码
     */
    private Long roleId;
    
    /**
     * 功能代码
     */
    private String actionCode;
    
    /**
     * 创建时间
     */
    private Date createDatetime;
    
    /**
     * 操作人ID
     */
    private Long operateId;
    
}