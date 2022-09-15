package com.planner.planner;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.planner.planner.Config.JwtContext;
import com.planner.planner.Config.RootAppContext;
import com.planner.planner.Config.SecurityContext;
import com.planner.planner.Config.ServletAppContext;
import com.planner.planner.Controller.AccountController;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.util.JwtUtil;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootAppContext.class, ServletAppContext.class, JwtContext.class, SecurityContext.class })
@Transactional
public class AccountTest {
	private static final Logger logger = LoggerFactory.getLogger(AccountTest.class);
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;

	@Autowired
	private JwtUtil jwtUtil;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private String token;
	private AccountDto testDto;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		token = jwtUtil.createToken(0);
		testDto = new AccountDto.Builder().setAccountId(1).setEmail("test@naver.com").setUserName("test").setNickName("test").build();
	}
	
	@Test
	public void 계정생성테스트() throws Exception{
		AccountDto test = new AccountDto.Builder().setAccountId(0).setEmail("test0@naver.com").setPassword("1234").setUserName("test0").setNickName("test0").build();
		ObjectNode node = mapper.createObjectNode();
		node.put("accountId", test.getAccountId());
		node.put("email", test.getEmail());
		node.put("password", test.getPassword());
		node.put("username",test.getUserName());
		node.put("nickname", test.getNickName());
		
		mockMvc.perform(post("/api/auth/register")
				.content(node.toString())
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isCreated());
	}
	
	@Test
	public void 계정프로필수정테스트() throws Exception {
		String jsonConvert = mapper.writeValueAsString(testDto);
		
		MockMultipartFile data = new MockMultipartFile("data", "data", MediaType.APPLICATION_JSON_VALUE, jsonConvert.getBytes());
		MockMultipartFile image = new MockMultipartFile("image", "test.jpg", MediaType.IMAGE_JPEG_VALUE,"<<jpeg data>>".getBytes());
		
		mockMvc.perform(multipart("/api/users/1")
				.file(data)
				.file(image)
				.with(request -> {request.setMethod("PUT"); return request;})
				//.contentType(MediaType.MULTIPART_FORM_DATA)
				.header("Authorization", token)
				.accept(MediaType.APPLICATION_JSON)
				)
		.andDo(print())
		.andExpect(status().isOk());
	}

}
