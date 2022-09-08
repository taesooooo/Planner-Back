package com.planner.planner;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planner.planner.Config.RootAppContext;

import com.planner.planner.Dto.AccountDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootAppContext.class })
public class AccountTest {
	private static final Logger logger = LoggerFactory.getLogger(AccountDaoTest.class);
	
	private MockMvc mockMvc;

	private ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new AccountTest()).addFilters(new CharacterEncodingFilter("UTF-8", true)).build();
	}

	@Test
	public void 계정프로필수정테스트() throws Exception {
		AccountDto accountDto = new AccountDto.Builder().setAccountId(1).setEmail("test@naver.com").setUserName("test")
				.setNickName("test").build();
		String jsonConvert = mapper.writeValueAsString(accountDto);
		MockMultipartFile file = new MockMultipartFile("images", "aaa.png", null,
				new FileInputStream("C:\\planner\\test\\aaa.png"));
		
		mockMvc.perform(multipart("/api/users/1")
				.file(file)
				.part(new MockPart("data",jsonConvert.getBytes()))
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.accept(MediaType.APPLICATION_JSON)
				.with(request -> {request.setMethod("PUT"); return request;})
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andReturn();
	}

}
