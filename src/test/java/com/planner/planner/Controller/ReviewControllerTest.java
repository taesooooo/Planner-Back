package com.planner.planner.Controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.Filter.JwtAuthenticationFilter;
import com.planner.planner.Util.JwtUtil;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Transactional
public class ReviewControllerTest {
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserDetailsService detailsService;
	
	private ObjectMapper mapper = new ObjectMapper();
	private String token;

	@BeforeEach
	public void setUp() throws Exception {
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil, detailsService);
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.addFilter(filter)
				.build();
		token = "Bearer "+ jwtUtil.createAccessToken(1);	
	}
	
	@ParameterizedTest
	@MethodSource("newReviewParameters")
	@DisplayName("리뷰 유효성 검사")
	public void 리뷰_유효성검사(int plannerId, String title, String content) throws Exception {
		ReviewDto testDto = ReviewDto.builder()
				.plannerId(plannerId)
				.title(title)
				.content(content)
				.build();

		mockMvc.perform(post("/api/reviews")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(testDto)))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	public static Stream<Arguments> newReviewParameters() {
		return Stream.of(
				Arguments.of(1, "", "재미있었다."),		// 제목 공백
				Arguments.of(1, "", "재미있었다.")			// 내용 공백
				);
	}
	

	@Test
	@DisplayName("리뷰 작성")
	public void 리뷰_작성_테스트() throws Exception {
		List<String> fileList = new ArrayList<String>();
		fileList.add("test.jpg");
		ReviewDto testDto = ReviewDto.builder()
				.plannerId(1)
				.title("test")
				.content("재미있었다.")
				.areaCode(1)
				.fileNames(fileList)
				.build();

		mockMvc.perform(post("/api/reviews")
				.servletPath("/api/reviews")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(testDto)))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.state").value(is(true)))
		.andExpect(jsonPath("$.data").isNumber());
	}
	
	@Test
	@DisplayName("리뷰 작성 지역코드 없는 경우")
	public void 리뷰_작성_지역코드_없는경우_테스트() throws Exception {
		List<String> fileList = new ArrayList<String>();
		fileList.add("test.jpg");
		ReviewDto testDto = ReviewDto.builder()
				.plannerId(1)
				.title("test")
				.content("재미있었다.")
				.fileNames(fileList)
				.build();

		mockMvc.perform(post("/api/reviews")
				.servletPath("/api/reviews")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(testDto)))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.state").value(is(true)))
		.andExpect(jsonPath("$.data").isNumber());
	}
	
	@Test
	@DisplayName("리뷰 작성 썸네일 없는 경우")
	public void 리뷰_작성_썸네일_없는경우() throws Exception {
		ReviewDto testDto = ReviewDto.builder()
				.plannerId(1)
				.title("test")
				.content("재미있었다.")
				.fileNames(null)
				.build();

		mockMvc.perform(post("/api/reviews")
				.servletPath("/api/reviews")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(testDto)))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.state").value(is(true)))
		.andExpect(jsonPath("$.data").isNumber());
	}
	
	@Test
	@DisplayName("리뷰 조회 - 최신순")
	public void 리뷰_목록_가져오기_최신순_테스트() throws Exception {		
		mockMvc.perform(get("/api/reviews")
				.servletPath("/api/reviews")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.param("itemCount", "10")
				.param("sortCriteria", "1")
				.param("keyword", "")
				.param("pageNum", "1"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.state").value(is(true)))
		.andExpect(jsonPath("$.data").isNotEmpty())
		.andExpect(jsonPath("$.data.list").isNotEmpty())
		.andExpect(jsonPath("$.data.list[0].reviewId").value(3))
		.andExpect(jsonPath("$.data.totalCount").value(3))
		.andExpect(jsonPath("$.data.pageIndex").value(1))
		.andExpect(jsonPath("$.data.pageLastIndex").value(1));
	}
	
	@Test
	@DisplayName("리뷰 조회 - 인기순")
	public void 리뷰_목록_가져오기_인기순_테스트() throws Exception {
		mockMvc.perform(get("/api/reviews")
				.servletPath("/api/reviews")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.param("itemCount", "10")
				.param("sortCriteria", "2")
				.param("keyword", "")
				.param("pageNum", "1"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.state").value(is(true)))
		.andExpect(jsonPath("$.data").isNotEmpty())
		.andExpect(jsonPath("$.data.list").isNotEmpty())
		.andExpect(jsonPath("$.data.list[0].reviewId").value(1))
		.andExpect(jsonPath("$.data.totalCount").value(3))
		.andExpect(jsonPath("$.data.pageIndex").value(1))
		.andExpect(jsonPath("$.data.pageLastIndex").value(1));
	}
	
	@Test
	@DisplayName("리뷰 조회 - 키워드")
	public void 리뷰_목록_가져오기_키워드_테스트() throws Exception {
		mockMvc.perform(get("/api/reviews")
				.servletPath("/api/reviews")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.param("itemCount", "10")
				.param("sortCriteria", "1")
				.param("keyword", "테스트")
				.param("pageNum", "1"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.state").value(is(true)))
		.andExpect(jsonPath("$.data").isNotEmpty())
		.andExpect(jsonPath("$.data.list").isNotEmpty())
		.andExpect(jsonPath("$.data.totalCount").value(1))
		.andExpect(jsonPath("$.data.pageIndex").value(1))
		.andExpect(jsonPath("$.data.pageLastIndex").value(1));
	}
	
	@Test
	@DisplayName("리뷰 조회 - 키워드")
	public void 리뷰_목록_가져오기_키워드_지역_테스트() throws Exception {
		mockMvc.perform(get("/api/reviews")
				.servletPath("/api/reviews")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.param("itemCount", "10")
				.param("sortCriteria", "1")
				.param("keyword", "테스트")
				.param("areaCode", "1")
				.param("pageNum", "1"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.state").value(is(true)))
		.andExpect(jsonPath("$.data").isNotEmpty())
		.andExpect(jsonPath("$.data.list").isNotEmpty())
		.andExpect(jsonPath("$.data.totalCount").value(1))
		.andExpect(jsonPath("$.data.pageIndex").value(1))
		.andExpect(jsonPath("$.data.pageLastIndex").value(1));
	}
	
	@Test
	@DisplayName("리뷰 조회")
	public void 리뷰_가져오기_테스트() throws Exception {
		mockMvc.perform(get("/api/reviews/{reviewId}", 1)
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.state").value(is(true)))
		.andExpect(jsonPath("$.data").isNotEmpty())
		.andExpect(jsonPath("$.data.comments.length()").value(3))
		.andExpect(jsonPath("$.data.comments[0].reComments.length()").value(1))
		.andExpect(jsonPath("$.data.comments[0].reComments[0].reComments.length()").value(1));
	}
	
	@ParameterizedTest
	@MethodSource("reviewUpdateParameters")
	@DisplayName("리뷰 수정 유효성 검사")
	public void 리뷰_수정_제목_공백_유효성검사() throws Exception {
		ReviewDto testDto = ReviewDto.builder()
				.plannerId(1)
				.title("")
				.content("재미있었다.")
				.areaCode(null)
				.build();

		mockMvc.perform(patch("/api/reviews/{reviewId}", 1)
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(testDto)))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	public static Stream<Arguments> reviewUpdateParameters() {
		return Stream.of(
				Arguments.of(1, "", "재미있었다.", 1),			// 제목 공백
				Arguments.of(1, "test", ".", 1),			// 내용 공백
				Arguments.of(1, "", "재미있었다.", 1000)		// 잘못된 지역코드
				);
	}
	
	@Test
	@DisplayName("리뷰 수정")
	public void 리뷰_수정_테스트() throws Exception {
		ReviewDto testDto = ReviewDto.builder()
				.plannerId(1)
				.title("update")
				.content("수정테스트")
				.areaCode(1)
				.build();
		
		mockMvc.perform(patch("/api/reviews/{reviewId}", 1)
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(testDto)))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.state").value(is(true)));
	}
	
	@Test
	@DisplayName("리뷰 삭제")
	public void 리뷰_삭제_테스트() throws Exception {
		mockMvc.perform(delete("/api/reviews/{reviewId}", 1)
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.state").value(is(true)));
	}
}
