package com.planner.planner.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planner.planner.Dto.CommentDto;
import com.planner.planner.Filter.JwtAuthenticationFilter;
import com.planner.planner.Util.JwtUtil;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Transactional
public class CommentControllerTest {
	
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
	public void setup() {
		JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtUtil, userDetailsService);
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.addFilters(jwtFilter)
				.build();
		token = "Bearer " + jwtUtil.createAccessToken(1);
	}
	
	@ParameterizedTest
	@MethodSource("commentParameters")
	public void 댓글_유효성_검사(String content, int parentId) throws Exception {
		CommentDto newComment = createComment(content, parentId);
		
		this.mockMvc.perform(post("/api/reviews/{reviewId}/comments", 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(newComment)))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	public static Stream<Arguments> commentParameters() {
		return Stream.of(
				Arguments.of("", 1),				// 내용 공백
				Arguments.of("테스트", 0)				// 잘못된 상위 댓글 아이디
				);
	}
	
	@Test
	public void 댓글_작성() throws Exception {
		CommentDto newComment = createComment("댓글테스트", null);
		
		this.mockMvc.perform(post("/api/reviews/{reviewId}/comments", 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(newComment)))
		.andDo(print())
		.andExpect(status().isCreated());
	}
	
	@ParameterizedTest
	@MethodSource("commentUpdateParameters")
	public void 댓글_수정_내용_공백_유효성검사(String content, int parentId) throws Exception {
		CommentDto updateComment = createComment(content, parentId);
		
		this.mockMvc.perform(patch("/api/reviews/{reviewId}/comments/{commentId}", 1, 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(updateComment)))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	public static Stream<Arguments> commentUpdateParameters() {
		return Stream.of(
				Arguments.of("", 1),				// 내용 공백
				Arguments.of("테스트", 0)				// 잘못된 상위 댓글 아이디
				);
	}
	
	@Test
	public void 댓글_수정() throws Exception {
		CommentDto updateComment = createComment("댓글수정테스트", null);
		
		this.mockMvc.perform(patch("/api/reviews/{reviewId}/comments/{commentId}", 1, 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(updateComment)))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	@Test
	public void 댓글_삭제() throws Exception {
		this.mockMvc.perform(delete("/api/reviews/{reviewId}/comments/{commentId}", 1, 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	private CommentDto createComment(String content, Integer parentId) {
		return CommentDto.builder()
				.commentId(1)
				.reviewId(1)
				.writerId(1)
				.writer("test")
				.content(content)
				.parentId(parentId)
				.build();
	}
}
