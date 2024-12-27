package com.kelaker.kcommon.common.ws.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public abstract class MessageDto {

    public MessageDto() {
        filterTo = new HashSet<>();
    }

    /**
     * 接收对象
     */
    private Long to;

    private Set<String> filterTo;

    /**
     * 发送对象Id
     */
    private String from;

    /**
     * 添加过滤器
     * @param userId
     */
    public void addFilter(String userId) {
        filterTo.add(userId);
    }


}
