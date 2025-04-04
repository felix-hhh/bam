package com.kelaker.kcommon.user.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInitExtendsEvent extends BaseEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public UserInitExtendsEvent(Object source) {
        super(source);
    }

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 性别
     */
    private int gender;

    /**
     * 头像
     */
    private String headerImg;

    /**
     * 昵称
     */
    private String nickname;
}
