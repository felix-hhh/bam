package com.kelaker.kcommon.user.api.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.common.user.dto.UserInfoShareDto;
import com.kelaker.kcommon.common.user.vo.UserInfoAllVo;
import com.kelaker.kcommon.user.dto.ChangeUserPwdDto;
import com.kelaker.kcommon.user.dto.UserInfoDefaultDataDto;
import com.kelaker.kcommon.user.dto.UserInfoSearchDto;
import com.kelaker.kcommon.user.dto.UserRobotCreateDto;
import com.kelaker.kcommon.user.service.UserInfoService;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.annotation.HasAction;
import com.kelaker.ktools.web.annotation.InModule;
import com.kelaker.ktools.web.base.api.BaseApi;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 用户基础信息
 *
 * @author makejava
 * @since 2021-12-10 10:59:47
 */
@RestController
@RequestMapping("/user/manage/info")
@InModule(moduleCode = "USER")
public class UserInfoManageApi extends BaseApi {

    /**
     * 服务对象
     */
    @Resource
    private UserInfoService userInfoService;

    /**
     * 分页查询用户信息
     *
     * @param searchVo
     */
    @HasAction(actionCode = "USER_INFO:PAGE", actionName = "用户信息列表")
    @PostMapping("/page")
    public IPage<UserInfoAllVo> queryUserInfo(@RequestBody RequestPage<UserInfoSearchDto> searchVo) {
        return this.userInfoService.queryUserInfoByPage(searchVo);
    }

    /**
     * 启用用户
     *
     * @param userId 用户ID
     */
    @HasAction(actionCode = "USER_INFO:ENABLE", actionName = "启用用户")
    @PutMapping("/enable/{userId}")
    public void enableUserInfo(@PathVariable("userId") @NotNull(message = "用户ID不能为空") Long userId) {
        this.userInfoService.enableUserInfo(userId);
    }

    /**
     * 禁用用户
     *
     * @param userId 用户ID
     */
    @HasAction(actionCode = "USER_INFO:DISABLE", actionName = "禁用用户")
    @PutMapping("/disable/{userId}")
    public void disableUserInfo(@PathVariable("userId") @NotNull(message = "用户ID不能为空") Long userId) {
        this.userInfoService.disableUserInfo(userId);
    }

    /**
     * 根据用户ID取回用户详情
     *
     * @param userId 用户ID
     */
    @HasAction(actionCode = "USER_INFO:PROFILE",actionName = "获取用户资料")
    @GetMapping("/get/{userId}")
    public UserInfoAllVo getUserProfile(@Validated @NotNull(message = "用户ID不能为空") @PathVariable("userId") Long userId) {
        return this.userInfoService.getUserInfoByIdNoCache(userId);
    }

    /**
     * 根据用户名取回用户ID
     *
     * @param username 用户名
     */
    @GetMapping("/get/username/{username}")
    public Set<Long> queryUserInfoByUsername(@Validated @NotBlank(message = "用户名不能为空") @PathVariable("username") String username) {
        return this.userInfoService.queryUserInfoByUsername(username);
    }

    /**
     * 根据手机号码取回用户ID
     *
     * @param phone 用户名
     */
    @GetMapping("/get/phone/{phone}")
    public Set<Long> queryUserInfoByPhone(@Validated @NotBlank(message = "手机号码不能为空") @PathVariable("phone") String phone) {
        return this.userInfoService.queryUserInfoByPhone(phone);
    }

    /**
     * 根据昵称取回用户ID
     *
     * @param nickname 昵称
     */
    @GetMapping("/get/nickname/{nickname}")
    public Set<Long> queryUserInfoByNickname(@Validated @NotBlank(message = "昵称不能为空") @PathVariable("nickname") String nickname) {
        return this.userInfoService.queryUserInfoByNickname(nickname);
    }

    /**
     * 修改用户密码
     *
     * @param dto 密码变更dto
     */
    @HasAction(actionCode = "USER_INFO:RESET_PASSWD", actionName = "重置用户密码")
    @PutMapping(value = "/pwd/change")
    public void changeUserPasswd(@RequestBody @Validated ChangeUserPwdDto dto) {
        this.userInfoService.changeUserPwd(dto);
    }

    /**
     * 检查用户是否能注销
     *
     * @param userId 用户ID
     */
    @PutMapping(value = "/off/check/{userId}")
    public boolean checkUserCanWriteOff(@RequestBody @Validated @NotNull(message = "用户ID不能为空") @PathVariable("userId") Long userId) {
        return this.userInfoService.checkUserCanWriteOff(userId);
    }

    /**
     * 获取用户头像和昵称-数组
     *
     * @param userInfoShareDto
     */
    @PostMapping("/share/list")
    public List<UserInfoAllVo> queryUserInfoExtends(@Validated @RequestBody UserInfoShareDto userInfoShareDto) {
        return this.userInfoService.queryUserInfoSimpleList(userInfoShareDto);
    }

    /**
     * 创建机器人
     *
     * @param dto
     */
    @PostMapping("/robot/create")
    public void createUserRobot(@Validated @RequestBody UserRobotCreateDto dto) {
        this.userInfoService.createRobot(dto);
    }

    /**
     * 修改用户基础数据
     *
     * @param dto
     */
    @HasAction(actionCode = "USER_INFO:CHANGE",actionName = "修改用户资料")
    @PutMapping("/change")
    public void initUserDefaultData(@Validated @RequestBody UserInfoDefaultDataDto dto) {
        this.userInfoService.changeDefaultUserData(dto);
    }
}
