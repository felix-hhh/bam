package com.kelaker.kcommon.user.constant;

/**
 * 业务类型
 */
public enum BusinessType {
    /**
     * 绑定手机
     */
    BIND_PHONE,
    /**
     * 重置手机号
     */
    RESET_PHONE,
    /**
     * 绑定微信OpenId
     */
    BIND_WXOPENID,
    /**
     * 绑定苹果ID
     */
    BIND_APPLEID,
    /**
     * 绑定实名认证
     */
    BIND_REALNAME,
    /**
     * 解除苹果ID
     */
    UNBIND_APPLEID,
    /**
     * 解除微信ID
     */
    UNBIND_WXOPENID,
    /**
     * 修改密码
     */
    CHANGE_PWD,
    /**
     * 注销用户
     */
    CANCEL_USER
}
