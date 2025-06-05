package com.planner.planner.Controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.isNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("test")
@SpringBootTest
public class RouteControllerTest {
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext applicationContext;
	
	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
	}
	
	@Test
	@DisplayName("경로 탐색 서버 API 호출 - 정상")
	public void routeTest() throws Exception {
		// 33.3057279944/126.2466098987|33.3209235283/126.2460707194,33.3209235283/126.2460707194|33.4018299117/126.6876111209
		List<String> testList = List.of("33.3057279944/126.2466098987|33.3209235283/126.2460707194","33.3209235283/126.2460707194|33.4018299117/126.6876111209");
		this.mockMvc.perform(get("/api/routes/find")
				.queryParam("coordinates", testList.stream().collect(Collectors.joining(",")))
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.characterEncoding("UTF-8"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data[0].start").value(notNullValue()))
		.andExpect(jsonPath("$.data[0].end").value(notNullValue()))
		.andExpect(jsonPath("$.data[0].routeList").value(notNullValue()));
	}
}
