package com.planner.planner.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.jupiter.api.Test;
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
	public void 초대_수락_권한없음() throws Exception {
		// 플래너 생성자는 1, 수락하는 유저는 2
		String fakeToken = "Bearer " + jwtUtil.createAccessToken(1);
		String uri = UriComponentsBuilder.fromUriString("/api/invitation/{inviteId}/accept")
				.build(1)
				.toString();
		
		this.mockMvc.perform(post(uri)
				.servletPath(uri)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", fakeToken))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void 초대_수락() throws Exception {
		this.mockMvc.perform(post("/api/invitation/{inviteId}/accept", 1)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void 초대_거절_권한없음() throws Exception {
		// 플래너 생성자는 1, 수락하는 유저는 2
		String fakeToken = "Bearer " + jwtUtil.createAccessToken(1);
		String uri = UriComponentsBuilder.fromUriString("/api/invitation/{inviteId}/reject")
				.build(1)
				.toString();
		this.mockMvc.perform(delete(uri)
				.servletPath(uri)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", fakeToken))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void 초대_거절() throws Exception {
		this.mockMvc.perform(delete("/api/invitation/{inviteId}/reject", 1)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk());
	}
}
