package com.planner.planner.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planner.planner.Filter.JwtAuthenticationFilter;
import com.planner.planner.Util.JwtUtil;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Transactional
public class NotificationControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(NotificationControllerTest.class);
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
	public void setUp() {
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil, detailsService);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.addFilter(filter)
				.build();
		token = "Bearer " + jwtUtil.createAccessToken(1);
	}
	
	@Test
	@DisplayName("알림 읽음")
	public void 알림_읽음() throws Exception {
		mockMvc.perform(post("/api/notifications/1/read")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk());
	}

	@Test
	@DisplayName("알림 삭제")
	public void 알림_삭제() throws Exception {
		mockMvc.perform(delete("/api/notifications/1")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk());
	}
}
