package com.kelaker.kcommon.user.configs;

import com.kelaker.kcommon.user.constant.UserConstant;
import com.kelaker.kcommon.user.event.PushSystemMessageEvent;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration("UserScheduleConfig")
@EnableScheduling
public class ScheduleConfig {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private ApplicationEventPublisher publisher;

    /**
     * 清除
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void clearVisitorRecord() {

    }
    public void pushMemberNotify() {
        log.debug(">>>>>>>>>>发送推送消息");
        PushSystemMessageEvent event = new PushSystemMessageEvent(this);
        String message = "您的会员权益还有3天即将过期，立即续费继续享受会员特权";
        event.setMessage(message);
        event.setTo(1233L);
        event.setType(UserConstant.MEMBER_ADVENT_SYSTEM_NOTICE_TYPE_KEY);
        event.pushExtendsData("memberType","VIP");

        this.publisher.publishEvent(event);


        message = "您的会员权益已过期，立即续费继续享受会员特权";
        event.setMessage(message);
        event.setTo(1233L);
        event.setType(UserConstant.MEMBER_ADVENT_SYSTEM_NOTICE_TYPE_KEY);
        event.pushExtendsData("memberType","VIP");

        this.publisher.publishEvent(event);

        message = "您的会员权益还有3天即将过期，立即续费继续享受会员特权";
        event.setMessage(message);
        event.setTo(1233L);
        event.setType(UserConstant.MEMBER_ADVENT_SYSTEM_NOTICE_TYPE_KEY);
        event.pushExtendsData("memberType","SVIP");

        this.publisher.publishEvent(event);


        message = "您的会员权益已过期，立即续费继续享受会员特权";
        event.setMessage(message);
        event.setTo(1233L);
        event.setType(UserConstant.MEMBER_ADVENT_SYSTEM_NOTICE_TYPE_KEY);
        event.pushExtendsData("memberType","SVIP");

        this.publisher.publishEvent(event);

    }
}
