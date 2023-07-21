package com.planner.planner.Controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import com.planner.planner.Dto.SpotLikeDto;
import com.planner.planner.Util.JwtUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { RootAppContext.class, ServletAppContext.class, JwtContext.class, SecurityContext.class })
@Transactional
@Sql(scripts = {"classpath:/Planner_Test_DB.sql"})
public class SpotControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(SpotControllerTest.class);

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private WebApplicationContext context;
	
	private String token;

	private MockMvc mockMvc;

	private ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		token = "Bearer" + jwtUtil.createToken(1);
	}
	
	@Test
	public void 여행지_지역코드_가져오기() throws Exception {
		mockMvc.perform(get("/api/spots/area-codes")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.state").value(true))
		.andExpect(jsonPath("$.data").isNotEmpty());
	}
	
	@Test
	public void 여행지_지역기반리스트_가져오기() throws Exception {
		mockMvc.perform(get("/api/spots/lists-area?areaCode=1&contentTypeId=12&numOfRows=10&pageNo=1")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.state").value(true))
		.andExpect(jsonPath("$.data").isNotEmpty())
		.andExpect(jsonPath("$.data.items").isNotEmpty())
		.andExpect(jsonPath("$.data.items.length()").value(10));
	}

	@Test
	public void 여행지_키워드별리스트_가져오기() throws Exception {
		mockMvc.perform(get("/api/spots/lists-keyword?areaCode=1&contentTypeId=12&keyword=서울&numOfRows=10&pageNo=1")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.state").value(true))
		.andExpect(jsonPath("$.data").isNotEmpty())
		.andExpect(jsonPath("$.data.items").isNotEmpty())
		.andExpect(jsonPath("$.data.items.length()").value(10));
	}
	
	@Test
	public void 여행지_세부사항_가져오기() throws Exception {
		int contentId = 2733967;
		
		mockMvc.perform(get("/api/spots/lists/"+Integer.toString(contentId))
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.state").value(true))
		.andExpect(jsonPath("$.data").isNotEmpty());
	}
	
	@Test
	public void 여행지_좋아요() throws Exception {
		SpotLikeDto likeDto = new SpotLikeDto.Builder()
				.setContentId(2763807)
				.setTitle("테스트")
				.setImage("테스트이미지주소")
				.build();
		
		mockMvc.perform(post("/api/spots/likes/")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(likeDto)))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.state").value(true));
	}
	
	@Test
	public void 여행지_좋아요_중복인경우() throws Exception {
		SpotLikeDto likeDto = new SpotLikeDto.Builder()
				.setContentId(2733967)
				.setTitle("테스트")
				.setImage("테스트이미지주소")
				.build();
		
		mockMvc.perform(post("/api/spots/likes/")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(likeDto)))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.state").value(false));
	}
	
	@Test
	public void 여행지_좋아요취소() throws Exception {
		int contentId = 2733967;
		
		mockMvc.perform(delete("/api/spots/likes/"+Integer.toString(contentId))
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.state").value(true));
	}
	

	@Test
	public void 여행지_좋아요취소_없는경우() throws Exception {
		int contentId = 2763807; // 번호 다름
		
		mockMvc.perform(delete("/api/spots/likes/"+Integer.toString(contentId))
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.state").value(false));
	}
}
