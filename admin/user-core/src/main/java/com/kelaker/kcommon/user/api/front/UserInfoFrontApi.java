package com.kelaker.kcommon.user.api.front;

import com.kelaker.kcommon.common.user.dto.UserInfoShareDto;
import com.kelaker.kcommon.common.user.vo.UserInfoAllVo;
import com.kelaker.kcommon.user.constant.BusinessType;
import com.kelaker.kcommon.user.dto.*;
import com.kelaker.kcommon.user.service.UserInfoService;
import com.kelaker.kcommon.user.vo.QrCodeVo;
import com.kelaker.kcommon.user.vo.SearchUserVo;
import com.kelaker.kcommon.user.vo.UserInfoShareVo;
import com.kelaker.ktools.common.vo.AuthCodeVo;
import com.kelaker.ktools.common.vo.TokenVo;
import com.kelaker.ktools.web.annotation.NoLogin;
import com.kelaker.ktools.web.annotation.TouristVisit;
import com.kelaker.ktools.web.base.api.BaseApi;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户基础信息
 *
 * @author felix huang
 * @since 2021-12-10 10:59:47
 */
@Slf4j
@RestController
@RequestMapping("/user/front/info")
public class UserInfoFrontApi extends BaseApi {

    /**
     * 服务对象
     */
    @Resource
    private UserInfoService userInfoService;

    /**
     * 用户登录
     *
     * @param userLoginDto 用户登录数据
     */
    @NoLogin
    @PostMapping("/login")
    public Object userLogin(@RequestBody @Validated UserLoginDto userLoginDto) {
        userLoginDto.getDeviceInfo().setIp(this.getClientIp());
        return this.userInfoService.userLogin(userLoginDto);
    }

    /**
     * 游客访问
     *
     * @return 用户登录数据
     */
    @NoLogin
    @PostMapping(value = "/tourist/login", produces = "application/json")
    public String touristLogin() {
        return this.userInfoService.touristLogin();
    }

    /**
     * 检查手机号码是否已经注册
     *
     * @param phone 手机号码
     */
    @NoLogin
    @GetMapping("/phone/check/{phone}")
    public boolean checkPhone(@Validated @NotBlank(message = "手机号码不能为空") @PathVariable("phone") String phone) {
        return this.userInfoService.checkPhone(phone);
    }

    /**
     * 给自己的手机号码发送短信
     *
     * @param type 短信类型
     */
    @GetMapping("/send/sms/{type}")
    public void sendSmsMy(@Validated @NotNull(message = "验证码类型不能为空") @PathVariable("type") BusinessType type) {
        // this.userInfoService.sendSmsMy(type);
    }

    /**
     * 忘记密码
     *
     * @param forgotPwdDto 忘记密码dto
     * @return 处理状态
     */
    @NoLogin
    @PutMapping("/pwd/forgot")
    public void forgotPwd(@Validated @RequestBody ForgotPwdDto forgotPwdDto) {
        this.userInfoService.forgotPwd(forgotPwdDto);
    }

    /**
     * 发送短信
     *
     * @param phone 手机号码
     */
    @NoLogin
    @GetMapping("/sms/{phone}")
    public void sendSms(@Validated @NotBlank(message = "手机号码不能为空") @PathVariable("phone") String phone) {
        // this.userInfoService.sendLoginSms(phone);
    }

    /**
     * 初始化密码
     *
     * @param initPwdDto
     */
    @PutMapping("/pwd/init")
    public void initializePwd(@Validated @RequestBody InitPwdDto initPwdDto) {
        this.userInfoService.initializePwd(initPwdDto);
    }

    /**
     * 修改密码
     *
     * @param changePwdDto
     */
    @PutMapping("/pwd/change")
    public void changePwd(@Validated @RequestBody ChangePwdDto changePwdDto) {
        this.userInfoService.changePwd(changePwdDto);
    }

    /**
     * 检查是否有密码
     */
    @GetMapping("/pwd/check")
    public boolean checkHasPwd() {
        return this.userInfoService.checkHasPwd();
    }

    /**
     * 取回个人基础资料
     */
    @GetMapping("/my")
    public UserInfoAllVo getMyProfile() {
        return this.userInfoService.getMyData();
    }

    /**
     * 根据ID查看个人资料(用于查看其他人公开的个人资料)
     *
     * @param userId 用户ID
     */
    @TouristVisit
    @GetMapping("/get/{userId}")
    public UserInfoAllVo getUserProfile(@Validated @NotNull(message = "用户ID不能为空") @PathVariable("userId") Long userId) {
        return this.userInfoService.getUserInfoById(userId);
    }

    /**
     * 刷新token
     * <p>
     * 使用有效期内的token调用，token的有效期得到重置
     * </p>
     */
    @TouristVisit
    @GetMapping("/token/refresh")
    public TokenVo refreshToken() {
        return this.userInfoService.refreshToken();
    }

    /**
     * 用户登出
     */
    @GetMapping("/logout")
    public void logout() {
        this.userInfoService.logout();
    }

    /**
     * 绑定OpenId
     *
     * @param bindOpenIdDto
     */
    @PutMapping("/bind/openid")
    public AuthCodeVo bindOpenId(@Validated @RequestBody BindOpenIdDto bindOpenIdDto) {
        return this.userInfoService.bindOpenId(bindOpenIdDto);
    }

