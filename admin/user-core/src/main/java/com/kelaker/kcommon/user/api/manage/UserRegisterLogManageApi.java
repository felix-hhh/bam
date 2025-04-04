package com.kelaker.kcommon.user.api.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.user.dto.UserRegisterLogSearchDto;
import com.kelaker.kcommon.user.service.UserRegisterLogService;
import com.kelaker.kcommon.user.vo.UserRegisterLogVo;
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
 * 用户注册信息
 *
 * @author Felix Huang
 * @since 2022-08-23 14:43:54
 */
@RestController
@InModule(moduleCode = "USER")
@RequestMapping("/user/manage/register/log")
public class UserRegisterLogManageApi extends BaseApi {

    /**
     * 服务对象
     */
    @Resource
    private UserRegisterLogService userRegisterLogService;


    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 查询结果
     */
    @HasAction(actionCode = "USER_REGISTER_LOG:PAGE",actionName = "用户注册日志")
    @PostMapping("/page")
    public IPage<UserRegisterLogVo> pageUserRegisterLog(@RequestBody RequestPage<UserRegisterLogSearchDto> searchDto) {
        return this.userRegisterLogService.queryPage(searchDto);
    }
}

