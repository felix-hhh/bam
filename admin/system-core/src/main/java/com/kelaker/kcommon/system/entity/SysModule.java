package com.kelaker.kcommon.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * 系统模块(SysModule)表实体类
 *
 * @author felix huang
 * @since 2020-03-27 11:42:57
 */
@Data
@TableName(value = "sys_module")
public class SysModule extends Model<SysModule> {
    /**
     * 模块代码
     */
    @TableId(value = "module_code", type = IdType.INPUT)
    private String moduleCode;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 健康检查地址
     */
    private String healthCheckUrl;

    /**
     * 备注
     */
    private String remark;

}