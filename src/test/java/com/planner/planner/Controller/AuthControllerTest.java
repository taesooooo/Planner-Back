package com.planner.planner.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.planner.planner.Config.JwtContext;
import com.planner.planner.Config.RootAppContext;
import com.planner.planner.Config.SecurityContext;
import com.planner.planner.Config.ServletAppContext;
import com.planner.planner.Dto.AccountDto;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootAppContext.class, ServletAppContext.class, JwtContext.class, SecurityContext.class })
public class AuthControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(AuthControllerTest.class);

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	private ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void 회원가입() throws Exception{
		AccountDto testDto = new AccountDto.Builder().setAccountId(0).setEmail("test0@naver.com").setPassword("1234").setUserName("test0").setNickName("test0").setPhone("01012345678").build();
		ObjectNode node = mapper.createObjectNode();
		node.put("accountId", testDto.getAccountId());
		node.put("email", testDto.getEmail());
		node.put("password", testDto.getPassword());
		node.put("username",testDto.getUserName());
		node.put("nickname", testDto.getNickName());
		node.put("phone", testDto.getPhone());

		mockMvc.perform(post("/api/auth/register")
				.content(node.toString())
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isCreated());
	}

	@Test
	public void 로그인() throws Exception {
		ObjectNode node = mapper.createObjectNode();
		node.put("email","test@naver.com");
		node.put("password", "1234");

		mockMvc.perform(post("/api/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(node.toString()))
		.andDo(print())
		.andExpect(status().isOk());
	}
}
