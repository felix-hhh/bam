package com.kelaker.kcommon.system.vo;

import lombok.Data;

import java.util.Set;

/**
 * 系统模块树形Vo
 * @author chym
 * @date 2020/4/9 11:21
 */
@Data
public class SysModuleTreeVo {

    /**
     * 系统模块代码
     */
    private String moduleCode;

    /**
     * 系统功能代码集合
     */
    private Set<String> actionCodes;
}
