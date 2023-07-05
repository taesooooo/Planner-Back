package com.planner.planner.Service;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dao.PlanMemberDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.PlanMemberDto;
import com.planner.planner.Exception.NotFoundMemberException;
import com.planner.planner.Exception.NotFoundUserException;
import com.planner.planner.Service.Impl.PlanMemberServiceImpl;

public class PlanMemberServiceTest {
	@InjectMocks
	private PlanMemberServiceImpl planMemberService;
	
	@Mock
	private AccountDao accountDao;
	
	@Mock
	private PlanMemberDao planMemberDao;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void 플래너_멤버_초대() throws Exception {
		int plannerId = 1;
		List<String>  inviteMemberEmails = new ArrayList<String>();
		inviteMemberEmails.add("test3@naver.com");
		AccountDto inviteMember = new AccountDto.Builder().setAccountId(3).build();
		
		when(accountDao.findAccountIdByNickName(anyString())).thenReturn(inviteMember);
		
		planMemberService.inviteMembers(plannerId, inviteMemberEmails);
		
		verify(accountDao, times(1)).findAccountIdByNickName(anyString());
		verify(planMemberDao, times(1)).insertPlanMember(anyInt(), anyInt());
	}
	
	@Test(expected = NotFoundUserException.class)
	public void 플래너_멤버_초대_사용자가없을때() throws Exception {
		int plannerId = 1;
		List<String>  inviteMemberEmails = new ArrayList<String>();
		inviteMemberEmails.add("test3@naver.com");
		
		when(accountDao.findAccountIdByNickName(anyString())).thenReturn(null);
		
		planMemberService.inviteMembers(plannerId, inviteMemberEmails);
		
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
