package com.kelaker.kcommon.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.system.dao.SysMenuDao;
import com.kelaker.kcommon.system.dto.SysMenuDto;
import com.kelaker.kcommon.system.dto.SysMenuSearchDto;
import com.kelaker.kcommon.system.entity.SysMenu;
import com.kelaker.kcommon.system.vo.SysMenuVo;
import com.kelaker.ktools.common.exception.BusinessException;
import com.kelaker.ktools.common.utils.ValidateUtil;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * 系统菜单(SysMenu)表服务
 *
 * @author Felix Huang
 * @since 2024-12-06 15:49:10
 */
@Service
public class SysMenuService extends BaseService<SysMenuDao, SysMenu> {

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 分页结果
     */
    public IPage<SysMenuVo> queryPage(RequestPage<SysMenuSearchDto> searchDto) {
        SysMenuSearchDto searchDtoData = searchDto.getData();
        SysMenu empty = super.objectConvert(searchDtoData, SysMenu.class);
        IPage<SysMenu> page = super.page(super.createPage(searchDto), super.createWrapper(empty));
        return mapPageToTarget(page, this::convertToVo);
    }

    /**
     * 添加对象
     *
     * @param dto 对象
     */
    public void addSysMenu(SysMenuDto dto) {
        SysMenu sysMenu = super.objectConvert(dto, SysMenu.class);
        //数据检查
        if (SysMenu.Type.FIRST.equals(sysMenu.getType())) {
            if(ValidateUtil.isNotBlank(sysMenu.getParentId())) {
                throw new BusinessException("一级菜单不能选择父级菜单");
            }
        }


        sysMenu.setType(SysMenu.Type.valueOf(dto.getType()));
        super.save(sysMenu);
    }

    /**
     * 对象转换
     */
    private SysMenuVo convertToVo(SysMenu sysMenu) {

        SysMenuVo sysMenuVo = super.objectConvert(sysMenu, SysMenuVo.class);
        sysMenuVo.setTypeStr(sysMenu.getType().getRemark());
        return sysMenuVo;
    }

    /**
     * 查询所有菜单
     *
     */
    public List<SysMenuVo> listAll() {
        List<SysMenu> list = super.list();
        List<SysMenuVo> sysMenuVos = super.mapListToTarget(list, this::convertToVo);

        return sysMenuVos
                .stream()
                .filter(sysMenuVo -> SysMenu.Type.FIRST.getValue().equals(sysMenuVo.getType()))
                .sorted(Comparator.comparing(SysMenuVo::getOrderNum))
                .map(sysMenuVo -> this.findChildren(sysMenuVos, sysMenuVo))
                .toList();
    }

    /**
     * 查找子元素
     *
     * @param menus     菜单数据
     * @param sysMenuVo 菜单
     */
    private SysMenuVo findChildren(List<SysMenuVo> menus, SysMenuVo sysMenuVo) {
        Long parentId = sysMenuVo.getId();
        List<SysMenuVo> children = menus.stream()
                .filter(sysMenu -> parentId.equals(sysMenu.getParentId()))
                .sorted(Comparator.comparing(SysMenuVo::getOrderNum))
                .toList();
        children.forEach(child -> this.findChildren(menus, child));
        sysMenuVo.setChildren(children);
        return sysMenuVo;
    }
}

