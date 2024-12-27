package com.kelaker.kcommon.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * (AdminInfoRoleLink)实体类
 *
 * @author chym
 * @since 2020-03-31 13:56:14
 */
@Data
@TableName(value = "sys_admin_info_role_link")
public class SysAdminInfoRoleLink extends Model<SysAdminInfoRoleLink> {

    /**
     * 管理员ID
     */
    private Long infoId;
    
    /**
     * 角色代码
     */
    private Long roleCode;
    
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDatetime;
    
    /**
     * 操作人ID
     */
    private Long operateId;
    
}