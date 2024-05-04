package com.planner.planner.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.AuthenticationCodeDto;
import com.planner.planner.Filter.JwtAuthenticationFilter;
import com.planner.planner.Util.JwtUtil;

import jakarta.servlet.http.Cookie;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
//@Transactional
public class AuthControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(AuthControllerTest.class);

	@Autowired
	private WebApplicationContext context;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserDetailsService userDetailsService;
	
	private MockMvc mockMvc;

	private ObjectMapper mapper = new ObjectMapper();

	@BeforeEach
	public void setUp() {
		JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtUtil, userDetailsService);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.addFilter(jwtFilter)
				.build();
	}
	
	@Test
	@DisplayName("회원가입 성공")
	public void 회원가입() throws Exception{
		AccountDto testDto = AccountDto.builder()
				.accountId(0)
				.email("test4@naver.com")
				.password("testtest!")
				.username("test0")
				.nickname("test0")
				.phone("01012345678")
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
	
	@ParameterizedTest
	@MethodSource("registerParameters")
	@DisplayName("회원가입 유효성 검사")
	public void 회원가입_유효성_검사(String email, String pw, String username, String nickname, String phone) throws Exception{
		AccountDto testDto = AccountDto.builder()
				.accountId(0)
				.email(email)
				.password(pw)
				.username(username)
				.nickname(nickname)
				.phone(phone)
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
	
	// 회원가임 유효성 검사 데이터
	public static Stream<Arguments> registerParameters() {
		return Stream.of(
				Arguments.of("", "testtest!", "test0", "test0", "01012345678"), 		// 이메일 미작성
				Arguments.of("test12", "testtest!", "test0", "test0", "01012345678"), 	// 이메일 잘못된 형식
				Arguments.of("test12", "", "test0", "test0", "01012345678"),			// 비밀번호 미작성
				Arguments.of("test12", "test!", "test0", "test0", "01012345678"),		// 비밀번호 잘못된 형식
				Arguments.of("test12", "test!", "", "test0", "01012345678"),			// 이름 미작성
				Arguments.of("test12", "test!", "test0", "", "01012345678"),			// 닉네임 미작성
				Arguments.of("test12", "test!", "test0", "test0", "01012345678910")		// 휴대폰 번호 개수 초과
				);
	}
	
	@Test
	@DisplayName("회원가입 이메일 인증 안된경우")
	public void 회원가입_이메일_인증_안된경우() throws Exception{
		AccountDto testDto = AccountDto.builder()
				.accountId(0)
				.email("testtest@naver.com")
				.password("testtest!")
				.username("test0")
				.nickname("test0")
				.phone("01012345678")
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
	
	@ParameterizedTest
	@MethodSource("loginParameters")
	@DisplayName("로그인 유효성 검사")
	public void 로그인_이메일_미작성_유효성검사(String email, String pw) throws Exception{	
		ObjectNode node = mapper.createObjectNode();
		node.put("email", email);
		node.put("password", pw);

		mockMvc.perform(post("/api/auth/login")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(node.toString()))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	public static Stream<Arguments> loginParameters() {
		return Stream.of(
				Arguments.of("", "testtest!"), 						// 이메일 미작성
				Arguments.of("test!naver.com", "testtest!"),		// 이메일 작못된 형식
				Arguments.of("test@naver.com", ""),					// 비밀번호 미작성
				Arguments.of("test@naver.com", "test")				// 비밀번호 잘못된 형식
				);
	}

	@Test
	@DisplayName("로그인 성공")
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
	@DisplayName("로그아웃")
	public void 로그아웃() throws Exception {
		String token = "Bearer " + jwtUtil.createAccessToken(1);
		
		mockMvc.perform(delete("/api/auth/logout")
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
//	@Test
//	public void 휴대폰_인증코드_전송_정상() throws Exception {
//		String phone = "";
//		this.mockMvc.perform(post("/api/auth/authentication-code/send")
//				.characterEncoding("UTF-8")
//				.accept(MediaType.APPLICATION_JSON)
//				.param("phone", phone))
//		.andDo(print())
//		.andExpect(status().isOk());
//	}
	
	@Test
	@DisplayName("이메일 인증코드 전송 정상(이메일 입력 필요)")
	public void 이메일_인증코드_전송_정상() throws Exception {
		String email = "";
		this.mockMvc.perform(post("/api/auth/authentication-code/request")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.param("email", email))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
//	@Test
//	public void 인증코드_전송_휴대폰번호_11글자_이하_유효성검사() throws Exception {
//		String phone = "01012345";
//		this.mockMvc.perform(post("/api/auth/authentication-code/send")
//				.characterEncoding("UTF-8")
//				.accept(MediaType.APPLICATION_JSON)
//				.param("phone", phone))
//		.andDo(print())
//		.andExpect(status().isBadRequest());
//	}
	
	@Test
	@DisplayName("인증코드 전송 이메일 유효성 검사")
	public void 인증코드_전송_이메일_유효성검사() throws Exception {
		String email = "test@";
		this.mockMvc.perform(post("/api/auth/authentication-code/request")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.param("email", email))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("휴대폰 인증코드 확인 정상")
	public void 휴대폰_인증코드_확인_정상() throws Exception {
		AuthenticationCodeDto authenticationCodeDto = AuthenticationCodeDto.builder()
				.phone("01012345678")
				.code("123456")
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
	@DisplayName("이메일 인증 코드 확인 정상")
	public void 이메일_인증코드_확인_정상() throws Exception {
		AuthenticationCodeDto authenticationCodeDto = AuthenticationCodeDto.builder()
				.email("test@naver.com")
				.code("123456")
				.build();
		
		this.mockMvc.perform(post("/api/auth/authentication-code/check")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(authenticationCodeDto)))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@ParameterizedTest
	@MethodSource("authenticationCodeCheckParameters")
	@DisplayName("인증코드 휴효성 검사")
	public void 인증코드_확인_휴대폰번호_유효성검사(String email, String phone, String code) throws Exception {
		AuthenticationCodeDto authenticationCodeDto = AuthenticationCodeDto.builder()
				.email(email)
				.phone(phone)
				.code(code)
				.build();
		
		this.mockMvc.perform(post("/api/auth/authentication-code/check")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(authenticationCodeDto)))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	public static Stream<Arguments> authenticationCodeCheckParameters() {
		return Stream.of(
				Arguments.of("010", null, "123456"),			// 잘못된 휴대폰 번호
				Arguments.of(null, "test@", "123456"),			// 잘못된 이메일 번호
				Arguments.of(null, null, "123456"),				// 이메일, 휴대폰 모두 없는 경우
				Arguments.of("01012345678", null, ""),			// 코드 미작성
				Arguments.of("01012345678", null, "1234")		// 코드 잘못된 형식(6글자)
				);
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
