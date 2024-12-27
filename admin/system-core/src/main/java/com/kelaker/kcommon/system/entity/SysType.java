package com.kelaker.kcommon.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * 系统类型
 *
 * @author felix huang
 * @since 2021-12-09
 */
@Data
@TableName("sys_type")
public class SysType extends Model<SysType> {

    @TableId(type = IdType.INPUT)
    private String typeCode;

    private String typeName;

    private String remark;
}
