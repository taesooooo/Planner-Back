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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.planner.planner.Config.RootAppContext;
import com.planner.planner.Config.SecurityContext;
import com.planner.planner.Config.ServletAppContext;
import com.planner.planner.Dto.ReviewDto;
import com.planner.planner.Util.JwtUtil;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootAppContext.class, ServletAppContext.class, SecurityContext.class })
@Sql(scripts = {"classpath:/PlannerData.sql"})
@Transactional
public class ReviewControllerTest {
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	private ObjectMapper mapper = new ObjectMapper();
	private String token;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.build();
		token = "Bearer "+ jwtUtil.createAccessToken(1);	
	}
	
	@Test
	public void 리뷰_제목_공백_유효성검사() throws Exception {
		ReviewDto testDto = new ReviewDto.Builder()
				.setPlannerId(1)
				.setTitle("")
				.setContent("재미있었다.")
				.setWriter("test")
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
	
	@Test
	public void 리뷰_내용_공백_유효성검사() throws Exception {
		ReviewDto testDto = new ReviewDto.Builder()
				.setPlannerId(1)
				.setTitle("test")
				.setContent("")
				.setWriter("test")
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

	@Test
	public void 리뷰_작성_테스트() throws Exception {
		List<String> fileList = new ArrayList<String>();
		fileList.add("test.jpg");
		ReviewDto testDto = new ReviewDto.Builder().setPlannerId(1).setTitle("test").setContent("재미있었다.").setAreaCode(1).setFileNames(fileList).build();

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
		.andExpect(jsonPath("$.data").value(4));
	}
	
	@Test
	public void 리뷰_작성_지역코드_없는경우_테스트() throws Exception {
		List<String> fileList = new ArrayList<String>();
		fileList.add("test.jpg");
		ReviewDto testDto = new ReviewDto.Builder().setPlannerId(1).setTitle("test").setContent("재미있었다.").setFileNames(fileList).build();

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
		.andExpect(jsonPath("$.data").value(4));
	}
	
	@Test
	public void 리뷰_작성_썸네일_없는경우() throws Exception {
		ReviewDto testDto = new ReviewDto.Builder().setPlannerId(1).setTitle("test").setContent("재미있었다.").setFileNames(null).build();

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
		.andExpect(jsonPath("$.data").value(4));
	}
	
	@Test
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
		.andExpect(jsonPath("$.data.comments.length()").value(1))
		.andExpect(jsonPath("$.data.comments[0].reComments.length()").value(1))
		.andExpect(jsonPath("$.data.comments[0].reComments[0].reComments.length()").value(1));
	}
	
	@Test
	public void 리뷰_수정_제목_공백_유효성검사() throws Exception {
		ReviewDto testDto = new ReviewDto.Builder()
				.setPlannerId(1)
				.setTitle("")
				.setContent("재미있었다.")
				.setWriter("test")
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
	
	@Test
	public void 리뷰_수정_내용_공백_유효성검사() throws Exception {
		ReviewDto testDto = new ReviewDto.Builder()
				.setPlannerId(1)
				.setTitle("test")
				.setContent("")
				.setWriter("test")
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
	
	@Test
	public void 리뷰_수정_테스트() throws Exception {
		ReviewDto testDto = new ReviewDto.Builder().setPlannerId(1).setTitle("update").setContent("수정테스트").setWriter("test").build();
		
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
	public void 리뷰_수정_권한없음() throws JsonProcessingException, Exception {
		String fakeToken = "Bearer " + jwtUtil.createAccessToken(2);
		ReviewDto testDto = new ReviewDto.Builder().setPlannerId(1).setTitle("update").setContent("수정테스트").setWriter("test").build();
		
		String uri = UriComponentsBuilder.fromUriString("/api/reviews/{reviewId}")
				.build(1)
				.toString();
		
		mockMvc.perform(patch(uri)
				.servletPath(uri)
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", fakeToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(testDto)))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
	
	@Test
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

	@Test
	public void 리뷰_삭제_권한없음() throws Exception {
		String fakeToken = "Bearer " + jwtUtil.createAccessToken(2);
		
		String uri = UriComponentsBuilder.fromUriString("/api/reviews/{reviewId}")
				.build(1)
				.toString();
		
		mockMvc.perform(delete(uri)
				.servletPath(uri)
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", fakeToken)
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
}
