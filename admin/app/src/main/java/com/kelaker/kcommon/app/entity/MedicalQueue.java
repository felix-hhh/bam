package com.kelaker.kcommon.app.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.kelaker.ktools.common.utils.ValidateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

/**
 * 队列(MedicalQueue)表实体类
 *
 * @author Felix Huang
 * @since 2025-04-09 10:39:07
 */
@Data
@TableName("medical_queue")
public class MedicalQueue extends Model<MedicalQueue> {

    /**
     * 序号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 医院ID
     */
    private Long hospitalId;

    /**
     * 医生ID
     */
    private Long doctorId;

    /**
     * 患者ID
     */
    private Long patientId;

    /**
     * 患者信息
     */
    private Object patientInfo;

    /**
     * 检查项目
     */
    private CheckItem checkItem;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDatetime;

    /**
     * 状态
     */
    private Status status;

    /**
     * 队列号码
     */
    private String queueNum;

    /**
     * 当前号码
     */
    private Long currentNum;

    /**
     * 诊断时间
     */
    private Date diagnosticTime;

    /**
     * 是否有诊断报告
     */
    private Boolean record;

    /**
     * 是否有评估报告
     */
    private Boolean report;

    /**
     * 是否有运动视频
     */
    private Boolean video;

    @Getter
    @AllArgsConstructor
    public enum CheckItem implements IEnum<String> {
        POSTURE_FUSION("M_Q_C_POSTURE_FUSION", "体位融合"),
        CHRONIC_PAIN_ASSESSMENT("M_Q_C_CHRONIC_PAIN_ASSESSMENT", "慢性疼痛评估");

        private final String value;
        private final String remark;

        public static CheckItem toEnum(String value) {
            if (ValidateUtil.isBlank(value)) {
                return null;
            }
            for (CheckItem checkItem : values()) {
                if (value.equals(checkItem.getValue())) {
                    return checkItem;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum Status implements IEnum<String> {

        QUEUING("M_Q_S_QUEUING", "排队中"),
        IN_PROGRESS("M_Q_S_IN_PROGRESS", "就诊中"),
        COMPLETED("M_Q_S_COMPLETED", "已完成"),
        EXPIRED("M_Q_S_EXPIRED", "已过期"),
        CANCELLED("M_Q_S_CANCELLED", "已取消"),
        WAIT("M_Q_S_WAIT", "待取号");

        private final String value;

        private final String remark;

        public static Status toEnum(String value) {
            if (ValidateUtil.isBlank(value)) {
                return null;
            }
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
