package com.planner.planner.Service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dao.PlannerDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.Dto.PlanMemberDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Exception.NotFoundMemberException;
import com.planner.planner.Exception.NotFoundPlanner;
import com.planner.planner.Exception.NotFoundUserException;
import com.planner.planner.Service.Impl.PlannerServiceImpl;

public class PlannerServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlannerServiceTest.class);
	
	@InjectMocks
	private PlannerServiceImpl plannerService;
	
	@Mock
	private PlannerDao plannerDao;
	
	@Mock
	private AccountDao accountDao;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void 새플래너_생성() throws Exception {
		String testCreatorEmail = "test@naver.com";
		PlannerDto planner = createBasePlanner();
		AccountDto creator = new AccountDto.Builder().setAccountId(1).build();
		List<AccountDto> users = new ArrayList<AccountDto>();
		users.add(new AccountDto.Builder().setAccountId(2).build());
		
		when(accountDao.findAccountIdByEmail(anyString())).thenReturn(creator, users.get(0));
		when(plannerDao.insertPlanMember(anyInt(), anyInt())).thenReturn(0);
		when(plannerDao.acceptInvitation(anyInt(), anyInt())).thenReturn(0);
		
		plannerService.newPlanner(planner);
		
		verify(accountDao, times(2)).findAccountIdByEmail(anyString());
		verify(plannerDao, times(2)).insertPlanMember(anyInt(), anyInt());
		verify(plannerDao, times(1)).acceptInvitation(anyInt(), anyInt());
	}
	
	@Test(expected = NotFoundUserException.class)
	public void 새플래너_생성_추가한멤버중사용자가없을때() throws Exception {
		String testCreatorEmail = "test@naver.com";
		PlannerDto planner = createBasePlanner();
		AccountDto creator = new AccountDto.Builder().setAccountId(1).build();
		
		when(accountDao.findAccountIdByEmail(anyString())).thenReturn(creator).thenReturn(null);
		
		plannerService.newPlanner(planner);
		
		verify(accountDao, times(2)).findAccountIdByEmail(anyString());
	}
	
	@Test
	public void 플래너아이디_플래너조회() throws Exception {
		int plannerId = 1;
		PlannerDto planner = createPlanner(1);
		
		when(plannerDao.findPlannerByPlannerId(plannerId)).thenReturn(planner);
		
		PlannerDto findPlanner = plannerService.findPlannerByPlannerId(plannerId);
		
		assertEquals(planner.getPlannerId(), findPlanner.getPlannerId());
	}
	
	@Test(expected = NotFoundPlanner.class)
	public void 플래너아이디_플래너조회_만약플래너가_없을때() throws Exception {
		int plannerId = 0;
		PlannerDto planner = createPlanner(1);
		
		when(plannerDao.findPlannerByPlannerId(0)).thenReturn(null);
		
		PlannerDto findPlanner = plannerService.findPlannerByPlannerId(0);

	}
	
	@Test
	public void 계정아이디_모든플래너조회() throws Exception {
		int plannerId = 1;
		List<PlannerDto> planners = new ArrayList<PlannerDto>();
		planners.add(createPlanner(1));
		planners.add(createPlanner(2));
		
		when(plannerDao.findPlannersByAccountId(plannerId)).thenReturn(planners);
		
		List<PlannerDto> findPlanners = plannerService.findPlannersByAccountId(plannerId);
		
		assertEquals(findPlanners.size(), 2);
	}
	
	@Test(expected = NotFoundPlanner.class)
	public void 계정아이디_모든플래너조회_만약플래너가_없을때() throws Exception {
		int plannerId = 1;
		List<PlannerDto> planners = new ArrayList<PlannerDto>();
		
		when(plannerDao.findPlannersByAccountId(plannerId)).thenReturn(planners);
		
		List<PlannerDto> findPlanners = plannerService.findPlannersByAccountId(plannerId);
	}
	
	@Test
	public void 모든플래너조회() throws Exception {
		List<PlannerDto> planners = new ArrayList<PlannerDto>();
		planners.add(createPlanner(1));
		planners.add(createPlanner(2));
		
		when(plannerDao.findPlannerAll()).thenReturn(planners);
		
		List<PlannerDto> findPlanners = plannerService.findPlannerAll();
		
		assertEquals(findPlanners.size(), 2);
	}
	
	@Test(expected = NotFoundPlanner.class)
	public void 모든플래너조회_만약플래너가_없을때() throws Exception {
		List<PlannerDto> planners = new ArrayList<PlannerDto>();
		
		when(plannerDao.findPlannerAll()).thenReturn(planners);
		
		List<PlannerDto> findPlanners = plannerService.findPlannerAll();
	}
	
	@Test
	public void 플래너_수정() throws Exception {
		PlannerDto planner = createBasePlanner();
		when(plannerDao.updatePlanner(anyInt(), any())).thenReturn(0);
		
		plannerService.modifyPlanner(planner);
		
		verify(plannerDao).updatePlanner(anyInt(), any());
	}
	
	@Test
	public void 플래너_삭제() throws Exception {
		when(plannerDao.deletePlanner(anyInt())).thenReturn(0);
		
		plannerService.deletePlanner(anyInt());
		
		verify(plannerDao).deletePlanner(anyInt());
	}
	
	@Test
	public void 플래너_멤버조회() throws Exception {
		when(plannerDao.findMembersByPlannerId(anyInt())).thenReturn(null);
		
		plannerService.findMembersByPlannerId(anyInt());
		
		verify(plannerDao).findMembersByPlannerId(anyInt());
	}
	
	@Test
	public void 플래너_멤버_초대() throws Exception {
		int plannerId = 1;
		List<String>  inviteMemberEmails = new ArrayList<String>();
		inviteMemberEmails.add("test3@naver.com");
		AccountDto inviteMember = new AccountDto.Builder().setAccountId(3).build();
		
		when(accountDao.findAccountIdByEmail(inviteMemberEmails.get(0))).thenReturn(inviteMember);
		
		plannerService.inviteMembers(plannerId, inviteMemberEmails);
		
		verify(accountDao, times(1)).findAccountIdByEmail(anyString());
		verify(plannerDao, times(1)).insertPlanMember(anyInt(), anyInt());
	}
	
	@Test(expected = NotFoundUserException.class)
	public void 플래너_멤버_초대_사용자가없을때() throws Exception {
		int plannerId = 1;
		List<String>  inviteMemberEmails = new ArrayList<String>();
		inviteMemberEmails.add("test3@naver.com");
		
		AccountDto inviteMember = new AccountDto.Builder().setAccountId(3).build();
		
		when(accountDao.findAccountIdByEmail(inviteMemberEmails.get(0))).thenReturn(null);
		
		plannerService.inviteMembers(plannerId, inviteMemberEmails);
		
		verify(accountDao, times(1)).findAccountIdByEmail(anyString());
	}
	
	@Test
	public void 플래너_멤버_삭제() throws Exception {
		int plannerId = 1;
		String testEmail = "test2@naver.com";
		List<PlanMemberDto> members = new ArrayList<PlanMemberDto>();
		members.add(new PlanMemberDto.Builder().setPlanMemberId(1).setAccountId(1).setPlannerId(1).build());
		members.add(new PlanMemberDto.Builder().setPlanMemberId(2).setAccountId(2).setPlannerId(1).build());
		AccountDto user = new AccountDto.Builder().setAccountId(2).build();
		
		when(plannerDao.findMembersByPlannerId(plannerId)).thenReturn(members);
		when(accountDao.findAccountIdByEmail(testEmail)).thenReturn(user);
		
		plannerService.deleteMember(plannerId, testEmail);

		verify(plannerDao).findMembersByPlannerId(anyInt());
		verify(accountDao).findAccountIdByEmail(anyString());
		verify(plannerDao).deletePlanMember(anyInt(), anyInt());
	}
	
	@Test(expected = NotFoundUserException.class)
	public void 플래너_멤버_삭제_사용자가없을때() throws Exception {
		int plannerId = 1;
		String testEmail = "test2@naver.com";
		List<PlanMemberDto> members = new ArrayList<PlanMemberDto>();
		members.add(new PlanMemberDto.Builder().setPlanMemberId(1).setAccountId(1).setPlannerId(1).build());
		members.add(new PlanMemberDto.Builder().setPlanMemberId(2).setAccountId(2).setPlannerId(1).build());

		when(plannerDao.findMembersByPlannerId(plannerId)).thenReturn(members);
		when(accountDao.findAccountIdByEmail(testEmail)).thenReturn(null);
		
		plannerService.deleteMember(plannerId, testEmail);

		verify(plannerDao).findMembersByPlannerId(anyInt());
		verify(accountDao).findAccountIdByEmail(anyString());
	}
	
	@Test(expected = NotFoundMemberException.class)
	public void 플래너_멤버_삭제_멤버가없을때() throws Exception {
		int plannerId = 1;
		String testEmail = "test3@naver.com";
		List<PlanMemberDto> members = new ArrayList<PlanMemberDto>();
		members.add(new PlanMemberDto.Builder().setPlanMemberId(1).setAccountId(1).setPlannerId(1).build());
		members.add(new PlanMemberDto.Builder().setPlanMemberId(2).setAccountId(2).setPlannerId(1).build());
		AccountDto user = new AccountDto.Builder().setAccountId(3).build();
		
		when(plannerDao.findMembersByPlannerId(plannerId)).thenReturn(members);
		when(accountDao.findAccountIdByEmail(testEmail)).thenReturn(user);
		
		plannerService.deleteMember(plannerId, testEmail);

		verify(plannerDao).findMembersByPlannerId(anyInt());
		verify(accountDao).findAccountIdByEmail(anyString());
	}
	
	@Test
	public void 새일정() throws Exception {
		when(plannerDao.insertPlan(any())).thenReturn(1);
		
		int planId = plannerService.newPlan(any());
		
		verify(plannerDao).insertPlan(any());
		
		assertEquals(1, planId);
	}
	
	@Test
	public void 일정_삭제() throws Exception {
		when(plannerDao.deletePlan(anyInt(), anyInt())).thenReturn(0);
		
		plannerService.deletePlan(anyInt(), anyInt());
		
		verify(plannerDao).deletePlan(anyInt(), anyInt());
	}
	
	@Test
	public void 일정_새여행지() throws Exception {
		when(plannerDao.insertPlanLocation(any())).thenReturn(1);
		
		int planLocationId = plannerService.newPlanLocation(any());
		
		verify(plannerDao).insertPlanLocation(any());
		
		assertEquals(1, planLocationId);
	}
	
	@Test
	public void 일정_여행지_삭제() throws Exception {
		when(plannerDao.deletePlanLocation(anyInt(), anyInt())).thenReturn(0);
		
		plannerService.deletePlanLocation(anyInt(), anyInt());
		
		verify(plannerDao).deletePlanLocation(anyInt(), anyInt());
	}
	
	
	private PlannerDto createBasePlanner() {
		List<String> memberEmails = new ArrayList<String>();
		memberEmails.add("test2@naver.com");
		PlannerDto planner = new PlannerDto.Builder()
				.setAccountId(1)
				.setTitle("테스트여행")
				.setPlanDateStart(LocalDateTime.of(2023, 1, 29, 00, 00))
				.setPlanDateEnd(LocalDateTime.of(2023, 1, 31, 00, 00))
				.setCreatorEmail("test@naver.com")
				.setPlanMemberEmails(memberEmails)
				.build();
		return planner;
	}
	
	private PlannerDto createPlanner(int plannerId) {
		List<String> memberEmails = new ArrayList<String>();
		memberEmails.add("test2@naver.com");
		List<PlanLocationDto> planLocations = new ArrayList<PlanLocationDto>();
		planLocations.add(new PlanLocationDto.Builder().setLocationId(1).setLocationContetntId(1000).setLocationImage("").setLocationTransportation(1).setPlanId(1).build());
		planLocations.add(new PlanLocationDto.Builder().setLocationId(2).setLocationContetntId(2000).setLocationImage("").setLocationTransportation(1).setPlanId(1).build());
		planLocations.add(new PlanLocationDto.Builder().setLocationId(3).setLocationContetntId(3000).setLocationImage("").setLocationTransportation(1).setPlanId(1).build());
		
		List<PlanDto> plans = new ArrayList<PlanDto>();
		plans.add(new PlanDto.Builder().setPlanId(1).setPlanDate(LocalDateTime.of(2023, 1,29,00,00)).setPlanLocations(planLocations).setPlannerId(plannerId).build());
		
		PlannerDto planner = new PlannerDto.Builder()
				.setPlannerId(plannerId)
				.setTitle("테스트여행")
				.setPlanDateStart(LocalDateTime.of(2023, 1, 29, 00, 00))
				.setPlanDateEnd(LocalDateTime.of(2023, 1, 31, 00, 00))
				.setCreatorEmail("test@naver.com")
				.setPlanMemberEmails(memberEmails)
				.setPlans(plans)
				.setLikeCount(0)
				.setCreateDate(LocalDateTime.of(2023, 1, 29, 00, 00))
				.setUpdateDate(LocalDateTime.of(2023, 1, 29, 00, 00))
				.build();
		
		return planner;
	}
}