    /**
     * 绑定手机并检查手机号是否已经被其他账号绑定
     *
     * @param bindPhoneDto
     */
    @PostMapping("/bind/phone")
    public AuthCodeVo bindPhone(@Validated @RequestBody BindPhoneDto bindPhoneDto) {
        return this.userInfoService.bindPhone(bindPhoneDto);
    }

    /**
     * 发送重设手机号码短信
     *
     * @param resetPhoneSmsDto 重设手机号码请求
     */
    @PostMapping("/phone/reset/sms")
    public void sendResetPhoneSms(@Validated @RequestBody ResetPhoneSmsDto resetPhoneSmsDto) {
        // this.userInfoService.sendResetPhoneSms(resetPhoneSmsDto);
    }

    /**
     * 重设手机号码
     * 
     * @param resetPhoneDto 重设手机号码
     */
    @PostMapping("/phone/reset")
    public void sendResetPhone(@Validated @RequestBody ResetPhoneDto resetPhoneDto) {
        // this.userInfoService.resetPhone(resetPhoneDto);
    }

    /**
     * 注销绑定
     *
     * @param offUserBindDto
     */
    @PostMapping("/bind/off")
    public void cancelUserByBind(@Validated @RequestBody OffUserBindDto offUserBindDto) {
        this.userInfoService.cancelUser(offUserBindDto);
    }

    /**
     * 取消用户绑定
     *
     * @param cancelUserBindDto 取消绑定类型
     */
    @PostMapping("/bind/cancel")
    public void cancelUserBind(@Validated @RequestBody CancelUserBindDto cancelUserBindDto) {
        this.userInfoService.cancelUserBind(cancelUserBindDto);
    }

    /**
     * 用户注销
     *
     * @param userCancelDto 用户注销资料
     */
    @PutMapping("/off")
    public void userCancel(@Validated @RequestBody UserCancelDto userCancelDto) {
        this.userInfoService.userCancel(userCancelDto);
    }

    /**
     * 检查用户是否能注销
     */
    @GetMapping("/off/check")
    public boolean checkUserCanWriteOff() {
        return this.userInfoService.checkUserCanWriteOff();
    }

    /**
     * 验证原手机
     *
     * @param validatePhoneDto
     */
    @PostMapping("/phone/check")
    public AuthCodeVo validateSourcePhone(@RequestBody @Validated ValidatePhoneDto validatePhoneDto) {
        return this.userInfoService.validateSmsMy(validatePhoneDto);
    }

    /**
     * 创建二维码
     */
    @GetMapping("/create/qrcode")
    public String createQrCode() {
        return this.userInfoService.createQrCodeInfo();
    }

    /**
     * 验证二维码值
     *
     * @param authCode 二维码内容
     */
    @GetMapping("/validate/qrcode/{authCode}")
    public QrCodeVo validateQrCode(
            @Validated @NotBlank(message = "验证码不能为空") @PathVariable("authCode") String authCode) {
        return this.userInfoService.validateQrCode(authCode);
    }

    /**
     * 更改用户名称，唯一凭证
     *
     * @param username ID号
     */
    @PutMapping("/update/username/{username}")
    public boolean updateUsername(@Validated @NotBlank(message = "ID号不能为空") @PathVariable("username") String username) {
        return this.userInfoService.updateUsername(username);
    }

    /**
     * 更改用户昵称，唯一凭证
     *
     * @param nickname 昵称
     */
    @PutMapping("/change/nickname/{nickname}")
    public void updateNickname(@Validated @NotBlank(message = "昵称不能为空") @PathVariable("nickname") String nickname) {
        this.userInfoService.changeNickname(nickname);
    }

    /**
     * 初始化用户数据
     *
     * @param dto 用户基础数据
     */
    @PutMapping("/init")
    public void initUserDefaultData(@Validated @RequestBody UserInfoDefaultDataDto dto) {
        this.userInfoService.initDefaultUserData(dto);
    }

    /**
     * 搜索用户
     *
     * @param searchUserDto 检索内容
     */
    @PostMapping("/search")
    public SearchUserVo searchUserInfo(@RequestBody @Validated SearchUserDto searchUserDto) {
        return this.userInfoService.queryUserInfo(searchUserDto);
    }

    /**
     * 检查用户昵称是否存在
     *
     * @param nickname 用户昵称
     */
    @GetMapping("/check/nickname/{nickname}")
    public boolean checkInfoNickname(
            @Validated @NotBlank(message = "用户昵称D不能为空") @PathVariable("nickname") String nickname) {
        return this.userInfoService.checkInfoNickname(nickname);
    }

    /**
     * 获取用户头像和昵称-数组
     *
     * @param userInfoShareDto
     */
    @TouristVisit
    @PostMapping("/share/list")
    public List<UserInfoAllVo> queryUserInfoExtends(@Validated @RequestBody UserInfoShareDto userInfoShareDto) {
        return this.userInfoService.queryUserInfoSimpleList(userInfoShareDto);
    }

    /**
     * 取回用户分享信息
     *
     * @param userId
     */
    @Deprecated
    @TouristVisit
    @GetMapping("/share/{userId}")
    public UserInfoShareVo getShareRelactionship(@PathVariable("userId") Long userId) {
        return this.userInfoService.getShareUserInfo(userId);
    }

    /**
     * 推荐用户
     */
    @GetMapping("/recommend")
    public List<String> getRUser() {
        return this.userInfoService.randomUserId();
    }
}
