package com.planner.planner.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planner.planner.Config.RootAppContext;
import com.planner.planner.Config.SecurityContext;
import com.planner.planner.Config.ServletAppContext;
import com.planner.planner.Dto.CommentDto;
import com.planner.planner.Util.JwtUtil;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootAppContext.class, ServletAppContext.class, SecurityContext.class })
@Sql(scripts = {"classpath:/PlannerData.sql"})
@Transactional
public class CommentControllerTest {
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	private ObjectMapper mapper = new ObjectMapper();
	private String token;

	
	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		token = "Bearer " + jwtUtil.createAccessToken(1);
	}
	
	@Test
	public void 댓글_내용_공백_유효성검사() throws Exception {
		CommentDto newComment = createComment("", null);
		
		this.mockMvc.perform(post("/api/reviews/{reviewId}/comments", 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(newComment)))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 댓글_잘못된상위댓글_유효성검사() throws Exception {
		CommentDto newComment = createComment("테스트", 0);
		
		this.mockMvc.perform(post("/api/reviews/{reviewId}/comments", 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(newComment)))
		.andDo(print())
		.andExpect(status().isBadRequest());
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
	
	@Test
	public void 댓글_수정_내용_공백_유효성검사() throws Exception {
		CommentDto updateComment = createComment("", null);
		
		this.mockMvc.perform(patch("/api/reviews/{reviewId}/comments/{commentId}", 1, 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(updateComment)))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void 댓글_수정_잘못된상위댓글_유효성검사() throws Exception {
		CommentDto updateComment = createComment("테스트", 0);
		
		this.mockMvc.perform(patch("/api/reviews/{reviewId}/comments/{commentId}", 1, 1)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(mapper.writeValueAsString(updateComment)))
		.andDo(print())
		.andExpect(status().isBadRequest());
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
	public void 댓글_수정_권한없음() throws Exception {
		String fakeToken = "Bearer " + jwtUtil.createAccessToken(2);
		CommentDto updateComment = createComment("댓글수정테스트", null);
		String uri = UriComponentsBuilder.fromUriString("/api/reviews/{reviewId}/comments/{commentId}")
				.build(1, 1)
				.toString();
		
		this.mockMvc.perform(patch(uri)
				.servletPath(uri)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", fakeToken)
				.content(mapper.writeValueAsString(updateComment)))
		.andDo(print())
		.andExpect(status().isForbidden());
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
	
	@Test
	public void 댓글_삭제_권한없음() throws Exception {
		String fakeToken = "Bearer " + jwtUtil.createAccessToken(2);
		String uri = UriComponentsBuilder.fromUriString("/api/reviews/{reviewId}/comments/{commentId}")
				.build(1, 1)
				.toString();
		
		this.mockMvc.perform(patch(uri)
				.servletPath(uri)
				.characterEncoding("UTF-8")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", fakeToken))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
	
	
	private CommentDto createComment(String content, Integer parentId) {
		return new CommentDto.Builder()
				.setCommentId(1)
				.setReviewId(1)
				.setWriterId(1)
				.setWriter("test")
				.setContent(content)
				.setParentId(parentId)
				.build();
	}
}
