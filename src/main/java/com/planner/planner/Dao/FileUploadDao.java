package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Dto.FileInfoDto;

public interface FileUploadDao {
	public void createFileInfo(int userId, FileInfoDto fileInfo);
	public FileInfoDto findByFileName(String fileName);
	public List<FileInfoDto> findTempFileList();
	public void updateBoardId(int boardId, List<String> fileNames);
	public void deleteById(int fileId);
}
