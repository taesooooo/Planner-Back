package com.planner.planner.Service.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.planner.planner.Dao.FileUploadDao;
import com.planner.planner.Dto.FileInfoDto;
import com.planner.planner.Dto.UploadFileDto;
import com.planner.planner.Service.FileService;
import com.planner.planner.Util.FileStore;

@Service
@Transactional
public class FileServiceImpl implements FileService {
	
	private FileStore fileStore;
	private FileUploadDao fileUploadDao;
	
	public FileServiceImpl(FileStore fileStore, FileUploadDao fileUploadDao) {
		this.fileStore = fileStore;
		this.fileUploadDao = fileUploadDao;
	}

	@Override
	public FileInfoDto findFileInfo(String fileName) throws Exception {
		FileInfoDto fileInfo = fileUploadDao.findByFileName(fileName);
		if(fileInfo == null) {
			throw new Exception(fileName + "에 대한 정보를 찾지 못했습니다.");
		}
		return fileInfo;
	}

	@Override
	public List<String> fileUpload(int userId, List<MultipartFile> images) throws Exception {
		// 이미지 저장
		List<FileInfoDto> fileList = fileStore.createFilePaths(images, fileStore.getBoardDir());
		
		File file = new File(fileStore.getBoardDir());
		if(!file.exists()) {
			file.mkdirs();
		}

		for(int i=0;i<fileList.size();i++) {
			FileInfoDto fileInfo = fileList.get(i);
			File f = new File(fileInfo.getFilePath());
			images.get(i).transferTo(f);
			
			fileUploadDao.createFileInfo(userId, fileInfo);
		}
		
		return fileList.stream().map(fileInfo -> fileInfo.getFileName()).collect(Collectors.toList());
	}

	@Override
	public UploadFileDto loadLocalFile(String filePath) throws Exception {
		byte[] buffer = getFile(fileStore.getBaseLocation() + filePath);
		
		String fileName = filePath;
		String extension = FilenameUtils.getExtension(fileName);
		
		UploadFileDto uploadFile = UploadFileDto.builder()
				.type(Files.probeContentType(new File(filePath).toPath()))
				.data(buffer)
				.build();
		
		return uploadFile;
	}

	@Override
	public UploadFileDto loadImage(String imageName) throws Exception {
		FileInfoDto fileInfo = this.findFileInfo(imageName);
		byte[] buffer = getFile(fileInfo.getFilePath());
		
		UploadFileDto uploadFile = UploadFileDto.builder()
				.type(fileInfo.getFileType())
				.data(buffer)
				.build();
		
		return uploadFile;
	}

	@Override
	public void deleteUploadFile(FileInfoDto fileInfo) throws Exception {
		File file = new File(fileInfo.getFilePath());
		if(!file.exists()) {
			throw new NoSuchFileException(fileInfo.getFileName() + "를 찾지 못했습니다.");
		}
		
		file.delete();
		fileUploadDao.deleteById(fileInfo.getFileId());
	}

	@Override
	public void updateBoardId(List<String> fileNames, int boardId) {
		fileUploadDao.updateBoardId(boardId, fileNames);
	}
	
	private byte[] getFile(String filePath) throws IOException {
		byte[] buffer = null;
		File file = new File(filePath);
		
		if(file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			buffer = new byte[(int) file.length()];
			fis.read(buffer);
			fis.close();
		}
		else {
			throw new NoSuchFileException(filePath + "를 찾지 못했습니다.");
		}
		
		return buffer;
	}
}
