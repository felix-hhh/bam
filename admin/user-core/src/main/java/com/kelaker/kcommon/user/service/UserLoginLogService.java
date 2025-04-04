package com.kelaker.kcommon.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.common.tools.vo.IpLocationVo;
import com.kelaker.kcommon.user.dao.UserLoginLogDao;
import com.kelaker.kcommon.user.dto.UserLoginLogDto;
import com.kelaker.kcommon.user.dto.UserLoginLogSearchDto;
import com.kelaker.kcommon.user.entity.UserInfoExtends;
import com.kelaker.kcommon.user.entity.UserLoginLog;
import com.kelaker.kcommon.user.vo.UserLoginLogVo;
import com.kelaker.ktools.common.utils.DateUtil;
import com.kelaker.ktools.common.utils.SecurityUtil;
import com.kelaker.ktools.common.utils.ValidateUtil;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户登录日志(UserLoginLog)表服务
 *
 * @author Felix Huang
 * @since 2022-08-23 14:23:29
 */
@Slf4j
@Service
public class UserLoginLogService extends BaseService<UserLoginLogDao, UserLoginLog> {

    @Resource
    private UserInfoExtendsService userInfoExtendsService;

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 分页结果
     */
    public IPage<UserLoginLogVo> queryPage(RequestPage<UserLoginLogSearchDto> searchDto) {
        UserLoginLogSearchDto searchDtoData = searchDto.getData();
        UserLoginLog empty = super.objectConvert(searchDtoData, UserLoginLog.class);
        IPage<UserLoginLog> page = super.page(super.createPage(searchDto), super.createWrapper(empty));
        return mapPageToTarget(page, this::convertToVo);
    }

    /**
     * 添加对象
     *
     * @param dto 对象
     */
    public void addUserLoginLog(UserLoginLogDto dto) {
        log.debug(">>>>>>>>>{}", dto);
        String deviceId = SecurityUtil.md5Encrypt(dto.getDeviceId());
        String userId = dto.getUserId();
        UserLoginLog userLoginLog = this.getUserLoginLog(deviceId, userId);
        UserInfoExtends userInfoExtends = this.userInfoExtendsService.getById(userId);
        if (ValidateUtil.isBlank(userLoginLog)) {
            userLoginLog = super.objectConvert(dto, UserLoginLog.class);
            userLoginLog.setDeviceId(deviceId);

            userLoginLog.setType(UserLoginLog.Type.valueOf(dto.getType()));
            String ip = userLoginLog.getIp();
            if (ValidateUtil.isNotBlank(ip)) {
//                IpLocationVo ipLocationVo = this.toolsIpDataFeignClient.convertIpToLocation(ip).getData();
                IpLocationVo ipLocationVo = new IpLocationVo();
                log.debug(">>>>>>>>>> ip:{}", ipLocationVo);
                if (!ValidateUtil.hasBlank(ipLocationVo.getContent(), ipLocationVo.getContent().getAddressDetail())) {
                    IpLocationVo.AddressDetail addressDetail = ipLocationVo.getContent().getAddressDetail();
                    userLoginLog.setLocalCity(addressDetail.getCity());
                    userLoginLog.setLocalCode(addressDetail.getAdcode());
                    userLoginLog.setLocalProvince(addressDetail.getProvince());

                    userInfoExtends.setLocalProvince(addressDetail.getProvince());
                    userInfoExtends.setLocalCity(addressDetail.getCity());
                    userInfoExtends.setLocalCode(addressDetail.getAdcode());
                    userInfoExtends.updateById();
                }

            }
            super.save(userLoginLog);
        } else {
            String ip = dto.getIp();
            if (ValidateUtil.isNotBlank(ip) && !ip.equals(userLoginLog.getIp())) {
//                IpLocationVo ipLocationVo = this.toolsIpDataFeignClient.convertIpToLocation(ip).getData();
                IpLocationVo ipLocationVo = new IpLocationVo();
                if (!ValidateUtil.hasBlank(ipLocationVo.getContent(), ipLocationVo.getContent().getAddressDetail())) {
                    IpLocationVo.AddressDetail addressDetail = ipLocationVo.getContent().getAddressDetail();
                    userLoginLog.setLocalCity(addressDetail.getCity());
                    userLoginLog.setLocalCode(addressDetail.getAdcode());
                    userLoginLog.setLocalProvince(addressDetail.getProvince());

                    userInfoExtends.setLocalProvince(addressDetail.getProvince());
                    userInfoExtends.setLocalCity(addressDetail.getCity());
                    userInfoExtends.setLocalCode(addressDetail.getAdcode());
                    userInfoExtends.updateById();
                }
                userLoginLog.setIp(ip);

            }
            userLoginLog.setType(UserLoginLog.Type.valueOf(dto.getType()));
            userLoginLog.setCreateDatetime(DateUtil.getCurrentDate());
            super.updateById(userLoginLog);
        }
    }

    /**
     * 取回用户最后一条登录日志
     *
     * @param deviceId
     */
    private UserLoginLog getUserLoginLog(String deviceId, String userId) {
        return super.lambdaQuery()
                .eq(UserLoginLog::getDeviceId, deviceId)
                .eq(UserLoginLog::getUserId, userId)
                .orderByDesc(UserLoginLog::getCreateDatetime)
                .last(" limit 1").one();
    }

    /**
     * 取回用户最后一条登录日志
     */
    public List<UserLoginLogVo> getUserLoginLog() {
        List<UserLoginLog> loginLogList = super.lambdaQuery()
                .eq(UserLoginLog::getUserId, super.getUserId())
                .orderByDesc(UserLoginLog::getCreateDatetime)
                .list();
        return super.mapListToTarget(loginLogList, this::convertToVo);
    }

    /**
     * 对象转换
     */
    private UserLoginLogVo convertToVo(UserLoginLog userLoginLog) {
        UserLoginLogVo userLoginLogVo = super.objectConvert(userLoginLog, UserLoginLogVo.class);
        userLoginLogVo.setTypeStr(userLoginLog.getType().getRemark());
        return userLoginLogVo;
    }
}

