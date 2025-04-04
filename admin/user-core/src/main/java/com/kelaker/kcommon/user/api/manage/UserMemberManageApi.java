package com.kelaker.kcommon.user.api.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.user.dto.UserMemberSearchDto;
import com.kelaker.kcommon.user.service.UserMemberService;
import com.kelaker.kcommon.user.vo.UserMemberVo;
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
 * 用户会员(UserMember)表控制层
 *
 * @author Felix Huang
 * @since 2023-05-02 22:42:01
 */
@RestController
@InModule(moduleCode = "USER")
@RequestMapping("/user/manage/member")
public class UserMemberManageApi extends BaseApi {
    /**
     * 服务对象
     */
    @Resource
    private UserMemberService userMemberService;

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 查询结果
     */
    @HasAction(actionCode = "USER_MEMBER:PAGE",actionName = "用户会员列表")
    @PostMapping("/page")
    public IPage<UserMemberVo> pageUserMember(@RequestBody RequestPage<UserMemberSearchDto> searchDto) {
        return this.userMemberService.queryPage(searchDto);
    }
}

