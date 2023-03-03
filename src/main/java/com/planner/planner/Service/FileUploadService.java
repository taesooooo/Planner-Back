package com.planner.planner.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.planner.planner.Dto.FileInfoDto;
import com.planner.planner.Dto.UploadFileDto;

public interface FileUploadService {
	public FileInfoDto findFileInfo(String fileName) throws Exception;
	public List<String> fileUpload(int userId, List<MultipartFile> images) throws Exception;
	public UploadFileDto loadImage(String imageName) throws Exception;
	public void deleteUploadFile(FileInfoDto fileInfo) throws Exception;
	public void updateBoardId(List<String> fileNames, int boardId);
}
