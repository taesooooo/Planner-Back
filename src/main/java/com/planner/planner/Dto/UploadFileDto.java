package com.planner.planner.Dto;

public class UploadFileDto {
	private String type;
	private byte[] data;
	
	public UploadFileDto() {
	}

	public UploadFileDto(String type, byte[] data) {
		this.type = type;
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}
