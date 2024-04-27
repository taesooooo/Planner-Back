package com.planner.planner.Service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dao.InvitationDao;
import com.planner.planner.Dao.NotificationDao;
import com.planner.planner.Dao.PlanMemberDao;
import com.planner.planner.Dao.PlannerDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.InvitationDto;
import com.planner.planner.Dto.NotificationDto;
import com.planner.planner.Dto.PlanMemberDto;
import com.planner.planner.Dto.PlanMemberInviteDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Exception.DuplicatePlanMemberException;
import com.planner.planner.Exception.NotFoundMemberException;
import com.planner.planner.Exception.NotFoundUserException;
import com.planner.planner.Service.Impl.PlanMemberServiceImpl;

public class PlanMemberServiceTest {
	@Mock
	private AccountDao accountDao;
	@Mock
	private PlanMemberDao planMemberDao;
	@Mock
	private PlannerDao plannerDao;
	@Mock
	private InvitationDao invitationDao;
	@Mock
	private NotificationDao notificationDao;

	@InjectMocks
	private PlanMemberServiceImpl planMemberService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void 플래너_멤버_초대() throws Exception {
		int plannerId = 1;
		List<String>  inviteMemberEmails = new ArrayList<String>();
		inviteMemberEmails.add("test3@naver.com");
		
		PlanMemberInviteDto invitenMembers= new PlanMemberInviteDto.Builder().setMembers(inviteMemberEmails).build();
		
		AccountDto inviteMember = new AccountDto.Builder().setAccountId(3).build();
		
		// 간략하게 필요한 데이터만 넣어 생성
		PlannerDto planner = new PlannerDto.Builder().setAccountId(1)
				.setPlannerId(1)
				.build();
		
		List<PlanMemberDto> invitedMember = Arrays.asList(
				new PlanMemberDto.Builder().setPlanMemberId(1).setAccountId(1).setPlannerId(1).build());
		
		
		when(accountDao.findAccountIdByNickName(anyString())).thenReturn(inviteMember);
		when(plannerDao.findPlannerByPlannerId(isNull(), anyInt())).thenReturn(planner);
		when(planMemberDao.findMembersByPlannerId(anyInt())).thenReturn(invitedMember);
		
		planMemberService.inviteMembers(plannerId, invitenMembers);
		
		verify(accountDao, times(1)).findAccountIdByNickName(anyString());
		verify(plannerDao, times(1)).findPlannerByPlannerId(isNull(), anyInt());
		verify(planMemberDao, times(1)).findMembersByPlannerId(anyInt());
		verify(invitationDao, times(1)).createInvitation(any(InvitationDto.class));
		verify(notificationDao, times(1)).createNotification(anyInt(), any(NotificationDto.class));
	}
	
	@Test(expected = DuplicatePlanMemberException.class)
	public void 플래너_멤버_초대_중복() throws Exception {
		int plannerId = 1;
		List<String>  inviteMemberEmails = new ArrayList<String>();
		inviteMemberEmails.add("test2@naver.com");
		
		PlanMemberInviteDto invitenMembers= new PlanMemberInviteDto.Builder().setMembers(inviteMemberEmails).build();
		
		AccountDto inviteMember = new AccountDto.Builder().setAccountId(2).build();
		
		// 간략하게 필요한 데이터만 넣어 생성
		PlannerDto planner = new PlannerDto.Builder().setAccountId(1)
				.setPlannerId(1)
				.build();
		
		List<PlanMemberDto> invitedMember = Arrays.asList(
				new PlanMemberDto.Builder().setPlanMemberId(1).setAccountId(1).setPlannerId(1).build(),
				new PlanMemberDto.Builder().setPlanMemberId(2).setAccountId(2).setPlannerId(1).build());
		
		when(accountDao.findAccountIdByNickName(anyString())).thenReturn(inviteMember);
		when(plannerDao.findPlannerByPlannerId(isNull(), anyInt())).thenReturn(planner);
		when(planMemberDao.findMembersByPlannerId(anyInt())).thenReturn(invitedMember);
		
		planMemberService.inviteMembers(plannerId, invitenMembers);
		
		verify(accountDao, times(1)).findAccountIdByNickName(anyString());
		verify(plannerDao, times(1)).findPlannerByPlannerId(isNull(), anyInt());
		verify(planMemberDao, times(1)).findMembersByPlannerId(anyInt());
	}
	
