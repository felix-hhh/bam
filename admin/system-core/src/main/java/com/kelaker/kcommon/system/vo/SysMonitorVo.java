package com.kelaker.kcommon.system.vo;

import com.kelaker.ktools.common.utils.DateUtil;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SysMonitorVo {

    /**
     * 启动时间
     */
    private Date startTime;

    private long runTime;

    /**
     * 虚拟机名称
     */
    private String vmName;

    /**
     * 虚拟机版本
     */
    private String vmVersion;

    /**
     * 运行参数
     */
    private List<String> inputArguments;

    /**
     * 服务器IP地址
     */
    private String hostAddress;

    /**
     * 服务器名称
     */
    private String hostName;

    /**
     * 虚拟机内存最大值
     */
    private long heapMemoryUsageMax;

    /**
     * 虚拟机已使用内存
     */
    private long heapMemoryUsageUsed;

    /**
     * 虚拟机内存初始值
     */
    private long heapMemoryUsageInit;

    /**
     * 虚拟内存提交的内存
     */
    private long heapMemoryUsageCommitted;

    private String osName;

    private String osVersion;

    private String osArch;

    private String javaVersion;

    private String javaHome;

    private String javaName;

    /**
     * 时区
     */
    private String timezone;

    /**
     * 语言
     */
    private String language;

    /**
     * 激活
     */
    private String active;

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

    /**
     * 取回运行时间
     *
     * @return 运行时间
     */
    public long getRunTime() {
        return DateUtil.getCurrentDate().getTime() - startTime.getTime();
    }
}
