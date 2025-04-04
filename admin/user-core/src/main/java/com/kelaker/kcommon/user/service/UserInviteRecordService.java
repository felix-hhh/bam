package com.kelaker.kcommon.user.service;

import com.kelaker.kcommon.user.configs.UserConfigProperties;
import com.kelaker.kcommon.user.constant.CacheConstant;
import com.kelaker.kcommon.user.dao.UserInviteRecordDao;
import com.kelaker.kcommon.user.entity.UserInviteRecord;
import com.kelaker.ktools.common.exception.BusinessException;
import com.kelaker.ktools.common.utils.NumberUtil;
import com.kelaker.ktools.web.base.service.BaseService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserInviteRecordService extends BaseService<UserInviteRecordDao, UserInviteRecord> {

    @Resource
    private RedisTemplate<String, Object> redis;

    @Resource
    private UserConfigProperties configProperties;

    /**
     * 获取邀请码
     */
    public Integer createCode() {
        Long userId = this.getUserId();
        int code = NumberUtil.getRandomInt(99999, 10000);
        // 设置过期时间
        redis.opsForValue().set(CacheConstant.USER_INVITE_CODE + code, userId, configProperties.getUserInviteCodeTimeout(), TimeUnit.MINUTES);
        return code;
    }

    /**
     * 邀请记录
     *
     * @param code
     */
    @Transactional(rollbackFor = Exception.class)
    public void invitationRecord(String code) {
        UserInviteRecord record = new UserInviteRecord();
        record.setInviteeId(this.getUserId());
        String key = CacheConstant.USER_INVITE_CODE + code;
        Object invitePerson = redis.opsForValue().get(key);
        if (invitePerson == null) {
            throw new BusinessException("邀请码失效");
        }
//        record.setUserId(invitePerson.toString());
        this.save(record);
        redis.delete(key);
    }
}
