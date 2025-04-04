package com.kelaker.kcommon.user.constant;

public interface CacheConstant {
    /**
     * 用户ID对应的token_key前缀
     */
    String CACHE_TOKEN_USER_PREFIX = "sys_token_userid:";

    /**
     * 用户ID缓存
     */
    String CACHE_USER_LIST_PREFIX = "userid_list:";

    /**
     * 设备ID对应的token
     */
    String CACHE_TOKEN_DEVICE_PREFIX = "sys_token_device:";

    /**
     * 缓存用户设备信息
     */
    String CACHE_DEVICE_USERID_PREFIX = "sys_device_userid:";

    /**
     * 验证手机号码
     */
    String CACHE_VALIDATE_PHONE_PREFIX = "validate_phone:";

    /**
     * 用于下一个业务验证码
     */
    String CACHE_NEXT_BUSI_PREFIX = "next_busi:";

    /**
     * 下一个业务需要的参数
     */
    String CACHE_NEXT_BUSI_VALUE = "next_busi_value:";

    /**
     * 二维码
     */
    String QR_CODE_BUSI_PREFIX = "qr_code:";

    /**
     * 用户信息缓存
     */
    String USER_INFO_CACHE_KEY = "user_info_cache:";

    /**
     * 用户关系缓存
     */
    String USER_RELACTIONSHIP_CACHE_KEY = "user_relactionship_cache:";

    /**
     * 访客列表
     */
    String USER_VISITOR_CACHE_KEY = "user_visitor_cache:";

    /**
     * 用户昵称数组
     */
    String USER_NICKNAME_CACHE_KEY = "user_nickname_cache:";

    /**
     * 用户经纬度缓存
     */
    String USER_LOCAL_CACHE_KEY = "user_local_cache:";

    /**
     * 在线用户
     */
    String ONLINE_USER_CACHE_KEY = "online_user";

    /**
     * 用户IP归属地
     */
    long USER_LOCAL_CACHE_TIMEOUT = 86400;

    /**
     * 用户关系缓存key
     */
    String USER_RELACTIONSHIP = "USER_RELACTIONSHIP:";

    /**
     * 用户邀请码
     */
    String USER_INVITE_CODE = "user_invite_code:";

    /**
     * 用户关注缓存
     */
    String USER_RELATION_CACHE_KEY = "USER_RELATION_CACHE:";

    /**
     * 用户拓展信息缓存
     */
    String USER_EXTENDS_CACHE_KEY = "USER_EXTENDS_CACHE:";

}
