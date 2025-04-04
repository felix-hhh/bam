package com.kelaker.kcommon.user.dto;

import lombok.Data;

/**
 * 机器人创建dto
 */
@Data
public class UserRobotCreateDto {
    /**
     * 昵称集合
     */
    private String nicknames;

    /**
     * 性别
     */
    private int gender;
}
