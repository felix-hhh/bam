package com.kelaker.kcommon.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @author felix
 * @since 2021/1/31
 */
@Data
@TableName("sys_config_group")
public class SysConfigGroup extends Model<SysConfigGroup> {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 系统类型
     */
    private String systemType;

    /**
     * 分组代码
     */
    private String groupCode;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 备注
     */
    private String remark;
}
