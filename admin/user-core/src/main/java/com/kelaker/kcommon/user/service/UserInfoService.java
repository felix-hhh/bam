package com.kelaker.kcommon.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.kelaker.kcommon.common.user.dto.UserInfoShareDto;
import com.kelaker.kcommon.common.user.vo.UserInfoAllVo;
import com.kelaker.kcommon.tools.service.WxMicService;
import com.kelaker.kcommon.user.configs.UserConfigProperties;
import com.kelaker.kcommon.user.constant.BusinessType;
import com.kelaker.kcommon.user.constant.CacheConstant;
import com.kelaker.kcommon.user.dao.UserInfoDao;
import com.kelaker.kcommon.user.dto.*;
import com.kelaker.kcommon.user.entity.UserInfo;
import com.kelaker.kcommon.user.entity.UserInfoExtends;
import com.kelaker.kcommon.user.event.UserInitExtendsEvent;
import com.kelaker.kcommon.user.event.UserLoginEvent;
import com.kelaker.kcommon.user.event.UserRegisterEvent;
import com.kelaker.kcommon.user.manage.UserDataManage;
import com.kelaker.kcommon.user.vo.*;
import com.kelaker.ktools.cache.manager.CacheManager;
import com.kelaker.ktools.common.exception.BusinessException;
import com.kelaker.ktools.common.populator.ConvertUtils;
import com.kelaker.ktools.common.utils.*;
import com.kelaker.ktools.common.vo.*;
import com.kelaker.ktools.web.base.service.BaseService;
import com.kelaker.ktools.web.configs.WebConfigProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户信息表(UserInfo)表服务接口
 *
 * @author makejava
 * @since 2021-12-10 10:59:47
 */
@Service
@Slf4j
public class UserInfoService extends BaseService<UserInfoDao, UserInfo> {

    @Resource
    private WebConfigProperties config;

    @Resource
    private CacheManager cacheManager;

    @Resource
    private UserConfigProperties userConfigProperties;

    @Resource
    private ApplicationEventPublisher publisher;

    @Resource
    private UserInfoExtendsService userInfoExtendsService;

    @Resource
    private WxMicService wxMicService;

    @Resource
    private WebConfigProperties webConfigProperties;

    @Resource
    @Lazy
    private UserDataManage userDataManage;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 创建机器人
     *
     * @param dto 创建机器人DTO
     */
    @Transactional(rollbackFor = Exception.class)
    public void createRobot(UserRobotCreateDto dto) {
        String[] nicknames = dto.getNicknames().split(",");
        for (String nickname : nicknames) {
            UserInfo userInfo = new UserInfo();
            userInfo.setNickname(nickname);
            userInfo.setStatus(UserInfo.Status.ENABLE);

            userInfo.setLastLoginDatetime(DateUtil.getCurrentDate());
            userInfo.setUsername(this.userConfigProperties.getUsernamePrefix() + StringUtil.getCurrentDate("yyyyMMddmmss"));
            super.save(userInfo);

            this.pushUserRegisterEvent(null, null, userInfo.getId());

            //初始化用户拓展资料
            this.userInfoExtendsService.initUserInfoExtends(userInfo.getId(), UserInfoExtends.UserRole.ROBOT, dto.getGender());
        }
    }

