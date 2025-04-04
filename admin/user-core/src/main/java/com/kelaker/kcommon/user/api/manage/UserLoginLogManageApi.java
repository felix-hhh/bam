package com.kelaker.kcommon.user.api.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.user.dto.UserLoginLogSearchDto;
import com.kelaker.kcommon.user.service.UserLoginLogService;
import com.kelaker.kcommon.user.vo.UserLoginLogVo;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.annotation.HasAction;
import com.kelaker.ktools.web.annotation.InModule;
import com.kelaker.ktools.web.base.api.BaseApi;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登录日志
 *
 * @author Felix Huang
 * @since 2022-08-23 14:23:28
 */
@RestController
@InModule(moduleCode = "USER")
@RequestMapping("/user/manage/login/log")
public class UserLoginLogManageApi extends BaseApi {
    /**
     * 服务对象
     */
    @Resource
    private UserLoginLogService userLoginLogService;

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 查询结果
     */
    @HasAction(actionCode = "USER_LOGIN_LOG:PAGE",actionName = "用户登录日志")
    @PostMapping("/page")
    public IPage<UserLoginLogVo> pageUserLoginLog(@RequestBody RequestPage<UserLoginLogSearchDto> searchDto) {
        return this.userLoginLogService.queryPage(searchDto);
    }
}

