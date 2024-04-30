package com.planner.planner.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planner.planner.Filter.JwtAuthenticationFilter;
import com.planner.planner.Service.FileService;
import com.planner.planner.Util.JwtUtil;


@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Transactional
public class FileStoreControllerTest {
	@Autowired
	private WebApplicationContext context;
	private MockMvc mockMvc;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserDetailsService detailsService;
	private String token;
	private ObjectMapper om = new ObjectMapper();
	
	@Autowired
	private FileService fileUpladService;
	
	@BeforeEach
	public void setup() {
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil, detailsService);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.addFilters(filter)
				.build();
		this.token = "Bearer " + jwtUtil.createAccessToken(1);
	}
	
	@Test
	@DisplayName("파일 업로드 성공")
	public void 파일_업로드() throws Exception {
		this.mockMvc.perform(multipart("/api/upload/file-upload")
				.file(new MockMultipartFile("files", "a.jpg", MediaType.IMAGE_JPEG_VALUE, "/jpg image/".getBytes()))
				.file(new MockMultipartFile("files", "b.png", MediaType.IMAGE_PNG_VALUE, "/png image/".getBytes()))
				.servletPath("/api/upload/file-upload")
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data.length()").value(2));
	}
	
	@Test
	@DisplayName("파일 가져오기 성공")
	public void 파일_가져오기() throws Exception {
		List<String> names = fileUpladService.fileUpload(1, Arrays.asList(new MockMultipartFile("images", "a.jpg", MediaType.IMAGE_JPEG_VALUE, "/jpg image/".getBytes())));
		String fileName = names.get(0);
		this.mockMvc.perform(get("/api/upload/files/{fileName}", fileName)
				.accept(MediaType.IMAGE_JPEG)
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.IMAGE_JPEG))
		.andExpect(content().bytes("/jpg image/".getBytes()));
	}
	
	@Test
	@DisplayName("파일 삭제 성공")
	public void 파일_삭제() throws Exception {
		List<String> names = fileUpladService.fileUpload(1, Arrays.asList(new MockMultipartFile("images", "a.jpg", MediaType.IMAGE_JPEG_VALUE, "/jpg image/".getBytes())));
		String fileName = names.get(0);
		this.mockMvc.perform(delete("/api/upload/files/{fileName}", fileName)
				.accept(MediaType.IMAGE_JPEG)
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.header("Authorization", token))
		.andDo(print())
		.andExpect(status().isOk());
	}
}
