package com.planner.planner.Dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	public FileInfoDto() {
		
	}

	public FileInfoDto(int fileId, int fileWriterId, int fileBoradId, String fileName, String filePath, String fileType,
			LocalDateTime uploadDate) {
		this.fileId = fileId;
		this.fileWriterId = fileWriterId;
		this.fileBoradId = fileBoradId;
		this.fileName = fileName;
		this.filePath = filePath;
		this.fileType = fileType;
		this.uploadDate = uploadDate;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public int getFileWriterId() {
		return fileWriterId;
	}

	public void setFileWriterId(int fileWriterId) {
		this.fileWriterId = fileWriterId;
	}

	public int getFileBoradId() {
		return fileBoradId;
	}

	public void setFileBoradId(int fileBoradId) {
		this.fileBoradId = fileBoradId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public LocalDateTime getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(LocalDateTime uploadDate) {
		this.uploadDate = uploadDate;
	}

	@Override
	public String toString() {
		return "FileInfoDto [fileId=" + fileId + ", fileWriterId=" + fileWriterId + ", fileBoradId=" + fileBoradId
				+ ", fileName=" + fileName + ", filePath=" + filePath + ", fileType=" + fileType + ", uploadDate="
				+ uploadDate + "]";
	}

	
}
