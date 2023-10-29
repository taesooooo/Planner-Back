package com.planner.planner.Service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.planner.planner.Common.Notification.NotificationLink;
import com.planner.planner.Common.Notification.NotificationMessage;
import com.planner.planner.Common.Notification.NotificationType;
import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dao.InvitationDao;
import com.planner.planner.Dao.NotificationDao;
import com.planner.planner.Dao.PlanMemberDao;
import com.planner.planner.Dao.PlannerDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.InvitationDto;
import com.planner.planner.Dto.NotificationDto;
import com.planner.planner.Dto.PlanMemberDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Exception.DuplicatePlanMemberException;
import com.planner.planner.Exception.NotFoundMemberException;
import com.planner.planner.Exception.NotFoundPlanner;
import com.planner.planner.Exception.NotFoundUserException;
import com.planner.planner.Service.PlanMemberService;

@Service
public class PlanMemberServiceImpl implements PlanMemberService {
	private AccountDao accountDao;
	private PlanMemberDao planMemberDao;
	private PlannerDao plannerDao;
	private InvitationDao invitationDao;
	private NotificationDao notificationDao;
	
	public PlanMemberServiceImpl(AccountDao accountDao, PlanMemberDao planMemberDao, PlannerDao plannerDao, InvitationDao invitationDao, NotificationDao notificationDao) {
		this.accountDao = accountDao;
		this.planMemberDao = planMemberDao;
		this.plannerDao = plannerDao;
		this.invitationDao = invitationDao;
		this.notificationDao = notificationDao;
	}

	@Override
	public List<PlanMemberDto> findMembersByPlannerId(int plannerId) throws Exception {
		return planMemberDao.findMembersByPlannerId(plannerId);
	}

	@Override
	public void inviteMembers(int plannerId, List<String> nickNames) throws Exception {
		List<AccountDto> users = new ArrayList<AccountDto>();
		for (String nickName : nickNames) {
			AccountDto user = accountDao.findAccountIdByNickName(nickName);
			if (user == null) {
				throw new NotFoundUserException(nickName + "에 해당하는 사용자를 찾지 못했습니다.");
			}
			users.add(user);
		}
		
		PlannerDto planner = plannerDao.findPlannerByPlannerId(null, plannerId);
		if (planner == null) {
			throw new NotFoundPlanner("존재하지 않는 플래너 입니다.");
		}
		
		List<PlanMemberDto> invitedUsers = planMemberDao.findMembersByPlannerId(plannerId);
		boolean isInvited = invitedUsers.stream()
				.anyMatch((item) -> users.stream().anyMatch((user) -> user.getAccountId() == item.getAccountId()));
		
		if(isInvited) {
			throw new DuplicatePlanMemberException("초대된 유저가 있습니다. 다시 시도하세요.");
		}

		for (AccountDto user : users) {
			InvitationDto invitation = new InvitationDto.Builder()
					.setAccountId(user.getAccountId())
					.setPlannerId(plannerId)
					.build();
			
			int inviteId = invitationDao.createInvitation(invitation);
			
			NotificationDto notificationDto = new NotificationDto.Builder()
					.setAccountId(user.getAccountId())
					.setContent(String.format(NotificationMessage.PLANNER_INVAITE, planner.getCreator()))
					.setLink(String.format(NotificationLink.PLANNER_INVITE_LINK, inviteId))
					.setNotificationType(NotificationType.PLANNER_INVITE)
					.build();
			notificationDao.createNotification(user.getAccountId(), notificationDto);	
		}
	}

	@Override
	public void deleteMember(int plannerId, String nickName) throws Exception {
		List<PlanMemberDto> members = planMemberDao.findMembersByPlannerId(plannerId);
		AccountDto user = accountDao.findAccountIdByNickName(nickName);
		if (user == null) {
			throw new NotFoundUserException("사용자를 찾을 수 없습니다.");
		}
		boolean isMatch = members.stream().anyMatch(m -> m.getAccountId() == user.getAccountId());
		if (!isMatch) {
			throw new NotFoundMemberException("멤버를 찾을 수 없습니다.");
		}
		planMemberDao.deletePlanMember(plannerId, user.getAccountId());
	}

}
