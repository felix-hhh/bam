package com.kelaker.kcommon.system.service;

import com.kelaker.kcommon.system.vo.SysMonitorVo;
import com.kelaker.kcommon.system.vo.SysRedisMonitorVo;
import com.kelaker.ktools.common.populator.ConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.lang.management.*;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Slf4j
@Service
public class SysMonitorService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    public SysMonitorVo jvmRunMonitor() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        long startTime = runtimeMXBean.getStartTime();
        //jvm名称
        String vmName = runtimeMXBean.getVmName();
        String vmVersion = runtimeMXBean.getVmVersion();
        List<String> inputArguments = runtimeMXBean.getInputArguments();

        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        long heapMemoryUsageMax = heapMemoryUsage.getMax();
        long heapMemoryUsageUsed = heapMemoryUsage.getUsed();
        long heapMemoryUsageInit = heapMemoryUsage.getInit();
        long heapMemoryUsageCommitted = heapMemoryUsage.getCommitted();

        Properties properties = System.getProperties();
        String osName = properties.getProperty("os.name");
        String osVersion = properties.getProperty("os.version");
        String osArch = properties.getProperty("os.arch");
        String timezone = properties.getProperty("user.timezone");
        String language = properties.getProperty("user.language");
        String active = properties.getProperty("spring.profiles.active");
        String javaVersion = properties.getProperty("java.version");
        String javaHome = properties.getProperty("java.home");
        String javaName = properties.getProperty("java.runtime.name");

        SysMonitorVo vo = new SysMonitorVo();

        vo.setStartTime(new Date(startTime));
        vo.setVmName(vmName);
        vo.setVmVersion(vmVersion);
        vo.setInputArguments(inputArguments);

        vo.setHeapMemoryUsageMax(heapMemoryUsageMax);
        vo.setHeapMemoryUsageUsed(heapMemoryUsageUsed);
        vo.setHeapMemoryUsageInit(heapMemoryUsageInit);
        vo.setHeapMemoryUsageCommitted(heapMemoryUsageCommitted);

        vo.setJavaVersion(javaVersion);
        vo.setJavaHome(javaHome);
        vo.setJavaName(javaName);
        vo.setOsName(osName);
        vo.setOsVersion(osVersion);
        vo.setOsArch(osArch);

        vo.setLanguage(language);
        vo.setActive(active);
        vo.setTimezone(timezone);

        InetAddress localHost = null;
        try {
            localHost = InetAddress.getLocalHost();

            String hostAddress = localHost.getHostAddress();
            String hostName = localHost.getHostName();

            vo.setHostAddress(hostAddress);
            vo.setHostName(hostName);
        } catch (Exception ignored) {

        }


        //缓存信息
        RedisConnection redisConnection = redisTemplate.getRequiredConnectionFactory().getConnection();
        Properties cacheProperties = redisConnection.info();
        String maxMemory = cacheProperties.getProperty("maxmemory_human");
        String usedMemory = cacheProperties.getProperty("used_memory_human");
        String redisVersion = cacheProperties.getProperty("redis_version");
        int connectedClients = ConvertUtils.convertToInteger(cacheProperties.getProperty("connected_clients"));
        int tcpPort = ConvertUtils.convertToInteger(cacheProperties.getProperty("tcp_port"));
        String aofEnabled = cacheProperties.getProperty("aof_enabled");

        long totalNetInputBytes = ConvertUtils.convertToLong(cacheProperties.getProperty("total_net_input_bytes"));
        long totalNetOutputBytes = ConvertUtils.convertToLong(cacheProperties.getProperty("total_net_output_bytes"));

        double usedCpuUser = ConvertUtils.convertToDouble(cacheProperties.getProperty("used_cpu_user"));
        double usedCpuSys = ConvertUtils.convertToDouble(cacheProperties.getProperty("used_cpu_sys"));

        vo.setConnectedClients(connectedClients);
        vo.setAofEnabled(aofEnabled);
        vo.setTotalNetInputBytes(totalNetInputBytes);
        vo.setTotalNetOutputBytes(totalNetOutputBytes);
        vo.setUsedCpuUser(usedCpuUser);
        vo.setUsedCpuSys(usedCpuSys);
        vo.setMaxMemory(maxMemory);
        vo.setUsedMemory(usedMemory);
        vo.setRedisVersion(redisVersion);
        vo.setTcpPort(tcpPort);

        return vo;
    }
}