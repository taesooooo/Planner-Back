package com.planner.planner.Dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FileInfoDto {
	private int fileId;
	private int fileWriterId;
	private int fileBoradId;
	private String fileName;
	private String filePath;
	private String fileType;
	private LocalDateTime uploadDate;
}
