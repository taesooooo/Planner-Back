package com.planner.planner.Service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.planner.planner.Dao.FileUploadDao;
import com.planner.planner.Dto.FileInfoDto;
import com.planner.planner.Dto.UploadFileDto;
import com.planner.planner.Service.Impl.FileServiceImpl;
import com.planner.planner.Util.FileStore;

public class FileServiceTest {
	
	// junit 임시폴더 테스트 종료시 자동 삭제(업로드 용도로 사용)
	@Rule
    public final TemporaryFolder folder = new TemporaryFolder();
	
	@InjectMocks
	private FileServiceImpl fileUploadService;
	
	@Mock
	private FileUploadDao fileUploadDao;
	
	@Spy
	private FileStore fileStore;

	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.openMocks(this);
		fileStore.setBaseLocation(folder.newFolder("Uploads").getAbsolutePath());
		System.out.println(fileStore.getBaseLocation());
	}
	
	@Test
	public void 파일_정보_가져오기() throws Exception {
		String fileName = "a.jpg";
		String filePath = fileStore.getBoardDir() + fileName;
		FileInfoDto fileInfo = new FileInfoDto(1, 1, 1, fileName, filePath, MediaType.IMAGE_JPEG_VALUE, LocalDateTime.now());
		
		when(fileUploadDao.getFileInfo(fileName)).thenReturn(fileInfo);
		
		FileInfoDto info = fileUploadService.findFileInfo(fileName);
		
		assertEquals(info.getFileName(), fileName);
	}
	
	@Test(expected = Exception.class)
	public void 파일_정보_가져오기_없는경우() throws Exception {
		String fileName = "a.jpg";
		String filePath = fileStore.getBoardDir() + fileName;
		//FileInfoDto fileInfo = new FileInfoDto(1, 1, 1, fileName, filePath, MediaType.IMAGE_JPEG_VALUE, LocalDateTime.now());
		
		when(fileUploadDao.getFileInfo(fileName)).thenReturn(null);
		
		FileInfoDto info = fileUploadService.findFileInfo(fileName);
	}

	@Test
	public void 파일_업로드() throws Exception {
		List<MultipartFile> files = new ArrayList<MultipartFile>();
		files.add(new MockMultipartFile("images", "a.jpg", MediaType.IMAGE_JPEG_VALUE, "/jpg image/".getBytes()));
		files.add(new MockMultipartFile("images", "a.png", MediaType.IMAGE_PNG_VALUE, "/png image/".getBytes()));
		files.add(new MockMultipartFile("images", "b.jpg", MediaType.IMAGE_JPEG_VALUE, "/jpg image/".getBytes()));
		files.add(new MockMultipartFile("images", "b.png", MediaType.IMAGE_PNG_VALUE, "/png image/".getBytes()));
		
		List<String> names = fileUploadService.fileUpload(1, files);
		
		assertEquals(names.size(), 4);
	}
	
	@Test
	public void 이미지_가져오기() throws Exception {
		MockMultipartFile file = new MockMultipartFile("images", "a.jpg", MediaType.IMAGE_JPEG_VALUE, "/jpg image/".getBytes());
		FileInfoDto fileInfo = new FileInfoDto(1, 1, 1, "a.jpg", "\\a.jpg", MediaType.IMAGE_JPEG_VALUE, LocalDateTime.now());
		//System.out.println(fileStore.getBaseLocation() + FileStore.boardDir + File.separator + file.getOriginalFilename());
		File f = new File(fileStore.getBoardDir() + file.getOriginalFilename());
		f.getParentFile().mkdirs();
		file.transferTo(f);
		when(fileUploadDao.getFileInfo(file.getOriginalFilename())).thenReturn(fileInfo);
		
		UploadFileDto data = fileUploadService.loadImage(file.getOriginalFilename());
		assertArrayEquals(data.getData(), file.getBytes());
	}
	
	@Test(expected = NoSuchFileException.class)
	public void 이미지_가져오기_파일_없는경우() throws Exception {
		MockMultipartFile file = new MockMultipartFile("images", "a.jpg", MediaType.IMAGE_JPEG_VALUE, "/jpg image/".getBytes());
		FileInfoDto fileInfo = new FileInfoDto(1, 1, 1, "a.jpg", "\\a.jpg", MediaType.IMAGE_JPEG_VALUE, LocalDateTime.now());
		File f = new File(fileStore.getBoardDir() + file.getOriginalFilename());
		f.getParentFile().mkdirs();
		//file.transferTo(f);
		when(fileUploadDao.getFileInfo(file.getOriginalFilename())).thenReturn(fileInfo);

		UploadFileDto data  = fileUploadService.loadImage(file.getOriginalFilename());

	}
	
	@Test
	public void 업로드_파일_게시판_아이디_연결() {
		List<String> fileNames = Arrays.asList("a.jpg","a.png","b.jpg","b.png");
		int boardId = 1;
		
		fileUploadService.updateBoardId(fileNames, boardId);
	}
	
	@Test
	public void 업로드_파일_삭제() throws Exception {
		String fileName = "a.jpg";
		String filePath = fileStore.getBoardDir() + fileName;
		FileInfoDto fileInfo = new FileInfoDto(1, 1, 1, "a.jpg", filePath, MediaType.IMAGE_JPEG_VALUE, LocalDateTime.now()); 
		File f = new File(filePath);
		f.getParentFile().mkdirs();
		Files.write(f.toPath(), "/jpg image/".getBytes());
		
		when(fileUploadDao.getFileInfo(fileName)).thenReturn(fileInfo);
		
		fileUploadService.deleteUploadFile(fileInfo);
	}
	
	@Test(expected = NoSuchFileException.class)
	public void 업로드_파일_삭제_파일_없는경우() throws Exception {
		String fileName = "a.jpg";
		String filePath = fileStore.getBoardDir() + fileName;
		FileInfoDto fileInfo = new FileInfoDto(1, 1, 1, "a.jpg", filePath, MediaType.IMAGE_JPEG_VALUE, LocalDateTime.now()); 
		
		when(fileUploadDao.getFileInfo(fileName)).thenReturn(fileInfo);
		
		fileUploadService.deleteUploadFile(fileInfo);
	}

}
