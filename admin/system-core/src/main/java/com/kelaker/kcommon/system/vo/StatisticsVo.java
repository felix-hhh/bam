package com.kelaker.kcommon.system.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 平台统计数据
 *
 * @author CHYM
 * @since 2020/10/15 10:33
 */
@Data
public class StatisticsVo {

    /**
     * 统计关键字
     */
    private String item;

    /**
     * 关键字名称
     */
    private String itemName;

    /**
     * 分组
     */
    private String groupCode;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 统计数据
     */
    private BigDecimal value;

    /**
     * 备注说明
     */
    private String remark;

    /**
     * 统计类型
     */
    private String type;

    /**
     * 类型名称
     */
    private String typeStr;

    /**
     * 最后刷新时间
     */
    private Date lastFlushDatetime;

    /**
     * 操作类型
     */
    private String operationType;
}
