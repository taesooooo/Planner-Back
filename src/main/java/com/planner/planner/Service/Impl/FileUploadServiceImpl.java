package com.planner.planner.Service.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.planner.planner.Dao.FileUploadDao;
import com.planner.planner.Dto.FileInfoDto;
import com.planner.planner.Dto.UploadFileDto;
import com.planner.planner.Service.FileUploadService;
import com.planner.planner.Util.FileStore;

@Service
@Transactional
public class FileUploadServiceImpl implements FileUploadService {
	
	private FileStore fileStore;
	private FileUploadDao fileUploadDao;
	
	public FileUploadServiceImpl(FileStore fileStore, FileUploadDao fileUploadDao) {
		this.fileStore = fileStore;
		this.fileUploadDao = fileUploadDao;
	}

	@Override
	public FileInfoDto findFileInfo(String fileName) throws Exception {
		FileInfoDto fileInfo = fileUploadDao.getFileInfo(fileName);
		if(fileInfo == null) {
			throw new Exception(fileName + "에 대한 정보를 찾지 못했습니다.");
		}
		return fileInfo;
	}

	@Override
	public List<String> fileUpload(int userId, List<MultipartFile> images) throws Exception {
		// 이미지 저장
		List<FileInfoDto> fileList = fileStore.createFilePaths(images, FileStore.boardDir);
		
		File file = new File(fileStore.getBaseLocation() + FileStore.boardDir);
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
	public UploadFileDto loadImage(String imageName) throws Exception {
		FileInfoDto fileInfo = this.findFileInfo(imageName);
		byte[] buffer = null;
		File file = new File(fileStore.getBaseLocation() + FileStore.boardDir + File.separator + imageName);
		if(file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			buffer = new byte[(int) file.length()];
			fis.read(buffer);
			fis.close();
		}
		else {
			throw new NoSuchFileException(imageName + "를 찾지 못했습니다.");
		}
		
		UploadFileDto uploadFile = new UploadFileDto(fileInfo.getFileType(), buffer);
		return uploadFile;
	}

	@Override
	public void deleteUploadFile(FileInfoDto fileInfo) throws Exception {
		File file = new File(fileInfo.getFilePath());
		if(!file.exists()) {
			throw new NoSuchFileException(fileInfo.getFileName() + "를 찾지 못했습니다.");
		}
		
		file.delete();
		fileUploadDao.deleteFileInfoById(fileInfo.getFileId());
	}

	@Override
	public void updateBoardId(List<String> fileNames, int boardId) {
		String joinStr = StringUtils.collectionToDelimitedString(fileNames, ",", "\"", "\"");
		fileUploadDao.updateBoardId(boardId, joinStr);
	}
}
