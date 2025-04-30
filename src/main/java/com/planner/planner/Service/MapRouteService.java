package com.planner.planner.Service;

import com.planner.planner.Dto.RouteDto;
import com.planner.planner.Dto.RouteResultDto;

public interface MapRouteService {
	public RouteResultDto findPath(RouteDto routeDto);
}
