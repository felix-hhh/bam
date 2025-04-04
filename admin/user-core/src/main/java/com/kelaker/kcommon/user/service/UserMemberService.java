package com.kelaker.kcommon.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kelaker.kcommon.user.constant.MemberType;
import com.kelaker.kcommon.user.dao.UserMemberDao;
import com.kelaker.kcommon.user.dto.UserMemberChangeDto;
import com.kelaker.kcommon.user.dto.UserMemberDto;
import com.kelaker.kcommon.user.dto.UserMemberSearchDto;
import com.kelaker.kcommon.user.entity.UserMember;
import com.kelaker.kcommon.user.vo.UserMemberVo;
import com.kelaker.ktools.common.utils.DateUtil;
import com.kelaker.ktools.common.utils.ValidateUtil;
import com.kelaker.ktools.common.vo.RequestPage;
import com.kelaker.ktools.web.base.service.BaseService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用户会员(UserMember)表服务
 *
 * @author Felix Huang
 * @since 2023-05-02 22:42:01
 */
@Service
public class UserMemberService extends BaseService<UserMemberDao, UserMember> {
    @Resource
    private UserInfoExtendsService userInfoExtendsService;

    /**
     * 分页查询
     *
     * @param searchDto 查询对象
     * @return 分页结果
     */
    public IPage<UserMemberVo> queryPage(RequestPage<UserMemberSearchDto> searchDto) {
        UserMemberSearchDto searchDtoData = searchDto.getData();
        UserMember empty = super.objectConvert(searchDtoData, UserMember.class);
        IPage<UserMember> page = super.page(super.createPage(searchDto), super.createWrapper(empty));
        return mapPageToTarget(page, this::convertToVo);
    }

    /**
     * 取回我的会员购买历史
     * @return
     */
    public List<UserMemberVo> listMyMember() {
        List<UserMember> list = super.lambdaQuery()
                .eq(UserMember::getUserId, super.getUserId())
                .orderByAsc(UserMember::getCreateDatetime)
                .list();
        return super.mapListToTarget(list, this::convertToVo);
    }

    /**
     * 添加对象
     *
     * @param dto 对象
     */
    @Transactional(rollbackFor = Exception.class)
    public void addUserMember(UserMemberDto dto) {
        Long userId = dto.getUserId();
        int memberTime = dto.getMemberTime();
        MemberType memberType = dto.getMemberType();
        UserMember userMember = super.objectConvert(dto, UserMember.class);

        userMember.setMemberType(memberType);
        Date startDate = DateUtil.getCurrentDate();
        userMember.setCreateDatetime(startDate);

        List<UserMember> userMembers = this.getUserMember(userId);
        Collections.sort(userMembers);
        if (ValidateUtil.isNotBlank(userMembers)) {
            UserMember currentUserMember = userMembers.get(0);

            if (memberType.equals(currentUserMember.getMemberType()) && !MemberType.SVIP.equals(memberType)) {
                //如果VIP等级等于当前等级，状态为暂停
                userMember.setStatus(UserMember.Status.PAUSE);
            } else {

                if (MemberType.SVIP.equals(currentUserMember.getMemberType())) {
                    //如果VIP等级为SVIP，且原来的等级也是SVIP，暂停新的SVIP
                    userMember.setStatus(UserMember.Status.PAUSE);
                } else {
                    //如果原来的等级不是SVIP，暂停原来的等级，启用新的会员
                    userMember.setStatus(UserMember.Status.NORMAL);
                    Date endDatetime = DateUtil.timeAddByDays(startDate, memberTime);
                    userMember.setStartDatetime(startDate);
                    userMember.setEndDatetime(endDatetime);
                    currentUserMember.setStatus(UserMember.Status.PAUSE);
                }
            }
        } else {
            userMember.setStatus(UserMember.Status.NORMAL);
        }
        userMembers.add(userMember);
        Collections.sort(userMembers);
        Date currentDate = null;
        for (UserMember member : userMembers) {
            if (ValidateUtil.isBlank(currentDate)) {
                currentDate = Optional.ofNullable(member.getStartDatetime()).orElse(DateUtil.getCurrentDate());
                member.setStartDatetime(currentDate);
            } else {
                member.setStartDatetime(DateUtil.timeAddByDays(currentDate, 1));
            }

            Date endDatetime = DateUtil.timeAddByDays(currentDate, member.getMemberTime());
            member.setEndDatetime(endDatetime);
            currentDate = endDatetime;
        }
        super.saveOrUpdateBatch(userMembers);

        List<UserMember> members = ValidateUtil.isNotBlank(this.getCurrentMemberByType(userMembers, MemberType.SVIP))?
                this.getCurrentMemberByType(userMembers, MemberType.SVIP):
                this.getCurrentMemberByType(userMembers, MemberType.VIP);
        int newMemberTime = 0;
        MemberType newMemberType = members.get(0).getMemberType();
        for (UserMember member : members) {
            newMemberTime += member.getMemberTime();
        }

        UserMemberChangeDto changeMemberTypeDto = new UserMemberChangeDto();
        changeMemberTypeDto.setMemberType(newMemberType);
        changeMemberTypeDto.setMemberTime(newMemberTime);
        this.userInfoExtendsService.changeMemberType(userId, changeMemberTypeDto);
    }

    /**
     * 刷新所有会员
     */
    @Transactional(rollbackFor = Exception.class)
    public void refreshAllMember() {
        List<UserMember> list = super.lambdaQuery()
                .eq(UserMember::getStatus, UserMember.Status.NORMAL).list();
        list.forEach(userMember -> {
            int memberTime = userMember.getMemberTime();
            int newMemberTime = --memberTime;
            if (newMemberTime <= 0) {
                //设置会员卡过期
                userMember.setStatus(UserMember.Status.FINISH);
                //设置用户会员等级

            }
            userMember.setMemberTime(newMemberTime);
            super.updateById(userMember);
        });
    }

    /**
     * 对象转换
     */
    private UserMemberVo convertToVo(UserMember userMember) {
        return super.objectConvert(userMember, UserMemberVo.class);
    }

    /**
     * 取回用户有效的会员记录
     *
     * @param userId 用户ID
     */
    private UserMember getUserMemberNormal(Long userId) {
        return super.lambdaQuery()
                .eq(UserMember::getUserId, userId)
                .eq(UserMember::getStatus, UserMember.Status.NORMAL)
                .one();
    }

    private List<UserMember> getUserMember(Long userId) {
        return super.lambdaQuery()
                .eq(UserMember::getUserId, userId)
                .and(wrapper -> wrapper
                        .eq(UserMember::getStatus, UserMember.Status.NORMAL)
                        .or()
                        .eq(UserMember::getStatus, UserMember.Status.PAUSE)
                )
                .list();
    }

    /**
     * 过滤会员卡数据
     *
     * @param userMembers 会员数据
     * @param memberType  会员类型
     */
    private List<UserMember> getCurrentMemberByType(List<UserMember> userMembers, MemberType memberType) {
        return userMembers
                .stream()
                .filter(userMember -> memberType.equals(userMember.getMemberType()))
                .collect(Collectors.toList());
    }


}

