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
		.andExpect(status().isOk());
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
		.andExpect(status().isOk());
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
		.andExpect(status().isCreated());
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
		.andExpect(status().isCreated());
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
				.setLocationContetntId(contentId)
				.setLocationImage("")
				.setLocationTransportation(transportation)
				.setPlanId(planId)
				.build();
	}
}
