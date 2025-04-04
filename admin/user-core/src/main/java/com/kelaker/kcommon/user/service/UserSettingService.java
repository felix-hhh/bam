package com.kelaker.kcommon.user.service;

import com.kelaker.kcommon.user.constant.UserSettingKey;
import com.kelaker.kcommon.user.dao.UserSettingDao;
import com.kelaker.kcommon.user.dto.UserSettingChangeDto;
import com.kelaker.kcommon.user.entity.UserSetting;
import com.kelaker.ktools.common.populator.ConvertUtils;
import com.kelaker.ktools.common.utils.ValidateUtil;
import com.kelaker.ktools.web.base.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户设置(UserSetting)表服务接口
 *
 * @author lfz
 * @since 2022-07-28 14:28:04
 */
@Slf4j
@Service
public class UserSettingService extends BaseService<UserSettingDao, UserSetting> {

    /**
     * 列表查询用户设置
     */
    public Map<String, Object> listUserSetting() {
        Long userId = super.getUserId();
        List<UserSetting> list = super.lambdaQuery()
                .eq(UserSetting::getUserId, userId)
                .list();
        return Arrays.stream(UserSettingKey.values()).collect(Collectors.toMap(UserSettingKey::getValue, obj -> {
            List<UserSetting> collect = list.stream().filter(data -> data.getConfigKey().equals(obj)).collect(Collectors.toList());
            if (ValidateUtil.isNotBlank(collect)) {
                return ConvertUtils.convertToBoolean(collect.get(0).getConfigValue());
            }
            return false;
        }));
    }

    /**
     * 变更用户设置
     *
     * @param userSettingChangeDto 用户设置
     */
    public void changeUserSetting(UserSettingChangeDto userSettingChangeDto) {
        Long userId = super.getUserId();
        UserSettingKey configKey = userSettingChangeDto.getConfigKey();
        UserSetting userSetting = super.lambdaQuery()
                .eq(UserSetting::getUserId, userId)
                .eq(UserSetting::getConfigKey, configKey)
                .one();
        if (ValidateUtil.isBlank(userSetting)) {
            userSetting = new UserSetting();
            userSetting.setUserId(userId);
            userSetting.setConfigKey(configKey);
        }
        userSetting.setConfigValue(ConvertUtils.convertToString(userSettingChangeDto.getConfigValue()));
        super.saveOrUpdate(userSetting);
    }
}

