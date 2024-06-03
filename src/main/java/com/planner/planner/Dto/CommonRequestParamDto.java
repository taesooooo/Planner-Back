package com.planner.planner.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.planner.planner.Common.PostType;
import com.planner.planner.Common.SortCriteria;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommonRequestParamDto {
	
	@Builder.Default
	private Integer itemCount = 10;
	@JsonProperty("criteria")
	@Builder.Default
	private SortCriteria sortCriteria = SortCriteria.LATEST;
	private String keyword = "";
	private Integer areaCode;
	private Integer pageNum;
	private PostType postType;
}
