package com.kelaker.kcommon.system.constance;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Felix Huang
 * @since 2021-01-06
 */
@Getter
@AllArgsConstructor
public enum LanguageLocationEnum {

    /**
     * 简体中文
     */
    ZH_CN("ZhCn", "简体中文"),
    /**
     * 马来语
     */
    MS("Ms", "马来语"),
    /**
     * 泰语
     */
    TH("Th", "泰语"),
    /**
     * 韩语
     */
    KO("Ko", "韩语"),
    /**
     * 印尼语
     */
    ID("Id", "印尼语"),
    /**
     * 日语
     */
    JP("Jp", "日语"),
    /**
     * 英文
     */
    EN("En", "英文");

    private final String value;

    private final String remark;
}
