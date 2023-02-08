package com.planner.planner.Controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.planner.planner.Config.JwtContext;
import com.planner.planner.Config.RootAppContext;
import com.planner.planner.Config.SecurityContext;
import com.planner.planner.Config.ServletAppContext;
import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.Dto.PlanMemoDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.util.JwtUtil;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootAppContext.class, ServletAppContext.class, JwtContext.class, SecurityContext.class })
@Sql(scripts = {"classpath:/Planner_Test_DB.sql"})
@Transactional
public class PlannerControllerTest {
	
	@Autowired
	private WebApplicationContext context;
	private MockMvc mockMvc;
	@Autowired
	private JwtUtil jwtUtil;
	private ObjectMapper mapper = new ObjectMapper();
	private String token;
	
	@Before
	public void setUp() throws Exception {
		this.mapper.registerModule(new JavaTimeModule());
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		this.token = "Bearer " + jwtUtil.createToken(1);
	}

	@Test
	public void 새플래너() throws Exception {
		int newPlannerId = 2;
		PlannerDto planner = createPlanner(newPlannerId);
		this.mockMvc.perform(post("/api/planners")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(planner)))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data", is(newPlannerId)));
	}
	
	@Test
	public void 플래너_목록() throws Exception {
		this.mockMvc.perform(get("/api/planners")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data").isNotEmpty());
	}
	
	@Test
	public void 플래너_조회_플래너아이디() throws Exception {
		int plannerId = 1;
		String url = String.format("/api/planners/%d", plannerId);
		this.mockMvc.perform(get(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data").isNotEmpty());
	}
	
	@Test
	public void 플래너_수정() throws Exception {
		int plannerId = 1;
		PlannerDto planner = createPlanner(1); // 기본 데이터는 제목이 초보여행
		String url = String.format("/api/planners/%d", plannerId);
		this.mockMvc.perform(patch(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(planner)))
		.andDo(print())
		.andExpect(status().isOk());
	}
	@Test
	public void 플래너_수정_다른사용자_요청_접근거부() throws Exception {
		int plannerId = 1;
		String notCreatetorIdToken =  "Bearer " + jwtUtil.createToken(2); // 생성자가 아닌 다른 사용자
		PlannerDto planner = createPlanner(1); // 기본 데이터는 제목이 초보여행
		String url = String.format("/api/planners/%d", plannerId);
		this.mockMvc.perform(patch(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", notCreatetorIdToken)
				.content(mapper.writeValueAsString(planner)))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void 플래너_삭제() throws Exception {
		int plannerId = 1;
		this.mockMvc.perform(delete("/api/planners/"+plannerId)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void 플래너_삭제_다른사용자_요청_접근거부() throws Exception {
		int plannerId = 1;
		String notCreatetorIdToken =  "Bearer " + jwtUtil.createToken(2); // 생성자가 아닌 다른 사용자
		this.mockMvc.perform(delete("/api/planners/"+plannerId)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", notCreatetorIdToken))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void 플래너_좋아요() throws Exception {
		int plannerId = 1;
		String url = String.format("/api/planners/%d/like", plannerId);
		this.mockMvc.perform(post(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void 새메모() throws Exception {
		int plannerId = 1;
		PlanMemoDto memo = createPlanMemo(0, "test", "test", plannerId, null, null);
		String url = String.format("/api/planners/%d/memos", plannerId);
		this.mockMvc.perform(post(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(memo)))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data").value(2));
	}
	
	@Test
	public void 새메모_다른사용자_요청_접근거부() throws Exception {
		int plannerId = 1;
		String notCreatetorIdToken =  "Bearer " + jwtUtil.createToken(2); // 생성자가 아닌 다른 사용자
		PlanMemoDto memo = createPlanMemo(0, "test", "test", plannerId, null, null);
		String url = String.format("/api/planners/%d/memos", plannerId);
		this.mockMvc.perform(post(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", notCreatetorIdToken)
				.content(mapper.writeValueAsString(memo)))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void 메모_수정() throws Exception {
		int plannerId = 1;
		int memoId = 1;
		PlanMemoDto memo = createPlanMemo(1, "메모수정", "메모수정테스트", plannerId, null, null);
		String url = String.format("/api/planners/%d/memos/%d", plannerId, memoId);
		this.mockMvc.perform(patch(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(memo)))
		.andDo(print())
		.andExpect(status().isOk());
	}
	@Test
	public void 메모_수정_다른사용자_요청_접근거부() throws Exception {
		int plannerId = 1;
		int memoId = 1;
		String notCreatetorIdToken =  "Bearer " + jwtUtil.createToken(2); // 생성자가 아닌 다른 사용자
		PlanMemoDto memo = createPlanMemo(1, "메모수정", "메모수정테스트", plannerId, null, null);
		String url = String.format("/api/planners/%d/memos/%d", plannerId, memoId);
		this.mockMvc.perform(patch(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", notCreatetorIdToken)
				.content(mapper.writeValueAsString(memo)))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void 메모_삭제() throws Exception {
		int plannerId = 1;
		int memoId = 1;
		String url = String.format("/api/planners/%d/memos/%d", plannerId, memoId);
		this.mockMvc.perform(delete(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk());
	}
	@Test
	public void 메모_삭제_다른사용자_요청_접근거부() throws Exception {
		int plannerId = 1;
		int memoId = 1;
		String notCreatetorIdToken =  "Bearer " + jwtUtil.createToken(2); // 생성자가 아닌 다른 사용자
		String url = String.format("/api/planners/%d/memos/%d", plannerId, memoId);
		this.mockMvc.perform(delete(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", notCreatetorIdToken))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void 멤버_초대() throws Exception {
		int plannerId = 1;
		List<String> emails = Arrays.asList("test2@naver.com","test3@naver.com");
		HashMap<String, List<String>> inviteEmails = new HashMap<String, List<String>>();
		inviteEmails.put("emails", emails);
		String url = String.format("/api/planners/%d/invite-member", plannerId);
		this.mockMvc.perform(post(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(inviteEmails)))
		.andDo(print())
		.andExpect(status().isOk());
	}
	@Test
	public void 멤버_초대_다른사용자_요청_접근거부() throws Exception {
		int plannerId = 1;
		String notCreatetorIdToken =  "Bearer " + jwtUtil.createToken(2); // 생성자가 아닌 다른 사용자
		List<String> emails = Arrays.asList("test2@naver.com","test3@naver.com");
		HashMap<String, List<String>> inviteEmails = new HashMap<String, List<String>>();
		inviteEmails.put("emails", emails);
		String url = String.format("/api/planners/%d/invite-member", plannerId);
		this.mockMvc.perform(post(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", notCreatetorIdToken)
				.content(mapper.writeValueAsString(inviteEmails)))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void 새일정() throws Exception {
		int plannerId = 1;
		PlanDto plan = createPlan(2, plannerId, LocalDateTime.of(2023, 1,29,00,00));
		String url = String.format("/api/planners/%d/plans", plannerId);
		this.mockMvc.perform(post(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(plan)))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data").value(plan.getPlanId()));
	}
	@Test
	public void 새일정_다른사용자_요청_접근거부() throws Exception {
		int plannerId = 1;
		String notCreatetorIdToken =  "Bearer " + jwtUtil.createToken(2); // 생성자가 아닌 다른 사용자
		PlanDto plan = createPlan(2, plannerId, LocalDateTime.of(2023, 1,29,00,00));
		String url = String.format("/api/planners/%d/plans", plannerId);
		this.mockMvc.perform(post(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", notCreatetorIdToken)
				.content(mapper.writeValueAsString(plan)))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void 일정_수정() throws Exception {
		int plannerId = 1;
		int planId = 1;
		PlanDto plan = createPlan(1, plannerId, LocalDateTime.of(2023, 02, 07, 00, 00));
		String url = String.format("/api/planners/%d/plans/%d", plannerId, planId);
		
		this.mockMvc.perform(patch(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(plan)))
		.andDo(print())
		.andExpect(status().isOk());
	}
	@Test
	public void 일정_수정_다른사용자_요청_접근거부() throws Exception {
		int plannerId = 1;
		int planId = 1;
		String notCreatetorIdToken =  "Bearer " + jwtUtil.createToken(2); // 생성자가 아닌 다른 사용자
		PlanDto plan = createPlan(1, plannerId, LocalDateTime.of(2023, 02, 07, 00, 00));
		String url = String.format("/api/planners/%d/plans/%d", plannerId, planId);
		
		this.mockMvc.perform(patch(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", notCreatetorIdToken)
				.content(mapper.writeValueAsString(plan)))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void 일정_삭제() throws JsonProcessingException, Exception {
		int plannerId = 1;
		int planId = 1;
		
		String url = String.format("/api/planners/%d/plans/%d", plannerId, planId);
		
		this.mockMvc.perform(delete(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk());
	}
	@Test
	public void 일정_삭제_다른사용자_요청_접근거부() throws JsonProcessingException, Exception {
		int plannerId = 1;
		int planId = 1;
		String notCreatetorIdToken =  "Bearer " + jwtUtil.createToken(2); // 생성자가 아닌 다른 사용자
		String url = String.format("/api/planners/%d/plans/%d", plannerId, planId);
		
		this.mockMvc.perform(delete(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", notCreatetorIdToken))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void 일정_새여행지() throws Exception {
		int plannerId = 1;
		int planId = 1;
		PlanLocationDto planLocation = createPlanLocation(2, 1000, 1, planId );
		String url = String.format("/api/planners/%d/plans/%d/plan-locations", plannerId, planId);
		this.mockMvc.perform(post(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(planLocation)))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data").value(planLocation.getLocationId()));
	}
	@Test
	public void 일정_새여행지_다른사용자_요청_접근거부() throws Exception {
		int plannerId = 1;
		int planId = 1;
		PlanLocationDto planLocation = createPlanLocation(2, 1000, 1, planId );
		String notCreatetorIdToken =  "Bearer " + jwtUtil.createToken(2); // 생성자가 아닌 다른 사용자
		String url = String.format("/api/planners/%d/plans/%d/plan-locations", plannerId, planId);
		this.mockMvc.perform(post(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", notCreatetorIdToken)
				.content(mapper.writeValueAsString(planLocation)))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void 여행지_수정() throws Exception {
		int plannerId = 1;
		int planId = 1;
		int planLocationId = 1;
		PlanLocationDto planLocation = createPlanLocation(1, 2000, 1, planId);
		String url = String.format("/api/planners/%d/plans/%d/plan-locations/%d", plannerId, planId, planLocationId);
		this.mockMvc.perform(patch(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(planLocation)))
		.andDo(print())
		.andExpect(status().isOk());
	}
	@Test
	public void 여행지_수정_다른사용자_요청_접근거부() throws Exception {
		int plannerId = 1;
		int planId = 1;
		int planLocationId = 1;
		PlanLocationDto planLocation = createPlanLocation(1, 2000, 1, planId);
		String notCreatetorIdToken =  "Bearer " + jwtUtil.createToken(2); // 생성자가 아닌 다른 사용자
		String url = String.format("/api/planners/%d/plans/%d/plan-locations/%d", plannerId, planId, planLocationId);
		this.mockMvc.perform(patch(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", notCreatetorIdToken)
				.content(mapper.writeValueAsString(planLocation)))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void 일정_여행지_삭제() throws Exception {
		int plannerId = 1;
		int planId = 1;
		int planLocationId = 1;
		String url = String.format("/api/planners/%d/plans/%d/plan-locations/%d", plannerId, planId, planLocationId);
		this.mockMvc.perform(delete(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk());
	}
	@Test
	public void 일정_여행지_삭제_다른사용자_요청_접근거부() throws Exception {
		int plannerId = 1;
		int planId = 1;
		int planLocationId = 1;
		String notCreatetorIdToken =  "Bearer " + jwtUtil.createToken(2); // 생성자가 아닌 다른 사용자
		String url = String.format("/api/planners/%d/plans/%d/plan-locations/%d", plannerId, planId, planLocationId);
		this.mockMvc.perform(delete(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", notCreatetorIdToken))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
	
	private PlannerDto createPlanner(int plannerId) {
		List<String> memberEmails = new ArrayList<String>();
		memberEmails.add("test2@naver.com");
		List<PlanLocationDto> planLocations = new ArrayList<PlanLocationDto>();
		planLocations.add(new PlanLocationDto.Builder().setLocationId(1).setLocationContentId(1000).setLocationImage("").setLocationTransportation(1).setPlanId(1).build());
		planLocations.add(new PlanLocationDto.Builder().setLocationId(2).setLocationContentId(2000).setLocationImage("").setLocationTransportation(1).setPlanId(1).build());
		planLocations.add(new PlanLocationDto.Builder().setLocationId(3).setLocationContentId(3000).setLocationImage("").setLocationTransportation(1).setPlanId(1).build());
		
		List<PlanDto> plans = new ArrayList<PlanDto>();
		plans.add(new PlanDto.Builder().setPlanId(1).setPlanDate(LocalDateTime.of(2023, 1,29,00,00)).setPlanLocations(planLocations).setPlannerId(plannerId).build());

		PlannerDto planner = new PlannerDto.Builder()
				.setPlannerId(plannerId)
				.setAccountId(1)
				.setTitle("테스트여행")
				.setPlanDateStart(LocalDateTime.of(2023, 1, 29, 0, 0))
				.setPlanDateEnd(LocalDateTime.of(2023, 1, 31, 0, 0))
				.setCreatorEmail("test@naver.com")
				.setPlanMemberEmails(memberEmails)
				.setPlans(plans)
				.setLikeCount(0)
				.setCreateDate(LocalDateTime.of(2023, 1, 29, 0, 0))
				.setUpdateDate(LocalDateTime.of(2023, 1, 29, 0, 0))
				.build();
		
		return planner;
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
	
	private PlanDto createPlan(int planId, int plannerId, LocalDateTime planDate) {
		return new PlanDto.Builder()
				.setPlanId(planId)
				.setPlanDate(planDate)
				.setPlanLocations(null)
				.setPlannerId(plannerId)
				.build();
	}
	
	private PlanLocationDto createPlanLocation(int locationId, int contentId, int transportation, int planId) {
		return new PlanLocationDto.Builder()
				.setLocationId(locationId)
				.setLocationContentId(contentId)
				.setLocationImage("")
				.setLocationTransportation(transportation)
				.setPlanId(planId)
				.build();
	}
}
