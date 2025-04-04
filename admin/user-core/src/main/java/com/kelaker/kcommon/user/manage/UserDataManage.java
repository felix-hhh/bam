package com.kelaker.kcommon.user.manage;

import com.kelaker.kcommon.common.user.vo.UserInfoAllVo;
import com.kelaker.kcommon.user.constant.CacheConstant;
import com.kelaker.kcommon.user.service.UserInfoService;
import com.kelaker.ktools.cache.annotation.CacheIt;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class UserDataManage {

    @Resource
    private UserInfoService userInfoService;

    /**
     * 取回人员基础信息
     *
     * @param userId
     *//*
    @CacheIt(key = CacheConstant.USER_INFO_CACHE_KEY + "USER_SIMPLE", paramKey = true)
    public UserInfoSimpleVo getUserInfoSimple(String userId) {
        UserInfoAllVo userInfoById = this.userInfoService.getUserInfoById(userId);
        UserInfoSimpleVo vo = new UserInfoSimpleVo();
        if(ValidateUtil.isNotBlank(userInfoById)){
            ConvertUtils.copyProperties(userInfoById, vo);
            vo.setIpLocation(userInfoById.getLocalProvince());
        }
        return vo;
    }*/

    /**
     * 取回用户详细信息
     *
     * @param userId 用户ID
     */
    @CacheIt(key = CacheConstant.USER_INFO_CACHE_KEY + "USER_ALL", paramKey = true)
    public UserInfoAllVo getUserInfoById(Long userId) {
        return userInfoService.getUserInfoById(userId);
    }
}
