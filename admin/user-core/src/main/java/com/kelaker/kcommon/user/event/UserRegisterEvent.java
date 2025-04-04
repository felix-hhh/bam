package com.kelaker.kcommon.user.event;

import com.kelaker.kcommon.user.dto.DeviceInfoDto;
import lombok.Getter;
import lombok.Setter;

/***
 * 注册日记
 */
@Getter
@Setter
public class UserRegisterEvent extends BaseEvent {

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public UserRegisterEvent(Object source) {
        super(source);
    }

    private DeviceInfoDto deviceInfoDto;

    /**
     * 注册类型
     */
    private String registerType;

    /**
     * 用户ID
     */
    private Long userId;
}
