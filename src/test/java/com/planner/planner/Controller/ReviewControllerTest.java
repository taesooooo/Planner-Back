package com.planner.planner.Controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlConfig.TransactionMode;
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
import com.planner.planner.Dao.ReviewDao;
import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.util.JwtUtil;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootAppContext.class, ServletAppContext.class, JwtContext.class, SecurityContext.class })
@Transactional
@Sql(scripts = {"classpath:/Planner_Test_DB.sql"})
public class ReviewControllerTest {
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	private ObjectMapper mapper = new ObjectMapper();
	private String token;
	
	@Autowired
	private ReviewDao reviewDao;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		token = "Bearer "+ jwtUtil.createToken(1);	
	}

	@Test
	public void 리뷰_작성_테스트() throws Exception {
		ReviewDto testDto = new ReviewDto.Builder().setPlannerId(1).setTitle("test").setContent("재미있었다.").setWriter(1).build();

		mockMvc.perform(post("/api/reviews")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(testDto)))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.state").value(is(true)));
	}
	
	@Test
	public void 리뷰_목록가져오기_테스트() throws Exception {
		int index = 1;
		mockMvc.perform(get("/api/reviews")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(index)))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.state").value(is(true)))
		.andExpect(jsonPath("$.data").isNotEmpty());
	}
	
	@Test
	public void 리뷰_가져오기_테스트() throws Exception {
		mockMvc.perform(get("/api/reviews/1")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.state").value(is(true)));
	}
	
	@Test
	public void 리뷰_수정_테스트() throws Exception {
		ReviewDto testDto = new ReviewDto.Builder().setReviewId(1).setPlannerId(1).setTitle("update").setContent("수정테스트").setWriter(1).build();
		
		mockMvc.perform(patch("/api/reviews/1")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(testDto)))
		.andDo(print())
		.andExpect(status().isNoContent())
		.andExpect(jsonPath("$.state").value(is(true)));
	}
	
	@Test
	public void 리뷰_삭제_테스트() throws Exception {
		mockMvc.perform(delete("/api/reviews/1")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNoContent())
		.andExpect(jsonPath("$.state").value(is(true)));
	}

}
