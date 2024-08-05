package com.planner.planner.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.planner.planner.Dto.FileInfoDto;

@Mapper
public interface FileUploadMapper {
	public int createFileInfo(int userId, FileInfoDto fileInfo);

	public FileInfoDto findByFileName(String fileName);

	public List<FileInfoDto> findTempFileList();

	public int updateBoardId(int boardId, List<String> fileNames);

	public int deleteById(int fileId);
}
