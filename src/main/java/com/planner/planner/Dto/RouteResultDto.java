package com.planner.planner.Dto;

import java.util.ArrayList;
import java.util.List;

import com.planner.planner.Common.Coordinate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RouteResultDto {
	private Coordinate start;
	private Coordinate end;
	private ArrayList<Coordinate> routeList;
}
