package com.kelaker.kcommon.user.constant;

public interface UserConstant {
    /**
     * 用户访问通知
     */
    String MEMBER_OPEN_SYSTEM_NOTICE_TYPE_KEY = "MEMBER_OPEN_SYSTEM_NOTICE";

    /**
     * 会员临期
     */
    String MEMBER_ADVENT_SYSTEM_NOTICE_TYPE_KEY = "MEMBER_ADVENT_SYSTEM_NOTICE";

    /**
     * 会员超时
     */
    String MEMBER_TIMEOUT_SYSTEM_NOTICE_TYPE_KEY = "MEMBER_TIMEOUT_SYSTEM_NOTICE";

    String USERID_EXTENDS_DATA_KEY = "userId";

    String USERS_EXTENDS_DATA_KEY = "users";

    /**
     * 用户注册标签
     */
    String USER_MQ_REGISTER_TAG = "USER_REGISTER";

    /**
     * 用户完善资料标签
     */
    String USER_MQ_INIT_TAG = "USER_INIT_EXTENDS";

    /**
     * 用户登录标签
     */
    String USER_MQ_LOGIN_TAG = "USER_LOGIN";


}
