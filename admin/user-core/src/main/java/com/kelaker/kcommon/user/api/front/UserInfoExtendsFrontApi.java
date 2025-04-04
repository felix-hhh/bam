package com.kelaker.kcommon.user.api.front;

import com.kelaker.kcommon.user.dto.UserInfoExtendsChangeDto;
import com.kelaker.kcommon.user.dto.UserLocationDto;
import com.kelaker.kcommon.user.service.UserInfoExtendsService;
import com.kelaker.kcommon.user.vo.UserRealNameDto;
import com.kelaker.ktools.web.base.api.BaseApi;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户拓展信息
 *
 * @author felix huang
 * @since 2021-12-14 21:26:51
 */
@RestController
@RequestMapping("/user/front/info/extends")
public class UserInfoExtendsFrontApi extends BaseApi {

    /**
     * 服务对象
     */
    @Resource
    private UserInfoExtendsService userInfoExtendsService;

    /**
     * 修改个人用户资料
     *
     * @param userInfoExtendsAddDto 个人用户资料
     */
    @PutMapping("/change")
    public void saveUserInfoExtends(@Validated @RequestBody UserInfoExtendsChangeDto userInfoExtendsAddDto) {
        this.userInfoExtendsService.updateUserInfoExtends(userInfoExtendsAddDto);
    }

    /**
     * 更新用户归属地
     *
     * @param locationDto 经纬度DTO
     */
    @PutMapping("/local")
    public void pushMyLocation(@RequestBody @Validated UserLocationDto locationDto) {
        locationDto.setIp(super.getClientIp());
        this.userInfoExtendsService.pushMyLocation(locationDto);
    }

    /**
     * 检查实名认证结果
     * @param userRealNameDto 实名认证dto
     */
    @PostMapping("/realname")
    public void checkRealName(@RequestBody @Validated UserRealNameDto userRealNameDto) {
        this.userInfoExtendsService.checkRealName(userRealNameDto);
    }
}
