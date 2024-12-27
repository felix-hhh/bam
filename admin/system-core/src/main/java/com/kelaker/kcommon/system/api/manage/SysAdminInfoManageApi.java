package com.kelaker.kcommon.system.api.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.common.system.vo.SysAdminInfoSimpleVo;
import com.kelaker.kcommon.system.dto.*;
import com.kelaker.kcommon.system.service.SysAdminInfoRoleLinkService;
import com.kelaker.kcommon.system.service.SysAdminInfoService;
import com.kelaker.kcommon.system.service.VerifyCodeService;
import com.kelaker.kcommon.system.vo.SysAdminRoleVo;
import com.kelaker.kcommon.system.vo.SysActionVo;
import com.kelaker.kcommon.system.vo.SysAdminInfoVo;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.annotation.HasAction;
import com.kelaker.ktools.web.annotation.InModule;
import com.kelaker.ktools.web.annotation.NoLogin;
import com.kelaker.ktools.web.annotation.RecordLog;
import com.kelaker.ktools.web.base.api.BaseApi;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 管理员信息
 *
 * @author felix huang
 * @since 2020-03-27 11:42:56
 */
@RestController
@RequestMapping("/system/manage/admin/info")
@InModule(moduleCode = "SYSTEM")
public class SysAdminInfoManageApi extends BaseApi {

    @Resource
    private SysAdminInfoService sysAdminInfoService;

    @Resource
    private SysAdminInfoRoleLinkService sysAdminInfoRoleLinkService;

    @Resource
    private VerifyCodeService verifyCodeService;

    /**
     * 添加管理员
     *
     * @param dto 管理员信息
     * @return 响应结果
     */
    @HasAction(actionCode = "ADMIN_INFO:ADD",actionName = "添加管理员")
    @PostMapping("/add")
    public SysAdminInfoSimpleVo add(@Validated @RequestBody SysAdminAddDto dto) {
        return sysAdminInfoService.addAdmin(dto);

    }

    /**
     * 禁用管理员账号
     *
     * @param adminId 用户ID
     */
    @HasAction(actionCode = "ADMIN_INFO:DISABLE", actionName = "禁用管理员")
    @PutMapping("/disable/{adminId}")
    public void disableById(@Validated @NotNull(message = "管理员Id不能为空") @PathVariable("adminId") Long adminId) {
        sysAdminInfoService.disableAdminById(adminId);
    }

    /**
     * 启用用户
     *
     * @param adminId 管理员Id
     */
    @HasAction(actionCode = "ADMIN_INFO:ENABLE",  actionName = "启用管理员")
    @PutMapping("/enable/{adminId}")
    public void enableById(@Validated @NotNull(message = "管理员Id不能为空") @PathVariable("adminId") Long adminId) {
        sysAdminInfoService.enableAdminById(adminId);
    }

    /**
     * 批量禁用管理员账号
     *
     * @param adminIds 管理员ID集合
     */
    @HasAction(actionCode = "ADMIN_INFO:DISABLE_BATCH",  actionName = "批量禁用管理员")
    @PutMapping("/disable/batch")
    public void disableByIds(@Validated @NotNull(message = "管理员集合ID集合不能为空") @RequestBody Set<Long> adminIds) {
        sysAdminInfoService.disableAdminByIds(adminIds);

    }

    /**
     * 批量启用管理员账号
     *
     * @param adminIds 管理员ID集合
     */
    @HasAction(actionCode = "ADMIN_INFO:ENABLE_BATCH", actionName = "批量启用管理员")
    @PutMapping("/enable/batch")
    public void enableByIds(@Validated @NotNull(message = "管理员集合ID集合不能为空") @RequestBody Set<Long> adminIds) {
        sysAdminInfoService.enableAdminByIds(adminIds);

    }

    /**
     * 强制重置管理员密码
     *
     * @param dto 强制重置登录密码请求信息
     */
    @HasAction(actionCode = "ADMIN_INFO:RESET_PWD",actionName = "重置密码")
    @PutMapping("/password/reset")
    public void forceResetLoginPassword(@Validated @RequestBody SysAdminResetPasswordDto dto) {
        sysAdminInfoService.forceResetAdminPassword(dto);
    }

    /**
     * 修改登录密码
     * <p>
     * 用于修改当前已经登录的管理员的登录密码
     * </p>
     *
     * @param dto
     */
    @PutMapping("/password/change")
    public void changeLoginPassword(@Validated @RequestBody SysAdminChangePasswordDto dto) {
        sysAdminInfoService.changeAdminPassword(dto);

    }

    /**
     * 编辑用户
     *
     * @param dto
     */
    @HasAction(actionCode = "ADMIN_INFO:CHANGE",actionName = "修改管理员")
    @PutMapping("/change")
    public void changeAdminInfo(@Validated @RequestBody SysAdminUpdateDto dto) {
        sysAdminInfoService.changeAdminInfo(dto);
    }

