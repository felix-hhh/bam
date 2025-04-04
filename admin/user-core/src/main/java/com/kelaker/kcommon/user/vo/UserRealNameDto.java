package com.kelaker.kcommon.user.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRealNameDto {
    @NotBlank(message = "真实姓名不能为空")
    private String realName;

    @NotBlank(message = "身份证号码不能为空")
    private String idCardNo;
}
