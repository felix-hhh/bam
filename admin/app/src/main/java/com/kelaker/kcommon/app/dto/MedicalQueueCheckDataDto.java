package com.kelaker.kcommon.app.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class MedicalQueueCheckDataDto {

    /**
     * 队列id
     */
    private Long id;

    /**
     * 检查数据
     */
    private List<Map<String,Object>> checkData;
}
