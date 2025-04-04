package com.kelaker.kcommon.user.listener;

import com.kelaker.kcommon.common.ws.dto.NotificationMessageDto;
import com.kelaker.kcommon.user.configs.UserConfigProperties;
import com.kelaker.kcommon.user.dto.DeviceInfoDto;
import com.kelaker.kcommon.user.dto.UserRegisterLogDto;
import com.kelaker.kcommon.user.event.PushSystemMessageEvent;
import com.kelaker.kcommon.user.event.UserInitExtendsEvent;
import com.kelaker.kcommon.user.event.UserLoginEvent;
import com.kelaker.kcommon.user.event.UserRegisterEvent;
import com.kelaker.kcommon.user.service.UserLoginLogService;
import com.kelaker.kcommon.user.service.UserRegisterLogService;
import com.kelaker.ktools.common.populator.ConvertUtils;
import com.kelaker.ktools.common.utils.ValidateUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用户事件处理器
 *
 * @author felix huang
 * @since 2021-12-17
 */
@Slf4j
@Component
public class UserEventListener {

    @Resource
    private UserRegisterLogService userRegisterLogService;

    @Resource
    private UserLoginLogService userLoginLogService;

    @Resource
    private UserConfigProperties userConfigProperties;

    /**
     * 推送系统消息
     *
     * @param event 系统消息事件
     */
    @EventListener
    public void pushSystemMessageEventListener(PushSystemMessageEvent event) {
        log.debug(">>>>>>>>>> 推送系统消息：{}",event.getMessage());
        Long to = event.getTo();
        String type = event.getType();
        Map<String, Object> extendsData = event.getExtendsData();
        String message = event.getMessage();

        NotificationMessageDto messageDto = new NotificationMessageDto();
        messageDto.setTo(to);

        NotificationMessageDto.MessageBody body = new NotificationMessageDto.MessageBody();
        body.setMessage(message);
        body.setType(type);
        body.setExtendsData(extendsData);

        messageDto.setBody(body);

//        this.messageFeignClient.sendSystemNotificationMessage(messageDto);
    }

    /**
     * 注册日记
     *
     * @param userRegisterEvent
     */
    @EventListener
    @Async
    public void userRegisterEventListener(UserRegisterEvent userRegisterEvent) {
        Long userId = userRegisterEvent.getUserId();

        DeviceInfoDto deviceInfoDto = userRegisterEvent.getDeviceInfoDto();
        if (ValidateUtil.isNotBlank(deviceInfoDto)) {
            UserRegisterLogDto dto = new UserRegisterLogDto();
            ConvertUtils.copyProperties(deviceInfoDto, dto);
            dto.setRegisterMode(userRegisterEvent.getRegisterType());
            dto.setUserId(userId);

            //用户注册日志
            this.userRegisterLogService.addUserRegisterLog(dto);
        }


       /* this.userHandler.userRegisterHandler(handlerData);
        //推送注册消息
        Message mqMessage = new Message();
        mqMessage.setTopic(this.userConfigProperties.getMqMessageUserProducerTopic());
        mqMessage.setTag(UserConstant.USER_MQ_REGISTER_TAG);
        Map<String, Object> messageData = new HashMap<>();
        messageData.put("userId", userId);
        String messageStr = JsonUtil.objToJson(messageData);
        mqMessage.setBody(messageStr.getBytes(StandardCharsets.UTF_8));
        this.producerBean.send(mqMessage);*/
        log.debug(">>>>>>>>>>用户注册推送：{}", userId);
    }

    /**
     * 登录日志
     *
     * @param userLoginEvent
     */
    @EventListener
    @Async
    public void userLoginEventListener(UserLoginEvent userLoginEvent) {
       /* String userId = userLoginEvent.getUserId();
        //日志添加及更新
        DeviceInfoDto deviceInfoDto = userLoginEvent.getDeviceInfoDto();
        UserLoginLogDto dto = new UserLoginLogDto();
        ConvertUtils.copyProperties(deviceInfoDto, dto);
        dto.setType(userLoginEvent.getLoginType());
        dto.setUserId(userId);
        this.userLoginLogService.addUserLoginLog(dto);

        //推送登录消息
        Message mqMessage = new Message();
        mqMessage.setTopic(this.userConfigProperties.getMqMessageUserProducerTopic());
        mqMessage.setTag(UserConstant.USER_MQ_LOGIN_TAG);
        Map<String, Object> messageData = new HashMap<>();
        messageData.put("userId", userId);
        String messageStr = JsonUtil.objToJson(messageData);
        mqMessage.setBody(messageStr.getBytes(StandardCharsets.UTF_8));
        this.producerBean.send(mqMessage);
        log.debug(">>>>>>>>>>用户登录推送：{}", userId);*/
    }

    /**
     * 用户完善资料事件
     *
     * @param userInitExtendsEvent
     */
    @EventListener
    @Async
    public void userInitExtendsEventListener(UserInitExtendsEvent userInitExtendsEvent) {
        /*Message mqMessage = new Message();
        mqMessage.setTopic(this.userConfigProperties.getMqMessageUserProducerTopic());
        mqMessage.setTag(UserConstant.USER_MQ_INIT_TAG);

        Map<String, Object> messageData = new HashMap<>();
        messageData.put("userId", userInitExtendsEvent.getUserId());
        messageData.put("gender", userInitExtendsEvent.getGender());
        messageData.put("headerImg", userInitExtendsEvent.getHeaderImg());
        messageData.put("nickname", userInitExtendsEvent.getNickname());
        String messageStr = JsonUtil.objToJson(messageData);
        mqMessage.setBody(messageStr.getBytes(StandardCharsets.UTF_8));
        this.producerBean.send(mqMessage);*/
    }

}
