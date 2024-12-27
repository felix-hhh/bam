package com.kelaker.kcommon.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * 系统功能表(SysAction)表实体类
 *
 * @author felix huang
 * @since 2020-03-27 11:42:57
 */
@Data
@TableName("sys_action")
public class SysAction extends Model<SysAction> {
    /**
    * 功能代码
    */
    @TableId(value = "action_code", type = IdType.INPUT)
    private String actionCode;
    
    /**
    * 功能名称
    */
    private String actionName;
    
    /**
    * 模块代码
    */
    private String moduleCode;
    
    /**
    * 备注
    */
    private String remark;

    /**
     * 权限URL
     */
    private String url;
}