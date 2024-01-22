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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
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
import com.planner.planner.Service.FileService;
import com.planner.planner.Util.JwtUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { RootAppContext.class, ServletAppContext.class, SecurityContext.class })
@Sql(scripts = {"classpath:/PlannerData.sql"})
public class FileStoreControllerTest {
	@Autowired
	private WebApplicationContext context;
	private MockMvc mockMvc;
	@Autowired
	private JwtUtil jwtUtil;
	private String token;
	private ObjectMapper om = new ObjectMapper();
	
	@Autowired
	private FileService fileUpladService;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		this.token = "Bearer " + jwtUtil.createAccessToken(1);
	}
	
	@Test
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
	
	@Test
	public void 파일_삭제_접근권한_없음() throws Exception {
		String fakeToken = "Bearer " + jwtUtil.createAccessToken(2);
		List<String> names = fileUpladService.fileUpload(1, Arrays.asList(new MockMultipartFile("images", "a.jpg", MediaType.IMAGE_JPEG_VALUE, "/jpg image/".getBytes())));
		String fileName = names.get(0);
		String uri = UriComponentsBuilder.fromUriString("/api/upload/files/{fileName}")
				.build(fileName)
				.toString();
		
		this.mockMvc.perform(delete(uri)
				.servletPath(uri)
				.accept(MediaType.IMAGE_JPEG)
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.header("Authorization", fakeToken))
		.andDo(print())
		.andExpect(status().isForbidden());
	}
}
