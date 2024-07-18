package com.planner.planner.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;

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
import com.planner.planner.Exception.PlannerNotFoundException;
import com.planner.planner.Exception.UserNotFoundException;
import com.planner.planner.Service.Impl.PlannerServiceImpl;

@ExtendWith(MockitoExtension.class)
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
	
	@BeforeEach
	public void setUp() throws Exception {
//		MockitoAnnotations.openMocks(this);
	}

	@Test
	@WithUserDetails()
	public void 새플래너_생성() throws Exception {
		PlannerDto planner = createBasePlanner();
		AccountDto creator = AccountDto.builder().accountId(1).nickname("test").build();
		List<AccountDto> users = new ArrayList<AccountDto>();
		users.add(AccountDto.builder().accountId(2).build());
		
//		when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(creator);
		when(accountDao.findByNickName(anyString())).thenReturn(creator, users.get(0));
		when(planMemberDao.insertPlanMember(anyInt(), anyInt())).thenReturn(0);
		
		plannerService.newPlanner(1, planner);
		
		verify(accountDao, times(1)).findByNickName(anyString());
		verify(planMemberDao, times(1)).insertPlanMember(anyInt(), anyInt());
		verify(invitationDao, times(1)).createInvitation(any(InvitationDto.class));
		verify(notificationDao, times(1)).createNotification(anyInt(), any(NotificationDto.class));
	}
	
	@Test
	public void 새플래너_생성_추가한멤버중사용자가없을때() throws Exception {
		String testCreatorEmail = "test@naver.com";
		PlannerDto planner = createBasePlanner();
		AccountDto creator = AccountDto.builder().accountId(1).build();
		
		when(accountDao.findByNickName(anyString())).thenReturn(null);
		
		assertThatThrownBy(() -> plannerService.newPlanner(1, planner))
				.isExactlyInstanceOf(UserNotFoundException.class);
		
		verify(accountDao).findByNickName(anyString());
	}
	
	@Test
	public void 플래너아이디_플래너조회() throws Exception {
		Integer accountId = 1;
		int plannerId = 1;
		PlannerDto planner = createPlanner(1);
		
		when(plannerDao.findPlannerByPlannerId(any(Integer.class), anyInt())).thenReturn(planner);
		
		PlannerDto findPlanner = plannerService.findPlannerByPlannerId(accountId, plannerId);
		
		assertThat(findPlanner.getPlannerId()).isEqualTo(planner.getPlannerId());
	}
	
	@Test
	public void 플래너아이디_플래너조회_만약플래너가_없을때() throws Exception {
		Integer accountId = 1;
		int plannerId = 1;
		PlannerDto planner = createPlanner(1);
		
		when(plannerDao.findPlannerByPlannerId(any(Integer.	class), anyInt())).thenReturn(null);
		
		assertThatThrownBy(() -> plannerService.findPlannerByPlannerId(accountId, plannerId))
				.isExactlyInstanceOf(PlannerNotFoundException.class);

	}
	
	@Test
	public void 계정_모든_플래너_가져오기() throws Exception {
		CommonRequestParamDto paramDto = CommonRequestParamDto.builder()
				.itemCount(10)
				.sortCriteria(SortCriteria.LATEST)
				.pageNum(1)
				.build();
		
		List<PlannerDto> planners = new ArrayList<PlannerDto>();
		planners.add(createPlanner(1));
		planners.add(createPlanner(2));
		
		int testTotalCount = planners.size();
		
		when(plannerDao.findListByAccountId(anyInt(), any(CommonRequestParamDto.class), any(PageInfo.class))).thenReturn(planners);
		when(plannerDao.getListTotalCount(anyInt(), any(CommonRequestParamDto.class))).thenReturn(testTotalCount);
		
		Page<PlannerDto> plannerList = plannerService.findPlannersByAccountId(1, paramDto);
		
		verify(plannerDao).findListByAccountId(anyInt(), any(CommonRequestParamDto.class), any(PageInfo.class));
		verify(plannerDao).getListTotalCount(anyInt(), any(CommonRequestParamDto.class));
		
		assertThat(plannerList).isNotNull();
		assertThat(plannerList.getList()).isNotNull();
		assertThat(plannerList.getTotalCount()).isEqualTo(testTotalCount);
	}
	
	@Test
	public void 계정_모든_플래너_가져오기_키워드() throws Exception {
		CommonRequestParamDto paramDto = CommonRequestParamDto.builder()
				.itemCount(10)
				.sortCriteria(SortCriteria.LATEST)
				.keyword("테스트")
				.pageNum(1)
				.build();
		
		List<PlannerDto> planners = new ArrayList<PlannerDto>();
		planners.add(createPlanner(1));
		planners.add(createPlanner(2));
		
		int testTotalCount = planners.size();
		
		when(plannerDao.findListByAccountId(anyInt(), any(CommonRequestParamDto.class), any(PageInfo.class))).thenReturn(planners);
		when(plannerDao.getListTotalCount(anyInt(), any(CommonRequestParamDto.class))).thenReturn(testTotalCount);
		
		Page<PlannerDto> plannerList = plannerService.findPlannersByAccountId(1, paramDto);
		
		verify(plannerDao).findListByAccountId(anyInt(), any(CommonRequestParamDto.class), any(PageInfo.class));
		verify(plannerDao).getListTotalCount(anyInt(), any(CommonRequestParamDto.class));
		
		assertThat(plannerList).isNotNull();
		assertThat(plannerList.getList()).isNotNull();
		assertThat(plannerList.getTotalCount()).isEqualTo(testTotalCount);
	}
	
	@Test
	public void 모든플래너조회() throws Exception {
		CommonRequestParamDto paramDto = CommonRequestParamDto.builder()
				.itemCount(10)
				.sortCriteria(SortCriteria.LATEST)
				.pageNum(1)
				.build();
		
		List<PlannerDto> planners = new ArrayList<PlannerDto>();
		planners.add(createPlanner(1));
		planners.add(createPlanner(2));
		
		int testTotalCount = planners.size();
		
		when(plannerDao.findAll(anyInt(), any(CommonRequestParamDto.class), any(PageInfo.class))).thenReturn(planners);
		when(plannerDao.getListTotalCount(isNull(), any(CommonRequestParamDto.class))).thenReturn(testTotalCount);
		
		Page<PlannerDto> plannerList = plannerService.findPlannerAll(1, paramDto);
		
		verify(plannerDao).findAll(anyInt(), any(CommonRequestParamDto.class), any(PageInfo.class));
		verify(plannerDao).getListTotalCount(isNull(), any(CommonRequestParamDto.class));
		
		assertThat(plannerList).isNotNull();
		assertThat(plannerList.getList()).isNotNull();
		assertThat(plannerList.getTotalCount()).isEqualTo(testTotalCount);
	}
	
	@Test
	public void 모든플래너조회_키워드() throws Exception {
		CommonRequestParamDto paramDto = CommonRequestParamDto.builder()
				.itemCount(10)
				.sortCriteria(SortCriteria.LATEST)
				.keyword("테스트")
				.pageNum(1)
				.build();
		
		List<PlannerDto> planners = new ArrayList<PlannerDto>();
		planners.add(createPlanner(1));
		
		int testTotalCount = 1;
		
		when(plannerDao.findAll(anyInt(), any(CommonRequestParamDto.class), any(PageInfo.class))).thenReturn(planners);
		when(plannerDao.getListTotalCount(isNull(), any(CommonRequestParamDto.class))).thenReturn(testTotalCount);
		
		Page<PlannerDto> plannerList = plannerService.findPlannerAll(1, paramDto);
		
		verify(plannerDao).findAll(anyInt(), any(CommonRequestParamDto.class), any(PageInfo.class));
		verify(plannerDao).getListTotalCount(isNull(), any(CommonRequestParamDto.class));
		
		assertThat(plannerList).isNotNull();
		assertThat(plannerList.getList()).isNotNull();
		assertThat(plannerList.getTotalCount()).isEqualTo(testTotalCount);
	}
	
	@Test
	public void 좋아요_플래너_모두_조회() throws Exception {
		CommonRequestParamDto paramDto = CommonRequestParamDto.builder()
				.itemCount(10)
				.sortCriteria(SortCriteria.LATEST)
				.pageNum(1)
				.build();
		
		List<PlannerDto> planners = new ArrayList<PlannerDto>();
		planners.add(createPlanner(1));
		planners.add(createPlanner(2));
		
		int testTotalCount = planners.size();
		
		when(plannerDao.findLikeList(anyInt(), any(CommonRequestParamDto.class), any(PageInfo.class))).thenReturn(planners);
		when(plannerDao.getLikeListTotalCount(anyInt(), any(CommonRequestParamDto.class))).thenReturn(testTotalCount);
		
		Page<PlannerDto> plannerList = plannerService.getLikePlannerList(1, paramDto);
		
		verify(plannerDao).findLikeList(anyInt(), any(CommonRequestParamDto.class), any(PageInfo.class));
		verify(plannerDao).getLikeListTotalCount(anyInt(), any(CommonRequestParamDto.class));
		
		assertThat(plannerList).isNotNull();
		assertThat(plannerList.getList()).isNotNull();
		assertThat(plannerList.getTotalCount()).isEqualTo(testTotalCount);
	}
	
	@Test
	public void 좋아요_플래너_모두_조회_키워드() throws Exception {
		CommonRequestParamDto paramDto = CommonRequestParamDto.builder()
				.itemCount(10)
				.sortCriteria(SortCriteria.LATEST)
				.keyword("테스트")
				.pageNum(1)
				.build();
		
		List<PlannerDto> planners = new ArrayList<PlannerDto>();
		planners.add(createPlanner(1));
		planners.add(createPlanner(2));
		
		int testTotalCount = planners.size();
		
		when(plannerDao.findLikeList(anyInt(), any(CommonRequestParamDto.class), any(PageInfo.class))).thenReturn(planners);
		when(plannerDao.getLikeListTotalCount(anyInt(), any(CommonRequestParamDto.class))).thenReturn(testTotalCount);
		
		Page<PlannerDto> plannerList = plannerService.getLikePlannerList(1, paramDto);
		
		verify(plannerDao).findLikeList(anyInt(), any(CommonRequestParamDto.class), any(PageInfo.class));
		verify(plannerDao).getLikeListTotalCount(anyInt(), any(CommonRequestParamDto.class));
		
		assertThat(plannerList).isNotNull();
		assertThat(plannerList.getList()).isNotNull();
		assertThat(plannerList.getTotalCount()).isEqualTo(testTotalCount);
	}
	
	
	private PlanMemoDto createPlanMemo(int memoId, String title, String content, int plannerId, LocalDateTime create, LocalDateTime update) {
		return PlanMemoDto.builder()
				.memoId(memoId)
				.title(title)
				.content(content)
				.createDate(create)
				.updateDate(update)
				.build();
	}
	
	private PlanLocationDto createPlanLocation() {
		return PlanLocationDto.builder()
				.locationId(1)
				.locationContentId(1000)
				.locationName("바다")
				.locationImage("바다사진")
				.locationAddr("바다주소")
				.locationMapx(111.111f)
				.locationMapy(111.111f)
				.locationTransportation(1)
				.planId(1)
				.build();
	}
	
	private PlanDto createPlan(int planId, LocalDate planDate ,List<PlanLocationDto> locations, int plannerId ) {
		return PlanDto.builder()
				.planId(planId)
				.planDate(planDate)
				.planLocations(locations)
				.plannerId(plannerId)
				.build();
	}
	
	private PlannerDto createBasePlanner() {
		List<String> memberEmails = new ArrayList<String>();
		memberEmails.add("test2@naver.com");
		PlannerDto planner = PlannerDto.builder()
				.accountId(1)
				.creator("test")
				.title("테스트여행")
				.planDateStart(LocalDate.of(2023, 1, 29))
				.planDateEnd(LocalDate.of(2023, 1, 31))
				.expense(1000)
				.memberCount(1)
				.memberTypeId(1)
				.planMembers(memberEmails)
				.build();
		return planner;
	}
	
	
	private PlannerDto createPlanner(int plannerId) {
		List<String> memberEmails = new ArrayList<String>();
		memberEmails.add("test2@naver.com");
		List<PlanLocationDto> planLocations = new ArrayList<PlanLocationDto>();
		//planLocations.add(PlanLocationDto.builder().locationId(1).locationContentId(1000).locationName("바다").locationImage("").locationTransportation(1).planId(1).build());
		planLocations.add(createPlanLocation());
		planLocations.add(createPlanLocation());
		planLocations.add(createPlanLocation());
		
		List<PlanDto> plans = new ArrayList<PlanDto>();
		plans.add(PlanDto.builder().planId(1).planLocations(planLocations).plannerId(plannerId).build());
		
		PlannerDto planner = PlannerDto.builder()
				.plannerId(plannerId)
				.accountId(1)
				.creator("test")
				.title("테스트여행")
				.planDateStart(LocalDate.of(2023, 1, 29))
				.planDateEnd(LocalDate.of(2023, 1, 31))
				.expense(1000)
				.memberCount(1)
				.memberTypeId(1)
				.planMembers(memberEmails)
				.plans(plans)
				.likeCount(0)
				.createDate(LocalDateTime.of(2023, 1, 29, 00, 00))
				.updateDate(LocalDateTime.of(2023, 1, 29, 00, 00))
				.build();
		
		return planner;
	}
}
