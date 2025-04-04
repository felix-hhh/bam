package com.kelaker.kcommon.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.user.dao.UserRegisterLogDao;
import com.kelaker.kcommon.user.dto.UserRegisterLogDto;
import com.kelaker.kcommon.user.dto.UserRegisterLogSearchDto;
import com.kelaker.kcommon.user.entity.UserRegisterLog;
import com.kelaker.kcommon.user.vo.UserRegisterLogVo;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import org.springframework.stereotype.Service;

/**
 * 用户注册信息表(UserRegisterLog)表服务
 *
 * @author Felix Huang
 * @since 2022-08-23 14:43:54
 */
@Service
public class UserRegisterLogService extends BaseService<UserRegisterLogDao, UserRegisterLog> {

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 分页结果
     */
    public IPage<UserRegisterLogVo> queryPage(RequestPage<UserRegisterLogSearchDto> searchDto) {
        UserRegisterLogSearchDto searchDtoData = searchDto.getData();
        UserRegisterLog empty = super.objectConvert(searchDtoData, UserRegisterLog.class);
        IPage<UserRegisterLog> page = super.page(super.createPage(searchDto), super.createWrapper(empty));
        return mapPageToTarget(page, this::convertToVo);
    }

    /**
     * 添加对象
     *
     * @param dto 对象
     */
    public void addUserRegisterLog(UserRegisterLogDto dto) {
        UserRegisterLog userRegisterLog = super.objectConvert(dto, UserRegisterLog.class);

        String ip = userRegisterLog.getIp();
        super.save(userRegisterLog);
    }

    /**
     * 对象转换
     */
    private UserRegisterLogVo convertToVo(UserRegisterLog userRegisterLog) {
        return super.objectConvert(userRegisterLog, UserRegisterLogVo.class);
    }
}

