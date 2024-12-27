package com.kelaker.kcommon.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.system.dao.SysLinkmanGroupDao;
import com.kelaker.kcommon.system.dto.SysLinkmanGroupDto;
import com.kelaker.kcommon.system.dto.SysLinkmanGroupSearchDto;
import com.kelaker.kcommon.system.entity.SysLinkmanGroup;
import com.kelaker.kcommon.system.vo.SysLinkmanGroupVo;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统联系人组(SysLinkmanGroup)表服务
 *
 * @author Felix Huang
 * @since 2023-04-04 16:15:43
 */
@Service
public class SysLinkmanGroupService extends BaseService<SysLinkmanGroupDao, SysLinkmanGroup> {

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 分页结果
     */
    public IPage<SysLinkmanGroupVo> queryPage(RequestPage<SysLinkmanGroupSearchDto> searchDto) {
        SysLinkmanGroupSearchDto searchDtoData = searchDto.getData();
        SysLinkmanGroup empty = super.objectConvert(searchDtoData, SysLinkmanGroup.class);
        IPage<SysLinkmanGroup> page = super.page(super.createPage(searchDto), super.createWrapper(empty));
        return mapPageToTarget(page, this::convertToVo);
    }

    /**
     * 添加对象
     *
     * @param dto 对象
     */
    public void addSysLinkmanGroup(SysLinkmanGroupDto dto) {
        SysLinkmanGroup sysLinkmanGroup = super.objectConvert(dto, SysLinkmanGroup.class);
        super.save(sysLinkmanGroup);
    }

    /**
     * 对象转换
     */
    private SysLinkmanGroupVo convertToVo(SysLinkmanGroup sysLinkmanGroup) {
        return super.objectConvert(sysLinkmanGroup, SysLinkmanGroupVo.class);
    }

    /**
     * 取回所有正常状态的联系人组
     */
    public List<SysLinkmanGroupVo> getAllEnableSysLinkmanGroup() {
        List<SysLinkmanGroup> list = super.list();
        return this.mapListToTarget(list, this::convertToVo);
    }


}

