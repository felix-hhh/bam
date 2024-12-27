package com.kelaker.kcommon.common.ws.data;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 媒体类型
 */
@Getter
@AllArgsConstructor
public enum MimeType implements IEnum<String> {
    /**
     * 文本
     */
    TEXT("MIME_TYPE_TEXT", "文本消息"),
    /**
     * 图片
     */
    IMAGE("MIME_TYPE_IMAGE", "图片消息"),
    /**
     * 视频
     */
    VIDEO("MIME_TYPE_VIDEO", "视频消息"),
    /**
     * 音频
     */
    AUDIO("MIME_TYPE_AUDIO", "音频消息"),
    /**
     * 文件消息
     */
    FILE("MIME_TYPE_FILE", "文件消息"),
    /**
     * 个人名片
     */
    VCARD("MIME_TYPE_VCARD", "名片消息"),
    /**
     * 位置
     */
    LOCATION("MIME_TYPE_LOCATION", "位置消息"),
    /**
     * 互动
     */
    INTERACTION("MIME_TYPE_INTERACTION", "互动消息"),
    /**
     * 动作
     */
    ACTION("MIME_TYPE_ACTION", "动作消息"),
    /**
     * 通知
     */
    NOTIFICATION("MIME_TYPE_NOTIFICATION", "通知消息");

    private final String value;

    private final String remark;

    public static MimeType toEnum(String value) {
        for (MimeType type : values()) {
            if (value.equals(type.getValue())) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}
