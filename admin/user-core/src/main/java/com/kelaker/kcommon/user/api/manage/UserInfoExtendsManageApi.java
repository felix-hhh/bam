package com.kelaker.kcommon.user.api.manage;

import com.kelaker.kcommon.user.constant.MemberType;
import com.kelaker.kcommon.user.dto.UserMemberChangeDto;
import com.kelaker.kcommon.user.service.UserInfoExtendsService;
import com.kelaker.ktools.web.annotation.InModule;
import com.kelaker.ktools.web.base.api.BaseApi;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户拓展(UserInfoExtends)表控制层
 *
 * @author felix huang
 * @since 2021-12-14 21:26:35
 */
@RestController
@RequestMapping("/user/manage/info/extends")
@InModule(moduleCode = "USER")
public class UserInfoExtendsManageApi extends BaseApi {
    /**
     * 服务对象
     */
    @Resource
    private UserInfoExtendsService userInfoExtendsService;

    @PutMapping("/member/{userId}")
    public void changeMember(@NotNull(message = "用户ID不能为空") @PathVariable("userId") Long userId) {
        UserMemberChangeDto dto = new UserMemberChangeDto();
        dto.setMemberType(MemberType.VIP);
        dto.setMemberTime(300);
        this.userInfoExtendsService.changeMemberType(userId,dto);
    }
}
