package com.kelaker.kcommon.common.data;

import com.kelaker.ktools.common.utils.ValidateUtil;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 模块之间传递的消息对象
 */
@Data
public class MessageData {
    /**
     * 主题
     */
    private String topic;

    /**
     * 标签
     */
    private String tag;

    /**
     * 消息数据
     */
    private Map<String, Object> data;

    /**
     * 往数据容器中填充数据
     *
     * @param key 键
     * @param obj 对象
     */
    public void put(String key, Object obj) {
        if (ValidateUtil.isBlank(data)) {
            data = new HashMap<>();
        }
        data.put(key, obj);
    }
}
