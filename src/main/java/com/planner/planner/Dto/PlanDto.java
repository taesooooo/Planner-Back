package com.planner.planner.Dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.planner.planner.Common.ValidationGroups.PlanCreateGroup;
import com.planner.planner.Common.ValidationGroups.PlanUpdateGroup;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PlanDto {
	private int planId;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "날짜 항목은 필수 항목입니다.", groups = {PlanCreateGroup.class, PlanUpdateGroup.class})
	private LocalDate planDate;
	
	private int plannerId;
	private List<PlanLocationDto> planLocations;
	
	@Min(value = 1, message = "인덱스가 잘못되었습니다.", groups = PlanUpdateGroup.class)
	private int planIndex;
	
	private List<PlanLocationRouteDto> planLocationRoutes;
}
