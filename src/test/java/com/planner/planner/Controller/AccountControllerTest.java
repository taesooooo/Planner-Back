package com.planner.planner.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
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
import com.planner.planner.Dto.AuthenticationCodeDto;
import com.planner.planner.Dto.FindPasswordDto;
import com.planner.planner.Dto.PasswordDto;
import com.planner.planner.Util.JwtUtil;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootAppContext.class, ServletAppContext.class, JwtContext.class, SecurityContext.class })
@Sql(scripts = {"classpath:/Planner_Test_DB.sql"})
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
		token = "Bearer " + jwtUtil.createToken(1);
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
	public void 계정정보수정_닉네임_미작성_유효성검사() throws Exception {
		AccountDto test = new AccountDto.Builder()
				.setAccountId(1)
				.setNickname("")
				.setPhone("01012341234")
				.build();
		
		mockMvc.perform(patch("/api/users/1")
				.content(mapper.writeValueAsString(test))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 계정정보수정_휴대폰번호_번호수_초과_유효성검사() throws Exception {
		AccountDto test = new AccountDto.Builder()
				.setAccountId(1)
				.setNickname("test")
				.setPhone("111111111111111")
				.build();
		
		mockMvc.perform(patch("/api/users/1")
				.content(mapper.writeValueAsString(test))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}

	@Test
	public void 계정정보수정() throws Exception {
		AccountDto test = new AccountDto.Builder()
				.setAccountId(1)
				.setNickname("test")
				.setPhone("01012341234")
				.build();
		
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
	public void 나의_플래너_가져오기() throws Exception {
		mockMvc.perform(get("/api/users/1/planners")
				.header("Authorization", token)
				.param("itemCount", "10")
				.param("sortCriteria", "1")
				.param("keyword", "")
				.param("pageNum", "1"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data").isNotEmpty())
		.andExpect(jsonPath("$.data.list").exists())
		.andExpect(jsonPath("$.data.list").isNotEmpty())
		.andExpect(jsonPath("$.data.list.length()").value(3))
		.andExpect(jsonPath("$.data.totalCount").value(3))
		.andExpect(jsonPath("$.data.pageIndex").value(1))
		.andExpect(jsonPath("$.data.pageLastIndex").value(1));
	}
	
	@Test
	public void 좋아요_플래너_가져오기() throws Exception {
		mockMvc.perform(get("/api/users/1/likes")
				.characterEncoding("UTF-8")
				.header("Authorization", token)
				.accept(MediaType.APPLICATION_JSON)
				.param("itemCount", "10")
				.param("sortCriteria", "1")
				.param("keyword", "")
				.param("postType", "1")
				.param("pageNum", "1"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data").isNotEmpty())
		.andExpect(jsonPath("$.data.list").exists())
		.andExpect(jsonPath("$.data.list").isNotEmpty())
		.andExpect(jsonPath("$.data.list.length()").value(5))
		.andExpect(jsonPath("$.data.totalCount").value(5))
		.andExpect(jsonPath("$.data.pageIndex").value(1))
		.andExpect(jsonPath("$.data.pageLastIndex").value(1));
	}
	
	@Test
	public void 좋아요_플래너_가져오기_키워드() throws Exception {
		mockMvc.perform(get("/api/users/1/likes")
				.characterEncoding("UTF-8")
				.header("Authorization", token)
				.accept(MediaType.APPLICATION_JSON)
				.param("itemCount", "10")
				.param("sortCriteria", "1")
				.param("keyword", "테스트")
				.param("postType", "1")
				.param("pageNum", "1"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data").isNotEmpty())
		.andExpect(jsonPath("$.data.list").exists())
		.andExpect(jsonPath("$.data.list").isNotEmpty())
		.andExpect(jsonPath("$.data.list.length()").value(2))
		.andExpect(jsonPath("$.data.totalCount").value(2))
		.andExpect(jsonPath("$.data.pageIndex").value(1))
		.andExpect(jsonPath("$.data.pageLastIndex").value(1));
	}
	
	@Test
	public void 좋아요_여행지_가져오기() throws Exception {
		mockMvc.perform(get("/api/users/1/likes")
				.characterEncoding("UTF-8")
				.header("Authorization", token)
				.accept(MediaType.APPLICATION_JSON)
				.param("itemCount", "10")
				.param("sortCriteria", "1")
				.param("keyword", "")
				.param("postType", "2")
				.param("pageNum", "1"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data").isNotEmpty())
		.andExpect(jsonPath("$.data.list").exists())
		.andExpect(jsonPath("$.data.list").isNotEmpty())
		.andExpect(jsonPath("$.data.list.length()").value(1))
		.andExpect(jsonPath("$.data.totalCount").value(1))
		.andExpect(jsonPath("$.data.pageIndex").value(1))
		.andExpect(jsonPath("$.data.pageLastIndex").value(1));
	}
	
	@Test
	public void 좋아요_여행지_가져오기_키워드() throws Exception {
		mockMvc.perform(get("/api/users/1/likes")
				.characterEncoding("UTF-8")
				.header("Authorization", token)
				.accept(MediaType.APPLICATION_JSON)
				.param("itemCount", "10")
				.param("sortCriteria", "1")
				.param("keyword", "테스트")
				.param("postType", "2")
				.param("pageNum", "1"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data").isNotEmpty())
		.andExpect(jsonPath("$.data.list").exists())
		.andExpect(jsonPath("$.data.list").isNotEmpty())
		.andExpect(jsonPath("$.data.list.length()").value(2))
		.andExpect(jsonPath("$.data.totalCount").value(2))
		.andExpect(jsonPath("$.data.pageIndex").value(1))
		.andExpect(jsonPath("$.data.pageLastIndex").value(1));
	}

	@Test
	public void 좋아요여행지확인() throws Exception {
		mockMvc.perform(get("/api/users/likes/1/check")
				.param("contentIds", "2733967, 2733968")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data[0].contentId").isNumber())
		.andExpect(jsonPath("$.data[0].state").isBoolean());
	}
	
	@Test
	public void 계정_확인_이메일() throws Exception {
		String searchEmail = "test@naver.com";
		this.mockMvc.perform(get("/api/users/search-member")
				.param("searchString", searchEmail)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data").value(true));
	}
	
	@Test
	public void 계정_확인_없는이메일() throws Exception {
		String searchEmail = "aa@naver.com";
		this.mockMvc.perform(get("/api/users/search-member")
				.param("searchString", searchEmail)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void 계정_찾기_유효성검사_공백() throws Exception {
		String url = "/api/users/find-email";
		AuthenticationCodeDto dto = new AuthenticationCodeDto.Builder()
				.setPhone("")
				.setCode("")
				.build();
		
		this.mockMvc.perform(post(url)
				.servletPath(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dto)))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message.phone").exists())
		.andExpect(jsonPath("$.message.code").exists());
	}

	@Test
	public void 계정_찾기_유효성검사_잘못된입력() throws Exception {
		String url = "/api/users/find-email";
		AuthenticationCodeDto dto = new AuthenticationCodeDto.Builder()
				.setPhone("010")
				.setCode("0")
				.build();
		
		this.mockMvc.perform(post(url)
				.servletPath(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dto)))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message.phone").exists())
		.andExpect(jsonPath("$.message.code").exists());
	}
	
	@Test
	public void 계정_찾기_정상() throws Exception {
		String url = "/api/users/find-email";
		AuthenticationCodeDto dto = new AuthenticationCodeDto.Builder()
				.setPhone("01012345678")
				.setCode("123456")
				.build();
		
		this.mockMvc.perform(post(url)
				.servletPath(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dto)))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 비밀번호_찾기_유효성검사_공백() throws Exception {
		String url = "/api/users/find-password";
		FindPasswordDto dto = new FindPasswordDto.Builder().setEmail("").build();
		
		this.mockMvc.perform(post(url)
				.servletPath(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dto)))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 비밀번호_찾기_정상() throws Exception {
		String url = "/api/users/find-password";
		FindPasswordDto dto = new FindPasswordDto.Builder().setEmail("test@naver.com").build();
		
		this.mockMvc.perform(post(url)
				.servletPath(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dto)))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void 비밀번호_변경_유효성검사_공백() throws Exception {
		String url = "/api/users/change-password";
		PasswordDto dto = new PasswordDto.Builder()
				.setNewPassword("")
				.setConfirmPassword("")
				.setKey("")
				.build();
		
		this.mockMvc.perform(post(url)
				.servletPath(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dto)))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message.newPassword").exists())
		.andExpect(jsonPath("$.message.confirmPassword").exists())
		.andExpect(jsonPath("$.message.key").exists());
	}
	
	@Test
	public void 비밀번호_변경_비밀번호_다름() throws Exception {
		String url = "/api/users/change-password";
		PasswordDto dto = new PasswordDto.Builder()
				.setNewPassword("abcdefghijk!")
				.setConfirmPassword("abcdefghijk")
				.setKey("")
				.build();
		
		this.mockMvc.perform(post(url)
				.servletPath(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dto)))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message.equalsPassword").exists());
	}
	
	@Test
	public void 비밀번호_변경_정상() throws Exception {
		String url = "/api/users/change-password";
		PasswordDto dto = new PasswordDto.Builder()
				.setNewPassword("test")
				.setConfirmPassword("test")
				.setKey("b7d2703cc82093b67c55cf33e8c40ed46f92bd7bcebe6323ab35fcbbfb9cdf2e")
				.build();
		
		this.mockMvc.perform(post(url)
				.servletPath(url)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dto)))
		.andDo(print())
		.andExpect(status().isOk());
	}
}
