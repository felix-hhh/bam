package com.kelaker.kcommon.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * 联系人组关联管理员(SysLinkmanGroupInfoLink)表实体类
 *
 * @author Felix Huang
 * @since 2023-04-04 16:15:43
 */
@Data
@TableName("sys_linkman_group_info_link")
public class SysLinkmanGroupInfoLink extends Model<SysLinkmanGroupInfoLink> {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String groupCode;

    private Long infoId;

    private Date createDatetime;
}

