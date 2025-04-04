package com.kelaker.kcommon.user.api.front;

import com.kelaker.kcommon.user.dto.UserSettingChangeDto;
import com.kelaker.kcommon.user.service.UserSettingService;
import com.kelaker.ktools.web.base.api.BaseApi;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户设置
 *
 * @author makejava
 * @since 2022-07-28 14:28:00
 */
@RestController
@RequestMapping("/user/front/setting")
public class UserSettingFrontApi extends BaseApi {

    /**
     * 查询当前用户所有设置数据
     */
    @Resource
    private UserSettingService userSettingService;

    /**
     * 取回用户设置
     * @return
     */
    @GetMapping("/get")
    public Map<String, Object> getUserSetting() {
        return this.userSettingService.listUserSetting();
    }

    /**
     * 变更用户配置状态
     *
     * @param userSettingChangeDto 用户配置DTO
     */
    @PutMapping("/change")
    public void insertUserSetting(@Validated @RequestBody UserSettingChangeDto userSettingChangeDto) {
        this.userSettingService.changeUserSetting(userSettingChangeDto);
    }

}

