package com.planner.planner.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planner.planner.Config.RootAppContext;
import com.planner.planner.Config.SecurityContext;
import com.planner.planner.Config.ServletAppContext;
import com.planner.planner.Util.JwtUtil;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootAppContext.class, ServletAppContext.class, SecurityContext.class })
@Sql(scripts = {"classpath:/PlannerData.sql"})
@Transactional
public class InvitationControllerTest {
	@Autowired
	private WebApplicationContext context;
	private MockMvc mockMvc;
	@Autowired
	private JwtUtil jwtUtil;
	private ObjectMapper mapper = new ObjectMapper();
	private String token;

	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		token = "Bearer " + jwtUtil.createAccessToken(2);
	}
	
	@Test
	public void 초대_수락_접근_제한_확인() throws Exception {
		// 플래너 생성자는 1, 수락하는 유저는 2
		String fakeToken = "Bearer " + jwtUtil.createAccessToken(1);
		
		this.mockMvc.perform(post("/api/invitation/1/accept")
				.servletPath("/api/invitation/1/accept")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", fakeToken))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void 초대_수락() throws Exception {
		this.mockMvc.perform(post("/api/invitation/1/accept")
				.servletPath("/api/invitation/1/accept")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void 초대_거절_접근_제한_확인() throws Exception {
		// 플래너 생성자는 1, 수락하는 유저는 2
		String fakeToken = "Bearer " + jwtUtil.createAccessToken(1);
				
		this.mockMvc.perform(delete("/api/invitation/1/reject")
				.servletPath("/api/invitation/1/reject")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", fakeToken))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void 초대_거절() throws Exception {
		this.mockMvc.perform(delete("/api/invitation/1/reject")
				.servletPath("/api/invitation/1/reject")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk());
	}
}
