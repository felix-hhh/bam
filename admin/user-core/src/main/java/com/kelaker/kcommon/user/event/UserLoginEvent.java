package com.kelaker.kcommon.user.event;

import com.kelaker.kcommon.user.dto.DeviceInfoDto;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户登录日志(UserLoginLog)表实体类
 *
 * @author lfz
 * @since 2022-08-08 16:03:28
 */
@Getter
@Setter
public class UserLoginEvent extends BaseEvent {

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public UserLoginEvent(Object source) {
        super(source);
    }

    private DeviceInfoDto deviceInfoDto;

    /**
     * 登录类型
     */
    private String loginType;

    /**
     * 用户ID
     */
    private Long userId;
}

