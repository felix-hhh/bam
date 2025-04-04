package com.kelaker.kcommon.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.kelaker.kcommon.common.tools.vo.IpLocationVo;
import com.kelaker.kcommon.common.user.vo.UserLocationVo;
import com.kelaker.kcommon.user.constant.CacheConstant;
import com.kelaker.kcommon.user.constant.MemberType;
import com.kelaker.kcommon.user.constant.UserConstant;
import com.kelaker.kcommon.user.dao.UserInfoExtendsDao;
import com.kelaker.kcommon.user.dto.UserInfoExtendsChangeDto;
import com.kelaker.kcommon.user.dto.UserInfoExtendsSearchDto;
import com.kelaker.kcommon.user.dto.UserLocationDto;
import com.kelaker.kcommon.user.dto.UserMemberChangeDto;
import com.kelaker.kcommon.user.entity.UserInfoExtends;
import com.kelaker.kcommon.user.event.PushSystemMessageEvent;
import com.kelaker.kcommon.user.vo.UserInfoExtendsVo;
import com.kelaker.kcommon.user.vo.UserRealNameDto;
import com.kelaker.ktools.cache.annotation.CacheExpire;
import com.kelaker.ktools.cache.annotation.CacheIt;
import com.kelaker.ktools.cache.manager.CacheManager;
import com.kelaker.ktools.common.exception.BusinessException;
import com.kelaker.ktools.common.populator.ConvertUtils;
import com.kelaker.ktools.common.utils.DateUtil;
import com.kelaker.ktools.common.utils.ValidateUtil;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户拓展(UserInfoExtends)表服务接口
 *
 * @author felix huang
 * @since 2021-12-14 21:26:35
 */
@Slf4j
@Service
public class UserInfoExtendsService extends BaseService<UserInfoExtendsDao, UserInfoExtends> {

    @Resource
    private CacheManager cacheManager;

    @Resource
    private ApplicationEventPublisher publisher;

    /**
     * 更新用户归属地
     *
     * @param locationDto
     */
    public void pushMyLocation(UserLocationDto locationDto) {

        //转换归属地
        String ipAddr = locationDto.getIp();
        IpLocationVo locationVo = new IpLocationVo();
//        IpLocationVo locationVo = toolsIpDataFeignClient.convertIpToLocation(ipAddr).getData();
        if (!ValidateUtil.hasBlank(locationVo, locationVo.getContent(), locationVo.getContent().getAddressDetail())) {
            IpLocationVo.Content content = locationVo.getContent();
            IpLocationVo.AddressDetail addressDetail = content.getAddressDetail();
            locationDto.setLocalCode(addressDetail.getAdcode());
            locationDto.setLocalCity(addressDetail.getCity());
            locationDto.setLocalProvince(addressDetail.getProvince());
            if (ValidateUtil.hasBlank(locationDto.getLongitude(), locationDto.getLatitude())) {
                locationDto.setLongitude(ConvertUtils.convertToDouble(content.getPoint().getX()));
                locationDto.setLatitude(ConvertUtils.convertToDouble(content.getPoint().getY()));
            }

            UserInfoExtends userInfoExtends = this.getById(super.getUserId());
            userInfoExtends.setLocalCity(addressDetail.getCity());
            userInfoExtends.setLocalProvince(addressDetail.getProvince());
            userInfoExtends.setLocalCode(addressDetail.getAdcode());
            super.updateById(userInfoExtends);
        }

        String cacheKey = CacheConstant.USER_LOCAL_CACHE_KEY + super.getUserId();

        this.cacheManager.cacheValue(cacheKey, locationDto, CacheConstant.USER_LOCAL_CACHE_TIMEOUT);
    }

    /**
     * 取回所有机器人列表
     */
    public Set<Long> getAllRobot() {
        return super.lambdaQuery()
                .eq(UserInfoExtends::getUserRole, UserInfoExtends.UserRole.ROBOT)
                .select(UserInfoExtends::getId)
                .list()
                .stream()
                .map(UserInfoExtends::getId)
                .collect(Collectors.toSet());
    }

