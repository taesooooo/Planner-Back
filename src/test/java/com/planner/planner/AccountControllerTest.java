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
public class AccountControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(AccountControllerTest.class);
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
		token = jwtUtil.createToken(1);
	}
	
	@Test
	public void 회원가입() throws Exception{
		AccountDto testDto = new AccountDto.Builder().setAccountId(0).setEmail("test0@naver.com").setPassword("1234").setUserName("test0").setNickName("test0").build();
		ObjectNode node = mapper.createObjectNode();
		node.put("accountId", testDto.getAccountId());
		node.put("email", testDto.getEmail());
		node.put("password", testDto.getPassword());
		node.put("username",testDto.getUserName());
		node.put("nickname", testDto.getNickName());
		
		mockMvc.perform(post("/api/users/register")
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
		
		mockMvc.perform(post("/api/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(node.toString()))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void 계정가져오기() throws Exception {
		mockMvc.perform(get("/api/users/1")
				.header("Authorization", token)
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void 계정정보수정() throws Exception {
		AccountDto test = new AccountDto.Builder().setAccountId(1).setEmail("test@naver.com").setUserName("test").setNickName("test").build();
		mockMvc.perform(put("/api/users/1")
				.content(mapper.writeValueAsString(test))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void 계정이미지수정() throws Exception {
		MockMultipartFile image = new MockMultipartFile("image", "test.jpg", MediaType.IMAGE_JPEG_VALUE,"<<jpeg data>>".getBytes());
		
		mockMvc.perform(multipart("/api/users/1")
				.file(image)
				.with(request -> {request.setMethod("PATCH"); return request;})
				//.contentType(MediaType.MULTIPART_FORM_DATA)
				.header("Authorization", token)
				.accept(MediaType.APPLICATION_JSON)
				)
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void 계정좋아요모두가져오기() throws Exception {
		mockMvc.perform(get("/api/users/1/likes")
				.header("Authorization", token)
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk());
	}

}
