package com.planner.planner.Service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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

import com.planner.planner.Common.Page;
import com.planner.planner.Common.PageInfo;
import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dao.PlannerDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.Dto.PlanMemberDto;
import com.planner.planner.Dto.PlanMemoDto;
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
	
	@Mock
	private PlannerDto plannerDto;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void 새플래너_생성() throws Exception {
		String testCreator = "test";
		PlannerDto planner = createBasePlanner();
		AccountDto creator = new AccountDto.Builder().setAccountId(1).build();
		List<AccountDto> users = new ArrayList<AccountDto>();
		users.add(new AccountDto.Builder().setAccountId(2).build());
		
		when(accountDao.findAccountIdByNickName(anyString())).thenReturn(creator, users.get(0));
		when(plannerDao.insertPlanMember(anyInt(), anyInt())).thenReturn(0);
		when(plannerDao.acceptInvitation(anyInt(), anyInt())).thenReturn(0);
		
		plannerService.newPlanner(planner);
		
		verify(accountDao, times(2)).findAccountIdByNickName(anyString());
		verify(plannerDao, times(2)).insertPlanMember(anyInt(), anyInt());
		verify(plannerDao, times(1)).acceptInvitation(anyInt(), anyInt());
	}
	
	@Test(expected = NotFoundUserException.class)
	public void 새플래너_생성_추가한멤버중사용자가없을때() throws Exception {
		String testCreatorEmail = "test@naver.com";
		PlannerDto planner = createBasePlanner();
		AccountDto creator = new AccountDto.Builder().setAccountId(1).build();
		
		when(accountDao.findAccountIdByNickName(anyString())).thenReturn(creator).thenReturn(null);
		
		plannerService.newPlanner(planner);
		
		verify(accountDao).findAccountIdByNickName(anyString());
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
		int testTotalCount = 2;
		
		when(plannerDao.findPlannersByAccountId(anyInt(), any(PageInfo.class))).thenReturn(planners);
		when(plannerDao.getTotalCount()).thenReturn(testTotalCount);
		
		Page<PlannerDto> plannerList = plannerService.findPlannersByAccountId(1, plannerId);
		
		assertEquals(plannerList.getList().size(), 2);
		assertEquals(planners.get(0).getPlannerId(), plannerList.getList().get(0).getPlannerId());
	}
	
	@Test(expected = NotFoundPlanner.class)
	public void 계정아이디_모든플래너조회_만약플래너가_없을때() throws Exception {
		int plannerId = 1;
		List<PlannerDto> planners = new ArrayList<PlannerDto>();
		
		when(plannerDao.findPlannersByAccountId(anyInt(), any(PageInfo.class))).thenReturn(planners);
		//when(plannerDao.getTotalCount()).thenReturn(testTotalCount);
		
		Page<PlannerDto> plannerList = plannerService.findPlannersByAccountId(1, plannerId);

	}
	
	@Test
	public void 모든플래너조회() throws Exception {
		List<PlannerDto> planners = new ArrayList<PlannerDto>();
		planners.add(createPlanner(1));
		planners.add(createPlanner(2));
		int testTotalCount = 2;
		
		when(plannerDao.findPlannerAll(any(PageInfo.class))).thenReturn(planners);
		when(plannerDao.getTotalCount()).thenReturn(testTotalCount);
		
		Page<PlannerDto> plannerList = plannerService.findPlannerAll(1);
		
		verify(plannerDao).findPlannerAll(any(PageInfo.class));
		verify(plannerDao).getTotalCount();
		
		assertEquals(2, plannerList.getList().size());
		assertEquals(planners.get(0).getPlannerId(), plannerList.getList().get(0).getPlannerId());
	}
	
	@Test(expected = NotFoundPlanner.class)
	public void 모든플래너조회_만약플래너가_없을때() throws Exception {
		List<PlannerDto> planners = new ArrayList<PlannerDto>();
		
		when(plannerDao.findPlannerAll(any(PageInfo.class))).thenReturn(planners);
		
		Page<PlannerDto> plannerList = plannerService.findPlannerAll(1);
	}
	
	@Test
	public void 플래너_수정() throws Exception {
		PlannerDto planner = createBasePlanner();
		when(plannerDao.updatePlanner(anyInt(), any())).thenReturn(0);
		
		plannerService.updatePlanner(planner);
		
		verify(plannerDao).updatePlanner(anyInt(), any());
	}
	
	@Test
	public void 플래너_삭제() throws Exception {
		when(plannerDao.deletePlanner(anyInt())).thenReturn(0);
		
		plannerService.deletePlanner(anyInt());
		
		verify(plannerDao).deletePlanner(anyInt());
	}
	
	@Test
	public void 플래너_좋아요() {
		int accountId = 1;
		int plannerId = 1;
		when(plannerDao.isLike(accountId, plannerId)).thenReturn(false);
		
		plannerService.plannerLikeOrUnLike(accountId, plannerId);
		
		verify(plannerDao).plannerLike(accountId, plannerId);
	}
	
	@Test
	public void 플래너_좋아요_취소() {
		int accountId = 1;
		int plannerId = 1;
		when(plannerDao.isLike(accountId, plannerId)).thenReturn(true);
		
		plannerService.plannerLikeOrUnLike(accountId, plannerId);
		
		verify(plannerDao).plannerUnLike(accountId, plannerId);
	}
	
	@Test
	public void 플래너_새메모() {
		int expectId = 1;
		int plannerId = 1;
		PlanMemoDto memo = createPlanMemo(expectId, "a", "aa", plannerId, LocalDateTime.of(2023, 2,1, 00,00), LocalDateTime.of(2023, 2,1, 00,00));
		when(plannerDao.insertPlanMemo(plannerId, memo)).thenReturn(1);
		
		int memoId = plannerService.newMemo(plannerId, memo);
		
		assertEquals(expectId, memoId);
	}
	
	@Test
	public void 플래너_메모_수정() {
		int memoId = 1;
		int plannerId = 1;
		PlanMemoDto memo = createPlanMemo(memoId, "a", "aa", plannerId, LocalDateTime.of(2023, 2,1, 00,00), LocalDateTime.of(2023, 2,1, 00,00));
		when(plannerDao.updatePlanMemo(memoId, memo)).thenReturn(0);
		
		plannerService.updateMemo(memoId, memo);
		
		verify(plannerDao).updatePlanMemo(memoId, memo);
		
	}
	
	@Test
	public void 플래너_메모_삭제() {
		int memoId = 1;
		when(plannerDao.deletePlanMemo(memoId)).thenReturn(0);
		
		plannerService.deleteMemo(memoId);
		
		verify(plannerDao).deletePlanMemo(memoId);
		
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
		
		when(accountDao.findAccountIdByNickName(anyString())).thenReturn(inviteMember);
		
		plannerService.inviteMembers(plannerId, inviteMemberEmails);
		
		verify(accountDao, times(1)).findAccountIdByNickName(anyString());
		verify(plannerDao, times(1)).insertPlanMember(anyInt(), anyInt());
	}
	
	@Test(expected = NotFoundUserException.class)
	public void 플래너_멤버_초대_사용자가없을때() throws Exception {
		int plannerId = 1;
		List<String>  inviteMemberEmails = new ArrayList<String>();
		inviteMemberEmails.add("test3@naver.com");
		
		when(accountDao.findAccountIdByNickName(anyString())).thenReturn(null);
		
		plannerService.inviteMembers(plannerId, inviteMemberEmails);
		
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
		
		when(plannerDao.findMembersByPlannerId(plannerId)).thenReturn(members);
		when(accountDao.findAccountIdByNickName(testNickName)).thenReturn(user);
		
		plannerService.deleteMember(plannerId, testNickName);

		verify(plannerDao).findMembersByPlannerId(anyInt());
		verify(accountDao).findAccountIdByNickName(anyString());
		verify(plannerDao).deletePlanMember(anyInt(), anyInt());
	}
	
	@Test(expected = NotFoundUserException.class)
	public void 플래너_멤버_삭제_사용자가없을때() throws Exception {
		int plannerId = 1;
		String testNickName = "test2";
		List<PlanMemberDto> members = new ArrayList<PlanMemberDto>();
		members.add(new PlanMemberDto.Builder().setPlanMemberId(1).setAccountId(1).setPlannerId(1).build());
		members.add(new PlanMemberDto.Builder().setPlanMemberId(2).setAccountId(2).setPlannerId(1).build());

		when(plannerDao.findMembersByPlannerId(plannerId)).thenReturn(members);
		when(accountDao.findAccountIdByNickName(testNickName)).thenReturn(null);
		
		plannerService.deleteMember(plannerId, testNickName);

		verify(plannerDao).findMembersByPlannerId(anyInt());
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
		
		when(plannerDao.findMembersByPlannerId(plannerId)).thenReturn(members);
		when(accountDao.findAccountIdByNickName(testNickName)).thenReturn(user);
		
		plannerService.deleteMember(plannerId, testNickName);

		verify(plannerDao).findMembersByPlannerId(anyInt());
		verify(accountDao).findAccountIdByNickName(anyString());
	}
	
	@Test
	public void 새일정() throws Exception {
		int plannerId = 1;
		int planId = 1;
		PlanDto plan = createPlan(planId, LocalDate.of(2023, 02,07), null, plannerId);
		when(plannerDao.insertPlan(plan)).thenReturn(1);
		
		int newPlanId = plannerService.newPlan(plan);
		
		verify(plannerDao).insertPlan(plan);
		
		assertEquals(planId, newPlanId);
	}
	
	@Test
	public void 일정_수정() throws Exception {
		int plannerId = 1;
		int planId = 1;
		PlanDto plan = createPlan(planId, LocalDate.of(2023, 02,07), null, plannerId);
		when(plannerDao.updatePlan(planId, plan)).thenReturn(0);
		
		plannerService.updatePlan(planId, plan);
		
		verify(plannerDao).updatePlan(planId, plan);
	}
	
	@Test
	public void 일정_삭제() throws Exception {
		int plannerId = 1;
		int planId = 1;
		when(plannerDao.deletePlan(planId)).thenReturn(0);
		
		plannerService.deletePlan(planId);
		
		verify(plannerDao).deletePlan(planId);
	}
	
	@Test
	public void 일정_새여행지() throws Exception {
		when(plannerDao.insertPlanLocation(any())).thenReturn(1);
		
		int planLocationId = plannerService.newPlanLocation(any());
		
		verify(plannerDao).insertPlanLocation(any());
		
		assertEquals(1, planLocationId);
	}
	
	@Test
	public void 일정_여행지_수정() throws Exception {
		int plannerId = 1;
		int planId = 1;
		int planLocationId = 1;
		PlanLocationDto planLocation = createPlanLocation();
		
		when(plannerDao.updatePlanLocation(planLocationId,planLocation)).thenReturn(0);
		
		plannerService.updatePlanLocation(planLocationId, planLocation);
		
		verify(plannerDao).updatePlanLocation(planLocationId, planLocation);
	}
	
	@Test
	public void 일정_여행지_삭제() throws Exception {
		int planLocationId = 1;
		when(plannerDao.deletePlanLocation(planLocationId)).thenReturn(0);
		
		plannerService.deletePlanLocation(planLocationId);
		
		verify(plannerDao).deletePlanLocation(planLocationId);
	}
	
	@Test
	public void 좋아요_플래너_모두_조회() throws Exception {
		int plannerId = 1;
		List<PlannerDto> planners = new ArrayList<PlannerDto>();
		planners.add(createPlanner(1));
		planners.add(createPlanner(2));
		int testTotalCount = 2;
		
		when(plannerDao.likePlannerList(anyInt(), any(PageInfo.class))).thenReturn(planners);
		when(plannerDao.getTotalCountByLike(anyInt())).thenReturn(testTotalCount);
		
		Page<PlannerDto> plannerList = plannerService.getLikePlannerList(1, plannerId);
		
		assertEquals(plannerList.getList().size(), 2);
		assertEquals(planners.get(0).getPlannerId(), plannerList.getList().get(0).getPlannerId());
	}
	
	@Test(expected = NotFoundPlanner.class)
	public void 좋아요_플래너_모두_조회_없는경우() throws Exception {
		int plannerId = 1;
		List<PlannerDto> planners = new ArrayList<PlannerDto>();
		int testTotalCount = 2;
		
		when(plannerDao.likePlannerList(anyInt(), any(PageInfo.class))).thenReturn(planners);
		//when(plannerDao.getTotalCountByLike(anyInt())).thenReturn(testTotalCount);
		
		Page<PlannerDto> plannerList = plannerService.getLikePlannerList(1, plannerId);
		
//		assertEquals(plannerList.getPlannerList().size(), 2);
//		assertEquals(planners.get(0).getPlannerId(), plannerList.getPlannerList().get(0).getPlannerId());
	}
	
	private PlanMemoDto createPlanMemo(int memoId, String title, String content, int plannerId, LocalDateTime create, LocalDateTime update) {
		return new PlanMemoDto.Builder()
				.setMemoId(memoId)
				.setTitle(title)
				.setContent(content)
				.setCreateDate(create)
				.setUpdateDate(update)
				.build();
	}
	
	private PlanLocationDto createPlanLocation() {
		return new PlanLocationDto.Builder()
				.setLocationId(1)
				.setLocationContentId(1000)
				.setLocationName("바다")
				.setLocationImage("바다사진")
				.setLocationAddr("바다주소")
				.setLocationMapx(111.111f)
				.setLocationMapy(111.111f)
				.setLocationTransportation(1)
				.setPlanId(1)
				.build();
	}
	
	private PlanDto createPlan(int planId, LocalDate planDate ,List<PlanLocationDto> locations, int plannerId ) {
		return new PlanDto.Builder()
				.setPlanId(planId)
				.setPlanDate(planDate)
				.setPlanLocations(locations)
				.setPlannerId(plannerId)
				.build();
	}
	
	private PlannerDto createBasePlanner() {
		List<String> memberEmails = new ArrayList<String>();
		memberEmails.add("test2@naver.com");
		PlannerDto planner = new PlannerDto.Builder()
				.setAccountId(1)
				.setCreator("test")
				.setTitle("테스트여행")
				.setPlanDateStart(LocalDate.of(2023, 1, 29))
				.setPlanDateEnd(LocalDate.of(2023, 1, 31))
				.setExpense(1000)
				.setMemberCount(1)
				.setMemberTypeId(1)
				.setPlanMembers(memberEmails)
				.build();
		return planner;
	}
	
	
	private PlannerDto createPlanner(int plannerId) {
		List<String> memberEmails = new ArrayList<String>();
		memberEmails.add("test2@naver.com");
		List<PlanLocationDto> planLocations = new ArrayList<PlanLocationDto>();
		//planLocations.add(new PlanLocationDto.Builder().setLocationId(1).setLocationContentId(1000).setLocationName("바다").setLocationImage("").setLocationTransportation(1).setPlanId(1).build());
		planLocations.add(createPlanLocation());
		planLocations.add(createPlanLocation());
		planLocations.add(createPlanLocation());
		
		List<PlanDto> plans = new ArrayList<PlanDto>();
		plans.add(new PlanDto.Builder().setPlanId(1).setPlanLocations(planLocations).setPlannerId(plannerId).build());
		
		PlannerDto planner = new PlannerDto.Builder()
				.setPlannerId(plannerId)
				.setAccountId(1)
				.setCreator("test")
				.setTitle("테스트여행")
				.setPlanDateStart(LocalDate.of(2023, 1, 29))
				.setPlanDateEnd(LocalDate.of(2023, 1, 31))
				.setExpense(1000)
				.setMemberCount(1)
				.setMemberTypeId(1)
				.setPlanMembers(memberEmails)
				.setPlans(plans)
				.setLikeCount(0)
				.setCreateDate(LocalDateTime.of(2023, 1, 29, 00, 00))
				.setUpdateDate(LocalDateTime.of(2023, 1, 29, 00, 00))
				.build();
		
		return planner;
	}
}
