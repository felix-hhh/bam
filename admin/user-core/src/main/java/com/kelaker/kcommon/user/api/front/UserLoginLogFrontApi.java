package com.kelaker.kcommon.user.api.front;

import com.kelaker.kcommon.user.service.UserLoginLogService;
import com.kelaker.kcommon.user.vo.UserLoginLogVo;
import com.kelaker.ktools.web.base.api.BaseApi;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户登录日志
 *
 * @author Felix Huang
 * @since 2022-08-23 14:23:28
 */
@RestController
@RequestMapping("/user/front/login/log")
public class UserLoginLogFrontApi extends BaseApi {
    /**
     * 服务对象
     */
    @Resource
    private UserLoginLogService userLoginLogService;

    /**
     * 取回用户自己的登录日志
     *
     * @return 用户登录日志列表
     */
    @GetMapping("/my")
    public List<UserLoginLogVo> getMyLoginLog() {
        return this.userLoginLogService.getUserLoginLog();
    }

}