    /**
     * 游客登录
     */
    public String touristLogin() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(ConvertUtils.convertToLong(StringUtil.getRandomNumberString(10)));
        userInfo.setClientType(ClientType.FRONT_TOURIST.name());
        userInfo.setNickname("游客" + StringUtil.getRandomNumberString(8));
        return this.buildToken(userInfo);
    }

    /**
     * 用户登录，此处登录包含注册功能
     *
     * @param userLoginDto 用户登录dto
     */
    @Transactional(rollbackFor = Exception.class)
    public String userLogin(UserLoginDto userLoginDto) {
        String smsAuthCode = userLoginDto.getSmsAuthCode();
        String passwd = userLoginDto.getPasswd();
        DeviceInfoDto deviceInfo = userLoginDto.getDeviceInfo();
        UserLoginDto.LoginType loginType = userLoginDto.getLoginType();
        if (ValidateUtil.isBlank(loginType)) {
            throw new BusinessException("登录类型错误", 61007);
        }

        switch (loginType) {
            case PWD -> {
                String phone = userLoginDto.getPhone();
                UserInfo userInfo = this.getUserInfoByPhone(phone);
                if (ValidateUtil.isBlank(userInfo)) {
                    throw new BusinessException("账号或密码错误", 61008);
                }
                if (UserInfo.Status.DISABLE.equals(userInfo.getStatus())) {
                    throw new BusinessException("该账号已被禁用");
                }
                String password = this.createUserPwd(userInfo, passwd);
                if (!password.equals(userInfo.getPasswd())) {
                    throw new BusinessException("账号或密码错误", 61009);
                }
                return this.buildToken(userInfo, true, deviceInfo, loginType);
            }
            case SMS -> {
                //短信验证码登录
                String phone = userLoginDto.getPhone();
                this.validateSmsAuthCode(smsAuthCode, phone);
                OtherLoginUserInfoDto loginDto = new OtherLoginUserInfoDto();
                loginDto.setOpenId(phone);
                UserInfo userInfo = this.userRegister(loginDto, loginType, deviceInfo);
                return this.buildToken(userInfo, true, deviceInfo, loginType);
            }
            case WXM_OPENID -> {
                String code = userLoginDto.getCode();
                if (ValidateUtil.isBlank(code)) {
                    throw new BusinessException("微信临时授权码不能为空");
                }
                String openId = wxMicService.getOpenId(code);
                OtherLoginUserInfoDto loginDto = new OtherLoginUserInfoDto();
                loginDto.setOpenId(openId);
                UserInfo userInfo = this.userRegister(loginDto, loginType, deviceInfo);
                return this.buildToken(userInfo, true, deviceInfo, loginType);
            }
            case WXM_PHONE -> {
                String code = userLoginDto.getCode();
                if (ValidateUtil.isBlank(code)) {
                    throw new BusinessException("微信临时授权码不能为空");
                }
                String phone = wxMicService.getPhoneNumber(code);
                OtherLoginUserInfoDto loginDto = new OtherLoginUserInfoDto();
                loginDto.setOpenId(phone);
                UserInfo userInfo = this.userRegister(loginDto, loginType, deviceInfo);
                return this.buildToken(userInfo, true, deviceInfo, loginType);
            }
            default -> throw new BusinessException("登录类型错误", 61013);
        }

    }

    /**
     * 取回个人资料
     *
     * @return 个人资料
     */
    public UserInfoAllVo getMyData() {
        UserInfo userInfo = super.getById(super.getUserId());
        if (ValidateUtil.isBlank(userInfo)) {
            return null;
        }

        return this.convertToVo(userInfo, false);
    }

    /**
     * 通过ID取回个人资料
     *
     * @param userId 用户ID
     */
    public UserInfoAllVo getUserInfoById(Long userId) {
        UserInfo userInfo = super.getById(userId);
        if (ValidateUtil.isBlank(userInfo)) {
            return null;
        }
        return this.convertToVo(userInfo, true);
    }

    /**
     * 取回用户信息，不用缓存
     *
     * @param userId 用户ID
     */
    public UserInfoAllVo getUserInfoByIdNoCache(Long userId) {
        return this.getUserInfoById(userId);
    }

    /**
     * 发送登录注册短信
     *
     * @param phone 手机号码
     */
    /*public String sendLoginSms(String phone) {
        AuthCodeVo smsAuthCode = this.cacheManager.getSmsAuthCode(phone);
        if (ValidateUtil.isNotBlank(smsAuthCode)) {
            Date createDatetime = smsAuthCode.getCreateDatetime();
            Date afterDate = DateUtil.timeAdd(createDatetime, 1L, TimeUnit.MINUTES);
            if (afterDate.after(DateUtil.getCurrentDate())) {
                throw new BusinessException("发送短信过于频繁", 61006);
            }
        }
        String authCode = StringUtil.getRandomNumberString(6);
        log.debug(">>>>>>>>>> 短信验证码：{}", authCode);

        SmsAuthCodeDto dto = new SmsAuthCodeDto();
        dto.setPhone(phone);
        dto.setSmsCode(authCode);
        this.toolsFeignClient.sendSmsAuth(dto);
        this.cacheManager.cacheSmsAuthCode(phone, authCode);
        return authCode;
    }*/

    /**
     * 验证原手机号码验证码
     *
     * @param validatePhoneDto 原手机验证码dto
     */
    public AuthCodeVo validateSmsMy(ValidatePhoneDto validatePhoneDto) {
        BusinessType businessType = validatePhoneDto.getBusinessType();
        String smsAuthCode = validatePhoneDto.getSmsAuthCode();

        this.checkPhoneValidate(smsAuthCode, businessType);

        String validateKey = CacheConstant.CACHE_VALIDATE_PHONE_PREFIX + businessType + super.getUserId();

        return this.createValidateCode(validateKey);
    }

    /**
     * 创建二维码有效码
     */
    public String createQrCodeInfo() {
        QrCodeVo qrCodeVo = new QrCodeVo();
        qrCodeVo.setUserId(super.getUserId());
        String authCode = StringUtil.getRandomString(32);
        qrCodeVo.setAuthCode(authCode);
        qrCodeVo.setCreateDatetime(DateUtil.getCurrentDate());
        this.cacheManager.cacheValue(CacheConstant.QR_CODE_BUSI_PREFIX + authCode, qrCodeVo, 24 * 60 * 60L);
        return authCode;
    }

    public QrCodeVo validateQrCode(String authCode) {
        QrCodeVo qrCodeVo = (QrCodeVo) this.cacheManager.getValue(CacheConstant.QR_CODE_BUSI_PREFIX + authCode);
        if (ValidateUtil.isBlank(qrCodeVo)) {
            this.cacheManager.removeValue(CacheConstant.QR_CODE_BUSI_PREFIX + authCode);
            throw new BusinessException("二维码已失效");
        }
        return qrCodeVo;
    }

    /**
     * 检查手机号码
     *
     * @param phone 手机号码
     */
    public boolean checkPhone(String phone) {
        super.checkNullParameter(phone);
        UserInfo userInfo = this.getUserInfoByPhone(phone);
        return ValidateUtil.isNotBlank(userInfo);
    }

    /**
     * 忘记密码
     *
     * @param forgotPwdDto 忘记密码dto
     */
    public void forgotPwd(ForgotPwdDto forgotPwdDto) {
        String phone = forgotPwdDto.getPhone();
        String smsAuthCode = forgotPwdDto.getSmsAuthCode();
        String newPwd = forgotPwdDto.getNewPwd();

        super.checkNullParameter(phone, smsAuthCode, newPwd);
        AuthCodeVo authCode = this.cacheManager.getSmsAuthCode(phone);
        if (ValidateUtil.isBlank(authCode)) {
            throw new BusinessException("短信验证码错误", 61004);
        }
        UserInfo userInfo = this.getUserInfoByPhone(phone);
        if (ValidateUtil.isBlank(userInfo)) {
            throw new BusinessException("用户不存在", 61003);
        }
        String pwd = this.createUserPwd(userInfo, newPwd);
        userInfo.setPasswd(pwd);
        super.updateById(userInfo);
    }

    /**
     * 初始化用户密码
     *
     * @param initPwdDto 密码
     */
    public void initializePwd(InitPwdDto initPwdDto) {
        String pwd = initPwdDto.getPwd();

        UserInfo userInfo = this.getCurrentUserInfo();

        //如果未绑定手机号，不能修改密码
        if (ValidateUtil.isBlank(userInfo.getPhone())) {
            throw new BusinessException("未绑定手机号不能设置密码");
        }
        //如果用户包含密码，抛出异常
        if (ValidateUtil.isNotBlank(userInfo.getPasswd())) {
            throw new BusinessException("用户密码不成重复设置", 61002);
        }

        this.checkPhoneValidate(initPwdDto.getAuthCode(), BusinessType.CHANGE_PWD);

        String userPwd = this.createUserPwd(userInfo, pwd);
        userInfo.setPasswd(userPwd);
        super.updateById(userInfo);
    }

    /**
     * 检查是否有密码
     *
     * @return 检查结果
     */
    public boolean checkHasPwd() {
        UserInfo userInfo = this.getCurrentUserInfo();
        String passwd = userInfo.getPasswd();
        return ValidateUtil.isNotBlank(passwd);
    }

    /**
     * 更换密码
     *
     * @param changePwdDto 更换密码DTO
     */
    public void changePwd(ChangePwdDto changePwdDto) {
        String newPwd = changePwdDto.getNewPwd();

        super.checkNullParameter(newPwd);

        UserInfo userInfo = this.getCurrentUserInfo();

        this.checkPhoneValidate(changePwdDto.getAuthCode(), BusinessType.CHANGE_PWD);

        newPwd = this.createUserPwd(userInfo, newPwd);
        userInfo.setPasswd(newPwd);
        updateById(userInfo);
    }

    /**
     * 分页查询用户对象
     *
     * @param searchDto 查询DTO
     */
    public IPage<UserInfoAllVo> queryUserInfoByPage(RequestPage<UserInfoSearchDto> searchDto) {
        //TODO 需要根据产品需求修正查询条件
        UserInfoSearchDto data = searchDto.getData();

        String phone = data.getPhone();
        if (ValidateUtil.isNotBlank(phone)) {
            phone = SecurityUtil.aesEncrypt(phone, this.webConfigProperties.getDataSecurityAesKey());
        }
        LambdaQueryChainWrapper<UserInfo> queryChainWrapper = super.lambdaQuery()
                .eq(ValidateUtil.isNotBlank(phone), UserInfo::getPhone, phone)
                .eq(ValidateUtil.isNotBlank(data.getId()), UserInfo::getId, data.getId())
                .like(ValidateUtil.isNotBlank(data.getNickname()), UserInfo::getNickname, data.getNickname())
                .like(ValidateUtil.isNotBlank(data.getUsername()), UserInfo::getUsername, data.getUsername())
                .ge(ValidateUtil.isNotBlank(data.getStartCreateDatetime()), UserInfo::getCreateDatetime, data.getStartCreateDatetime())
                .le(ValidateUtil.isNotBlank(data.getEndCreateDatetime()), UserInfo::getCreateDatetime, data.getEndCreateDatetime());
        String keyword = data.getKeyword();
        if (ValidateUtil.isNotBlank(keyword)) {
            queryChainWrapper
                    .eq(UserInfo::getPhone, SecurityUtil.aesEncrypt(keyword, this.webConfigProperties.getDataSecurityAesKey()))
                    .or()
                    .like(UserInfo::getNickname, keyword)
                    .or()
                    .like(UserInfo::getUsername, keyword);
        }
        IPage<UserInfo> page = queryChainWrapper.page(super.createPage(searchDto));

        return mapPageToTarget(page, userInfo -> this.convertToVo(userInfo, true));
    }

    /**
     * 禁用用户
     *
     * @param userId 用户ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void disableUserInfo(Long userId) {
        UserInfo userInfo = super.getById(userId);
        if (ValidateUtil.isBlank(userInfo)) {
            throw new BusinessException("用户不存在");
        }
        if (UserInfo.Status.DELETE.equals(userInfo.getStatus())) {
            throw new BusinessException("此操作不能对删除的用户操作");
        }

        userInfo.setStatus(UserInfo.Status.DISABLE);
        super.updateById(userInfo);

        //移除登录
        String userIdKey = CacheConstant.CACHE_TOKEN_USER_PREFIX + userId;

        ZSetOperations<String, Object> opsForZSet = this.redisTemplate.opsForZSet();
        Set<Object> range = opsForZSet.range(userIdKey, 0, -1);
        range.forEach(cacheTokenObj -> {
            String cacheTokenStr = ConvertUtils.convertToString(cacheTokenObj);
            this.cacheManager.removeToken(cacheTokenStr);
        });
        this.cacheManager.removeValue(userIdKey);
    }

    /**
     * 启用用户
     *
     * @param userId 用户ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void enableUserInfo(Long userId) {
        UserInfo userInfo = super.getById(userId);
        if (ValidateUtil.isBlank(userInfo)) {
            throw new BusinessException("用户不存在");
        }
        if (UserInfo.Status.DELETE.equals(userInfo.getStatus())) {
            throw new BusinessException("此操作不能对删除的用户操作");
        }

        userInfo.setStatus(UserInfo.Status.ENABLE);
        super.updateById(userInfo);
    }

    /**
     * 刷新当前用户的token
     */
    public TokenVo refreshToken() {
        TokenVo token = super.getToken();

        Long userId = token.getUserTag();
        String tokenStr = token.getToken();
        if (ClientType.FRONT_TOURIST.equals(token.getClientType())) {
            //如果是游客
            if (token.isExpired()) {
                throw new BusinessException("登录已失效");
            }
            TokenVo tokenVo = this.cacheManager.getToken(tokenStr);
            tokenVo.setCreateDatetime(DateUtil.getCurrentDate());
            tokenVo.setExpireSeconds(config.getAppTokenExpireSeconds());

            // 缓存 tokenVo
            this.cacheManager.cacheToken(tokenStr, tokenVo);
            return tokenVo;
        }

        String cacheKey = CacheConstant.CACHE_TOKEN_USER_PREFIX + userId;
        ZSetOperations<String, Object> opsForZSet = this.redisTemplate.opsForZSet();
        Long cacheIndex = opsForZSet.rank(cacheKey, tokenStr);
        log.debug(">>>>>>>>>> token:{},cacheIndex:{}", tokenStr, cacheIndex);
        if (ValidateUtil.isBlank(cacheIndex)) {
            throw new BusinessException("登录已失效");
        }
        UserInfo userInfo = this.getMyUserInfo();
        opsForZSet.add(cacheKey, tokenStr, DateUtil.createTimestamp());

        TokenVo tokenVo = this.cacheManager.getToken(tokenStr);
        tokenVo.setCreateDatetime(DateUtil.getCurrentDate());
        tokenVo.setExpireSeconds(config.getAppTokenExpireSeconds());
        tokenVo.setUserData(this.buildUserData(userInfo));

        // 缓存 tokenVo
        this.cacheManager.cacheToken(tokenStr, tokenVo);
        return tokenVo;
    }

    protected String buildToken(UserInfo theUser) {
        final TokenVo tokenVo = new TokenVo();
        tokenVo.setClientType(ClientType.FRONT_TOURIST);
        tokenVo.setCreateDatetime(new Date());
        tokenVo.setExpireSeconds(config.getAppTokenExpireSeconds());

        tokenVo.setUserTag(theUser.getId());
        tokenVo.setUserData(this.buildUserData(theUser));

        String token = JwtUtil.createToken(config.getJwtSecret(), tokenVo);
        if (ValidateUtil.isBlank(token)) {
            throw new BusinessException("生成token失败", 61005);
        }

        final String tokenTail = tailToken(token);
        this.cacheManager.cacheToken(tokenTail, tokenVo);
        return token;
    }

    /**
     * 生成token
     *
     * @param theUser theUser
     * @return token
     */
    protected String buildToken(UserInfo theUser, boolean rebuild, DeviceInfoDto deviceInfoDto, UserLoginDto.LoginType loginType) {

        // 生成token
        final TokenVo tokenVo = new TokenVo();
        tokenVo.setClientType(ClientType.FRONT);
        tokenVo.setCreateDatetime(new Date());
        tokenVo.setExpireSeconds(config.getAppTokenExpireSeconds());

        // 设置用户唯一标识 & 用户信息
        tokenVo.setUserTag(theUser.getId());
        tokenVo.setUserData(this.buildUserData(theUser));

        if (rebuild) {
            // 生成token
            String token = JwtUtil.createToken(config.getJwtSecret(), tokenVo);
            if (ValidateUtil.isBlank(token)) {
                throw new BusinessException("生成token失败", 61005);
            }

            // 清除原来的 Token Vo
            String userIdKey = CacheConstant.CACHE_TOKEN_USER_PREFIX + theUser.getId();

            ZSetOperations<String, Object> opsForZSet = this.redisTemplate.opsForZSet();

            // 缓存 token
            final String tokenTail = tailToken(token);
            opsForZSet.add(userIdKey, tokenTail, DateUtil.createTimestamp());

            // 缓存 tokenVo
            this.cacheManager.cacheToken(tokenTail, tokenVo);
            ThreadLocalUtil.setTokenInfo(tokenVo);
            if (ValidateUtil.isNotBlank(deviceInfoDto)) {
                String deviceId = SecurityUtil.md5Encrypt(deviceInfoDto.getDeviceId());
                String deviceCacheKey = CacheConstant.CACHE_TOKEN_DEVICE_PREFIX + deviceId;
                String deviceCache = (String) this.cacheManager.getValue(deviceCacheKey);
                if (ValidateUtil.isNotBlank(deviceCache)) {
                    opsForZSet.remove(userIdKey, tokenTail);
                    this.cacheManager.cacheValue(deviceCacheKey, tokenTail, webConfigProperties.getAppTokenExpireSeconds());
                }

                this.pushUserLoginEvent(deviceInfoDto, loginType, theUser.getId());
            }

            Long cacheTokenSize = Optional.ofNullable(opsForZSet.size(userIdKey)).orElse(0L);
            if (this.userConfigProperties.getMaxToken() < cacheTokenSize) {
                Set<Object> range = opsForZSet.range(userIdKey, 0, 0);
                range.forEach(cacheTokenObj -> {
                    String cacheTokenStr = ConvertUtils.convertToString(cacheTokenObj);
                    this.cacheManager.removeToken(cacheTokenStr);
                    opsForZSet.remove(userIdKey, cacheTokenObj);
                });
            }

            return token;
        } else {
            String userIdKey = CacheConstant.CACHE_TOKEN_USER_PREFIX + theUser.getId();
            ZSetOperations<String, Object> opsForZSet = this.redisTemplate.opsForZSet();
            Set<Object> range = opsForZSet.range(userIdKey, 0, -1);
            range.forEach(cacheTokenObj -> {
                String cacheTokenStr = ConvertUtils.convertToString(cacheTokenObj);
                this.cacheManager.cacheToken(cacheTokenStr, tokenVo);
            });
            return super.getToken().getToken();
        }

    }

    /**
     * 后台更换用户密码
     *
     * @param dto 更换密码dto
     */
    public void changeUserPwd(ChangeUserPwdDto dto) {
        Long userId = dto.getUserId();
        UserInfo userInfo = this.getUserInfoOptById(userId).orElseThrow(() -> new BusinessException("用户不存在"));

        String passwd = dto.getPasswd();
        String newPwd = this.createUserPwd(userInfo, passwd);
        userInfo.setPasswd(newPwd);
        super.updateById(userInfo);
    }


    /**
     * 用户取消DTO
     *
     * @param userCancelDto 用户取消对象
     */
    public void userCancel(UserCancelDto userCancelDto) {
        UserInfo myUserInfo = this.getMyUserInfo();

        this.checkPhoneValidate(userCancelDto.getSmsAuthCode(), BusinessType.CANCEL_USER);
        myUserInfo.setStatus(UserInfo.Status.DELETE);
        myUserInfo.setDeleteDatetime(DateUtil.getCurrentDate());
        super.updateById(myUserInfo);
    }

    /**
     * 检查用户是否能注销
     *
     * @return 检查结果
     */
    public boolean checkUserCanWriteOff() {
        return this.checkUserCanWriteOff(super.getUserId());
    }

    /**
     * 根据用户ID检查用户是否能注销
     *
     * @param userId 用户ID
     * @return 检查结果
     */
    public boolean checkUserCanWriteOff(Long userId) {
        // TODO 检查账户
        return true;
    }

    /**
     * 更换昵称
     *
     * @param nickname 昵称
     */
    public void changeNickname(String nickname) {
        Long userId = super.getUserId();
        UserInfo userInfo = this.getUserInfoOptById(userId).orElseThrow(() -> new BusinessException("用户不存在"));
        userInfo.setNickname(nickname);
        //从新构造token
        super.updateById(userInfo);

    }

    /**
     * 完善资料
     *
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void initDefaultUserData(UserInfoDefaultDataDto dto) {
        Long userId = super.getUserId();
        String nickname = dto.getNickname();
        Integer gender = dto.getGender();
        String headerImg = dto.getHeaderImg();
        UserInfo userInfo = this.getUserInfoOptById(userId).orElseThrow(() -> new BusinessException("用户不存在"));
        userInfo.setNickname(nickname);
        UserInfoExtends userInfoExtends = this.userInfoExtendsService.getById(userId);
        Integer extendsGender = userInfoExtends.getGender();
        if (ValidateUtil.isNotBlank(extendsGender)) {
            throw new BusinessException("性别设置后不允许更改");
        }
        userInfoExtends.setGender(gender);
        userInfoExtends.setHeadImgPath(headerImg);
        userInfoExtends.updateById();

        userInfo.setExtendsData(true);
        super.updateById(userInfo);

        UserInitExtendsEvent event = new UserInitExtendsEvent(this);
        ConvertUtils.copyProperties(dto, event);
        event.setUserId(userId);
        this.publisher.publishEvent(event);
    }

    /**
     * 完善资料
     *
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
    public void changeDefaultUserData(UserInfoDefaultDataDto dto) {
        Long userId = dto.getUserId();
        String nickname = dto.getNickname();
        Integer gender = dto.getGender();
        String signature = dto.getSignature();
        String headerImg = dto.getHeaderImg();
        UserInfo userInfo = this.getUserInfoOptById(userId).orElseThrow(() -> new BusinessException("用户不存在"));
        userInfo.setNickname(nickname);
        UserInfoExtends userInfoExtends = this.userInfoExtendsService.getById(userId);
        userInfoExtends.setGender(gender);
        userInfoExtends.setHeadImgPath(headerImg);
        userInfoExtends.setSignature(signature);
        userInfoExtends.updateById();

        userInfo.setExtendsData(true);
        super.updateById(userInfo);
    }

    /**
     * 绑定手机号码
     *
     * @param bindPhoneDto 绑定手机号码DTo
     */
    public AuthCodeVo bindPhone(BindPhoneDto bindPhoneDto) {
        String oldPhone = this.getMyPhone();
        if (ValidateUtil.isNotBlank(oldPhone)) {
            //验证原手机验证码
            String sourcePhoneValidateCode = bindPhoneDto.getSourcePhoneValidateCode();

            this.checkPhoneValidate(sourcePhoneValidateCode, BusinessType.BIND_PHONE);
        }

        String phone = bindPhoneDto.getPhone();
        String smsAuthCode = bindPhoneDto.getSmsAuthCode();
        this.validateSmsAuthCode(smsAuthCode, phone);

        //检查新手机是否拥有账户
        UserInfo userInfo = this.getUserInfoByPhone(phone);
        if (ValidateUtil.isNotBlank(userInfo)) {
            String validateKey = CacheConstant.CACHE_NEXT_BUSI_PREFIX + BusinessType.CANCEL_USER + super.getUserId();
            String valueKey = CacheConstant.CACHE_NEXT_BUSI_VALUE + BusinessType.CANCEL_USER + super.getUserId();
            this.cacheManager.cacheValue(valueKey, phone, 300L);
            return this.createValidateCode(validateKey);
        }
        UserInfo myUserInfo = this.getMyUserInfo();
        myUserInfo.setPhone(phone);
        super.updateById(myUserInfo);
        this.buildToken(myUserInfo, false, null, null);
        return null;
    }

    /**
     * 解除用户绑定的登录方式
     *
     * @param cancelUserPhoneDto
     */
    public void cancelUserBind(CancelUserBindDto cancelUserPhoneDto) {
        BusinessType businessType = cancelUserPhoneDto.getBusinessType();
        UserInfo myUserInfo = this.getMyUserInfo();
        switch (businessType) {
            case UNBIND_APPLEID -> {
                if (ValidateUtil.isBlank(myUserInfo.getPhone()) && ValidateUtil.isBlank(myUserInfo.getWxOpenId())) {
                    throw new BusinessException("无法解除最后一种登录方式的绑定");
                }
                myUserInfo.setAppleOpenId(null);
            }
            case UNBIND_WXOPENID -> {
                if (ValidateUtil.isBlank(myUserInfo.getPhone()) && ValidateUtil.isBlank(myUserInfo.getAppleOpenId())) {
                    throw new BusinessException("无法解除最后一种登录方式的绑定");
                }
                myUserInfo.setWxOpenId(null);
            }
            default -> throw new BusinessException("不支持该业务类型");
        }
        super.updateById(myUserInfo);
    }

    /**
     * 注销绑定用户，当用户存在绑定时候，注销之前账号
     *
     * @param offUserBindDto 注销用户DTO
     */
    @Transactional(rollbackFor = Exception.class)
    public void cancelUser(OffUserBindDto offUserBindDto) {

        BusinessType businessType = offUserBindDto.getBusinessType();
        String valueKey = CacheConstant.CACHE_NEXT_BUSI_VALUE + BusinessType.CANCEL_USER + super.getUserId();
        if (this.checkNextBusiAuthCode(offUserBindDto.getAuthCode(), BusinessType.CANCEL_USER)) {

            UserInfo myUserInfo = this.getMyUserInfo();
            switch (businessType) {
                case BIND_PHONE -> {
                    String phone = (String) this.cacheManager.getValue(valueKey);

                    UserInfo userInfoByPhone = this.getUserInfoByPhone(phone);
                    if (ValidateUtil.isBlank(userInfoByPhone)) {
                        throw new BusinessException("原账号不存在");
                    }
                    this.userCancel(userInfoByPhone);
                    //取回当前用户对象
                    myUserInfo.setPhone(phone);

                }
                case BIND_WXOPENID -> {
                    String wxOpenId = (String) this.cacheManager.getValue(valueKey);

                    UserInfo userInfoByWxOpenId = this.getUserInfoByWxOpenId(wxOpenId);
                    if (ValidateUtil.isBlank(userInfoByWxOpenId)) {
                        throw new BusinessException("原账号不存在");
                    }
                    this.userCancel(userInfoByWxOpenId);

                    //取回当前用户对象
                    myUserInfo.setWxOpenId(wxOpenId);
                }
                case BIND_APPLEID -> {
                    String appleOpenId = (String) this.cacheManager.getValue(valueKey);

                    UserInfo userInfoByAppleOpenId = this.getUserInfoByAppleOpenId(appleOpenId);
                    if (ValidateUtil.isBlank(userInfoByAppleOpenId)) {
                        throw new BusinessException("原账号不存在");
                    }
                    this.userCancel(userInfoByAppleOpenId);

                    //取回当前用户对象
                    myUserInfo.setAppleOpenId(appleOpenId);
                }
                default -> throw new BusinessException("业务类型错误");
            }
            super.updateById(myUserInfo);
        } else {
            throw new BusinessException("信息验证不通过");
        }
    }

    /**
     * 取回用户token
     *
     * @param token token
     * @return tokenVo
     */
    public TokenVo getUserToken(String token) {
        return this.cacheManager.getToken(token);
    }

    /**
     * 修改密里号
     *
     * @param username 用户名称
     */
    public boolean updateUsername(String username) {
        UserInfo userInfo = this.getUserInfoByUsername(username);
        if (ValidateUtil.isNotBlank(userInfo)) {
            throw new BusinessException("密里号已存在");
        }
        userInfo = super.getById(super.getUserId());
        if (ValidateUtil.isNotBlank(userInfo.getChangeUsernameDatetime())) {
            throw new BusinessException("密里号是唯一凭证,只能修改一次");
        }
        userInfo.setUsername(username);
        userInfo.setChangeUsernameDatetime(DateUtil.getCurrentDate());
        return super.updateById(userInfo);
    }

    /**
     * 搜索用户
     *
     * @param searchUserDto
     */
    public SearchUserVo queryUserInfo(SearchUserDto searchUserDto) {

        SearchUserVo svo = new SearchUserVo();
        UserInfo userInfo = this.searchUserInfo(searchUserDto);
        if (ValidateUtil.isNotBlank(userInfo)) {
            Long userId = userInfo.getId();
            if (super.getUserId().equals(userId)) {
                //无法检索自己
                return null;
            }
            UserInfoExtendsVo userInfoExtends = this.userInfoExtendsService.getUserInfoExtendsByUserIdFront(userId);
            svo.setId(userId);
            svo.setPhone(userInfo.getPhone());
            svo.setUsername(userInfo.getUsername());
            svo.setNickname(userInfo.getNickname());
            svo.setHeadImgPath(userInfoExtends.getHeadImgPath());
        } else {
            log.info("暂无用户相关信息");
            return null;
        }
        return svo;
    }

    /**
     * 检查用户昵称是否存在
     *
     * @param nickname 昵称
     */
    public boolean checkInfoNickname(String nickname) {
        List<UserInfo> list = super.lambdaQuery()
                .eq(UserInfo::getNickname, nickname)
                .ne(UserInfo::getStatus, UserInfo.Status.DELETE)
                .list();
        return ValidateUtil.isNotBlank(list);
    }

    /**
     * 获取用户详情
     *
     * @param userId
     */
    public UserInfoAllVo getUserDetail(String userId) {
        UserInfo userInfo = super.lambdaQuery()
                .eq(UserInfo::getId, userId)
                .oneOpt()
                .orElse(null);
        return this.convertToVo(userInfo, true);
    }

    /**
     * 变更用户状态
     *
     * @param userState 用户状态
     */
    public void changeUserState(UserState userState) {
        if (ClientType.FRONT.equals(userState.getClientType())) {
            if (UserState.State.ONLINE.equals(userState.getState())) {
                this.redisTemplate.opsForSet().add(CacheConstant.ONLINE_USER_CACHE_KEY, userState.getUserTag());
            } else {
                this.redisTemplate.opsForSet().remove(CacheConstant.ONLINE_USER_CACHE_KEY, userState.getUserTag());
            }
        }
    }

    /**
     * 随机获取在线用户
     *
     * @return 在线用户列表
     */
    public List<String> randomUserId() {
        SetOperations<String, Object> opsForSet = this.redisTemplate
                .opsForSet();
        Long size = opsForSet.size(CacheConstant.ONLINE_USER_CACHE_KEY);
        return opsForSet
                .randomMembers(CacheConstant.ONLINE_USER_CACHE_KEY, size == null || size > 10 ? 10 : size)
                .stream()
                .filter(userId -> super.getUserId().equals(userId))
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    /**
     * 取回用户在线状态
     *
     * @param userId 用户ID
     */
    public boolean getUserOnlineState(Long userId) {
        String cacheKey = CacheConstant.ONLINE_USER_CACHE_KEY;
        return Boolean.TRUE.equals(this.redisTemplate.opsForSet().isMember(cacheKey, userId));
    }

    /**
     * 查询用户名列表
     *
     * @param username 用户名
     */
    public Set<Long> queryUserInfoByUsername(String username) {
        return super.lambdaQuery()
                .eq(UserInfo::getUsername, username)
                .list()
                .stream()
                .map(UserInfo::getId)
                .collect(Collectors.toSet());
    }

    /**
     * 查询用户名列表
     *
     * @param nickname 用户名
     */
    public Set<Long> queryUserInfoByNickname(String nickname) {
        return super.lambdaQuery()
                .eq(UserInfo::getNickname, nickname)
                .list()
                .stream()
                .map(UserInfo::getId)
                .collect(Collectors.toSet());
    }

    /**
     * 根据手机号码查询用户
     *
     * @param phone 手机号码
     */
    public Set<Long> queryUserInfoByPhone(String phone) {
        return super.lambdaQuery()
                .eq(UserInfo::getPhone, SecurityUtil.aesEncrypt(phone, this.webConfigProperties.getDataSecurityAesKey()))
                .list()
                .stream()
                .map(UserInfo::getId)
                .collect(Collectors.toSet());
    }

    /**
     * 验证下一个业务需要的验证码是否正确
     *
     * @param authCode     验证码
     * @param businessType 业务类型
     */
    public boolean checkNextBusiAuthCode(String authCode, BusinessType businessType) {
        String validateKey = CacheConstant.CACHE_NEXT_BUSI_PREFIX + businessType + super.getUserId();
        AuthCodeVo authCodeVo = (AuthCodeVo) this.cacheManager.getValue(validateKey);
        this.cacheManager.removeValue(validateKey);
        return ValidateUtil.isNotBlank(authCodeVo) && authCodeVo.isValidate(authCode);
    }

    /**
     * 注册类型
     */
    public enum RegisterType {
        /**
         * 微信小程序openId
         */
        WXMINAPPOPENID,
        /**
         * 苹果openId
         */
        APPLEOPENID,
        /**
         * 苹果openId
         */
        GOOGLEOPENID,
        /**
         * 手机号码
         */
        PHONE,
        /**
         * 管理员注册
         */
        MANAGE
    }

    /**
     * 推送用户注册事件
     */
    public void pushUserRegisterEvent(DeviceInfoDto deviceInfoDto, UserLoginDto.LoginType loginType, Long userId) {
        log.debug(">>>>>>>>>> 用户注册：{}", userId);
        UserRegisterEvent userRegisterEvent = new UserRegisterEvent(this);
        userRegisterEvent.setDeviceInfoDto(deviceInfoDto);
        if (ValidateUtil.isNotBlank(loginType)) {
            userRegisterEvent.setRegisterType(loginType.name());
        }
        userRegisterEvent.setUserId(userId);

        this.publisher.publishEvent(userRegisterEvent);
    }

    /**
     * 查询秘里号是否存在
     *
     * @param username 用户名
     */
    public UserInfo getUserInfoByUsername(String username) {
        return super.lambdaQuery()
                .eq(UserInfo::getUsername, username)
                .eq(UserInfo::getStatus, UserInfo.Status.ENABLE)
                .one();
    }

    /**
     * 获取用户分享资料
     *
     * @param userId 用户ID
     */
    public UserInfoShareVo getShareUserInfo(Long userId) {
        UserInfoAllVo userInfoAllVo = this.userDataManage.getUserInfoById(userId);
        return super.objectConvert(userInfoAllVo, UserInfoShareVo.class);
    }

    /**
     * 获取用户昵称和头像数据-数据
     *
     * @param userInfoShareDto
     */
    public List<UserInfoAllVo> queryUserInfoSimpleList(UserInfoShareDto userInfoShareDto) {
        return userInfoShareDto.getUserIds().stream()
                .map(userId -> this.userDataManage.getUserInfoById(userId))
                .collect(Collectors.toList());
    }

    /**
     * 查找用户
     *
     * @param searchUserDto 用户查找DTO
     */
    protected UserInfo searchUserInfo(SearchUserDto searchUserDto) {
        return super.lambdaQuery()
                .eq(UserInfo::getUsername, searchUserDto.getSearchValue())
                .or()
                .eq(UserInfo::getPhone, SecurityUtil.aesEncrypt(searchUserDto.getSearchValue(), this.webConfigProperties.getDataSecurityAesKey()))
                .or()
                .eq(UserInfo::getNickname, searchUserDto.getSearchValue())
                .eq(UserInfo::getStatus, UserInfo.Status.ENABLE)
                .one();
    }

    /**
     * 用户注册
     *
     * @param otherLogin
     * @param loginType
     * @param deviceInfoDto
     */
    private UserInfo userRegister(OtherLoginUserInfoDto otherLogin, UserLoginDto.LoginType loginType, DeviceInfoDto deviceInfoDto) {
        super.checkNullParameter(otherLogin, loginType, deviceInfoDto);

        final String openId = otherLogin.getOpenId();
        UserInfo userInfo = switch (loginType) {
            case SMS, ONECLICK, WXM_PHONE -> this.getUserInfoByPhone(openId);
            case WX, WXM_OPENID -> this.getUserInfoByWxOpenId(openId);
            case APPLEID -> this.getUserInfoByAppleOpenId(openId);
            case PWD -> throw new BusinessException("不支持该种方式");
        };

        //如果用户不存在，走注册逻辑
        if (ValidateUtil.isBlank(userInfo)) {
            //用户注册
            UserInfo saveUserInfo = new UserInfo();
            UserInfoExtends.UserRole userRole = UserInfoExtends.UserRole.NORMAL;
            switch (loginType) {
                case SMS, ONECLICK, WXM_PHONE -> saveUserInfo.setPhone(openId);
                case WX, WXM_OPENID -> saveUserInfo.setWxOpenId(openId);
                case APPLEID -> saveUserInfo.setAppleOpenId(openId);
            }

            saveUserInfo.setCreateDatetime(DateUtil.getCurrentDate());
            saveUserInfo.setLastLoginDatetime(DateUtil.getCurrentDate());
            saveUserInfo.setStatus(UserInfo.Status.ENABLE);
            saveUserInfo.setUsername(this.userConfigProperties.getUsernamePrefix() + StringUtil.getCurrentDate("yyyyMMddmmss"));
            saveUserInfo.setNickname(saveUserInfo.getNickname() == null ? "m" + StringUtil.getRandomNumberString(12) : saveUserInfo.getNickname());
            //此处短信注册不设置密码
            super.save(saveUserInfo);
            userInfo = saveUserInfo;
            //注册日志
            this.pushUserRegisterEvent(deviceInfoDto, loginType, saveUserInfo.getId());
            //初始化用户拓展资料
            this.userInfoExtendsService.initUserInfoExtends(userInfo.getId(), UserInfoExtends.UserRole.NORMAL);
        } else {
            if (UserInfo.Status.DISABLE.equals(userInfo.getStatus())) {
                throw new BusinessException("该账号已被禁用");
            }
            userInfo.setLastLoginDatetime(DateUtil.getCurrentDate());
            super.updateById(userInfo);
        }
        return userInfo;
    }

    /**
     * 登录登录事件
     */
    private void pushUserLoginEvent(DeviceInfoDto deviceInfoDto, UserLoginDto.LoginType loginType, Long userId) {
        log.debug(">>>>>>>>>> 用户登录：{}", userId);
        UserLoginEvent userLoginEvent = new UserLoginEvent(this);
        userLoginEvent.setDeviceInfoDto(deviceInfoDto);
        userLoginEvent.setLoginType(loginType.name());
        userLoginEvent.setUserId(userId);

        this.publisher.publishEvent(userLoginEvent);
    }

    /**
     * 用户取消DTO
     *
     * @param userInfo 用户ID
     */
    private void userCancel(UserInfo userInfo) {
        userInfo.setDeleteDatetime(DateUtil.getCurrentDate());
        userInfo.setStatus(UserInfo.Status.DELETE);
        super.updateById(userInfo);
    }

    /**
     * 取回当前账户的手机号码
     *
     * @return 手机号码
     */
    private String getMyPhone() {
        Map<String, Object> userData = super.getToken().getUserData();
        if (ValidateUtil.isNotBlank(userData)) {
            return ConvertUtils.convertToString(userData.get("phone"));
        }
        return null;
    }

    /**
     * 验证手机验证码是否正确
     *
     * @param smsAuthCode 手机验证码
     * @param phone       手机号
     */
    private void validateSmsAuthCode(String smsAuthCode, String phone) {
        AuthCodeVo smsAuthCodeVo = this.cacheManager.getSmsAuthCode(phone);
        if (ValidateUtil.isBlank(smsAuthCodeVo)) {
            throw new BusinessException("短信验证码错误", 61011);
        }
        if (!smsAuthCodeVo.isValidate(smsAuthCode)) {
            throw new BusinessException("短信验证码错误", 61012);
        }
        this.cacheManager.removeSmsAuthCode(phone);
    }

    /**
     * 旧手机验证通过后的验证码验证
     *
     * @param authCode 验证码
     * @param type     业务
     */
    private void checkPhoneValidate(String authCode, BusinessType type) {
        if (ValidateUtil.isBlank(authCode)) {
            throw new BusinessException("原手机校验码不能为空");
        }
        String cacheKey = CacheConstant.CACHE_VALIDATE_PHONE_PREFIX + type + super.getUserId();

        this.checkAuthCode(cacheKey, authCode);

    }

    /**
     * 检查验证码是否正确
     *
     * @param cacheKey 缓存key
     * @param authCode 验证码
     */
    private void checkAuthCode(String cacheKey, String authCode) {
        AuthCodeVo authCodeVo = (AuthCodeVo) this.cacheManager.getValue(cacheKey);
        if (ValidateUtil.isBlank(authCodeVo)) {
            throw new BusinessException("验证码错误", 61011);
        }
        if (!authCodeVo.isValidate(authCode)) {
            throw new BusinessException("验证码错误", 61012);
        }
        this.cacheManager.removeValue(cacheKey);

    }

    /**
     * 检查密码是否正确
     *
     * @param userInfo
     * @param checkPasswd
     */
    private void checkPasswdEquals(UserInfo userInfo, String checkPasswd) {
        String userPwd = this.createUserPwd(userInfo, checkPasswd);
        if (!userPwd.equals(userInfo.getPasswd())) {
            throw new BusinessException("密码验证不通过");
        }
    }

    /**
     * 发送二级验证码
     *
     * @param validateKey 缓存的key
     */
    private AuthCodeVo createValidateCode(String validateKey) {
        String authCode = StringUtil.getRandomNumberString(6);

        AuthCodeVo authCodeVo = new AuthCodeVo();
        authCodeVo.setAuthCode(authCode);
        Date currentDate = DateUtil.getCurrentDate();
        authCodeVo.setCreateDatetime(currentDate);
        authCodeVo.setExpireTime(DateUtil.timeAdd(currentDate, 5L, TimeUnit.MINUTES));

        this.cacheManager.cacheValue(validateKey, authCodeVo, 300L);
        return authCodeVo;
    }

    /**
     * 取回我的用户对象
     */
    private UserInfo getMyUserInfo() {
        Long userId = super.getUserId();
        return super.getById(userId);
    }

    /**
     * 构建需要存放的用户数据
     *
     * @param userInfo userInfo
     * @return 用户数据
     */
    private Map<String, Object> buildUserData(UserInfo userInfo) {
        Map<String, Object> dataMap = new HashMap<>(2);
        dataMap.put("phone", userInfo.getPhone());
        dataMap.put("nickname", userInfo.getNickname());
        dataMap.put("username", userInfo.getUsername());
        dataMap.put("clientType", Optional.ofNullable(userInfo.getClientType()).orElse(ClientType.FRONT.name()));
        UserInfoExtends userInfoExtends = this.userInfoExtendsService.getById(userInfo.getId());
        if (ValidateUtil.isNotBlank(userInfoExtends)) {
            dataMap.put("headImgPath", userInfoExtends.getHeadImgPath());
            dataMap.put("signature", userInfoExtends.getSignature());
            dataMap.put("memberType", userInfoExtends.getMemberType() != null ? userInfoExtends.getMemberType().getValue() : null);
            dataMap.put("authLevel", userInfoExtends.getAuthLevel());
        }
        dataMap.put("extendsData", userInfo.isExtendsData());
        return dataMap;
    }

    /**
     * 获取token 第三段
     *
     * @param token token
     * @return token第三段
     */
    private String tailToken(String token) {
        return token.substring(token.lastIndexOf(".") + 1);
    }

    /**
     * 设置用户密码
     *
     * @param userInfo 用户信息
     * @param pwd      密码
     */
    private String createUserPwd(UserInfo userInfo, String pwd) {
        return SecurityUtil.md5Encrypt(SecurityUtil.md5Encrypt(pwd) + userInfo.getId());
    }

    /**
     * 取回当前登录用户对象
     */
    private UserInfo getCurrentUserInfo() {
        Long userId = super.getUserId();
        UserInfo userInfo = super.getById(userId);
        //如果用户找不到，表明用户token校验逻辑出现问题
        if (ValidateUtil.isBlank(userInfo)) {
            throw new BusinessException("用户数据异常", 61001);
        }
        return userInfo;
    }

    /**
     * 转换对象到Vo
     *
     * @param userInfo
     */
    private UserInfoAllVo convertToVo(UserInfo userInfo, boolean cache) {
        UserInfoAllVo userInfoVo = super.objectConvert(userInfo, UserInfoAllVo.class);

        Long userId = userInfoVo.getId();
        UserInfoExtends userInfoExtends;
        if (cache) {
            userInfoExtends = this.userInfoExtendsService.getUserById(userId);
        } else {
            userInfoExtends = this.userInfoExtendsService.getUserByIdNoCache(userId);
        }

        ConvertUtils.copyProperties(userInfoExtends, userInfoVo);
        if (ValidateUtil.isNotBlank(userInfo.getPhone())) {
            userInfoVo.setPhone(StringUtil.overlay(userInfo.getPhone(), "****", 3, 7));
        }
        userInfoVo.setStatusStr(userInfo.getStatus().getRemark());
        userInfoVo.setBindWxOpenId(ValidateUtil.isNotBlank(userInfo.getWxOpenId()));
        userInfoVo.setBindAppleOpenId(ValidateUtil.isNotBlank(userInfo.getAppleOpenId()));
        userInfoVo.setBindPhone(ValidateUtil.isNotBlank(userInfo.getPhone()));
        userInfoVo.setChangedUsername(ValidateUtil.isNotBlank(userInfo.getChangeUsernameDatetime()));
        userInfoVo.setOnline(this.getUserOnlineState(userId));
        return userInfoVo;
    }

    /**
     * 用户登出
     */
    public void logout() {
        Long userId = super.getUserId();
        String tokenStr = super.getToken().getToken();
        String userIdKey = CacheConstant.CACHE_TOKEN_USER_PREFIX + userId;
        Long tokenIndex = this.redisTemplate.opsForZSet().rank(userIdKey, tokenStr);
        if (ValidateUtil.isNotBlank(tokenIndex)) {
            this.redisTemplate.opsForZSet().remove(userIdKey, tokenStr);
        }
        this.cacheManager.removeToken(tokenStr);
    }

    /**
     * 取回用户ID
     *
     * @param userId
     */
    private Optional<UserInfo> getUserInfoOptById(Long userId) {
        return super.lambdaQuery().eq(UserInfo::getId, userId).oneOpt();
    }

    /**
     * 通过手机号码取回用户
     *
     * @param phone 手机好吗
     * @return 用户实体
     */
    private UserInfo getUserInfoByPhone(String phone) {
        super.checkNullParameter(phone);
        phone = SecurityUtil.aesEncrypt(phone, this.webConfigProperties.getDataSecurityAesKey());
        return super.lambdaQuery()
                .eq(UserInfo::getPhone, phone)
                .ne(UserInfo::getStatus, UserInfo.Status.DELETE)
                .one();
    }

    /**
     * 根据openid取回用户
     *
     * @param openid openid
     * @return 用户实体
     */
    private UserInfo getUserInfoByWxOpenId(String openid) {
        return super.lambdaQuery()
                .eq(UserInfo::getWxOpenId, openid)
                .ne(UserInfo::getStatus, UserInfo.Status.DELETE)
                .one();
    }

    /**
     * 根据openid取回用户
     *
     * @param openid openid
     * @return 用户实体
     */
    private UserInfo getUserInfoByAppleOpenId(String openid) {
        return super.lambdaQuery()
                .eq(UserInfo::getAppleOpenId, openid)
                .ne(UserInfo::getStatus, UserInfo.Status.DELETE)
                .one();
    }


}
