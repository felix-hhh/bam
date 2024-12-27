package com.kelaker.kcommon.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.system.dao.SysAdminLogDao;
import com.kelaker.kcommon.system.dto.SysAdminLogRequestDto;
import com.kelaker.kcommon.system.entity.SysAdminInfo;
import com.kelaker.kcommon.system.entity.SysAdminLog;
import com.kelaker.kcommon.system.entity.SysAdminRole;
import com.kelaker.kcommon.system.vo.SysAdminLogVo;
import com.kelaker.ktools.cache.annotation.CacheIt;
import com.kelaker.ktools.common.populator.ConvertUtils;
import com.kelaker.ktools.common.utils.ValidateUtil;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.common.vo.TokenVo;
import com.kelaker.ktools.web.base.service.BaseService;
import com.kelaker.ktools.web.helper.LogHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 管理员日志(AdminLog)表服务实现类
 *
 * @author felix huang
 * @since 2020-03-27 11:42:56
 */
@Service
@Slf4j
public class SysAdminLogService extends BaseService<SysAdminLogDao, SysAdminLog> implements LogHelper {

    @Resource
    private SysAdminInfoRoleLinkService sysAdminInfoRoleLinkService;

    /**
     * 分页查找管理员日志
     *
     * @param params
     */
    public IPage<SysAdminLogVo> queryPage(RequestPage<SysAdminLogRequestDto> params) {
        SysAdminLogRequestDto vo = params.getData();
        IPage<SysAdminLog> queryPage = super.createPage(params);
        IPage<SysAdminLog> page = super.lambdaQuery()
                .eq(ValidateUtil.isNotBlank(vo.getKey()), SysAdminLog::getUsername, vo.getKey())
                .or()
                .eq(ValidateUtil.isNotBlank(vo.getKey()), SysAdminLog::getRoleName, vo.getKey())
                .or()
                .eq(ValidateUtil.isNotBlank(vo.getKey()), SysAdminLog::getType, vo.getKey())
                .ge(ValidateUtil.isNotBlank(vo.getStartDate()), SysAdminLog::getCreateDatetime, vo.getStartDate())
                .le(ValidateUtil.isNotBlank(vo.getEndDate()), SysAdminLog::getCreateDatetime, vo.getEndDate())
                .like(ValidateUtil.isNotBlank(vo.getRequestData()), SysAdminLog::getRequestData, vo.getRequestData())
                .orderByDesc(SysAdminLog::getCreateDatetime)
                .page(queryPage);
        return mapPageToTarget(page, SysAdminLogVo.class);
    }


    /**
     * 写日志
     *
     * @param type
     * @param description
     * @param uri
     * @param body
     */
    @Override
    public void writeLog(String type, String description, String uri, String body) {
        SysAdminLog log = new SysAdminLog();
        log.setDescription(description);
        log.setType(type);
        log.setRequestUri(uri);
        log.setRequestData(body);
        TokenVo token = super.getToken();
        log.setUsername(ConvertUtils.convertToString(token.getUserData().get("username")));
        log.setInfoId(ConvertUtils.convertToLong(token.getUserTag()));

        final List<SysAdminRole> sysAdminRoles = sysAdminInfoRoleLinkService.listGrantedRolesByAdminId(log.getInfoId());
        if (ValidateUtil.isNotBlank(sysAdminRoles)) {
            final String collect = sysAdminRoles.stream()
                    .map(SysAdminRole::getRoleName)
                    .collect(Collectors.joining(":"));
            log.setRoleName(collect);
        }


        super.save(log);
    }

    /**
     * 记录管理员登录日志
     *
     * @param info
     * @param ip
     */
    public void writeLog(SysAdminInfo info, String ip) {
        SysAdminLog log = new SysAdminLog();
        log.setDescription("管理员登录");
        log.setInfoId(info.getId());
        log.setType("login");
        log.setUsername(info.getUsername());
        log.setRequestUri(ip);

        final List<SysAdminRole> sysAdminRoles = sysAdminInfoRoleLinkService.listGrantedRolesByAdminId(info.getId());
        if (ValidateUtil.isNotBlank(sysAdminRoles)) {
            final String collect = sysAdminRoles.stream()
                    .map(SysAdminRole::getRoleName)
                    .collect(Collectors.joining(":"));
            log.setRoleName(collect);
        }
        super.save(log);
    }
}