package com.planner.planner.Dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.planner.planner.Common.Coordinate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlanLocationRouteDto {
	private int id;
	private int planId;
	private int startIndex;
	private int endIndex;
	private List<Coordinate> routeList;
	
	@JsonIgnore
	private String routeWKT;
}
