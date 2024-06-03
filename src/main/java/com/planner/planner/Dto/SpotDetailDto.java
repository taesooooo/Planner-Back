package com.planner.planner.Dto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.planner.planner.Dto.OpenApi.CommonDetailDto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SpotDetailDto {
	@JsonUnwrapped
	@JsonIncludeProperties(value = { "title", "addr1", "addr2", "firstimage", "firstimage2",
			"mapx", "mapy", "homepage", "telname", "zipcode", "overview" })
	private CommonDetailDto detail;
	private int likeCount;
	private boolean likeState;
}
