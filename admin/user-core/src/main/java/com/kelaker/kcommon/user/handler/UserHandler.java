package com.kelaker.kcommon.user.handler;

import com.kelaker.kcommon.common.data.MessageData;

/**
 * 用户处理器
 */
public interface UserHandler {

    /**
     * 用户登录处理器
     *
     * @param messageData 登录数据
     */
    void userLoginHandler(MessageData messageData);

    /**
     * 用户注册处理器
     *
     * @param messageData
     */
    void userRegisterHandler(MessageData messageData);
}
