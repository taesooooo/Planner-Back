package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Dto.FileInfoDto;

public interface FileUploadDao {
	public FileInfoDto getFileInfo(String fileName);
	public List<FileInfoDto> getTempFileInfoList();
	public void createFileInfo(int userId, FileInfoDto fileInfo);
	public void updateBoardId(int boardId, List<String> fileNames);
	public void deleteFileInfoById(int fileId);
}
