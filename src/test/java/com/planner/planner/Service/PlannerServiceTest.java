package com.planner.planner.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
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
import com.planner.planner.Common.SortCriteria;
import com.planner.planner.Dao.AccountDao;
import com.planner.planner.Dao.InvitationDao;
import com.planner.planner.Dao.NotificationDao;
import com.planner.planner.Dao.PlanMemberDao;
import com.planner.planner.Dao.PlannerDao;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.InvitationDto;
import com.planner.planner.Dto.NotificationDto;
import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.Dto.PlanMemoDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Exception.NotFoundPlanner;
import com.planner.planner.Exception.NotFoundUserException;
import com.planner.planner.Service.Impl.PlannerServiceImpl;

public class PlannerServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlannerServiceTest.class);
	
	@InjectMocks
	private PlannerServiceImpl plannerService;
	
	@Mock
	private AccountDao accountDao;
	@Mock
	private PlannerDao plannerDao;
	@Mock
	private PlanMemberDao planMemberDao;
	@Mock
	private InvitationDao invitationDao;
	@Mock
	private NotificationDao notificationDao;
	
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
		when(planMemberDao.insertPlanMember(anyInt(), anyInt())).thenReturn(0);
		
		plannerService.newPlanner(planner);
		
		verify(accountDao, times(1)).findAccountIdByNickName(anyString());
		verify(planMemberDao, times(1)).insertPlanMember(anyInt(), anyInt());
		verify(invitationDao, times(1)).createInvitation(any(InvitationDto.class));
		verify(notificationDao, times(1)).createNotification(anyInt(), any(NotificationDto.class));
	}
	
	@Test(expected = NotFoundUserException.class)
	public void 새플래너_생성_추가한멤버중사용자가없을때() throws Exception {
		String testCreatorEmail = "test@naver.com";
		PlannerDto planner = createBasePlanner();
		AccountDto creator = new AccountDto.Builder().setAccountId(1).build();
		
		when(accountDao.findAccountIdByNickName(anyString())).thenReturn(null);
		
		plannerService.newPlanner(planner);
		
		verify(accountDao).findAccountIdByNickName(anyString());
	}
	
	@Test
	public void 플래너아이디_플래너조회() throws Exception {
		Integer accountId = 1;
		int plannerId = 1;
		PlannerDto planner = createPlanner(1);
		
		when(plannerDao.findPlannerByPlannerId(any(Integer.class), anyInt())).thenReturn(planner);
		
		PlannerDto findPlanner = plannerService.findPlannerByPlannerId(accountId, plannerId);
		
		assertEquals(planner.getPlannerId(), findPlanner.getPlannerId());
	}
	
	@Test(expected = NotFoundPlanner.class)
	public void 플래너아이디_플래너조회_만약플래너가_없을때() throws Exception {
		Integer accountId = null;
		int plannerId = 0;
		PlannerDto planner = createPlanner(1);
		
		when(plannerDao.findPlannerByPlannerId(any(Integer.class), anyInt())).thenReturn(null);
		
		PlannerDto findPlanner = plannerService.findPlannerByPlannerId(accountId, plannerId);

	}
	
	@Test
	public void 계정_모든_플래너_가져오기() throws Exception {
		CommonRequestParamDto paramDto = new CommonRequestParamDto.Builder()
				.setItemCount(10)
				.setSortCriteria(SortCriteria.LATEST)
				.setPageNum(1)
				.build();
		
		List<PlannerDto> planners = new ArrayList<PlannerDto>();
		planners.add(createPlanner(1));
		planners.add(createPlanner(2));
		
		int testTotalCount = planners.size();
		
		when(plannerDao.findPlannersByAccountId(anyInt(), any(CommonRequestParamDto.class), any(PageInfo.class))).thenReturn(planners);
		when(plannerDao.getListTotalCount(anyInt(), any(CommonRequestParamDto.class))).thenReturn(testTotalCount);
		
		Page<PlannerDto> plannerList = plannerService.findPlannersByAccountId(1, paramDto);
		
		verify(plannerDao).findPlannersByAccountId(anyInt(), any(CommonRequestParamDto.class), any(PageInfo.class));
		verify(plannerDao).getListTotalCount(anyInt(), any(CommonRequestParamDto.class));
		
		assertThat(plannerList).isNotNull();
		assertThat(plannerList.getList()).isNotNull();
		assertThat(plannerList.getTotalCount()).isEqualTo(testTotalCount);
	}
	
	@Test
	public void 계정_모든_플래너_가져오기_키워드() throws Exception {
		CommonRequestParamDto paramDto = new CommonRequestParamDto.Builder()
				.setItemCount(10)
				.setSortCriteria(SortCriteria.LATEST)
				.setKeyword("테스트")
				.setPageNum(1)
				.build();
		
		List<PlannerDto> planners = new ArrayList<PlannerDto>();
		planners.add(createPlanner(1));
		planners.add(createPlanner(2));
		
		int testTotalCount = planners.size();
		
		when(plannerDao.findPlannersByAccountId(anyInt(), any(CommonRequestParamDto.class), any(PageInfo.class))).thenReturn(planners);
		when(plannerDao.getListTotalCount(anyInt(), any(CommonRequestParamDto.class))).thenReturn(testTotalCount);
		
		Page<PlannerDto> plannerList = plannerService.findPlannersByAccountId(1, paramDto);
		
		verify(plannerDao).findPlannersByAccountId(anyInt(), any(CommonRequestParamDto.class), any(PageInfo.class));
		verify(plannerDao).getListTotalCount(anyInt(), any(CommonRequestParamDto.class));
		
		assertThat(plannerList).isNotNull();
		assertThat(plannerList.getList()).isNotNull();
		assertThat(plannerList.getTotalCount()).isEqualTo(testTotalCount);
	}
	
	@Test
	public void 모든플래너조회() throws Exception {
		CommonRequestParamDto paramDto = new CommonRequestParamDto.Builder()
				.setItemCount(10)
				.setSortCriteria(SortCriteria.LATEST)
				.setPageNum(1)
				.build();
		
		List<PlannerDto> planners = new ArrayList<PlannerDto>();
		planners.add(createPlanner(1));
		planners.add(createPlanner(2));
		
		int testTotalCount = planners.size();
		
		when(plannerDao.findPlannerAll(anyInt(), any(CommonRequestParamDto.class), any(PageInfo.class))).thenReturn(planners);
		when(plannerDao.getListTotalCount(isNull(), any(CommonRequestParamDto.class))).thenReturn(testTotalCount);
		
		Page<PlannerDto> plannerList = plannerService.findPlannerAll(1, paramDto);
		
		verify(plannerDao).findPlannerAll(anyInt(), any(CommonRequestParamDto.class), any(PageInfo.class));
		verify(plannerDao).getListTotalCount(isNull(), any(CommonRequestParamDto.class));
		
		assertThat(plannerList).isNotNull();
		assertThat(plannerList.getList()).isNotNull();
		assertThat(plannerList.getTotalCount()).isEqualTo(testTotalCount);
	}
	
	@Test
	public void 모든플래너조회_키워드() throws Exception {
		CommonRequestParamDto paramDto = new CommonRequestParamDto.Builder()
				.setItemCount(10)
				.setSortCriteria(SortCriteria.LATEST)
				.setKeyword("테스트")
				.setPageNum(1)
				.build();
		
		List<PlannerDto> planners = new ArrayList<PlannerDto>();
		planners.add(createPlanner(1));
		
		int testTotalCount = 1;
		
		when(plannerDao.findPlannerAll(anyInt(), any(CommonRequestParamDto.class), any(PageInfo.class))).thenReturn(planners);
		when(plannerDao.getListTotalCount(isNull(), any(CommonRequestParamDto.class))).thenReturn(testTotalCount);
		
		Page<PlannerDto> plannerList = plannerService.findPlannerAll(1, paramDto);
		
		verify(plannerDao).findPlannerAll(anyInt(), any(CommonRequestParamDto.class), any(PageInfo.class));
		verify(plannerDao).getListTotalCount(isNull(), any(CommonRequestParamDto.class));
		
		assertThat(plannerList).isNotNull();
		assertThat(plannerList.getList()).isNotNull();
		assertThat(plannerList.getTotalCount()).isEqualTo(testTotalCount);
	}
	
	@Test
	public void 좋아요_플래너_모두_조회() throws Exception {
		CommonRequestParamDto paramDto = new CommonRequestParamDto.Builder()
				.setItemCount(10)
				.setSortCriteria(SortCriteria.LATEST)
				.setPageNum(1)
				.build();
		
		List<PlannerDto> planners = new ArrayList<PlannerDto>();
		planners.add(createPlanner(1));
		planners.add(createPlanner(2));
		
		int testTotalCount = planners.size();
		
		when(plannerDao.findLikePlannerList(anyInt(), any(CommonRequestParamDto.class), any(PageInfo.class))).thenReturn(planners);
		when(plannerDao.getLikeListTotalCount(anyInt(), any(CommonRequestParamDto.class))).thenReturn(testTotalCount);
		
		Page<PlannerDto> plannerList = plannerService.getLikePlannerList(1, paramDto);
		
		verify(plannerDao).findLikePlannerList(anyInt(), any(CommonRequestParamDto.class), any(PageInfo.class));
		verify(plannerDao).getLikeListTotalCount(anyInt(), any(CommonRequestParamDto.class));
		
		assertThat(plannerList).isNotNull();
		assertThat(plannerList.getList()).isNotNull();
		assertThat(plannerList.getTotalCount()).isEqualTo(testTotalCount);
	}
	
	@Test
	public void 좋아요_플래너_모두_조회_키워드() throws Exception {
		CommonRequestParamDto paramDto = new CommonRequestParamDto.Builder()
				.setItemCount(10)
				.setSortCriteria(SortCriteria.LATEST)
				.setKeyword("테스트")
				.setPageNum(1)
				.build();
		
		List<PlannerDto> planners = new ArrayList<PlannerDto>();
		planners.add(createPlanner(1));
		planners.add(createPlanner(2));
		
		int testTotalCount = planners.size();
		
		when(plannerDao.findLikePlannerList(anyInt(), any(CommonRequestParamDto.class), any(PageInfo.class))).thenReturn(planners);
		when(plannerDao.getLikeListTotalCount(anyInt(), any(CommonRequestParamDto.class))).thenReturn(testTotalCount);
		
		Page<PlannerDto> plannerList = plannerService.getLikePlannerList(1, paramDto);
		
		verify(plannerDao).findLikePlannerList(anyInt(), any(CommonRequestParamDto.class), any(PageInfo.class));
		verify(plannerDao).getLikeListTotalCount(anyInt(), any(CommonRequestParamDto.class));
		
		assertThat(plannerList).isNotNull();
		assertThat(plannerList.getList()).isNotNull();
		assertThat(plannerList.getTotalCount()).isEqualTo(testTotalCount);
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
