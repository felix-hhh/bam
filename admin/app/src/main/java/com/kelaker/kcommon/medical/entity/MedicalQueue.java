package com.kelaker.kcommon.medical.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
    private Integer id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 医院ID
     */
    private Integer hospitalId;

    /**
     * 医生ID
     */
    private Integer doctorId;

    /**
     * 患者ID
     */
    private Integer patientId;

    /**
     * 患者信息
     */
    private Object patientInfo;

    /**
     * 检查项目
     */
    private String checkItem;

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
    private Integer currentNum;

    /**
     * 诊断时间
     */
    private Date diagnosticTime;

    @Getter
    @AllArgsConstructor
    public enum Status implements IEnum<String> {

        QUEUING("M_Q_S_QUEUING", "排队中"),
        IN_PROGRESS("M_Q_S_IN_PROGRESS", "就诊中"),
        COMPLETED("M_Q_S_COMPLETED", "已完成"),
        EXPIRED("M_Q_S_EXPIRED", "已过期"),
        CANCELLED("M_Q_S_CANCELLED", "已取消");

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
