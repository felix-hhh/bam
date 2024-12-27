package com.kelaker.kcommon.system.service;

import com.kelaker.kcommon.system.dao.SysTypeDao;
import com.kelaker.kcommon.system.dto.SysTypeDto;
import com.kelaker.kcommon.system.entity.SysType;
import com.kelaker.kcommon.system.manager.SysCacheManager;
import com.kelaker.kcommon.system.vo.SysTypeVo;
import com.kelaker.ktools.web.base.service.BaseService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * @author Felix Huang
 * @since 2021-02-01
 */
@Service
public class SysTypeService extends BaseService<SysTypeDao, SysType> {

    @Resource
    private SysCacheManager sysCacheManager;

    /**
     * 取回所有系统类型
     *
     * @return
     */
    public List<SysTypeVo> getAllType() {
        List<SysType> sysType = this.sysCacheManager.getSysTypeAllFromCache();
        return super.mapListToTarget(sysType, SysTypeVo.class);
    }

    /**
     * 保存系统类型
     *
     * @param dto
     */
    public void addSystemType(SysTypeDto dto) {
        SysType sysType = super.objectConvert(dto, SysType.class);
        super.save(sysType);
    }

    /**
     * 取回所有系统类型，用于选择框
     *
     * @return
     */
    public List<SysTypeVo> getAllSysTypeSimple() {
        List<SysType> sysTypeList = this.sysCacheManager.getSysTypeAllFromCache();
        sysTypeList.forEach(sysType -> sysType.setRemark(""));

        return super.mapListToTarget(sysTypeList, SysTypeVo.class);
    }

}
