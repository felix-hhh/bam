package com.kelaker.kcommon.user.api.front;

import com.kelaker.kcommon.user.service.UserMemberService;
import com.kelaker.kcommon.user.vo.UserMemberVo;
import com.kelaker.ktools.web.base.api.BaseApi;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户会员(UserMember)表控制层
 *
 * @author Felix Huang
 * @since 2023-05-02 22:42:01
 */
@RestController
@RequestMapping("/user/front/member")
public class UserMemberFrontApi extends BaseApi {
    /**
     * 服务对象
     */
    @Resource
    private UserMemberService userMemberService;

    /**
     * 取回我的会员历史
     */
    @GetMapping("/list/my")
    public List<UserMemberVo> listMyMember() {
        return this.userMemberService.listMyMember();
    }

}

