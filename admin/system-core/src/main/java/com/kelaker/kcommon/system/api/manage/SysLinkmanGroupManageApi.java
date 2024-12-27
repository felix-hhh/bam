package com.kelaker.kcommon.system.api.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.system.dto.SysLinkmanGroupSearchDto;
import com.kelaker.kcommon.system.service.SysLinkmanGroupService;
import com.kelaker.kcommon.system.vo.SysLinkmanGroupVo;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.api.BaseApi;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 系统联系人组
 *
 * @author Felix Huang
 * @since 2023-04-04 16:15:42
 */
@RestController
@RequestMapping("/system/manage/linkman/group")
public class SysLinkmanGroupManageApi extends BaseApi {
    /**
     * 服务对象
     */
    @Resource
    private SysLinkmanGroupService sysLinkmanGroupService;

    /**
     * 分页查询联系人组
     *
     * @param searchDto 查询对象
     * @return 查询结果
     */
    @PostMapping("/page")
    public IPage<SysLinkmanGroupVo> pageSysLinkmanGroup(@RequestBody RequestPage<SysLinkmanGroupSearchDto> searchDto) {
        return this.sysLinkmanGroupService.queryPage(searchDto);
    }

    /**
     * 取回所有正常状态的联系人组
     */
    @PostMapping("/list")
    public List<SysLinkmanGroupVo> getAllEnableSysLinkmanGroup() {
        return this.sysLinkmanGroupService.getAllEnableSysLinkmanGroup();
    }
}

