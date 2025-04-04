package com.kelaker.kcommon.user.vo;

import lombok.Data;

import java.util.Date;

/**
 * 用户拓展信息Vo
 */
@Data
public class UserInfoExtendsVo {
    /**
     * 主键
     */
    private String id;

    /**
     * 头像路径
     */
    private String headImgPath;

    /**
     * 创建时间
     */
    private Date createDatetime;

    /**
     * 修改时间
     */
    private Date modifyDatetime;

    /**
     * 性别
     */
    private Integer gender;



}
