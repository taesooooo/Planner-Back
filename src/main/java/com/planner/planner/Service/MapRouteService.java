package com.planner.planner.Service;

import java.util.List;

import com.planner.planner.Dto.RouteResultDto;

public interface MapRouteService {
	public List<RouteResultDto> findPath(List<String> list);
}
