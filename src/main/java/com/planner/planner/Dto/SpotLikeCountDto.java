package com.planner.planner.Dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SpotLikeCountDto {
	private int contentId;
	private int count;
}
