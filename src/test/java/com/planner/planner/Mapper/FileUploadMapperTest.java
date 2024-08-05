package com.planner.planner.Mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

import com.planner.planner.Dto.FileInfoDto;

@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FileUploadMapperTest {
	@Autowired
	private FileUploadMapper mapper;
	
	@DisplayName("업로드 파일 정보 생성")
	@Test
	public void createFileInfo() {
		FileInfoDto testFileInfo = FileInfoDto.builder()
				.fileName("testFileName")
				.filePath("testFilePath")
				.fileType("testFileType")
				.build();
		
		int result = mapper.createFileInfo(1, testFileInfo);
		
		assertThat(result).isEqualTo(1);
	}
	
	@DisplayName("파일이름으로 파일 정보 가져오기")
	@Test
	public void findByFileName() {
		FileInfoDto testFileInfo = FileInfoDto.builder()
				.fileId(1)
				.fileWriterId(1)
				.fileBoradId(0)
				.fileName("testFile1.jpg")
				.filePath("img/temp/testFile1.jpg")
				.fileType("image/jpeg")
				.build();
		
		FileInfoDto findFileInfo = mapper.findByFileName("testFile1.jpg");
		
		assertThat(findFileInfo).usingRecursiveComparison()
				.ignoringFields("uploadDate")
				.isEqualTo(testFileInfo);
		
		assertThat(findFileInfo).extracting(FileInfoDto::getUploadDate)
				.isNotNull();
	}
	
	@DisplayName("임시 파일 정보 리스트 가져오기")
	@Test
	public void findTempFileList() {
		List<FileInfoDto> testFileInfoList = Arrays.asList(FileInfoDto.builder()
				.fileId(1)
				.fileWriterId(1)
				.fileBoradId(0)
				.fileName("testFile1.jpg")
				.filePath("img/temp/testFile1.jpg")
				.fileType("image/jpeg")
				.build(),
				FileInfoDto.builder()
				.fileId(2)
				.fileWriterId(1)
				.fileBoradId(0)
				.fileName("testFile2.jpg")
				.filePath("img/temp/testFile2.jpg")
				.fileType("image/jpeg")
				.build()
				);
		
		List<FileInfoDto> findFileInfoList = mapper.findTempFileList();
		
		assertThat(findFileInfoList).usingRecursiveComparison()
				.ignoringFields("uploadDate")
				.isEqualTo(testFileInfoList);
		
		assertThat(findFileInfoList).extracting(FileInfoDto::getUploadDate)
				.isNotNull();
	}
	
	@DisplayName("업로드 파일에 게시글 아이디 연결")
	@Test
	public void updateBoardId() {
		List<String> fileNameList = Arrays.asList("testFile1.jpg", "testFile2.jpg", "testFile3.jpg");
		
		int result = mapper.updateBoardId(1, fileNameList);
		
		assertThat(result).isEqualTo(3);
	}
	
	@DisplayName("파일 정보 삭제")
	@Test
	public void deleteById() {
		int result = mapper.deleteById(1);
		
		assertThat(result).isEqualTo(1);
	}
}
