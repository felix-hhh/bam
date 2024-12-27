package com.kelaker.kcommon.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.common.system.vo.SysAdminInfoSimpleVo;
import com.kelaker.kcommon.system.constant.SystemConstant;
import com.kelaker.kcommon.system.dao.SysAdminInfoDao;
import com.kelaker.kcommon.system.dto.*;
import com.kelaker.kcommon.system.entity.SysAction;
import com.kelaker.kcommon.system.entity.SysAdminInfo;
import com.kelaker.kcommon.system.entity.SysAdminInfoRoleLink;
import com.kelaker.kcommon.system.entity.SysAdminRole;
import com.kelaker.kcommon.system.vo.AdminInfoBaseVo;
import com.kelaker.kcommon.system.vo.SysActionVo;
import com.kelaker.kcommon.system.vo.SysAdminInfoVo;
import com.kelaker.kcommon.system.vo.SysModuleTreeVo;
import com.kelaker.ktools.cache.annotation.CacheIt;
import com.kelaker.ktools.cache.manager.CacheManager;
import com.kelaker.ktools.common.constants.Constant;
import com.kelaker.ktools.common.exception.BusinessException;
import com.kelaker.ktools.common.populator.ConvertUtils;
import com.kelaker.ktools.common.utils.JwtUtil;
import com.kelaker.ktools.common.utils.ThreadLocalUtil;
import com.kelaker.ktools.common.utils.ValidateUtil;
import com.kelaker.ktools.common.vo.ClientType;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.common.vo.TokenVo;
import com.kelaker.ktools.web.base.service.BaseService;
import com.kelaker.ktools.web.configs.WebConfigProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 管理员信息(AdminInfo)表服务实现类
 *
 * @author felix huang
 * @since 2020-03-27 11:42:56
 */
@Slf4j
@Service
public class SysAdminInfoService extends BaseService<SysAdminInfoDao, SysAdminInfo> {

    @Resource
    private SysAdminRoleActionLinkService sysAdminRoleActionLinkService;

    @Resource
    private SysAdminInfoRoleLinkService sysAdminInfoRoleLinkService;

    @Resource
    private SysAdminRoleService sysAdminRoleService;

    @Resource
    private WebConfigProperties config;

    @Resource
    private VerifyCodeService verifyCodeService;

    @Resource
    private CacheManager cacheManager;

    @Resource
    private SysAdminLogService sysAdminLogService;

    @Resource
    private SysActionService sysActionService;

    /**
     * 添加管理员
     *
     * @param dto {@link SysAdminAddDto}
     * @throws BusinessException 如果账号已存在
     */
    @Transactional(rollbackFor = Exception.class)
    public SysAdminInfoSimpleVo addAdmin(SysAdminAddDto dto) {
        dto.setUsername(dto.getUsername().toLowerCase());
        if (dto.getUsername().equals(config.getAdminName())) {
            throw new BusinessException("账号已存在");
        }
        findByUsername(dto.getUsername())
                .ifPresent((adminInfo) -> {
                    throw new BusinessException("账号已存在");
                });

        SysAdminInfo needToSave = super.objectConvert(dto, SysAdminInfo.class);
        needToSave.setStatus(SysAdminInfo.Status.NORMAL);
        needToSave.setPasswd(encodePassword(dto.getPassword(), dto.getUsername()));

        // 记录用户
        super.save(needToSave);
        return this.objectConvert(needToSave, SysAdminInfoSimpleVo.class);
    }

    /**
     * 管理员登录
     *
     * @param dto {@link SysAdminLoginDto}
     * @return token
     * @throws BusinessException 如果登录失败
     */
    public String adminLogin(SysAdminLoginDto dto, String ip) {
        // 1.校验验证码
        if (!verifyCodeService.verifyImageVerifyCode(dto.getIdentity(), dto.getVerifyCode())) {
            throw new BusinessException("验证码错误");
        }

        SysAdminInfo theSysAdminInfo;
        if (dto.getUsername().equals(config.getAdminName())) {
            if (encodePassword(dto.getPassword(), config.getAdminName()).equalsIgnoreCase(config.getAdminPwd())) {
                theSysAdminInfo = this.getSuperAdminInfo();
            } else {
                throw new BusinessException("密码错误");
            }
        } else {
            // 2.校验密码
            theSysAdminInfo = verifyAdminLoginInfo(dto);

            // 3.校验状态
            if (SysAdminInfo.Status.DISABLE.equals(theSysAdminInfo.getStatus())) {
                throw new BusinessException("账号已禁用");
            }
            // 记录管理员登录日志
//            sysAdminLogService.writeLog(theSysAdminInfo, ip);
        }

        // 4.生成token
        final String token = buildToken(theSysAdminInfo);

        // 5.更新登录时间
        theSysAdminInfo.setLastLoginDatetime(new Date());
        updateById(theSysAdminInfo);

        return token;
    }

