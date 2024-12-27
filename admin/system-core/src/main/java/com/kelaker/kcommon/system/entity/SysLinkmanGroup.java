package com.kelaker.kcommon.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * 系统联系人组(SysLinkmanGroup)表实体类
 *
 * @author Felix Huang
 * @since 2023-04-04 16:15:42
 */
@Data
@TableName("sys_linkman_group")
public class SysLinkmanGroup extends Model<SysLinkmanGroup> {

    /**
     * id
     */
    @TableId(type = IdType.INPUT)
    private String groupCode;

    /**
     * 组名称
     */
    private String groupName;

    /**
     * 备注
     */
    private String remark;
}

