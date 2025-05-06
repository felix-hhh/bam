package com.kelaker.kcommon.tools.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

/**
 * 文件上传记录(ToolsFileUpload)表实体类
 *
 * @author Felix Huang
 * @since 2025-04-30 03:44:50
 */
@Data
@TableName("tools_file")
public class ToolsFile extends Model<ToolsFile> {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 真实文件名
     */
    private String realFileName;

    /**
     * 原文件名
     */
    private String origFileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDatetime;

    /**
     * 平台
     */
    private Platform platform;

    /**
     * 状态
     */
    private Status status;

    @Getter
    @AllArgsConstructor
    public enum Platform implements IEnum<String> {
        ALIYUN("T_F_U_P_ALIYUN", "阿里云"),
        TENCENT("T_F_U_P_TENCENT", "腾讯云"),
        LOCAL("T_F_U_P_LOCAL", "本地");

        private final String value;
        private final String remark;

        public static Platform toEnum(String value) {
            for (Platform platform : values()) {
                if (value.equals(platform.getValue())) {
                    return platform;
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
        NORMAL("T_F_U_S_NORMAL", "正常"),
        DELETE("T_F_U_S_DELETE", "删除"),
        HIDDEN("T_F_U_S_HIDDEN", "隐藏");

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
