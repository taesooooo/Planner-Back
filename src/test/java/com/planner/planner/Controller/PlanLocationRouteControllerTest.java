package com.planner.planner.Controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.apache.catalina.core.ApplicationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.planner.planner.Common.Coordinate;
import com.planner.planner.Dto.PlanLocationRouteDto;
import com.planner.planner.Util.ResponseMessage;

@ActiveProfiles("test")
@SpringBootTest
//@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
public class PlanLocationRouteControllerTest {
	@Autowired
	private WebApplicationContext applicationContext;
	
//	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
	}

	@Test
	void createLocationRouteUpdate() throws Exception {
		int testPlannerId = 1;
		int testPlanId = 1;
		PlanLocationRouteDto newDto = PlanLocationRouteDto.builder()
				.planId(1)
				.routeList(List.of(new Coordinate(1.11, 1.11), new Coordinate(2.22, 2.22)))
				.build();

		mockMvc.perform(post("/api/planners/{plannerId}/plans/{planId}/location-routes", testPlannerId, testPlanId)
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newDto)))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.state").value(true))
		.andExpect(jsonPath("$.data").value(3));
	}

	@Test
	void findLocationRouteByIdTest() throws Exception {
		int testPlannerId = 1;
		int testPlanId = 1;
		int testLocationRouteId = 1;
		PlanLocationRouteDto testDto = PlanLocationRouteDto.builder()
				.id(1)
				.planId(1)
				.startIndex(0)
				.endIndex(1)
				.routeList(List.of(new Coordinate(0.00, 0.00),new Coordinate(1.11, 1.11), new Coordinate(2.22, 2.22)))
				.build();

		MvcResult result = mockMvc.perform(get("/api/planners/{plannerId}/plans/{planId}/location-routes/{locationRouteId}", testPlannerId, testPlanId, testLocationRouteId)
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.state").value(true))
		.andReturn();
		
		String content = result.getResponse().getContentAsString();
		
		PlanLocationRouteDto resultDto = objectMapper.treeToValue(objectMapper.readTree(content).get("data"), PlanLocationRouteDto.class);
		
		assertThat(resultDto).usingRecursiveComparison()
				.isEqualTo(testDto);
	}

	@Test
	void updateLocationRouteTest() throws Exception {
		int testPlannerId = 1;
		int testPlanId = 1;
		int testLocationRouteId = 1;

		PlanLocationRouteDto updateDto = PlanLocationRouteDto.builder()
				.id(testLocationRouteId)
				.planId(testPlanId)
				.routeList(List.of(new Coordinate(33.00, 128.00)))
				.build();

		mockMvc.perform(patch("/api/planners/{plannerId}/plans/{planId}/location-routes/{locationRouteId}", testPlannerId, testPlanId, testLocationRouteId)
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateDto)))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.state").value(true));
	}

	@Test
	void deleteLocationRouteTest() throws Exception {
		int testPlannerId = 1;
		int testPlanId = 1;
		int testLocationRouteId = 1;

		mockMvc.perform(delete("/api/planners/{plannerId}/plans/{planId}/location-routes/{locationRouteId}", testPlannerId, testPlanId, testLocationRouteId)
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.state").value(true));
	}
}
