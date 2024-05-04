package com.planner.planner.Dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(value = Include.NON_NULL)
public class LikeDto {
	List<PlannerDto> likePlanners;
	List<SpotLikeDto> likeSpots;
}
