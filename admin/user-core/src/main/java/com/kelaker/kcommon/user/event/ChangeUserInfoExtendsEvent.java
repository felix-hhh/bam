package com.kelaker.kcommon.user.event;

import lombok.Getter;
import lombok.Setter;

/**
 * 更换用户拓展资料
 *
 * @author felix huang
 * @since 2021-12-17
 */
@Getter
@Setter
public class ChangeUserInfoExtendsEvent extends BaseEvent {

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public ChangeUserInfoExtendsEvent(Object source) {
        super(source);
    }

    /**
     * 变更值
     */
    private String value;

}
