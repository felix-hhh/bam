package com.kelaker.kcommon.user.api.front;

import com.kelaker.kcommon.user.service.UserInviteRecordService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 邀请码
 */
@RestController
@RequestMapping("/user/front/invite")
public class UserInviteRecordFrontApi {

    @Resource
    private UserInviteRecordService recordService;
    
}
