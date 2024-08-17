package com.planner.planner.Controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planner.planner.Dto.AccountDto;
import com.planner.planner.Dto.FindEmailDto;
import com.planner.planner.Filter.JwtAuthenticationFilter;
import com.planner.planner.Util.JwtUtil;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Transactional
@WithUserDetails(value = "1")
public class AccountControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(AccountControllerTest.class);
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserDetailsService userDetailsService;

	private ObjectMapper mapper = new ObjectMapper();

	private String token;

	@BeforeEach
	public void setUp() {
		JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtUtil, userDetailsService);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.addFilter(jwtFilter)
				.build();
		token = "Bearer " + jwtUtil.createAccessToken(1);
	}

	@Test
//	@WithUserDetails(value = "1")
	public void 계정_가져오기() throws Exception {
		mockMvc.perform(get("/api/users/{userId}", 1)
				.header("Authorization", token)
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
//	@Test
	@DisplayName("계정 정보 수정 유효성 검사(잘못된 값)")
	@ParameterizedTest
	@MethodSource("userUpdateParameters")
	public void 계정정보수정_유효성_검사(String nickname, String phone) throws Exception {
		AccountDto test = AccountDto.builder()
				.nickname(nickname)
				.phone(phone)
				.build();
		
		mockMvc.perform(patch("/api/users/{userId}", 1)
				.content(mapper.writeValueAsString(test))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	private static Stream<Arguments> userUpdateParameters() {
		return Stream.of(
				Arguments.of("", "01012345678"),			// 공백
				Arguments.of("test", "01012345678910"));	// 잘못된 전화번호
	}

	@Test
	public void 계정_정보_수정() throws Exception {
		AccountDto test = AccountDto.builder()
				.nickname("test")
				.phone("01056781234")
				.build();
		
		mockMvc.perform(patch("/api/users/{userId}", 1)
				.content(mapper.writeValueAsString(test))
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk());
	}

//	@Test
//	public void 계정_정보_수정_권한없음() throws Exception {
//		String fakeToken = "Bearer " + jwtUtil.createAccessToken(2);
//		String uri = UriComponentsBuilder.fromUriString("/api/users/{userId}")
//				.build(1)
//				.toString();
//		AccountDto test = AccountDto.builder()
//				.nickname("test")
//				.phone("01012345678")
//				.build();
//		
//		mockMvc.perform(patch(uri)
//				.servletPath(uri)
//				.content(mapper.writeValueAsString(test))
//				.contentType(MediaType.APPLICATION_JSON)
//				.header("Authorization", fakeToken)
//				.accept(MediaType.APPLICATION_JSON))
//		.andDo(print())
//		.andExpect(status().isForbidden());
//	}

	@Test
	public void 계정이미지수정() throws Exception {
		MockMultipartFile image = new MockMultipartFile("image", "test.jpg", MediaType.IMAGE_JPEG_VALUE,"<<jpeg data>>".getBytes());

		mockMvc.perform(multipart("/api/users/{userId}/images", 1)
				.file(image)
				.with(request -> {request.setMethod("PATCH"); return request;})
				//.contentType(MediaType.MULTIPART_FORM_DATA)
				.header("Authorization", token)
				.accept(MediaType.APPLICATION_JSON)
				)
		.andDo(print())
		.andExpect(status().isOk());
	}
	
//	@Test
//	public void 계정이미지수정_권한없음() throws Exception {
//		String fakeToken = "Bearer " + jwtUtil.createAccessToken(2);
//		String uri = UriComponentsBuilder.fromUriString("/api/users/{userId}/images")
//				.build(1)
//				.toString();
//		MockMultipartFile image = new MockMultipartFile("image", "test.jpg", MediaType.IMAGE_JPEG_VALUE,"<<jpeg data>>".getBytes());
//
//		mockMvc.perform(multipart(uri)
//				.file(image)
//				.with(request -> {request.setMethod("PATCH"); return request;})
//				.servletPath(uri)
//				//.contentType(MediaType.MULTIPART_FORM_DATA)
//				.header("Authorization", fakeToken)
//				.accept(MediaType.APPLICATION_JSON)
//				)
//		.andDo(print())
//		.andExpect(status().isForbidden());
//	}
	
	@Test
	public void 나의_플래너_가져오기() throws Exception {
		mockMvc.perform(get("/api/users/{userId}/planners", 1)
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
	public void 나의_플래너_지역별_가져오기() throws Exception {
		mockMvc.perform(get("/api/users/{userId}/planners", 1)
				.characterEncoding("UTF-8")
				.header("Authorization", token)
				.param("itemCount", "10")
				.param("areaCode", "1")
				.param("sortCriteria", "1")
				.param("keyword", "")
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
	
//	@Test
//	public void 나의_플래너_가져오기_권한없음() throws Exception {
//		String fakeToken = "Bearer " + jwtUtil.createAccessToken(2);
//		String uri = UriComponentsBuilder.fromUriString("/api/users/{userId}/planners")
//				.build(1)
//				.toString();
//		mockMvc.perform(get(uri)
//				.servletPath(uri)
//				.header("Authorization", fakeToken)
//				.param("itemCount", "10")
//				.param("sortCriteria", "1")
//				.param("keyword", "")
//				.param("pageNum", "1"))
//		.andDo(print())
//		.andExpect(status().isForbidden());
//	}
	
	@Test
	public void 좋아요_플래너_가져오기() throws Exception {
		mockMvc.perform(get("/api/users/{userId}/likes", 1)
				.characterEncoding("UTF-8")
				.header("Authorization", token)
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
		mockMvc.perform(get("/api/users/{userId}/likes", 1)
				.characterEncoding("UTF-8")
				.header("Authorization", token)
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
	public void 좋아요_플래너_가져오기_지역별() throws Exception {
		mockMvc.perform(get("/api/users/{userId}/likes", 1)
				.characterEncoding("UTF-8")
				.header("Authorization", token)
				.param("itemCount", "10")
				.param("areaCode", "1")
				.param("sortCriteria", "1")
				.param("keyword", "")
				.param("postType", "1")
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
	
//	@Test
//	public void 좋아요_플래너_가져오기_권한없음() throws Exception {
//		String fakeToken = "Bearer " + jwtUtil.createAccessToken(2);
//		String uri = UriComponentsBuilder.fromUriString("/api/users/{userId}/likes")
//				.build(1)
//				.toString();
//		mockMvc.perform(get(uri)
//				.servletPath(uri)
//				.characterEncoding("UTF-8")
//				.header("Authorization", fakeToken)
//				.param("itemCount", "10")
//				.param("sortCriteria", "1")
//				.param("keyword", "")
//				.param("postType", "1")
//				.param("pageNum", "1"))
//		.andDo(print())
//		.andExpect(status().isForbidden());
//	}
	
	@Test
	public void 좋아요_여행지_가져오기() throws Exception {
		mockMvc.perform(get("/api/users/{userId}/likes", 1)
				.characterEncoding("UTF-8")
				.header("Authorization", token)
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
		.andExpect(jsonPath("$.data.list.length()").value(3))
		.andExpect(jsonPath("$.data.totalCount").value(3))
		.andExpect(jsonPath("$.data.pageIndex").value(1))
		.andExpect(jsonPath("$.data.pageLastIndex").value(1));
	}
	
	@Test
	public void 좋아요_여행지_가져오기_키워드() throws Exception {
		mockMvc.perform(get("/api/users/{userId}/likes", 1)
				.characterEncoding("UTF-8")
				.header("Authorization", token)
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
	public void 좋아요_여행지_가져오기_지역별() throws Exception {
		mockMvc.perform(get("/api/users/{userId}/likes", 1)
				.characterEncoding("UTF-8")
				.header("Authorization", token)
				.accept(MediaType.APPLICATION_JSON)
				.param("itemCount", "10")
				.param("areaCode", "1")
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
	
//	@Test
//	public void 좋아요_여행지_가져오기_권한없음() throws Exception {
//		String fakeToken = "Bearer " + jwtUtil.createAccessToken(2);
//		String uri = UriComponentsBuilder.fromUriString("/api/users/{userId}/likes")
//				.build(1)
//				.toString();
//		mockMvc.perform(get(uri)
//				.servletPath(uri)
//				.characterEncoding("UTF-8")
//				.header("Authorization", fakeToken)
//				.param("itemCount", "10")
//				.param("sortCriteria", "1")
//				.param("keyword", "")
//				.param("postType", "2")
//				.param("pageNum", "1"))
//		.andDo(print())
//		.andExpect(status().isForbidden());
//	}
	
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
	public void 계정_확인_없는_이메일() throws Exception {
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
	
	@ParameterizedTest
	@MethodSource("findUserParameters")
	public void 계정_찾기_유효성검사() throws Exception {
		FindEmailDto dto = FindEmailDto.builder()
				.userName("")
				.phone("")
				.code("")
				.build();
		
		this.mockMvc.perform(post("/api/users/find-email")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dto)))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message.userName").exists())
		.andExpect(jsonPath("$.message.phone").exists())
		.andExpect(jsonPath("$.message.code").exists());
	}
	
	private static Stream<Arguments> findUserParameters() {
		return Stream.of(
				Arguments.of("","",""),				// 공백	
				Arguments.of("test","0","12"));		// 잘못된 입력
	}
	
//	@Test
//	public void 계정_찾기_코드_요청() throws Exception {
//		FindEmailDto dto = FindEmailDto.builder()
//				.userName("테스트")
//				.phone("01012345678")
//				.build();
//		
//		this.mockMvc.perform(post("/api/users/find-email")
//				.accept(MediaType.APPLICATION_JSON)
//				.characterEncoding("UTF-8")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(mapper.writeValueAsString(dto)))
//		.andDo(print())
//		.andExpect(status().isOk());
//	}
//	
//	@Test
//	public void 계정_찾기_코드_확인_및_정상() throws Exception {
//		FindEmailDto dto = FindEmailDto.builder()
//				.userName("테스트")
//				.phone("01012345678")
//				.code("123456")
//				.build();
//		
//		this.mockMvc.perform(post("/api/users/find-email")
//				.accept(MediaType.APPLICATION_JSON)
//				.characterEncoding("UTF-8")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(mapper.writeValueAsString(dto)))
//		.andDo(print())
//		.andExpect(status().isOk());
//	}
//	
//	@Test
//	public void 비밀번호_찾기_유효성검사_공백() throws Exception {
//		FindPasswordDto dto = FindPasswordDto.builder()
//				.email("")
//				.build();
//		
//		this.mockMvc.perform(post("/api/users/find-password")
//				.accept(MediaType.APPLICATION_JSON)
//				.characterEncoding("UTF-8")
//				.contentType(MediaType.APPLICATION_JSON)	
//				.content(mapper.writeValueAsString(dto)))
//		.andDo(print())
//		.andExpect(status().isBadRequest());
//	}
//	
//	@Test
//	public void 비밀번호_찾기_정상() throws Exception {
//		FindPasswordDto dto = FindPasswordDto.builder()
//				.email("")
//				.build();
//		
//		this.mockMvc.perform(post("/api/users/find-password")
//				.accept(MediaType.APPLICATION_JSON)
//				.characterEncoding("UTF-8")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(mapper.writeValueAsString(dto)))
//		.andDo(print())
//		.andExpect(status().isOk());
//	}
//	
//	@Test
//	public void 비밀번호_변경_유효성검사_공백() throws Exception {
//		PasswordDto dto = PasswordDto.builder()
//				.newPassword("")
//				.confirmPassword("")
//				.key("")
//				.build();
//		
//		this.mockMvc.perform(post("/api/users/change-password")
//				.accept(MediaType.APPLICATION_JSON)
//				.characterEncoding("UTF-8")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(mapper.writeValueAsString(dto)))
//		.andDo(print())
//		.andExpect(status().isBadRequest())
//		.andExpect(jsonPath("$.message.newPassword").exists())
//		.andExpect(jsonPath("$.message.confirmPassword").exists())
//		.andExpect(jsonPath("$.message.key").exists());
//	}
//	
//	@Test
//	public void 비밀번호_변경_비밀번호_다름() throws Exception {
//		PasswordDto dto = PasswordDto.builder()
//				.newPassword("abcdefghijk!")
//				.confirmPassword("abcdefghijk")
//				.key("")
//				.build();
//		
//		this.mockMvc.perform(post("/api/users/change-password")
//				.accept(MediaType.APPLICATION_JSON)
//				.characterEncoding("UTF-8")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(mapper.writeValueAsString(dto)))
//		.andDo(print())
//		.andExpect(status().isBadRequest())
//		.andExpect(jsonPath("$.message.equalsPassword").exists());
//	}
//	
//	@Test
//	public void 비밀번호_변경_정상() throws Exception {
//		PasswordDto dto = PasswordDto.builder()
//				.newPassword("testtest!")
//				.confirmPassword("testtest!")
//				.key("b7d2703cc82093b67c55cf33e8c40ed46f92bd7bcebe6323ab35fcbbfb9cdf2e")
//				.build();
//		
//		this.mockMvc.perform(post("/api/users/change-password")
//				.accept(MediaType.APPLICATION_JSON)
//				.characterEncoding("UTF-8")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(mapper.writeValueAsString(dto)))
//		.andDo(print())
//		.andExpect(status().isOk());
//	}
	
	@Test
	public void 알림_조회() throws Exception {
		this.mockMvc.perform(get("/api/users/{userId}/notifications", 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data").isNotEmpty())
		.andExpect(jsonPath("$.data[0].id").value(1));
	}
	
//	@Test
//	public void 알림_조회_권한없음() throws Exception {
//		String fakeToken = "Bearer " + jwtUtil.createAccessToken(2);
//		String uri = UriComponentsBuilder.fromUriString("/api/users/{userId}/notifications")
//				.build(1)
//				.toString();
//		this.mockMvc.perform(get(uri)
//				.servletPath(uri)
//				.accept(MediaType.APPLICATION_JSON)
//				.characterEncoding("UTF-8")
//				.header("Authorization", fakeToken))
//		.andDo(print())
//		.andExpect(status().isForbidden());
//	}
}