	@Test(expected = NotFoundUserException.class)
	public void 플래너_멤버_초대_사용자가없을때() throws Exception {
		int plannerId = 1;
		List<String>  inviteMemberEmails = new ArrayList<String>();
		inviteMemberEmails.add("test3@naver.com");
		
		PlanMemberInviteDto invitenMembers= new PlanMemberInviteDto.Builder().setMembers(inviteMemberEmails).build();
		
		when(accountDao.findAccountIdByNickName(anyString())).thenReturn(null);
		
		planMemberService.inviteMembers(plannerId, invitenMembers);
		
		verify(accountDao, times(1)).findAccountIdByNickName(anyString());
	}
	
	@Test
	public void 플래너_멤버_삭제() throws Exception {
		int plannerId = 1;
		String testNickName = "test2";
		List<PlanMemberDto> members = new ArrayList<PlanMemberDto>();
		members.add(new PlanMemberDto.Builder().setPlanMemberId(1).setAccountId(1).setPlannerId(1).build());
		members.add(new PlanMemberDto.Builder().setPlanMemberId(2).setAccountId(2).setPlannerId(1).build());
		AccountDto user = new AccountDto.Builder().setAccountId(2).build();
		
		when(planMemberDao.findMembersByPlannerId(plannerId)).thenReturn(members);
		when(accountDao.findAccountIdByNickName(testNickName)).thenReturn(user);
		
		planMemberService.deleteMember(plannerId, testNickName);

		verify(planMemberDao).findMembersByPlannerId(anyInt());
		verify(accountDao).findAccountIdByNickName(anyString());
		verify(planMemberDao).deletePlanMember(anyInt(), anyInt());
	}
	
	@Test(expected = NotFoundUserException.class)
	public void 플래너_멤버_삭제_사용자가없을때() throws Exception {
		int plannerId = 1;
		String testNickName = "test2";
		List<PlanMemberDto> members = new ArrayList<PlanMemberDto>();
		members.add(new PlanMemberDto.Builder().setPlanMemberId(1).setAccountId(1).setPlannerId(1).build());
		members.add(new PlanMemberDto.Builder().setPlanMemberId(2).setAccountId(2).setPlannerId(1).build());

		when(planMemberDao.findMembersByPlannerId(plannerId)).thenReturn(members);
		when(accountDao.findAccountIdByNickName(testNickName)).thenReturn(null);
		
		planMemberService.deleteMember(plannerId, testNickName);

		verify(planMemberDao).findMembersByPlannerId(anyInt());
		verify(accountDao).findAccountIdByNickName(anyString());
	}
	
	@Test(expected = NotFoundMemberException.class)
	public void 플래너_멤버_삭제_멤버가없을때() throws Exception {
		int plannerId = 1;
		String testNickName = "test3";
		List<PlanMemberDto> members = new ArrayList<PlanMemberDto>();
		members.add(new PlanMemberDto.Builder().setPlanMemberId(1).setAccountId(1).setPlannerId(1).build());
		members.add(new PlanMemberDto.Builder().setPlanMemberId(2).setAccountId(2).setPlannerId(1).build());
		AccountDto user = new AccountDto.Builder().setAccountId(3).build();
		
		when(planMemberDao.findMembersByPlannerId(plannerId)).thenReturn(members);
		when(accountDao.findAccountIdByNickName(testNickName)).thenReturn(user);
		
		planMemberService.deleteMember(plannerId, testNickName);

		verify(planMemberDao).findMembersByPlannerId(anyInt());
		verify(accountDao).findAccountIdByNickName(anyString());
	}

}
