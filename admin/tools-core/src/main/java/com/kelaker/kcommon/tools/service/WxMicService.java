package com.kelaker.kcommon.tools.service;

import com.kelaker.kcommon.tools.configs.ToolsConfigProperties;
import com.kelaker.kcommon.tools.dto.ToolsSubscribeMessageDto;
import com.kelaker.kcommon.tools.vo.WxAccessTokenVo;
import com.kelaker.kcommon.tools.vo.WxPhoneVo;
import com.kelaker.kcommon.tools.vo.WxTokenVo;
import com.kelaker.ktools.cache.manager.CacheManager;
import com.kelaker.ktools.common.exception.BusinessException;
import com.kelaker.ktools.common.utils.JsonUtil;
import com.kelaker.ktools.common.utils.NetUtil;
import com.kelaker.ktools.common.utils.ValidateUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信接口相关service
 */
@Slf4j
@Service
public class WxMicService {

    @Resource
    private ToolsConfigProperties toolsConfigProperties;

    @Resource
    private CacheManager cacheManager;

    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

    /**
     * 获取手机号码
     */
    public static final String PHONE_NUMBER_URL = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=%s";

    /**
     * 消息模版
     */
    public static final String SUBSCRIBE_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=%s";

    /**
     * 获取用户授权
     */
    public static final String AUTH_URL = "https://api.weixin.qq.com/sns/jscode2session";

    /**
     * 取回accessToken
     */
    public String getAccessToken() {
        final String ACCESS_TOKEN_CACHE_KEY = "WX_MIC_ACCESS_TOKEN";

        String value = (String) cacheManager.getValue(ACCESS_TOKEN_CACHE_KEY);
        if (ValidateUtil.isNotBlank(value)) {
            return value;
        }

        String appId = toolsConfigProperties.getWxMicAppId();
        String secret = toolsConfigProperties.getWxMicSecret();
        if (ValidateUtil.hasBlank(appId, secret)) {
            throw new BusinessException("微信API密钥配置异常");
        }

        Map<String, Object> param = new HashMap<>();
        param.put("grant_type", "client_credential");
        param.put("appid", appId);
        param.put("secret", secret);
        String rep = NetUtil.sendGet(ACCESS_TOKEN_URL, param);
        WxAccessTokenVo wxPhoneVo = JsonUtil.jsonToObj(rep, WxAccessTokenVo.class);
        String accessToken = wxPhoneVo.getAccessToken();
        cacheManager.cacheValue("WX_MIC_ACCESS_TOKEN", accessToken, 600);
        return accessToken;
    }

    /**
     * 发送模版消息
     */
    public void sendSubscribeMessage(ToolsSubscribeMessageDto dto) {
        String accessToken = getAccessToken();
        String url = String.format(SUBSCRIBE_MESSAGE_URL, accessToken);
        Map<String, Object> param = new HashMap<>();
        param.put("template_id", dto.getTemplateId());
        param.put("page", dto.getPage());
        param.put("touser", dto.getToUser());
        param.put("data", dto.getData());
        param.put("miniprogram_state", dto.getMiniProgramState().toString().toLowerCase());
        try {
            String rep = NetUtil.sendPost(url, param, null, NetUtil.PostType.JSON);
            log.debug(rep);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 取回手机号码
     *
     * @param code 手机号码获取凭证
     */
    public String getPhoneNumber(String code) {
        String access_token = getAccessToken();
        String url = String.format(PHONE_NUMBER_URL, access_token);
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);

        try {
            String rep = NetUtil.sendPost(url, param, null, NetUtil.PostType.JSON);
            WxPhoneVo wxPhoneVo = JsonUtil.jsonToObj(rep, WxPhoneVo.class);
            if (wxPhoneVo.getErrcode() != 0) {
                log.error("wxPhoneVo:{}", wxPhoneVo);
                throw new BusinessException("获取手机号码异常");
            }
            return wxPhoneVo.getPhoneInfo().getPhoneNumber();
        } catch (Exception e) {
            throw new BusinessException("获取手机号码异常");
        }
    }

    /**
     * 取回openId
     *
     * @param code
     */
    public String getOpenId(String code) {
        String appId = toolsConfigProperties.getWxMicAppId();
        String secret = toolsConfigProperties.getWxMicSecret();

        Map<String, Object> param = new HashMap<>();
        param.put("appid", appId);
        param.put("secret", secret);
        param.put("js_code", code);
        param.put("grant_type", "authorization_code");
        String rep = NetUtil.sendGet(AUTH_URL, param);
        log.info("小程序获取openid：{}", rep);
        WxTokenVo wxTokenVo = JsonUtil.jsonToObj(rep, WxTokenVo.class);
        if (wxTokenVo.getErrcode() != 0) {
            throw new BusinessException("获取微信openid异常");
        }
        return wxTokenVo.getOpenid();
    }
}
