package com.planner.planner.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planner.planner.Config.JwtContext;
import com.planner.planner.Config.RootAppContext;
import com.planner.planner.Config.SecurityContext;
import com.planner.planner.Config.ServletAppContext;
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
	public void 계정가져오기() throws Exception {
		mockMvc.perform(get("/api/users/1")
				.header("Authorization", token)
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk());
	}

	@Test
	public void 계정정보수정() throws Exception {
		AccountDto test = new AccountDto.Builder().setAccountId(1).setNickName("test").setPhone("01012341234").build();
		mockMvc.perform(patch("/api/users/1")
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

		mockMvc.perform(multipart("/api/users/images/1")
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
	public void 좋아요모두가져오기() throws Exception {
		mockMvc.perform(get("/api/users/likes/1")
				.header("Authorization", token)
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk());
	}

	@Test
	public void 좋아요여행지확인() throws Exception {
		List<Integer> list = Arrays.asList(3,4,5,6);

		mockMvc.perform(get("/api/users/likes/1/check")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtUtil.createToken(1))
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(list)))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data[0].contentId").isNumber())
		.andExpect(jsonPath("$.data[0].state").isBoolean());
	}

}
