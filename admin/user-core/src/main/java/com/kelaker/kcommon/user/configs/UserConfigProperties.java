package com.kelaker.kcommon.user.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "kelaker.kcommon.user")
public class UserConfigProperties {

    /**
     * 用户名前缀
     */
    private String usernamePrefix;

    /**
     * mq消费者分组ID
     */
    private String mqGroupId;

    /**
     * mq name server address
     */
    private String mqNameSrvAddr;

    /**
     * mq账号
     */
    private String mqAccessKey;

    /**
     * mq密钥
     */
    private String mqSecretKey;

    /**
     * 请求超时
     */
    private long mqTimeout;

    /**
     * mq消息消费者topic
     */
    private String mqConsumerTopic;

    private String mqUserStateTag;

    private String mqAssetsConsumerTopic;

    private String mqAssetsGroupId;

    private String mqUserMemberTag;

    /**
     * 用户mq生产者主题
     */
    private String mqMessageUserProducerTopic;

    /**
     * 消费线程数
     */
    private String consumeThreadNums;

    /**
     * 用户邀请码过期时间
     */
    private Integer userInviteCodeTimeout;

    /**
     * 最大登录数
     */
    private int maxToken;

    /**
     * 微信小程序密钥
     */
    private String wxMinAppSecret;

    /**
     * 微信小程序appid
     */
    private String wxMinAppId;
}
