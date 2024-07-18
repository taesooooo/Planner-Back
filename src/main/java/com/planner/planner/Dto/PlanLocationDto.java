package com.planner.planner.Dto;

import com.planner.planner.Common.ValidationGroups.PlanLocationCreateGroup;
import com.planner.planner.Common.ValidationGroups.PlanLocationUpdateGroup;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PlanLocationDto {
	private int locationId;
	
	@Min(value = 1, message = "콘텐츠 아이디가 잘못되었습니다.", groups = {PlanLocationCreateGroup.class, PlanLocationUpdateGroup.class})
	private int locationContentId;
	
	private String locationName;
	private String locationImage;
	private String locationAddr;
	private double locationMapx;
	private double locationMapy;
	
	@Min(value = 1, message = "이동수단이 잘못되었습니다.", groups = {PlanLocationCreateGroup.class, PlanLocationUpdateGroup.class})
	private int locationTransportation;
	
	@Min(value = 1, message = "인덱스가 잘못되었습니다.", groups = PlanLocationUpdateGroup.class)
	private int index;
	private int planId;
}
