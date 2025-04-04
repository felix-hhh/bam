package com.kelaker.kcommon.user.constant;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户设置key
 */
@Getter
@AllArgsConstructor
public enum UserSettingKey implements IEnum<String> {

    /**
     * 好友查看主页
     */
    FRIEND_HOME_VIEW("FRIEND_HOME_VIEW", "好友查看主页"),
    /**
     * 陌生人查看主页
     */
    STRANGER_HOME_VIEW("STRANGER_HOME_VIEW", "陌生人查看主页"),
    /**
     * 好友匹配
     */
    FRIEND_MATCHING("FRIEND_MATCHING", "好友匹配"),
    /**
     * 好友查找
     */
    FRIEND_FIND("FRIEND_FIND", "好友查找"),
    /**
     * 陌生人聊天开关
     */
    STRANGER_CHAT("STRANGER_CHAT", "陌生人聊天开关"),
    /**
     * 点赞消息
     */
    LIKE_MESSAGE("LIKE_MESSAGE", "点赞消息"),
    /**
     * 收藏开关
     */
    COLLECT_MESSAGE("COLLECT_MESSAGE", "收藏开关"),
    /**
     * 关注消息
     */
    CONCERN_MESSAGE("CONCERN_MESSAGE", "关注消息"),
    /**
     * 圈我消息
     */
    LINK_MESSAGE("LINK_MESSAGE", "圈我消息");

    private final String value;

    private final String remark;

    @Override
    public String toString() {
        return value;
    }
}
