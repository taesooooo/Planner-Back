package com.planner.planner.Controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.Dto.PlanMemberInviteDto;
import com.planner.planner.Dto.PlanMemoDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Filter.JwtAuthenticationFilter;
import com.planner.planner.Util.JwtUtil;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Transactional
public class PlannerControllerTest {
	@Autowired
	private WebApplicationContext context;
	private MockMvc mockMvc;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserDetailsService detailsService;
	
	private ObjectMapper mapper = new ObjectMapper();
	private String token;
	
	@BeforeEach
	public void setUp() throws Exception {
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil, detailsService);
		this.mapper.registerModule(new JavaTimeModule());
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.addFilter(filter)
				.build();
		this.token = "Bearer " + jwtUtil.createAccessToken(2);
	}
	
	@ParameterizedTest
	@MethodSource("newPlannerParameters")
	@DisplayName("새플래너 유효성 검사")
	public void 새플래너_유효성검사(int accountId, String creator, String title, LocalDate startDate, LocalDate endDate, int expense,
			int memberCount, int memberType) throws Exception {
		PlannerDto planner = PlannerDto.builder()
				.accountId(accountId)
				.creator(creator)
				.title(title)
				.planDateStart(startDate)
				.planDateEnd(endDate)
				.expense(expense)
				.memberCount(memberCount)
				.memberTypeId(memberType)
				.planMembers(null)
				.plans(null)
				.likeCount(0)
				.build();
		
		this.mockMvc.perform(post("/api/planners")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(planner)))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	public static Stream<Arguments> newPlannerParameters() {
		return Stream.of(
				Arguments.of(1, "", "테스트 여행", LocalDate.of(2023, 1, 29), LocalDate.of(2023, 1, 31), 1000, 1, 1),		// 생성자 공백
				Arguments.of(1, "test", "", LocalDate.of(2023, 1, 29), LocalDate.of(2023, 1, 31), 1000, 1, 1),			// 제목 공백
				Arguments.of(1, "test", "테스트 여행", null, LocalDate.of(2023, 1, 31), 1000, 1, 1),						// 시작날짜 공백
				Arguments.of(1, "test", "테스트 여행", LocalDate.of(2023, 1, 29), null, 1000, 1, 1),						// 종료날짜 공백
				Arguments.of(1, "test", "테스트 여행", LocalDate.of(2023, 1, 28), LocalDate.of(2023, 1, 27), 1000, 1, 1),	// 잘못된 날짜
				Arguments.of(1, "test", "테스트 여행", LocalDate.of(2023, 1, 29), LocalDate.of(2023, 1, 31), 1000, 0, 1),	// 잘못된 멤버수
				Arguments.of(1, "test", "테스트 여행", LocalDate.of(2023, 1, 29), LocalDate.of(2023, 1, 31), 1000, 1, 5)	// 잘못된 멤버수
				);
	}

	@Test
	@DisplayName("새 플래너 성공")
	public void 새플래너() throws Exception {
		PlannerDto planner = PlannerDto.builder()
				.plannerId(1)
				.accountId(1)
				.creator("test")
				.title("테스트여행")
				.planDateStart(LocalDate.now())
				.planDateEnd(LocalDate.now())
				.expense(1000)
				.memberCount(1)
				.memberTypeId(1)
				.planMembers(new ArrayList<String>())
				.plans(new ArrayList<PlanDto>())
				.likeCount(0)
				.build();
		
		this.mockMvc.perform(post("/api/planners")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(planner)))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data").isNumber());
	}
	
	@Test
	@DisplayName("플래너 리스트 조회 - 최신순")
	public void 플래너_리스트_조회_최신순() throws Exception {
		this.mockMvc.perform(get("/api/planners")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.header("Authorization", token)
				.param("itemCount", "10")
				.param("sortCriteria", "1")
				.param("keyword", "")
				.param("pageNum", "1"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.state").value(is(true)))
		.andExpect(jsonPath("$.data").isNotEmpty())
		.andExpect(jsonPath("$.data.list").exists())
		.andExpect(jsonPath("$.data.list").isNotEmpty())
		.andExpect(jsonPath("$.data.list.length()").value(9))
		.andExpect(jsonPath("$.data.totalCount").value(9))
		.andExpect(jsonPath("$.data.pageIndex").value(1))
		.andExpect(jsonPath("$.data.pageLastIndex").value(1));
	}
	
	@Test
	@DisplayName("플래너 리스트 조회 - 인기순")
	public void 플래너_리스트_조회_인기순() throws Exception {
		 this.mockMvc.perform(get("/api/planners")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.header("Authorization", token)
				.param("itemCount", "10")
				.param("sortCriteria", "2")
				.param("keyword", "")
				.param("pageNum", "1"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.state").value(is(true)))
		.andExpect(jsonPath("$.data").isNotEmpty())
		.andExpect(jsonPath("$.data.list").exists())
		.andExpect(jsonPath("$.data.list").isNotEmpty())
		.andExpect(jsonPath("$.data.list.length()").value(9))
		.andExpect(jsonPath("$.data.totalCount").value(9))
		.andExpect(jsonPath("$.data.pageIndex").value(1))
		.andExpect(jsonPath("$.data.pageLastIndex").value(1));
	}
	
	@Test
	@DisplayName("플래너 리스트 조회 - 키워드")
	public void 플래너_리스트_조회_키워드() throws Exception {
		this.mockMvc.perform(get("/api/planners")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.header("Authorization", token)
				.param("itemCount", "10")
				.param("sortCriteria", "1")
				.param("keyword", "테스트")
				.param("pageNum", "1"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.state").value(is(true)))
		.andExpect(jsonPath("$.data").isNotEmpty())
		.andExpect(jsonPath("$.data.list").exists())
		.andExpect(jsonPath("$.data.list").isNotEmpty())
		.andExpect(jsonPath("$.data.list.length()").value(3))
		.andExpect(jsonPath("$.data.totalCount").value(3))
		.andExpect(jsonPath("$.data.pageIndex").value(1))
		.andExpect(jsonPath("$.data.pageLastIndex").value(1));
	}
	
	@Test
	@DisplayName("플래너 리스트 조회 - 지역")
	public void 플래너_리스트_조회_지역() throws Exception {
		this.mockMvc.perform(get("/api/planners")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.header("Authorization", token)
				.param("itemCount", "10")
				.param("sortCriteria", "1")
				.param("areaCode", "")
				.param("keyword", "")
				.param("pageNum", "1"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.state").value(is(true)))
		.andExpect(jsonPath("$.data").isNotEmpty())
		.andExpect(jsonPath("$.data.list").exists())
		.andExpect(jsonPath("$.data.list").isNotEmpty())
		.andExpect(jsonPath("$.data.list.length()").value(9))
		.andExpect(jsonPath("$.data.totalCount").value(9))
		.andExpect(jsonPath("$.data.pageIndex").value(1))
		.andExpect(jsonPath("$.data.pageLastIndex").value(1));
	}
	
	@Test
	@DisplayName("플래너 리스트 조회")
	public void 플래너_조회_플래너아이디() throws Exception {
		this.mockMvc.perform(get("/api/planners/{plannerId}", 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data").isNotEmpty());
	}
	
	@Test
	@DisplayName("플래너 리스트 조회 - 정렬 확인")
	public void 플래너_조회_정렬_확인() throws Exception {
		this.mockMvc.perform(get("/api/planners/{plannerId}", 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data").isNotEmpty())
		.andExpect(jsonPath("$.data.plans[0].planId").value(1))
		.andExpect(jsonPath("$.data.plans[0].planLocations[0].locationId").value(1))
		.andExpect(jsonPath("$.data.plans[1].planLocations[0].locationId").value(3))
		.andExpect(jsonPath("$.data.plans[2].planLocations[0].locationId").value(2))
		.andExpect(jsonPath("$.data.plans[1].planId").value(3))
		.andExpect(jsonPath("$.data.plans[2].planId").value(2));
	}
	
	@ParameterizedTest
	@MethodSource("updatePlannerParameters")
	@DisplayName("플래너 수정 유효성 검사")
	public void 플래너_수정_유효성검사(int accountId, String creator, String title, LocalDate startDate, LocalDate endDate, int expense,
			int memberCount, int memberType) throws Exception {
		PlannerDto planner = PlannerDto.builder()
				.accountId(accountId)
				.creator(creator)
				.title(title)
				.planDateStart(startDate)
				.planDateEnd(endDate)
				.expense(expense)
				.memberCount(memberCount)
				.memberTypeId(memberType)
				.planMembers(null)
				.plans(null)
				.likeCount(0)
				.build();
		
		this.mockMvc.perform(patch("/api/planners/{plannerId}", 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(planner)))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	public static Stream<Arguments> updatePlannerParameters() {
		return Stream.of(
				Arguments.of(1, "test", "", LocalDate.of(2023, 1, 29), LocalDate.of(2023, 1, 31), 1000, 1, 1),			// 제목 공백
				Arguments.of(1, "test", "테스트 여행", null, LocalDate.of(2023, 1, 31), 1000, 1, 1),						// 시작날짜 공백
				Arguments.of(1, "test", "테스트 여행", LocalDate.of(2023, 1, 29), null, 1000, 1, 1),						// 종료날짜 공백
				Arguments.of(1, "test", "테스트 여행", LocalDate.of(2023, 1, 28), LocalDate.of(2023, 1, 27), 1000, 1, 1),	// 잘못된 날짜
				Arguments.of(1, "test", "테스트 여행", LocalDate.of(2023, 1, 29), LocalDate.of(2023, 1, 31), 1000, 0, 1),	// 잘못된 멤버수
				Arguments.of(1, "test", "테스트 여행", LocalDate.of(2023, 1, 29), LocalDate.of(2023, 1, 31), 1000, 1, 5)	// 잘못된 멤버수
				);
	}

	
	@Test
	@DisplayName("플래너 수정")
	public void 플래너_수정() throws Exception {
		PlannerDto planner = PlannerDto.builder()
				.accountId(1)
				.creator("test")
				.title("테스트여행")
				.areaCode(1)
				.planDateStart(LocalDate.of(2023, 1, 28))
				.planDateEnd(LocalDate.of(2023, 1, 28))
				.expense(1000)
				.memberCount(1)
				.memberTypeId(1)
				.planMembers(null)
				.plans(null)
				.likeCount(0)
				.build();
		
		this.mockMvc.perform(patch("/api/planners/{plannerId}", 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(planner)))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("플래너 삭제")
	public void 플래너_삭제() throws Exception {	
		this.mockMvc.perform(delete("/api/planners/{plannerId}", 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	
	@Test
	@DisplayName("플래너 좋아요 또는 취소")
	public void 플래너_좋아요_또는_취소() throws Exception {
		this.mockMvc.perform(post("/api/planners/{plannerId}/like", 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.requestAttr("userId", 1))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("새 메모 유효성 검사")
	public void 새메모_제목_공백_유효성검사() throws Exception {
		PlanMemoDto memo = createPlanMemo(0, "", "test", 1, null, null);
		
		this.mockMvc.perform(post("/api/planners/{plannerId}/memos" , 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(memo)))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("새 메모")
	public void 새메모() throws Exception {
		PlanMemoDto memo = createPlanMemo(0, "test", "test", 1, null, null);
		
		this.mockMvc.perform(post("/api/planners/{plannerId}/memos" , 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(memo)))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data").value(5));
	}
	
	@Test
	@DisplayName("메모 수정 유효성 검사")
	public void 메모_수정_제목_공백_유효성검사() throws Exception {
		PlanMemoDto memo = createPlanMemo(0, "", "test", 1, null, null);

		this.mockMvc.perform(patch("/api/planners/{plannerId}/memos/{memoId}" , 1, 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(memo)))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("메모 수정")
	public void 메모_수정() throws Exception {
		PlanMemoDto memo = createPlanMemo(1, "메모수정", "메모수정테스트", 1, null, null);
		
		this.mockMvc.perform(patch("/api/planners/{plannerId}/memos/{memoId}" , 1, 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(memo)))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("메모 삭제")
	public void 메모_삭제() throws Exception {
		String url = UriComponentsBuilder.fromUriString("/api/planners")
				.path("/{plannerId}/memos/{memoId}")
				.buildAndExpand(1, 1)
				.toString();
		
		this.mockMvc.perform(delete("/api/planners/{plannerId}/memos/{memoId}", 1, 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk());
	}

	@Test
	@DisplayName("플래너 멤버 초대")
	public void 멤버_초대() throws Exception {
		List<String> members = Arrays.asList("test3");
		PlanMemberInviteDto invitenMembers= PlanMemberInviteDto.builder().members(members).build();

		this.mockMvc.perform(post("/api/planners/{plannerId}/invite-member", 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(invitenMembers)))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("플래너 멤버 초대 중복")
	public void 멤버_초대_중복() throws Exception {
		List<String> members = Arrays.asList("test2","test3");
		PlanMemberInviteDto invitenMembers= PlanMemberInviteDto.builder().members(members).build();
		
		this.mockMvc.perform(post("/api/planners/{plannerId}/invite-member", 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(invitenMembers)))
		.andDo(print())
		.andExpect(status().isConflict());
	}
	
	@Test
	@DisplayName("플래너 멤버 빈어 있는 데이터로 요청 시")
	public void 멤버_초대_빈배열() throws Exception {
		PlanMemberInviteDto invitenMembers= PlanMemberInviteDto.builder().members(new ArrayList<String>()).build();
		
		this.mockMvc.perform(post("/api/planners/{plannerId}/invite-member", 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(invitenMembers)))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("플래너 멤버 삭제")
	public void 멤버_삭제() throws Exception {
		String deleteMember = "test2";
		
		this.mockMvc.perform(delete("/api/planners/{plannerId}/delete-member", 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.param("nick_name", deleteMember))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("새 일정 유효성 검사")
	public void 새일정_날짜_공백_유효성검사() throws Exception {
		PlanDto plan = createPlan(2, 1, 1024, null);
		
		this.mockMvc.perform(post("/api/planners/{plannerId}/plans", 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(plan)))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("새 일정")
	public void 새일정() throws Exception {
		PlanDto plan = createPlan(0, 1, 1024, LocalDate.now());
		
		this.mockMvc.perform(post("/api/planners/{plannerId}/plans", 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(plan)))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data").value(7));
	}
	
	@ParameterizedTest
	@MethodSource("planUpdateParameters")
	@DisplayName("새 일정 수정 유효성 검사")
	public void 일정수정_날짜_공백_유효성검사(int planId, int plannerId, int index, LocalDate planDate) throws Exception {
		PlanDto plan = createPlan(0, 0, 1024, null);

		this.mockMvc.perform(patch("/api/planners/{plannerId}/plans/{planId}", 1, 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(plan)))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	public static Stream<Arguments> planUpdateParameters() {
		return Stream.of(
				Arguments.of(0, 0, 1024, null), 					// 날짜 공백
				Arguments.of(0, 0, 0, LocalDate.of(2023, 1,29)) 	// 잘못된 정렬 인덱스 
				);
	}
	
	@Test
	@DisplayName("일정 수정")
	public void 일정_수정() throws Exception {
		PlanDto plan = createPlan(0, 0, 1024, LocalDate.now());

		this.mockMvc.perform(patch("/api/planners/{plannerId}/plans/{planId}", 1, 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(plan)))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("일정 삭제")
	public void 일정_삭제() throws Exception {		
		this.mockMvc.perform(delete("/api/planners/{plannerId}/plans/{planId}", 1, 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk());
	}

	
	@ParameterizedTest
	@MethodSource("newPlanParameters")
	@DisplayName("일정 수정 유효성 검사")
	public void 일정_새여행지_유효성검사(int locationId, int contentId, int transportation, int index, int planId) throws Exception {
		PlanLocationDto planLocation = createPlanLocation(locationId, contentId, transportation, index, planId);
		
		this.mockMvc.perform(post("/api/planners/{plannerId}/plans/{planId}/plan-locations", 1, 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(planLocation)))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	public static Stream<Arguments> newPlanParameters() {
		return Stream.of(
				Arguments.of(2, 0, 1, 1024, 1),			// 잘못된 콘텐츠 아이디
				Arguments.of(2, 1000, 0, 1024, 1)		// 잘못된 이동수단
				);
	}
	
	@Test
	@DisplayName("새 여행지")
	public void 일정_새여행지() throws Exception {
		PlanLocationDto planLocation = createPlanLocation(2, 1000, 1, 1024, 1);
		
		this.mockMvc.perform(post("/api/planners/{plannerId}/plans/{planId}/plan-locations", 1, 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(planLocation)))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data").value(7));
	}
	
	@ParameterizedTest
	@MethodSource("planLocationUpdateParameters")
	@DisplayName("여행지 수정 유효성 검사")
	public void 여행지_수정_잘못된콘텐츠아이디_유효성검사(int locationId, int contentId, int transportation, int index, int planId) throws Exception {
		PlanLocationDto planLocation = createPlanLocation(1, 0, 1, 1024, 0);

		this.mockMvc.perform(patch("/api/planners/{plannerId}/plans/{planId}/plan-locations/{planLocationId}", 1, 1, 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(planLocation)))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	public static Stream<Arguments> planLocationUpdateParameters() {
		return Stream.of(
				Arguments.of(1, 0, 1, 1024, 0),				// 잘못된 콘텐츠 아이디
				Arguments.of(1, 2000, 0, 1024, 0),			// 잘못된 이동 수단
				Arguments.of(1, 2000, 1, 0, 0)				// 잘못된 정렬 인덱스
				);
	}

	@Test
	@DisplayName("여행지 수정")
	public void 여행지_수정() throws Exception {
		PlanLocationDto planLocation = createPlanLocation(1, 2000, 1, 1024, 0);
		
		this.mockMvc.perform(patch("/api/planners/{plannerId}/plans/{planId}/plan-locations/{planLocationId}", 1, 1, 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(planLocation)))
		.andDo(print())
		.andExpect(status().isOk());
	}
	

	@Test
	@DisplayName("여행지 삭제")
	public void 일정_여행지_삭제() throws Exception {
		this.mockMvc.perform(delete("/api/planners/{plannerId}/plans/{planId}/plan-locations/{planLocationId}", 1, 1, 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	private PlannerDto createPlanner(int plannerId) {
		List<String> members = new ArrayList<String>();
		members.add("test2");
		List<PlanLocationDto> planLocations = new ArrayList<PlanLocationDto>();
		planLocations.add(PlanLocationDto.builder().locationId(1).locationContentId(1000).locationName("바다").locationImage("").locationTransportation(1).index(1024).planId(1).build());
		planLocations.add(PlanLocationDto.builder().locationId(2).locationContentId(2000).locationName("땅").locationImage("").locationTransportation(1).index(2048).planId(1).build());
		planLocations.add(PlanLocationDto.builder().locationId(3).locationContentId(3000).locationName("하늘").locationImage("").locationTransportation(1).index(3072).planId(1).build());
		
		List<PlanDto> plans = new ArrayList<PlanDto>();
		plans.add(PlanDto.builder().planId(1).planLocations(planLocations).index(1024).plannerId(plannerId).build());

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
				.planMembers(members)
				.plans(plans)
				.likeCount(0)
				.createDate(LocalDateTime.of(2023, 1, 29, 0, 0))
				.updateDate(LocalDateTime.of(2023, 1, 29, 0, 0))
				.build();
		
		return planner;
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
	
	private PlanDto createPlan(int planId, int plannerId, int index, LocalDate planDate) {
		return PlanDto.builder()
				.planId(planId)
				.planDate(planDate)
				.planLocations(null)
				.index(index)
				.plannerId(plannerId)
				.build();
	}
	
	private PlanLocationDto createPlanLocation(int locationId, int contentId, int transportation, int index, int planId) {
		return PlanLocationDto.builder()
				.locationId(locationId)
				.locationContentId(contentId)
				.locationName("바다")
				.locationImage("바다사진")
				.locationAddr("바다주소")
				.locationMapx(123.1234567891)
				.locationMapy(12.1234567891)
				.locationTransportation(transportation)
				.index(index)
				.planId(planId)
				.build();
	}
}