    /**
     * 管理员登录
     *
     * @param dto 管理员登录请求信息
     */
    @NoLogin
    @RecordLog(type = "LOGIN",description = "管理员登录")
    @PostMapping("/login")
    public String login(@Validated @RequestBody SysAdminLoginDto dto) {
        return sysAdminInfoService.adminLogin(dto, super.getClientIp());
    }

    /**
     * 用户注销登录
     */
    @PutMapping("/logout")
    public void logout() {
        sysAdminInfoService.logout(super.getRequest());

    }

    /**
     * 用户注销登录-v2
     */
    @PutMapping("/v2/logout")
    public void logoutV2() {
        sysAdminInfoService.logout();

    }

    /**
     * 获取当前管理员所持有的功能列表
     */
    @GetMapping("/actions/list")
    public List<SysActionVo> listAdminRoleVosByAdminId() {
        return sysAdminInfoService.listLoginAdminGrantedActions();
    }

    /**
     * 角色绑定管理员
     *
     * @param dto 角色绑定管理员请求信息
     */
    @HasAction(actionCode = "ADMIN_INFO:GRANT_ROLE",actionName = "角色授权用户")
    @PutMapping("/role/grant")
    public void grantRoles(@Validated @RequestBody SysRoleBindAdminsDto dto) {
        sysAdminInfoService.grantRoles(dto);

    }

    /**
     * 根据管理员ID获取其已经授予的角色
     *
     * @param adminId
     */
    @HasAction(actionCode = "ADMIN_INFO:LIST_ROLE",actionName = "获取用户角色")
    @GetMapping("/roles/list/{adminId}")
    public Set<SysAdminRoleVo> listAdminRoleVosByAdminId(@Validated @NotNull(message = "管理员ID不能为空") @PathVariable("adminId") Long adminId) {
        return sysAdminInfoRoleLinkService.listGrantedRoleVosByAdminId(adminId);
    }

    /**
     * 分页查询用户列表
     *
     * @param dto 查询条件
     */
    @HasAction(actionCode = "ADMIN_INFO:PAGE",actionName = "管理员列表")
    @PostMapping("/page")
    public IPage<SysAdminInfoVo> pageAdminInfo(@Validated @RequestBody RequestPage<SysAdminInfoSearchDto> dto) {
        return sysAdminInfoService.pageAdminInfo(dto);
    }

    /**
     * 添加拓展参数
     * @param dto dto
     */
    @HasAction(actionCode = "ADMIN_INFO:EXTEND_DATA_ADD",actionName = "添加管理员拓展参数")
    @PostMapping("/extend/data/add")
    public void addExtendData(@Validated @RequestBody SysAdminInfoAddExtendDataDto dto) {
        this.sysAdminInfoService.addExtendData(dto);
    }

    /**
     * 根据角色代码获取其已经绑定的管理员列表
     *
     * @param roleCode 角色代码
     */
    @HasAction(actionCode = "ADMIN_INFO:ROLE",actionName = "角色下管理员")
    @GetMapping("/list/{roleCode}")
    public List<SysAdminInfoVo> listBindAdminVosByRoleCode(@Validated @NotNull(message = "角色代码不能为空") @PathVariable("roleCode") Long roleCode) {
        return sysAdminInfoService.listBindAdminVosByRoleCode(roleCode);
    }

    /**
     * 管理员刷新Token
     */
    @GetMapping(value = "/token/refresh")
    public String refreshToken() {
        return sysAdminInfoService.refreshToken();
    }

    /**
     * 获取登录验证码
     *
     * @param identity 唯一标识
     */
    @NoLogin
    @GetMapping(value = "/image/{identity}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public void getVerifyImage(@Validated @PathVariable("identity") String identity) {
        verifyCodeService.makeImageVerifyCode(identity, super.getResponse());
    }

    /**
     * 当前登录用户绑定微信
     *
     * @param openId
     */
    @PutMapping("/bind/{openId}")
    public void bindOpenId(@Validated @NotBlank(message = "openId不能为空") @PathVariable("openId") String openId) {
        sysAdminInfoService.bindOpenId(openId);

    }

    /**
     * 当前登录用户解绑微信
     */
    @PutMapping("/unbind")
    public void unBindOpenId() {
        sysAdminInfoService.unBindOpenId();

    }

    /**
     * 微信登录
     *
     * @param openId 微信
     */
    @PutMapping("/login/{openId}")
    @NoLogin
    public String loginByOpenId(@Validated @NotBlank(message = "openId不能为空") @PathVariable("openId") String openId) {
        return sysAdminInfoService.loginByOpenId(openId, super.getClientIp());
    }

}