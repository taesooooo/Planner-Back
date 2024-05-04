package com.planner.planner.Dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.planner.planner.Dto.OpenApi.CommonBasedDto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SpotDto {
	@JsonUnwrapped
	private CommonBasedDto basedSpot;
	private int likeCount;
	private boolean likeState;
}