    /**
     * 取回用户归属地
     */
    public UserLocationVo pullMyLocation(String userId) {

        String cacheKey = CacheConstant.USER_LOCAL_CACHE_KEY + userId;

        UserLocationDto locationDto = (UserLocationDto) this.cacheManager.getValue(cacheKey);
        if (ValidateUtil.isBlank(locationDto)) {
            return null;
        }
        UserLocationVo userLocationVo = super.objectConvert(locationDto, UserLocationVo.class);
        userLocationVo.setIpLocation(locationDto.getLocalProvince());
        return userLocationVo;
    }

    /**
     * 取回个人资料
     */
    public UserInfoExtendsVo getMyUserInfoExtends() {
        UserInfoExtends userInfoExtends = super.getById(super.getUserId());
        if (ValidateUtil.isBlank(userInfoExtends)) {
            throw new BusinessException("暂无用户信息");
        }
        return super.objectConvert(userInfoExtends, UserInfoExtendsVo.class);
    }

    /**
     * 根据用户ID取回个人资料（前端）
     *
     * @param id 用户ID
     */
    public UserInfoExtendsVo getUserInfoExtendsByUserIdFront(Long id) {
        UserInfoExtends userInfoExtends = super.getById(id);
        if (ValidateUtil.isBlank(userInfoExtends)) {
            throw new BusinessException("暂无用户信息");
        }
        return super.objectConvert(userInfoExtends, UserInfoExtendsVo.class);
    }

    /**
     * 取回用户拓展信息
     *
     * @param id 用户ID
     */
    public UserInfoExtendsVo getUserInfoExtends(String id) {
        UserInfoExtends userInfoExtends = super.getById(id);
        if (ValidateUtil.isBlank(userInfoExtends)) {
            throw new BusinessException("暂无用户信息");
        }
        return super.objectConvert(userInfoExtends, UserInfoExtendsVo.class);
    }

    /**
     * 初始化拓展资料
     *
     * @param userId 用户ID
     */
    public void initUserInfoExtends(Long userId, UserInfoExtends.UserRole userRole) {
        UserInfoExtends userInfoExtends = super.getById(userId);
        if (ValidateUtil.isNotBlank(userInfoExtends)) {
            throw new BusinessException("用户拓展资料已经存在");
        }

        userInfoExtends = new UserInfoExtends();
        userInfoExtends.setId(userId);
        userInfoExtends.setUserRole(userRole);
        super.save(userInfoExtends);
    }

    /**
     * 初始化拓展资料
     *
     * @param userId 用户ID
     */
    public void initUserInfoExtends(Long userId, UserInfoExtends.UserRole userRole, int gender) {
        UserInfoExtends userInfoExtends = super.getById(userId);
        if (ValidateUtil.isNotBlank(userInfoExtends)) {
            throw new BusinessException("用户拓展资料已经存在");
        }

        userInfoExtends = new UserInfoExtends();
        userInfoExtends.setId(userId);
        userInfoExtends.setUserRole(userRole);
        userInfoExtends.setGender(gender);
        super.save(userInfoExtends);
    }

    /**
     * 修改用户拓展资料
     *
     * @param userInfoExtendsChangeDto
     */
    public void updateUserInfoExtends(UserInfoExtendsChangeDto userInfoExtendsChangeDto) {
        UserInfoExtends userInfoExtends = super.getById(super.getUserId());
        if (ValidateUtil.isBlank(userInfoExtends)) {
            throw new BusinessException("拓展资料不存在");
        }

        String value = userInfoExtendsChangeDto.getValue();

        switch (userInfoExtendsChangeDto.getType()) {
            case GENDER -> {
                Integer gender = userInfoExtends.getGender();
                if (ValidateUtil.isNotBlank(gender)) {
                    throw new BusinessException("性别设定后不允许修改");
                }
                userInfoExtends.setGender(ConvertUtils.convertToInteger(value));
            }
            case HEAD_IMG -> userInfoExtends.setHeadImgPath(value);
            case SIGNATURE -> userInfoExtends.setSignature(value);
        }
        userInfoExtends.setModifyDatetime(DateUtil.getCurrentDate());
        super.updateById(userInfoExtends);
    }

