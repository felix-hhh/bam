package com.kelaker.kcommon.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.common.system.vo.SysAdminInfoSimpleVo;
import com.kelaker.kcommon.system.dao.SysLinkmanGroupInfoLinkDao;
import com.kelaker.kcommon.system.dto.SysLinkmanGroupInfoLinkDto;
import com.kelaker.kcommon.system.dto.SysLinkmanGroupInfoLinkSearchDto;
import com.kelaker.kcommon.system.entity.SysLinkmanGroupInfoLink;
import com.kelaker.kcommon.system.vo.SysAdminInfoVo;
import com.kelaker.kcommon.system.vo.SysLinkmanGroupInfoLinkVo;
import com.kelaker.ktools.common.populator.ConvertUtils;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 联系人组关联管理员(SysLinkmanGroupInfoLink)表服务
 *
 * @author Felix Huang
 * @since 2023-04-04 16:15:43
 */
@Service
public class SysLinkmanGroupInfoLinkService extends BaseService<SysLinkmanGroupInfoLinkDao, SysLinkmanGroupInfoLink> {

    @Resource
    private SysAdminInfoService adminInfoService;

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 分页结果
     */
    public IPage<SysLinkmanGroupInfoLinkVo> queryPage(RequestPage<SysLinkmanGroupInfoLinkSearchDto> searchDto) {
        SysLinkmanGroupInfoLinkSearchDto searchDtoData = searchDto.getData();
        SysLinkmanGroupInfoLink empty = super.objectConvert(searchDtoData, SysLinkmanGroupInfoLink.class);
        IPage<SysLinkmanGroupInfoLink> page = super.page(super.createPage(searchDto), super.createWrapper(empty));
        return mapPageToTarget(page, this::convertToVo);
    }

    /**
     * 添加对象
     *
     * @param dto 对象
     */
    public void addSysLinkmanGroupInfoLink(SysLinkmanGroupInfoLinkDto dto) {
        SysLinkmanGroupInfoLink sysLinkmanGroupInfoLink = super.objectConvert(dto, SysLinkmanGroupInfoLink.class);
        super.save(sysLinkmanGroupInfoLink);
    }

    /**
     * 根据联系人代码查找联系人
     * @param groupCode 联系人组代码
     * @return
     */
    public List<SysAdminInfoSimpleVo> getAdminInfoByLinkmanGroup(String groupCode) {
        return super.lambdaQuery()
                .eq(SysLinkmanGroupInfoLink::getGroupCode, groupCode)
                .list()
                .stream()
                .map(link ->{
                    SysAdminInfoVo sysAdminInfoVo = this.adminInfoService.getAdminInfoById(link.getInfoId());
                    SysAdminInfoSimpleVo vo = new SysAdminInfoSimpleVo();
                    ConvertUtils.copyProperties(sysAdminInfoVo, vo);
                    return vo;
                })
                .collect(Collectors.toList());
    }

    /**
     * 对象转换
     */
    private SysLinkmanGroupInfoLinkVo convertToVo(SysLinkmanGroupInfoLink sysLinkmanGroupInfoLink) {
        return super.objectConvert(sysLinkmanGroupInfoLink, SysLinkmanGroupInfoLinkVo.class);
    }
}

