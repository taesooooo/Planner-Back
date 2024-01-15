package com.planner.planner.Controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.Cookie;

import org.junit.Before;
import org.junit.Test;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.JsonPath;
import com.planner.planner.Config.RootAppContext;
import com.planner.planner.Config.SecurityContext;
import com.planner.planner.Config.ServletAppContext;
import com.planner.planner.Config.ValidationConfig;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.AuthenticationCodeDto;
import com.planner.planner.Util.JwtUtil;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ValidationConfig.class, RootAppContext.class, ServletAppContext.class, SecurityContext.class })
@Sql(scripts = {"classpath:/PlannerData.sql"})
@Transactional
public class AuthControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(AuthControllerTest.class);

	@Autowired
	private WebApplicationContext context;
	@Autowired
	private JwtUtil jwtUtil;
	
	private MockMvc mockMvc;

	private ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void 회원가입_이메일_미작성_유효성검사() throws Exception{
		AccountDto testDto = new AccountDto.Builder()
				.setAccountId(0)
				.setEmail("")
				.setPassword("testtest!")
				.setUsername("test0")
				.setNickname("test0")
				.setPhone("01012345678")
				.build();
		
		ObjectNode node = mapper.createObjectNode();
		node.put("accountId", testDto.getAccountId());
		node.put("email", testDto.getEmail());
		node.put("password", testDto.getPassword());
		node.put("username",testDto.getUsername());
		node.put("nickname", testDto.getNickname());
		node.put("phone", testDto.getPhone());

		mockMvc.perform(post("/api/auth/register")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(node.toString()))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 회원가입_이메일_잘못된형식_유효성검사() throws Exception{
		AccountDto testDto = new AccountDto.Builder()
				.setAccountId(0)
				.setEmail("test!")
				.setPassword("testtest!")
				.setUsername("test0")
				.setNickname("test0")
				.setPhone("01012345678")
				.build();
		
		ObjectNode node = mapper.createObjectNode();
		node.put("accountId", testDto.getAccountId());
		node.put("email", testDto.getEmail());
		node.put("password", testDto.getPassword());
		node.put("username",testDto.getUsername());
		node.put("nickname", testDto.getNickname());
		node.put("phone", testDto.getPhone());

		mockMvc.perform(post("/api/auth/register")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(node.toString()))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 회원가입_비밀번호_미작성_유효성검사() throws Exception{
		AccountDto testDto = new AccountDto.Builder()
				.setAccountId(0)
				.setEmail("test4@naver.com")
				.setPassword("")
				.setUsername("test0")
				.setNickname("test0")
				.setPhone("01012345678")
				.build();
		
		ObjectNode node = mapper.createObjectNode();
		node.put("accountId", testDto.getAccountId());
		node.put("email", testDto.getEmail());
		node.put("password", testDto.getPassword());
		node.put("username",testDto.getUsername());
		node.put("nickname", testDto.getNickname());
		node.put("phone", testDto.getPhone());

		mockMvc.perform(post("/api/auth/register")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(node.toString()))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 회원가입_비밀번호_유효성검사() throws Exception{
		AccountDto testDto = new AccountDto.Builder()
				.setAccountId(0)
				.setEmail("test4@naver.com")
				.setPassword("test!")
				.setUsername("test0")
				.setNickname("test0")
				.setPhone("01012345678")
				.build();
		
		ObjectNode node = mapper.createObjectNode();
		node.put("accountId", testDto.getAccountId());
		node.put("email", testDto.getEmail());
		node.put("password", testDto.getPassword());
		node.put("username",testDto.getUsername());
		node.put("nickname", testDto.getNickname());
		node.put("phone", testDto.getPhone());

		mockMvc.perform(post("/api/auth/register")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(node.toString()))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 회원가입_이름_미작성_유효성검사() throws Exception{
		AccountDto testDto = new AccountDto.Builder()
				.setAccountId(0)
				.setEmail("test4@naver.com")
				.setPassword("testtest!")
				.setUsername("")
				.setNickname("test0")
				.setPhone("01012345678")
				.build();
		
		ObjectNode node = mapper.createObjectNode();
		node.put("accountId", testDto.getAccountId());
		node.put("email", testDto.getEmail());
		node.put("password", testDto.getPassword());
		node.put("username",testDto.getUsername());
		node.put("nickname", testDto.getNickname());
		node.put("phone", testDto.getPhone());

		mockMvc.perform(post("/api/auth/register")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(node.toString()))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 회원가입_닉네임_미작성_유효성검사() throws Exception{
		AccountDto testDto = new AccountDto.Builder()
				.setAccountId(0)
				.setEmail("test4@naver.com")
				.setPassword("testtest!")
				.setUsername("test0")
				.setNickname("")
				.setPhone("01012345678")
				.build();
		
		ObjectNode node = mapper.createObjectNode();
		node.put("accountId", testDto.getAccountId());
		node.put("email", testDto.getEmail());
		node.put("password", testDto.getPassword());
		node.put("username",testDto.getUsername());
		node.put("nickname", testDto.getNickname());
		node.put("phone", testDto.getPhone());

		mockMvc.perform(post("/api/auth/register")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(node.toString()))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 회원가입_휴대폰번호_번호수_초과_유효성검사() throws Exception{
		AccountDto testDto = new AccountDto.Builder()
				.setAccountId(0)
				.setEmail("test4@naver.com")
				.setPassword("testtest!")
				.setUsername("")
				.setNickname("test0")
				.setPhone("11111111111111")
				.build();
		
		ObjectNode node = mapper.createObjectNode();
		node.put("accountId", testDto.getAccountId());
		node.put("email", testDto.getEmail());
		node.put("password", testDto.getPassword());
		node.put("username",testDto.getUsername());
		node.put("nickname", testDto.getNickname());
		node.put("phone", testDto.getPhone());

		mockMvc.perform(post("/api/auth/register")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(node.toString()))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 회원가입_이메일_인증_안된경우() throws Exception{
		AccountDto testDto = new AccountDto.Builder()
				.setAccountId(0)
				.setEmail("testtest@naver.com")
				.setPassword("testtest!")
				.setUsername("test0")
				.setNickname("test0")
				.setPhone("01012345678")
				.build();
		
		ObjectNode node = mapper.createObjectNode();
		node.put("accountId", testDto.getAccountId());
		node.put("email", testDto.getEmail());
		node.put("password", testDto.getPassword());
		node.put("username",testDto.getUsername());
		node.put("nickname", testDto.getNickname());
		node.put("phone", testDto.getPhone());

		mockMvc.perform(post("/api/auth/register")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(node.toString()))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}

	@Test
	public void 회원가입() throws Exception{
		AccountDto testDto = new AccountDto.Builder()
				.setAccountId(0)
				.setEmail("test4@naver.com")
				.setPassword("testtest!")
				.setUsername("test0")
				.setNickname("test0")
				.setPhone("01012345678")
				.build();
		
		ObjectNode node = mapper.createObjectNode();
		node.put("accountId", testDto.getAccountId());
		node.put("email", testDto.getEmail());
		node.put("password", testDto.getPassword());
		node.put("username",testDto.getUsername());
		node.put("nickname", testDto.getNickname());
		node.put("phone", testDto.getPhone());

		mockMvc.perform(post("/api/auth/register")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(node.toString()))
		.andDo(print())
		.andExpect(status().isCreated());
	}
	
	@Test
	public void 로그인_이메일_미작성_유효성검사() throws Exception{
		AccountDto testDto = new AccountDto.Builder()
				.setAccountId(0)
				.setEmail("")
				.setPassword("testtest!")
				.setUsername("test0")
				.setNickname("test0")
				.setPhone("01012345678")
				.build();
		
		ObjectNode node = mapper.createObjectNode();
		node.put("accountId", testDto.getAccountId());
		node.put("email", testDto.getEmail());
		node.put("password", testDto.getPassword());
		node.put("username",testDto.getUsername());
		node.put("nickname", testDto.getNickname());
		node.put("phone", testDto.getPhone());

		mockMvc.perform(post("/api/auth/login")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(node.toString()))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 로그인_이메일_잘못된형식_유효성검사() throws Exception{
		AccountDto testDto = new AccountDto.Builder()
				.setAccountId(0)
				.setEmail("test!naver.com")
				.setPassword("testtest!")
				.setUsername("")
				.setNickname("test0")
				.setPhone("01012345678")
				.build();
		
		ObjectNode node = mapper.createObjectNode();
		node.put("accountId", testDto.getAccountId());
		node.put("email", testDto.getEmail());
		node.put("password", testDto.getPassword());
		node.put("username",testDto.getUsername());
		node.put("nickname", testDto.getNickname());
		node.put("phone", testDto.getPhone());

		mockMvc.perform(post("/api/auth/login")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(node.toString()))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 로그인_비밀번호_미작성_유효성검사() throws Exception{
		AccountDto testDto = new AccountDto.Builder()
				.setAccountId(0)
				.setEmail("test@naver.com")
				.setPassword("")
				.setUsername("")
				.setNickname("test0")
				.setPhone("01012345678")
				.build();
		
		ObjectNode node = mapper.createObjectNode();
		node.put("accountId", testDto.getAccountId());
		node.put("email", testDto.getEmail());
		node.put("password", testDto.getPassword());
		node.put("username",testDto.getUsername());
		node.put("nickname", testDto.getNickname());
		node.put("phone", testDto.getPhone());

		mockMvc.perform(post("/api/auth/login")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(node.toString()))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 로그인_비밀번호_유효성검사() throws Exception{
		AccountDto testDto = new AccountDto.Builder()
				.setAccountId(0)
				.setEmail("test@naver.com")
				.setPassword("test!")
				.setUsername("")
				.setNickname("test0")
				.setPhone("01012345678")
				.build();
		
		ObjectNode node = mapper.createObjectNode();
		node.put("accountId", testDto.getAccountId());
		node.put("email", testDto.getEmail());
		node.put("password", testDto.getPassword());
		node.put("username",testDto.getUsername());
		node.put("nickname", testDto.getNickname());
		node.put("phone", testDto.getPhone());

		mockMvc.perform(post("/api/auth/login")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(node.toString()))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}

	@Test
	public void 로그인() throws Exception {
		ObjectNode node = mapper.createObjectNode();
		node.put("email","test@naver.com");
		node.put("password", "testtest!");

		mockMvc.perform(post("/api/auth/login")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(node.toString()))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(cookie().exists("RefreshToken"))
		.andExpect(jsonPath("$.data.user").isNotEmpty())
		.andExpect(jsonPath("$.data.accessToken").isNotEmpty());
	}
	
	@Test
	public void 로그아웃() throws Exception {
		String token = "Bearer " + jwtUtil.createAccessToken(1);
		
		mockMvc.perform(get("/api/auth/logout")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void 휴대폰_인증코드_전송_정상() throws Exception {
		String phone = "";
		this.mockMvc.perform(post("/api/auth/authentication-code/send")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.param("phone", phone))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void 이메일_인증코드_전송_정상() throws Exception {
		String email = "";
		this.mockMvc.perform(post("/api/auth/authentication-code/send")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.param("email", email))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void 인증코드_전송_휴대폰번호_11글자_이하_유효성검사() throws Exception {
		String phone = "01012345";
		this.mockMvc.perform(post("/api/auth/authentication-code/send")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.param("phone", phone))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 인증코드_전송_이메일_유효성검사() throws Exception {
		String email = "test@";
		this.mockMvc.perform(post("/api/auth/authentication-code/send")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.param("email", email))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 휴대폰_인증코드_확인_정상() throws Exception {
		AuthenticationCodeDto authenticationCodeDto = new AuthenticationCodeDto.Builder()
				.setPhone("")
				.setCode("123456")
				.build();
		
		this.mockMvc.perform(post("/api/auth/authentication-code/check")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(authenticationCodeDto)))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void 이메일_인증코드_확인_정상() throws Exception {
		AuthenticationCodeDto authenticationCodeDto = new AuthenticationCodeDto.Builder()
				.setEmail("")
				.setCode("123456")
				.build();
		
		this.mockMvc.perform(post("/api/auth/authentication-code/check")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(authenticationCodeDto)))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void 인증코드_확인_휴대폰번호_유효성검사() throws Exception {
		AuthenticationCodeDto authenticationCodeDto = new AuthenticationCodeDto.Builder()
				.setPhone("010")
				.setCode("123456")
				.build();
		
		this.mockMvc.perform(post("/api/auth/authentication-code/check")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(authenticationCodeDto)))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 인증코드_확인_이메일_유효성검사() throws Exception {
		AuthenticationCodeDto authenticationCodeDto = new AuthenticationCodeDto.Builder()
				.setEmail("test@")
				.setCode("123456")
				.build();
		
		this.mockMvc.perform(post("/api/auth/authentication-code/check")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(authenticationCodeDto)))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 인증코드_확인_휴대폰번호_이메일_모두_없는경우() throws Exception {
		AuthenticationCodeDto authenticationCodeDto = new AuthenticationCodeDto.Builder()
				.setCode("123456")
				.build();
		
		this.mockMvc.perform(post("/api/auth/authentication-code/check")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(authenticationCodeDto)))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 인증코드_확인_인증코드_미작성_유효성검사() throws Exception {
		AuthenticationCodeDto authenticationCodeDto = new AuthenticationCodeDto.Builder()
				.setPhone("01012345678")
				.setCode("")
				.build();
		
		this.mockMvc.perform(post("/api/auth/authentication-code/check")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(authenticationCodeDto)))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 인증코드_확인_인증코드_6글자_유효성검사() throws Exception {
		AuthenticationCodeDto authenticationCodeDto = new AuthenticationCodeDto.Builder()
				.setPhone("01012345678")
				.setCode("1234")
				.build();
		
		this.mockMvc.perform(post("/api/auth/authentication-code/check")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(authenticationCodeDto)))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 토큰_재발급_토큰이_없는경우() throws Exception {
		mockMvc.perform(post("/api/auth/token-reissue")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 토큰_재발급_리프레시토큰이_잘못된_토큰인경우() throws Exception {
		mockMvc.perform(post("/api/auth/token-reissue")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.cookie(new Cookie("RefreshToken", "aaaa.bbbb.ccc")))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
	
	@Test
	public void 토큰_재발급() throws Exception {
		// 테스트용 무기한 리프레시 토큰
		String refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJwbGFubmVyIiwiaWF0IjoxNzAzOTE1ODE1fQ.iLobjjcY4pEUEnmHJhJNrg8-wddr_xxkrn7DtNRL2CM";
		
		mockMvc.perform(post("/api/auth/token-reissue")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.cookie(new Cookie("RefreshToken", refreshToken)))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(cookie().exists("RefreshToken"))
		.andExpect(jsonPath("$.data.accessToken").exists());
	}
	
	@Test
	public void 토큰_재발급_리프레시_토큰_기간만료() throws Exception {
		String expireRefreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJwbGFubmVyIiwiaWF0IjoxNzAzOTA5NDk1LCJleHAiOjE3MDI2OTk4OTV9.kljyEzgp1CxpiWivbETgFJXwN8H3wJj03L0YWi-ZuFw";
		mockMvc.perform(post("/api/auth/token-reissue")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.cookie(new Cookie("RefreshToken", expireRefreshToken)))
		.andDo(print())
		.andExpect(status().isUnauthorized());
	}
}