    /**
     * 分页查询用户拓展信息
     *
     * @param searchDto 查询vo
     */
    public IPage<UserInfoExtendsVo> queryUserInfoExtendsPage(RequestPage<UserInfoExtendsSearchDto> searchDto) {
        UserInfoExtends userInfoExtends = super.objectConvert(searchDto.getData(), UserInfoExtends.class);
        IPage<UserInfoExtends> page = super.page(super.createPage(searchDto), super.createWrapper(userInfoExtends));
        return super.mapPageToTarget(page, this::convertToVo);
    }

    private UserInfoExtendsVo convertToVo(UserInfoExtends userInfoExtends) {
        return super.objectConvert(userInfoExtends, UserInfoExtendsVo.class);
    }

    /**
     * 关注(关注+1，粉丝+1）
     *
     * @param userId 好友ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void concern(String userId, boolean add) {
        LambdaUpdateChainWrapper<UserInfoExtends> myWrapper = super.lambdaUpdate()
                .eq(UserInfoExtends::getId, super.getUserId());

        LambdaUpdateChainWrapper<UserInfoExtends> friendsWrapper = super.lambdaUpdate()
                .eq(UserInfoExtends::getId, userId);

        if (add) {
            myWrapper.setSql("concern_count = concern_count + 1");
            friendsWrapper.setSql("fans_count = fans_count + 1");

        } else {
            myWrapper.setSql("concern_count = concern_count - 1");
            friendsWrapper.setSql("fans_count = fans_count - 1");
        }

        myWrapper.update();
        friendsWrapper.update();
    }

    /**
     * 好友情况下取消关注
     *
     * @param userId 用户ID
     * @param add    成为好友还是取消关注
     */
    @Transactional(rollbackFor = Exception.class)
    public void friend(String userId, boolean add) {
        LambdaUpdateChainWrapper<UserInfoExtends> myWrapper = super.lambdaUpdate()
                .eq(UserInfoExtends::getId, super.getUserId());

        LambdaUpdateChainWrapper<UserInfoExtends> friendsWrapper = super.lambdaUpdate()
                .eq(UserInfoExtends::getId, userId);

        if (add) {
            myWrapper.setSql("friend_count = friend_count + 1, concern_count = concern_count + 1");
            friendsWrapper.setSql("friend_count = friend_count + 1, fans_count = fans_count + 1");
        } else {
            myWrapper.setSql("friend_count = friend_count - 1, concern_count = concern_count - 1");
            friendsWrapper.setSql("friend_count = friend_count - 1, fans_count = fans_count - 1");
        }

        myWrapper.update();
        friendsWrapper.update();
    }

    /**
     * 关注数 +1
     *
     * @param userId 用户ID
     */
    protected void addVisitorCount(String userId) {
        super.lambdaUpdate()
                .eq(UserInfoExtends::getId, userId)
                .setSql(" visitor_count = visitor_count + 1")
                .update();
    }

    /**
     * 用户拓展信息
     *
     * @param userId 用户ID
     */
    @CacheIt(key = CacheConstant.USER_EXTENDS_CACHE_KEY, paramKey = true)
    public UserInfoExtends getUserById(Long userId) {
        return this.getById(userId);
    }

    /**
     * 用户拓展信息
     *
     * @param userId
     */
    public UserInfoExtends getUserByIdNoCache(Long userId) {
        return this.getById(userId);
    }

    /**
     * 开通直播
     */
    public void changeLiveStateEnable(String userId) {
        UserInfoExtends userInfoExtends = super.getById(userId);
        boolean live = userInfoExtends.isLive();
        if (live) {
            throw new BusinessException("请勿重复开通直播");
        }
        userInfoExtends.setLive(true);
        super.updateById(userInfoExtends);
    }

