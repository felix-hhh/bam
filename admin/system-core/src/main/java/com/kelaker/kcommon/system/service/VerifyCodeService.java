package com.kelaker.kcommon.system.service;

import com.kelaker.ktools.cache.manager.CacheManager;
import com.kelaker.ktools.common.utils.StringUtil;
import com.kelaker.ktools.common.utils.VerifyCodeUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.io.IOException;

/**
 * 验证码服务（图形 + 短信）
 *
 * @author chym
 * @date 2020/4/2 9:05
 */
@Service
@Slf4j
public class VerifyCodeService {

    /**
     * 图形验证码长度
     */
    private static final int IMAGE_VC_RANDOM_CODE_LENGTH = 4;

    /**
     * 图形验证码图片高度
     */
    private static final int IMAGE_VC_HEIGHT = 50;

    /**
     * 图形验证码图片宽度
     */
    private static final int IMAGE_VC_WIDTH = 150;

    @Resource
    private CacheManager cacheManager;

    /**
     * 生成验证码
     *
     * @param identity 唯一标识，用于缓存key
     * @param response
     */
    @Synchronized
    public void makeImageVerifyCode(String identity, HttpServletResponse response) {
        String randomVc = StringUtil.getRandomString(IMAGE_VC_RANDOM_CODE_LENGTH);
        this.makeImageVerifyCode(identity, randomVc, response);
    }

    @Synchronized
    public void makeNumberImageVerifyCode(String identity, HttpServletResponse response) {
        String randomVc = StringUtil.getRandomNumberString(IMAGE_VC_RANDOM_CODE_LENGTH);
        this.makeImageVerifyCode(identity, randomVc, response);
    }

    /**
     * 生成图形验证码
     *
     * @param identity 唯一标识，用作缓存Key
     * @param response {@link HttpServletResponse}
     */
    private void makeImageVerifyCode(String identity, String randomVc, HttpServletResponse response) {

        try {
            // 缓存验证码
            cacheManager.cacheImgAuthCode(identity, randomVc);

            final ServletOutputStream ops = response.getOutputStream();

            // 生成验证码
            VerifyCodeUtils.outputImage(IMAGE_VC_WIDTH,
                    IMAGE_VC_HEIGHT,
                    ops,
                    randomVc);
            response.setStatus(HttpServletResponse.SC_OK);

            // 禁止浏览器缓存
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            ops.flush();
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log.error("生成图形验证码失败! {}", e.getMessage());
            return;
        }

        if (log.isDebugEnabled()) {
            log.debug("生成图形验证码: {}", randomVc);
        }
    }

    /**
     * 校验图形验证码
     *
     * @param identity   唯一标识，用作缓存Key
     * @param verifyCode 需要校验的验证码
     * @return 是否校验通过
     */
    public boolean verifyImageVerifyCode(String identity,
                                         String verifyCode) {
        final String randomCode = cacheManager.getImgAuthCode(identity);
        log.debug(">>>>>>>>>>输入验证码：{}, 实际验证码：{}", verifyCode, randomCode);

        boolean result = false;
        if (randomCode != null) {
            result = randomCode.equalsIgnoreCase(verifyCode);
        }

        // 清理验证码不论正确与否
        cacheManager.removeImgAuthCode(identity);
        return result;
    }
}
