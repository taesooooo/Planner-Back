package com.planner.planner.Dto.OpenApi;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OpenApiDto {
	private Integer areaCode;
	private Integer contentTypeId; 
	private Integer numOfRows; 
	private Double mapX;
	private Double mapY; 
	private Integer radius;
	private String keyword;
	private Integer contentId;
	private Integer pageNo;
}