    /**
     * 开通直播
     */
    public void changeLiveStateDisable(String userId) {
        UserInfoExtends userInfoExtends = super.getById(userId);
        boolean live = userInfoExtends.isLive();
        if (!live) {
            throw new BusinessException("请勿重复关闭直播");
        }
        userInfoExtends.setLive(false);
        super.updateById(userInfoExtends);
    }

    /**
     * 用户开通店铺
     *
     * @param userId
     */
    public void changeShopStateEnable(String userId) {
        UserInfoExtends userInfoExtends = super.getById(userId);
        boolean shop = userInfoExtends.isHasShop();
        if (shop) {
            throw new BusinessException("请勿重复开通店铺");
        }
        userInfoExtends.setHasShop(true);
        super.updateById(userInfoExtends);
    }

    /**
     * 用户关闭店铺
     *
     * @param userId 用户ID
     */
    public void changeShopStateDisable(String userId) {
        UserInfoExtends userInfoExtends = super.getById(userId);
        boolean shop = userInfoExtends.isHasShop();
        if (!shop) {
            throw new BusinessException("请勿重复关闭店铺");
        }
        userInfoExtends.setHasShop(false);
        super.updateById(userInfoExtends);
    }

    /**
     * 查询所有地区的男性用户
     *
     * @param localCode 地区代码
     */
    @CacheIt(key = CacheConstant.CACHE_USER_LIST_PREFIX + "MALE", paramKey = true)
    public Set<Long> getMaleByLocalCode(String localCode) {
        return super.lambdaQuery()
                .eq(UserInfoExtends::getLocalCode, localCode)
                .eq(UserInfoExtends::getGender, 1)
                .select(UserInfoExtends::getId)
                .list()
                .stream()
                .map(UserInfoExtends::getId)
                .collect(Collectors.toSet());
    }

    /**
     * 查询制定地区的所有女性用户
     *
     * @param localCode 地区代码
     */
    @CacheIt(key = CacheConstant.CACHE_USER_LIST_PREFIX + "FEMALE", paramKey = true)
    public Set<Long> getFemaleByLocalCode(String localCode) {
        return super.lambdaQuery()
                .eq(UserInfoExtends::getLocalCode, localCode)
                .eq(UserInfoExtends::getGender, 0)
                .select(UserInfoExtends::getId)
                .list()
                .stream()
                .map(UserInfoExtends::getId)
                .collect(Collectors.toSet());
    }

    /**
     * 变更会员类型
     *
     * @param dto
     */
    @CacheExpire(key = CacheConstant.USER_INFO_CACHE_KEY + "USER_ALL", paramKey = true)
    public void changeMemberType(Long userId, UserMemberChangeDto dto) {
        MemberType memberType = dto.getMemberType();
        UserInfoExtends userInfoExtends = super.getById(userId);
        if (ValidateUtil.isBlank(memberType)) {
            //清空
            userInfoExtends.setMemberType(null);
            userInfoExtends.setMemberTime(null);
        } else {
            userInfoExtends.setMemberTime(DateUtil.timeAddByDays(DateUtil.getCurrentDate(), dto.getMemberTime()));
            userInfoExtends.setMemberType(memberType);

            PushSystemMessageEvent event = new PushSystemMessageEvent(this);
            String message = String.format("成功开通 " + memberType.name() + " 会员");
            event.setMessage(message);
            event.setTo(userId);
            event.setType(UserConstant.MEMBER_OPEN_SYSTEM_NOTICE_TYPE_KEY);

            this.publisher.publishEvent(event);
        }
        super.updateById(userInfoExtends);
    }

    /**
     * 检查实名认证是否存在别的账户
     *
     * @param userRealNameDto 实名认证检查
     */
    public UserInfoExtends checkRealName(UserRealNameDto userRealNameDto) {
        return super.lambdaQuery()
                .eq(UserInfoExtends::getRealName, userRealNameDto.getRealName())
                .eq(UserInfoExtends::getIdCardNo, userRealNameDto.getIdCardNo())
                .one();
    }
}
