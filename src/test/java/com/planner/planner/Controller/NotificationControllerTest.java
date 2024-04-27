package com.planner.planner.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planner.planner.Config.RootAppContext;
import com.planner.planner.Config.SecurityConfiguration;
import com.planner.planner.Config.ServletAppContext;
import com.planner.planner.Util.JwtUtil;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootAppContext.class, ServletAppContext.class, SecurityConfiguration.class })
@Sql(scripts = {"classpath:/PlannerData.sql"})
@Transactional
public class NotificationControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(NotificationControllerTest.class);
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Autowired
	private JwtUtil jwtUtil;

	private ObjectMapper mapper = new ObjectMapper();

	private String token;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		token = "Bearer " + jwtUtil.createAccessToken(1);
	}
	
	@Test
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
	public void 알림_읽음_권한없음() throws Exception {
		String fakeToken = token = "Bearer " + jwtUtil.createAccessToken(2);
		String uri = UriComponentsBuilder.fromUriString("/api/notifications/{notificationId}/read")
				.build(1)
				.toString();
		mockMvc.perform(post(uri)
				.servletPath(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.header("Authorization", fakeToken))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void 알림_삭제() throws Exception {
		mockMvc.perform(delete("/api/notifications/1")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void 알림_삭제_권한없음() throws Exception {
		String fakeToken = token = "Bearer " + jwtUtil.createAccessToken(2);
		String uri = UriComponentsBuilder.fromUriString("/api/notifications/{notificationId}")
				.build(1)
				.toString();
		mockMvc.perform(delete(uri)
				.servletPath(uri)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.header("Authorization", fakeToken))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
}
