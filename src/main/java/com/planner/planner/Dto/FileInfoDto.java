package com.planner.planner.Dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class FileInfoDto {
	private int fileId;
	@JsonIgnore
	private int fileWriterId;
	private int fileBoradId;
	private String fileName;
	@JsonIgnore
	private String filePath;
	private String fileType;
	private LocalDateTime uploadDate;
}
