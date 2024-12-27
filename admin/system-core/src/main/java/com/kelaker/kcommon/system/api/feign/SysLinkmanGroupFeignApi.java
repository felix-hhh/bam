package com.kelaker.kcommon.system.api.feign;

import com.kelaker.kcommon.common.system.vo.SysAdminInfoSimpleVo;
import com.kelaker.kcommon.system.service.SysLinkmanGroupInfoLinkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping({"/system/feign/linkman/group"})
public class SysLinkmanGroupFeignApi {

    @Resource
    private SysLinkmanGroupInfoLinkService sysLinkmanGroupInfoLinkService;

    /**
     * 根据分组代码取回联系人列表
     * @param groupCode 分组代码
     * @return
     */
    @GetMapping("/get/{groupCode}")
    public List<SysAdminInfoSimpleVo> getAdminInfoByLinkmanGroup(@PathVariable("groupCode") String groupCode) {
        return sysLinkmanGroupInfoLinkService.getAdminInfoByLinkmanGroup(groupCode);
    }
}
