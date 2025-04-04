package com.kelaker.kcommon.user.dto;

import com.kelaker.kcommon.user.constant.UserSettingKey;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 用户设置(UserSetting)表实体类
 *
 * @author felix huang
 * @since 2022-07-28 14:28:03
 */

@Data
public class UserSettingChangeDto {

    @NotNull(message = "配置key不能为空")
    private UserSettingKey configKey;

    /**
     * 配置值
     */
    @NotNull(message = "配置值不能为空")
    private String configValue;
}

