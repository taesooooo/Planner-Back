package com.planner.planner.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import com.planner.planner.Dto.SpotLikeDto;
import com.planner.planner.Filter.JwtAuthenticationFilter;
import com.planner.planner.Util.JwtUtil;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Transactional
public class SpotControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(SpotControllerTest.class);
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserDetailsService detailsService;
	
	private String token;

	private MockMvc mockMvc;

	private ObjectMapper mapper = new ObjectMapper();

	@BeforeEach
	public void setUp() throws Exception {
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil, detailsService);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.addFilter(filter)
				.build();
		token = "Bearer" + jwtUtil.createAccessToken(1);
	}
	
	@Test
	@DisplayName("여행지 조회 - 지역코드")
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
	@DisplayName("여행지 조회 - 지역기반")
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
	@DisplayName("여행지 조회 - 키워드")
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
	@DisplayName("여행지 세부사항 조회")
	public void 여행지_세부사항_가져오기() throws Exception {
		mockMvc.perform(get("/api/spots/lists/{contentId}", 2733967)
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
	@DisplayName("여행지 좋아요")
	public void 여행지_좋아요() throws Exception {
		SpotLikeDto likeDto = SpotLikeDto.builder()
				.contentId(2763807)
				.title("테스트")
				.image("테스트이미지주소")
				.build();
		
		mockMvc.perform(post("/api/spots/likes")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(likeDto))
				.requestAttr("userId", 1))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.state").value(true));
	}
	
	@Test
	@DisplayName("여행지 좋아요 중복")
	public void 여행지_좋아요_중복인경우() throws Exception {
		SpotLikeDto likeDto = SpotLikeDto.builder()
				.contentId(2733967)
				.title("테스트")
				.image("테스트이미지주소")
				.build();
		
		mockMvc.perform(post("/api/spots/likes")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(likeDto))
				.requestAttr("userId", 1))
		.andDo(print())
		.andExpect(status().isConflict())
		.andExpect(jsonPath("$.state").value(false));
	}
	
	@Test
	@DisplayName("여행지 좋아요 취소")
	public void 여행지_좋아요취소() throws Exception {
		mockMvc.perform(delete("/api/spots/likes/{contentId}", 2733967)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
				.requestAttr("userId", 1))
		.andDo(print())
		.andExpect(jsonPath("$.state").value(true));
	}
	

	@Test
	@DisplayName("여행지 좋아요 취소 해당 데이터가 없는 경우")
	public void 여행지_좋아요취소_없는경우() throws Exception {
		// 번호 다름
		mockMvc.perform(delete("/api/spots/likes/{contentId}", 2763807)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.state").value(false));
	}
}
