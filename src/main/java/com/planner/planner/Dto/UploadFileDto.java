package com.planner.planner.Dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UploadFileDto {
	private String type;
	private byte[] data;
}
