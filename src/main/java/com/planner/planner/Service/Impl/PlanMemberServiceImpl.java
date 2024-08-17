package com.planner.planner.Service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Common.Notification.NotificationLink;
import com.planner.planner.Common.Notification.NotificationMessage;
import com.planner.planner.Common.Notification.NotificationType;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.InvitationDto;
import com.planner.planner.Dto.NotificationDto;
import com.planner.planner.Dto.PlanMemberDto;
import com.planner.planner.Dto.PlanMemberInviteDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Exception.DuplicatePlanMemberException;
import com.planner.planner.Exception.MemberNotFoundException;
import com.planner.planner.Exception.PlannerNotFoundException;
import com.planner.planner.Exception.UserNotFoundException;
import com.planner.planner.Mapper.AccountMapper;
import com.planner.planner.Mapper.InvitationMapper;
import com.planner.planner.Mapper.NotificationMapper;
import com.planner.planner.Mapper.PlanMemberMapper;
import com.planner.planner.Mapper.PlannerMapper;
import com.planner.planner.Service.PlanMemberService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanMemberServiceImpl implements PlanMemberService {
	private final AccountMapper accountMapper;
	private final PlanMemberMapper planMemberMapper;
	private final PlannerMapper plannerMapper;
	private final InvitationMapper invitationMapper;
	private final NotificationMapper notificationMapper;
	
	@Override
	public List<PlanMemberDto> findMembersByPlannerId(int plannerId) throws Exception {
		return planMemberMapper.findPlanMemberListByPlannerId(plannerId);
	}

	@Override
	public void inviteMembers(int plannerId, PlanMemberInviteDto members) throws Exception {
		List<AccountDto> users = new ArrayList<AccountDto>();
		for (String nickName : members.getMembers()) {
			AccountDto user = accountMapper.findByNickName(nickName);
			if (user == null) {
				throw new UserNotFoundException(nickName + "에 해당하는 사용자를 찾지 못했습니다.");
			}
			users.add(user);
		}
		
		PlannerDto planner = plannerMapper.findByPlannerId(null, plannerId);
		if (planner == null) {
			throw new PlannerNotFoundException("존재하지 않는 플래너 입니다.");
		}
		
		List<PlanMemberDto> invitedUsers = planMemberMapper.findPlanMemberListByPlannerId(plannerId);
		boolean isInvited = invitedUsers.stream()
				.anyMatch((item) -> users.stream().anyMatch((user) -> user.getAccountId() == item.getAccountId()));
		
		if(isInvited) {
			throw new DuplicatePlanMemberException("초대된 유저가 있습니다. 다시 시도하세요.");
		}

		for (AccountDto user : users) {
			InvitationDto invitation = InvitationDto.builder()
					.accountId(user.getAccountId())
					.plannerId(plannerId)
					.build();
			
			int inviteId = invitationMapper.createInvitation(invitation);
			
			NotificationDto notificationDto = NotificationDto.builder()
					.accountId(user.getAccountId())
					.content(String.format(NotificationMessage.PLANNER_INVAITE, planner.getCreator()))
					.link(String.format(NotificationLink.PLANNER_INVITE_LINK, inviteId))
					.notificationType(NotificationType.PLANNER_INVITE)
					.build();
			
			notificationMapper.insertNotification(user.getAccountId(), notificationDto);	
		}
	}

	@Override
	public void deleteMember(int plannerId, String nickName) throws Exception {
		List<PlanMemberDto> members = planMemberMapper.findPlanMemberListByPlannerId(plannerId);
		AccountDto user = accountMapper.findByNickName(nickName);
		if (user == null) {
			throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
		}
		boolean isMatch = members.stream().anyMatch(m -> m.getAccountId() == user.getAccountId());
		if (!isMatch) {
			throw new MemberNotFoundException("멤버를 찾을 수 없습니다.");
		}
		planMemberMapper.deletePlanMember(plannerId, user.getAccountId());
	}

}
