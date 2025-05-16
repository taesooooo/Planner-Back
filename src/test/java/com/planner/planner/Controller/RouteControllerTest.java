package com.planner.planner.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
		this.mockMvc.perform(get("/api/routes/find")
				.queryParam("start", "33.4824388,126.4898217")
				.queryParam("end", "33.4845859,126.4963428")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.characterEncoding("UTF-8"))
		.andDo(print())
		.andExpect(status().isOk());
	}
}
