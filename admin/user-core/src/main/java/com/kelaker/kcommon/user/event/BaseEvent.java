package com.kelaker.kcommon.user.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * 事件基类
 *
 * @author felix huang
 * @since 2021-10-20
 */
@Setter
@Getter
public abstract class BaseEvent extends ApplicationEvent {

    /**
     * 事件ID
     */
    private Long eventId;

    /**
     * 事件类型
     */
    private EventType eventType;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public BaseEvent(Object source) {
        super(source);
    }

}
