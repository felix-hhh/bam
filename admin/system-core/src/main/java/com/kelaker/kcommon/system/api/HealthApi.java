package com.kelaker.kcommon.system.api;

import com.kelaker.ktools.common.vo.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/health")
public class HealthApi {
    /**
     * 健康检查
     */
    @GetMapping("/check")
    public R health() {
        return R.success();
    }
}
