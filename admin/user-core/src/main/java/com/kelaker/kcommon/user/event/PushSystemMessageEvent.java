package com.kelaker.kcommon.user.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Setter
@Getter
public class PushSystemMessageEvent  extends ApplicationEvent {
    public PushSystemMessageEvent(Object source) {
        super(source);
        extendsData = new ConcurrentHashMap<>();
    }

    /**
     * 目标用户
     */
    private Long to;

    /**
     * 消息类型
     */
    private String type;

    /**
     * 消息
     */
    private String message;

    /**
     * 拓展信息
     */
    private Map<String, Object> extendsData;

    /**
     * 添加扩展数据
     *
     * @param key
     * @param value
     */
    public void pushExtendsData(String key, Object value) {
        extendsData.put(key, value);
    }
}
