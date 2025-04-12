package com.kelaker.kcommon.system.api.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.system.dto.SysAdminRoleDto;
import com.kelaker.kcommon.system.dto.SysAdminRoleGrantActionDto;
import com.kelaker.kcommon.system.dto.SysAdminRoleSearchDto;
import com.kelaker.kcommon.system.dto.SysRoleBindAdminsDto;
import com.kelaker.kcommon.system.service.SysAdminInfoRoleLinkService;
import com.kelaker.kcommon.system.service.SysAdminRoleActionLinkService;
import com.kelaker.kcommon.system.service.SysAdminRoleService;
import com.kelaker.kcommon.system.vo.SysAdminRoleVo;
import com.kelaker.kcommon.system.vo.SysActionVo;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.annotation.HasAction;
import com.kelaker.ktools.web.annotation.InModule;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * 管理员角色
 *
 * @author felix huang
 * @since 2020-03-27 11:42:57
 */
@RestController
@RequestMapping("/system/manage/role")
@InModule(moduleCode = "SYSTEM")
public class SysAdminRoleManageApi {

    /**
     * 服务对象
     */
    @Resource
    private SysAdminRoleService sysAdminRoleService;

    @Resource
    private SysAdminRoleActionLinkService sysAdminRoleActionLinkService;

    @Resource
    private SysAdminInfoRoleLinkService sysAdminInfoRoleLinkService;

    /**
     * 角色授予功能
     *
     * @param dto
     */
    @HasAction(actionCode = "ADMIN_ROLE:LIST_ROLE", actionName = "角色授权")
    @PutMapping("/actions/grant")
    public void grantActions(@Validated @RequestBody SysAdminRoleGrantActionDto dto) {
        sysAdminRoleService.grantActions(dto);
    }

    /**
     * 新增角色
     *
     * @param dto 需要添加的角色信息
     */
    @HasAction(actionCode = "ADMIN_ROLE:ADD_ROLE", actionName = "添加角色")
    @PostMapping("/add")
    public void add(@Validated @RequestBody SysAdminRoleDto dto) {
        sysAdminRoleService.addAdminRole(dto);

    }

    /**
     * 启用角色
     *
     * @param roleId 角色代码
     */
    @HasAction(actionCode = "ADMIN_ROLE:ENABLE", actionName = "禁用角色")
    @PutMapping("/enable/{roleId}")
    public void enableByRoleCode(@Validated @NotBlank(message = "角色ID不能为空") @PathVariable("roleId") Long roleId) {
        sysAdminRoleService.enableByRoleCode(roleId);

    }

    /**
     * 禁用角色
     *
     * @param roleId 角色代码
     */
    @HasAction(actionCode = "ADMIN_ROLE:DISABLE", actionName = "禁用角色")
    @PutMapping("/disable/{roleId}")
    public void disableByRoleCode(@Validated @NotNull(message = "角色代码不能为空") @PathVariable("roleId") Long roleId) {
        sysAdminRoleService.disableByRoleCode(roleId);
    }

    /**
     * 根据角色代码获取其已经授予的功能
     *
     * @param roleCode 角色代码
     */
    @HasAction(actionCode = "ADMIN_ROLE:ACTION", actionName = "角色权限")
    @GetMapping("/actions/list/{roleCode}")
    public List<SysActionVo> listGrantedActionVosByRoleCode(@Validated @NotNull(message = "角色代码不能为空") @PathVariable("roleCode") String roleCode) {
        return sysAdminRoleActionLinkService.listGrantedActionVosByRoleCode(roleCode);
    }

    /**
     * 查询角色列表
     */
    @HasAction(actionCode = "ADMIN_ROLE:LIST", actionName = "列表查询角色")
    @GetMapping("/list")
    public List<SysAdminRoleVo> listAdminRole() {
        return sysAdminRoleService.listRoleVos();
    }

    /**
     * 分页查询角色
     *
     * @param dto 查询dto
     */
    @HasAction(actionCode = "ADMIN_ROLE:PAGE", actionName = "分页查询角色")
    @PostMapping("/page")
    public IPage<SysAdminRoleVo> pageAdminRole(RequestPage<SysAdminRoleSearchDto> dto) {
        return sysAdminRoleService.pageSysAdminRole(dto);
    }

    /**
     * 修改角色信息
     *
     * @param dto 角色信息
     */
    @HasAction(actionCode = "ADMIN_ROLE:CHANGE", actionName = "修改角色")
    @PutMapping("/change")
    public void changeAdminRole(@Validated @RequestBody SysAdminRoleDto dto) {
        this.sysAdminRoleService.changeAdminRole(dto);
    }

    /**
     * 从角色中移除管理员
     *
     * @param dto dto
     */
    @HasAction(actionCode = "ADMIN_ROLE:USER_DEL", actionName = "移除角色中的管理员")
    @DeleteMapping("/user/del")
    public void removeAdminsInRole(@Validated @RequestBody SysRoleBindAdminsDto dto) {
        this.sysAdminInfoRoleLinkService.deleteRoleUser(dto);
    }
}