    /**
     * 微信登录
     *
     * @param openId
     * @param ip
     */
    public String loginByOpenId(String openId, String ip) {
        SysAdminInfo sysAdminInfo = this.findByOpenId(openId);
        if (ValidateUtil.isBlank(sysAdminInfo)) {
            throw new BusinessException("账号不存在");
        } else {
            // 校验状态
            if (SysAdminInfo.Status.DISABLE.equals(sysAdminInfo.getStatus())) {
                throw new BusinessException("账号已禁用");
            }
            // 记录管理员登录日志
            sysAdminLogService.writeLog(sysAdminInfo, ip);

            sysAdminInfo.setLastLoginDatetime(new Date());
            String token = this.buildToken(sysAdminInfo);
            super.updateById(sysAdminInfo);
            return token;
        }
    }

    /**
     * 当前登录用户绑定微信
     *
     * @param openId openId
     */
    public void bindOpenId(String openId) {
        Long userId = this.getLoggingUserIdSafely();
        // 一个微信之内绑定一个账号
        SysAdminInfo sysAdminInfo = this.findByOpenId(openId);
        if (ValidateUtil.isNotBlank(sysAdminInfo)) {
            if (userId.equals(sysAdminInfo.getId())) {
                throw new BusinessException("该账号已绑定微信");
            } else {
                throw new BusinessException("该微信已绑定其他账号");
            }
        } else {
            SysAdminInfo loginUser = new SysAdminInfo();
            loginUser.setId(userId);
            loginUser.setOpenId(openId);
            super.updateById(loginUser);
        }
    }

    /**
     * 解绑微信
     */
    public void unBindOpenId() {
        Long userId = this.getLoggingUserIdSafely();
        SysAdminInfo sysAdminInfo = new SysAdminInfo();
        sysAdminInfo.setId(userId);
        sysAdminInfo.setOpenId(null);
        super.updateById(sysAdminInfo);
    }

    /**
     * 刷新 Token
     */
    public String refreshToken() {
        return buildToken(getAdminByIdThrowExpIfNotExist(getLoggingUserIdSafely()));
    }

