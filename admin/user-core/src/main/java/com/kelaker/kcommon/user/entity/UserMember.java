package com.kelaker.kcommon.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.kelaker.kcommon.user.constant.MemberType;
import com.kelaker.ktools.common.populator.ConvertUtils;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户会员(UserMember)表实体类
 *
 * @author Felix Huang
 * @since 2023-05-02 22:42:01
 */
@Data
@TableName("user_member")
public class UserMember extends Model<UserMember> implements Comparable<UserMember> {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 会员类型
     */
    private MemberType memberType;

    /**
     * 会员时间
     */
    private int memberTime;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDatetime;

    /**
     * 花费
     */
    private BigDecimal cost;

    /**
     * 状态
     */
    private Status status;

    /**
     * 来源
     */
    private String source;

    /**
     * 开始时间
     */
    private Date startDatetime;

    /**
     * 结束时间
     */
    private Date endDatetime;

    /**
     * 支付ID
     */
    private String payId;

    /**
     * 会员商品类型
     */
    private String memberTitle;

    /**
     * 取回评分
     */
    public Long getScore() {
        return ConvertUtils.convertToLong((MemberType.SVIP.equals(memberType) ? "1" : "2") + ConvertUtils.convertToString(createDatetime.getTime()));
    }

    @Override
    public int compareTo(@NotNull UserMember o) {
        Long o1Score = getScore();
        Long o2Score = o.getScore();
        return Long.compare(o1Score, o2Score);
    }

    @Getter
    @AllArgsConstructor
    public enum Status implements IEnum<String> {

        /**
         * 正常
         */
        NORMAL("U_M_S_NORMAL", "正常"),
        /**
         * 暂停
         */
        PAUSE("U_M_S_PAUSE", "暂停"),
        /**
         * 禁用
         */
        DISABLE("U_M_S_DISABLE", "禁用"),
        /**
         * 结束
         */
        FINISH("U_M_S_FINISH", "结束");

        private final String value;

        private final String remark;

        public static Status toEnum(String value) {
            for (Status status : values()) {
                if (value.equals(status.getValue())) {
                    return status;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}

