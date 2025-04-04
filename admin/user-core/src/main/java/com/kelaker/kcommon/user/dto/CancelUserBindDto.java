package com.kelaker.kcommon.user.dto;

import com.kelaker.kcommon.user.constant.BusinessType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CancelUserBindDto {
    /**
     * 类型
     */
    @NotNull(message = "业务类型不能为空")
    private BusinessType businessType;
}