    /**
     * 用户注销登录
     *
     * @param request {@link HttpServletRequest}
     */
    public void logout(HttpServletRequest request) {
        final String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.startsWithIgnoreCase(token, Constant.TOKEN_HEADER_PREFIX)) {
            // remove token
            final String cacheKey = token.substring(Constant.TOKEN_HEADER_PREFIX.length());
            cacheManager.removeToken(cacheKey);
            return;
        }
        throw new BusinessException("非法Token");
    }

    /**
     * 登出-适用于分布式场景
     */
    public void logout() {
        TokenVo tokenVo = super.getToken();
        String token = ConvertUtils.convertToString(cacheManager.getValue(String.valueOf(tokenVo.getUserTag())));
        cacheManager.removeToken(token);
        cacheManager.removeValue(String.valueOf(tokenVo.getUserTag()));
    }

    /**
     * 获取token 第三段
     *
     * @param token token
     * @return token第三段
     */
    private String tailToken(String token) {
        final String[] splitTokens = token.split("\\.");
        if (splitTokens.length != 3) {
            throw new BusinessException("生成Token失败");
        }
        return splitTokens[2];
    }

    /**
     * 构建需要存放的用户数据
     *
     * @param sysAdminInfo adminInfo
     * @return 用户数据
     */
    private Map<String, Object> buildUserData(SysAdminInfo sysAdminInfo) {
        Map<String, Object> dataMap = new HashMap<>(2);
        dataMap.put("username", sysAdminInfo.getUsername());
        dataMap.put("nickname", sysAdminInfo.getNickname());
        return dataMap;
    }

    /**
     * 管理员授予角色
     *
     * @param dto {@link SysRoleBindAdminsDto}
     */
    public void grantRoles(SysRoleBindAdminsDto dto) {
        // 1.确保管理员存在
        if (!CollectionUtils.isEmpty(dto.getAdminIds())) {
            if (super.listByIds(dto.getAdminIds()).size() != dto.getAdminIds().size()) {
                throw new BusinessException("管理员ID列表数据不正确");
            }
        }

        // 2.确保权限信息存在
        sysAdminRoleService.getOptById(dto.getRoleId())
                .orElseThrow(() -> new BusinessException("角色代码不存在"));

        // 3.授权
        sysAdminInfoRoleLinkService.batchGrantRole(dto.getAdminIds(),
                dto.getRoleId(),
                getLoggingUserIdSafely());
    }

    /**
     * 管理员信息分页查询
     *
     * @param dto dto
     * @return 分页信息
     */
    public IPage<SysAdminInfoVo> pageAdminInfo(@RequestBody RequestPage<SysAdminInfoSearchDto> dto) {
        SysAdminInfoSearchDto queryDto = dto.getData();

        IPage<SysAdminInfo> sourcePage = super.lambdaQuery()
                .like(ValidateUtil.isNotBlank(queryDto.getUsername()), SysAdminInfo::getUsername, queryDto.getUsername())
                .page(super.createPage(dto));
        return super.mapPageToTarget(sourcePage, this::entityToVo);
    }

    /**
     * 获取当前登录用户的权限列表
     *
     * @return 权限列表
     */
    public List<SysActionVo> listLoginAdminGrantedActions() {
        String username = ConvertUtils.convertToString(super.getToken().getUserData().get("username"));
        if (username.equals(config.getAdminName())) {
            return sysActionService.getActionVoList();
        } else {
            final long adminId = getLoggingUserIdSafely();
            return listGrantedActionsByAdminId(adminId).stream()
                    .map(sysActionService::entityToVo)
                    .collect(Collectors.toList());
        }
    }

    /**
     * 根据管理员ID查询管理员所持有的功能权限
     *
     * @param adminId 管理员ID
     * @return 功能列表
     */
    public Set<SysAction> listGrantedActionsByAdminId(long adminId) {
        // 查询用户已经授予的角色列表
        final List<SysAdminRole> roles = sysAdminInfoRoleLinkService.listGrantedRolesByAdminId(adminId);

        // 查询角色授予的功能列表 && 过滤已经禁用的角色
        return sysAdminRoleActionLinkService.listGrantedActionsByRoleCodes(roles.stream()
                .filter(adminRole -> {
                    return SysAdminRole.Status.NORMAL.equals(adminRole.getStatus());
                })
                .map(SysAdminRole::getId)
                .collect(Collectors.toSet())
        );
    }

    /**
     * 获取该角色已经绑定的管理员列表
     *
     * @param roleCode 角色代码
     * @return 管理员列表
     */
    public List<SysAdminInfoVo> listBindAdminVosByRoleCode(Long roleCode) {
        final List<Long> adminIds = sysAdminInfoRoleLinkService.listByRoleCode(roleCode).stream()
                .map(SysAdminInfoRoleLink::getInfoId)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(adminIds)) {
            return Collections.emptyList();
        }
        List<SysAdminInfo> sysAdminInfos = super.listByIds(adminIds);
        return super.mapListToTarget(sysAdminInfos, this::entityToVo);
    }

    /**
     * 根据管理员ID获取管理员的树形模块
     *
     * @param adminId 管理员ID
     * @return 树形模块
     */
    public List<SysModuleTreeVo> treeAdminModulesByAdminId(long adminId) {
        final Map<String, List<SysAction>> moduleActionsMap = listGrantedActionsByAdminId(adminId).stream()
                .collect(Collectors.groupingBy(SysAction::getModuleCode));

        return sysActionService.treeAdminModules(moduleActionsMap);
    }

    /**
     * 构建超级管理员的树形模块
     */
    public List<SysModuleTreeVo> treeAdminModulesBySuperAdmin() {
        Map<String, List<SysAction>> moduleActionsMap = sysActionService.getAllActionList()
                .stream()
                .collect(Collectors.groupingBy(SysAction::getModuleCode));
        return sysActionService.treeAdminModules(moduleActionsMap);
    }

    /**
     * 校验管理员登录密码，如果账号不存在则抛异常
     *
     * @param dto {@link SysAdminLoginDto}
     * @return {@link SysAdminInfo} 校验通过，null 校验不通过
     * @throws BusinessException 如果账号不存在, 或者密码不正确
     */
    public SysAdminInfo verifyAdminLoginInfo(SysAdminLoginDto dto) {
        // 校验密码
        final SysAdminInfo theAdmin = findByUsername(dto.getUsername())
                .orElseThrow(() -> new BusinessException("账号不存在"));
        if (encodePassword(dto.getPassword(), theAdmin.getUsername()).equals(theAdmin.getPasswd())) {
            return theAdmin;
        }
        throw new BusinessException("密码错误");
    }

    /**
     * 超级管理员强制修改管理员账号密码
     *
     * @param dto {@link SysAdminResetPasswordDto}
     * @throws BusinessException 如果账号不存在
     */
    public void forceResetAdminPassword(SysAdminResetPasswordDto dto) {
        final SysAdminInfo theSysAdminInfo = getAdminByIdThrowExpIfNotExist(dto.getUserId());
        theSysAdminInfo.setPasswd(encodePassword(dto.getNewPassword(), theSysAdminInfo.getUsername()));
        this.updateById(theSysAdminInfo);
    }

    /**
     * 管理员修改自身密码
     *
     * @param dto {@link SysAdminChangePasswordDto}
     * @throws BusinessException 如果账号不存在
     */
    public void changeAdminPassword(SysAdminChangePasswordDto dto) {
        String username = ConvertUtils.convertToString(super.getToken().getUserData().get("username"));
        if (config.getAdminName().equals(username)) {
            throw new BusinessException("超级管理员不允许修改");
        }
        // 获取当前已经登录的用户ID
        final long theUserId = getLoggingUserIdSafely();
        final SysAdminInfo theSysAdminInfo = getAdminByIdThrowExpIfNotExist(theUserId);

        if (!encodePassword(dto.getCurrentPassword(), theSysAdminInfo.getUsername()).equals(theSysAdminInfo.getPasswd())) {
            throw new BusinessException("当前密码不正确");
        }
        theSysAdminInfo.setPasswd(encodePassword(dto.getNewPassword(), theSysAdminInfo.getUsername()));
        this.updateById(theSysAdminInfo);
    }

    /**
     * 管理员修改自身资料
     *
     * @param dto {@link SysAdminUpdateDto}
     * @throws BusinessException 如果账号不存在
     */
    public void changeAdminInfo(SysAdminUpdateDto dto) {
        SysAdminInfo theAdmin = getAdminByIdThrowExpIfNotExist(dto.getId());
        ConvertUtils.copyProperties(dto, theAdmin);
        updateById(theAdmin);
    }

    /**
     * 取回管理员信息
     *
     * @param id 管理员ID
     */
    @CacheIt(key = SystemConstant.SYS_ADMIN_INFO_CACHE_KEY + "ID_", paramKey = true)
    public SysAdminInfoVo getAdminInfoById(Long id) {
        SysAdminInfo sysAdminInfo = super.getById(id);
        if (ValidateUtil.isBlank(sysAdminInfo)) {
            throw new BusinessException("管理员不存在");
        }
        return this.entityToVo(sysAdminInfo);
    }

    /**
     * 取回管理员信息
     *
     * @param id 管理员ID
     */
    public AdminInfoBaseVo getAdminInfoBaseById(Long id) {
        SysAdminInfo sysAdminInfo = super.getById(id);
        if (ValidateUtil.isBlank(sysAdminInfo)) {
            throw new BusinessException("管理员不存在");
        }
        return super.objectConvert(sysAdminInfo, AdminInfoBaseVo.class);
    }

    /**
     * 禁用管理员账号
     *
     * @param userId 管理员账号ID
     * @throws BusinessException 如果账号不存在
     */
    public void disableAdminById(long userId) {
        updateAdminStatusById(userId, SysAdminInfo.Status.DISABLE);
    }

    /**
     * 启用管理员账号
     *
     * @param userId 管理员账号ID
     * @throws BusinessException 如果账号不存在
     */
    public void enableAdminById(long userId) {
        updateAdminStatusById(userId, SysAdminInfo.Status.NORMAL);
    }

    /**
     * 批量禁用管理员账号
     *
     * @param userId 管理员账号ID
     * @throws BusinessException 如果账号不存在
     */
    @Transactional(rollbackFor = BusinessException.class)
    public void disableAdminByIds(Set<Long> userId) {
        updateAdminStatusByIds(userId, SysAdminInfo.Status.DISABLE);
    }

    /**
     * 批量启用管理员账号
     *
     * @param userId 管理员账号ID
     * @throws BusinessException 如果账号不存在
     */
    @Transactional(rollbackFor = BusinessException.class)
    public void enableAdminByIds(Set<Long> userId) {
        updateAdminStatusByIds(userId, SysAdminInfo.Status.NORMAL);
    }

    /**
     * 根据管理员账号ID修改用户状态
     *
     * @param userId    管理员用户ID
     * @param newStatus 新的用户状态 {@link SysAdminInfo.Status}
     * @throws BusinessException 如果账号不存在
     */
    public void updateAdminStatusById(long userId,
                                      SysAdminInfo.Status newStatus) {
        final SysAdminInfo theAdmin = getAdminByIdThrowExpIfNotExist(userId);
        theAdmin.setStatus(newStatus);
        this.updateById(theAdmin);
    }

    /**
     * 添加拓展参数
     *
     * @param dto dto
     */
    public void addExtendData(SysAdminInfoAddExtendDataDto dto) {
        Long adminId = dto.getAdminId();
        Map<String, Object> extendData = dto.getExtendData();

        SysAdminInfo sysAdminInfo = super.getOptById(adminId).orElseThrow(() -> new BusinessException("管理员不存在"));
        Map<String, Object> currentExtendData = sysAdminInfo.getExtendData();
        if (ValidateUtil.isBlank(currentExtendData)) {
            currentExtendData = new HashMap<>();
        }
        currentExtendData.putAll(extendData);
        sysAdminInfo.setExtendData(currentExtendData);
        this.updateById(sysAdminInfo);
    }

    /**
     * 根据管理员账号IDS修改用户状态
     *
     * @param userIds   管理员用户ID
     * @param newStatus 新的用户状态 {@link SysAdminInfo.Status}
     * @throws BusinessException 如果账号不存在
     */
    private void updateAdminStatusByIds(Set<Long> userIds,
                                        SysAdminInfo.Status newStatus) {
        super.lambdaUpdate().set(SysAdminInfo::getStatus, newStatus.getValue())
                .in(SysAdminInfo::getId, userIds)
                .update();
    }

    /**
     * 根据管理员ID查询账号信息
     *
     * @param userId 管理员用户ID
     * @return {@link SysAdminInfo}
     * @throws BusinessException 如果账号不存在
     */
    public SysAdminInfo getAdminByIdThrowExpIfNotExist(long userId) {
        return this.getOptById(userId).orElseThrow(() -> new BusinessException("账号不存在"));
    }

    /**
     * 根据用户名查找管理员信息
     *
     * @param username 用户名
     * @return {@link Optional}
     */
    public Optional<SysAdminInfo> findByUsername(String username) {
        return lambdaQuery()
                .eq(SysAdminInfo::getUsername, username)
                .oneOpt();
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
     * 列表查询所有管理员
     *
     * @param dto 查询条件
     */
    public List<SysAdminInfoVo> listAdminInfo(SysAdminInfoSearchDto dto) {
        List<SysAdminInfo> list = super.lambdaQuery()
                .like(ValidateUtil.isNotBlank(dto.getUsername()), SysAdminInfo::getUsername, dto.getUsername()).list();
        return super.mapListToTarget(list, this::entityToVo);
    }

    //--------------------------- helper functions ---------------------------//

    /**
     * entity to vo
     *
     * @param entity entity
     * @return vo
     */
    protected SysAdminInfoVo entityToVo(SysAdminInfo entity) {
        List<SysAdminRole> sysAdminRoles = sysAdminInfoRoleLinkService.listGrantedRolesByAdminId(entity.getId());

        SysAdminInfoVo sysAdminInfoVo = super.objectConvert(entity, SysAdminInfoVo.class);
        sysAdminInfoVo.setStatusStr(entity.getStatus().getRemark());
        StringBuilder roleNames = new StringBuilder();
        sysAdminRoles.forEach(sysAdminRole -> roleNames.append(sysAdminRole.getRoleName()).append(","));
        if (!roleNames.isEmpty()) {
            roleNames.substring(roleNames.length() - 1);
        }
        sysAdminInfoVo.setRoles(roleNames.toString());
        return sysAdminInfoVo;
    }

    /**
     * 根据openId查询
     *
     * @param openId openId
     */
    private SysAdminInfo findByOpenId(String openId) {
        return super.lambdaQuery()
                .eq(SysAdminInfo::getOpenId, openId)
                .one();
    }

    /**
     * 加密管理员登录密码 MD5(password + secrecy)
     *
     * @param password 需要加密的密码
     * @param secrecy  盐值
     * @return 加密后的字符串
     */
    private String encodePassword(String password, String secrecy) {
        log.debug(">>>>>>>>>>pwd:{},secrecy:{}", password, secrecy);
        String encodePasswd = DigestUtils.md5DigestAsHex((password + secrecy).getBytes()).toUpperCase();
        log.debug(encodePasswd);
        return encodePasswd;
    }

    /**
     * 获取当前已经登录的管理员ID(管理员必须已经登录)
     *
     * @return 管理员ID
     */
    private Long getLoggingUserIdSafely() {
        final Long userId = getUserId();

        return ConvertUtils.convertToLong(userId);
    }

    private String buildToken(SysAdminInfo theSysAdminInfo) {
        final TokenVo tokenVo = new TokenVo();
        tokenVo.setClientType(ClientType.ADMIN);
        tokenVo.setCreateDatetime(new Date());
        tokenVo.setExpireSeconds(config.getAdminTokenExpireSeconds());

        // 4.1获取管理员所拥有的角色
        final Set<Long> roles = sysAdminInfoRoleLinkService.listGrantedRolesByAdminId(theSysAdminInfo.getId())
                .stream()
                .map(SysAdminRole::getId)
                .collect(Collectors.toSet());
        tokenVo.setRoles(roles);

        //4.2 设置用户唯一标识 & 用户信息
        tokenVo.setUserTag(theSysAdminInfo.getId());
        tokenVo.setUserData(buildUserData(theSysAdminInfo));

        //生成token
        String token = JwtUtil.createToken(config.getJwtSecret(), tokenVo);
        if (StringUtils.isBlank(token)) {
            throw new BusinessException("生成token失败");
        }

        //4.3 移除之前数据
        String adminId = theSysAdminInfo.getId().toString();
        String oldToken = ConvertUtils.convertToString(this.cacheManager.getValue(buildUserTokenKey(adminId)));
        if (ValidateUtil.isNotBlank(oldToken)) {
            this.cacheManager.removeToken(oldToken);
        }

        // 5.缓存token (第三段)
        String tokenStr = tailToken(token);
        cacheManager.cacheToken(tokenStr, tokenVo);
        // 限制同一时间同一帐号只能在一个设备上登录

        cacheManager.cacheValue(buildUserTokenKey(adminId), tokenStr, tokenVo.getExpireSeconds());

        //放入线程
        ThreadLocalUtil.setTokenInfo(tokenVo);
        return token;
    }

    private String buildUserTokenKey(String adminId) {
        return "sys_user_token:" + adminId;
    }

    protected SysAdminInfo getSuperAdminInfo() {
        SysAdminInfo sysAdminInfo = new SysAdminInfo();
        sysAdminInfo.setId(0L);
        sysAdminInfo.setUsername(config.getAdminName());
        sysAdminInfo.setNickname(config.getAdminName());
        return sysAdminInfo;
    }

}