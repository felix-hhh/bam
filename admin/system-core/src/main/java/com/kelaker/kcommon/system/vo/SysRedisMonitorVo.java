package com.kelaker.kcommon.system.vo;

import com.kelaker.ktools.common.utils.DateUtil;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SysRedisMonitorVo {

    private String maxMemory;

    private String usedMemory;

    private String redisVersion;

    private int connectedClients;

    private int tcpPort;

    private String aofEnabled;

    private long totalNetInputBytes;

    private long totalNetOutputBytes;

    private double usedCpuUser;

    private double usedCpuSys;

}
