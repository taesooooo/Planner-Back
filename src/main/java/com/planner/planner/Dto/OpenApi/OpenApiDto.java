package com.planner.planner.Dto.OpenApi;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OpenApiDto {
	private int areaCode;
	private int contentTypeId; 
	private int numOfRows; 
	private double mapX; 
	private double mapY; 
	private int radius;
	private String keyword;
	private int contentId;
	private int pageNo;
}
