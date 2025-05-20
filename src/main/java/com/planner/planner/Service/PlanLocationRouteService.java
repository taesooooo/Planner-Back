package com.planner.planner.Service;

import java.util.List;

import com.planner.planner.Dto.PlanLocationRouteDto;

public interface PlanLocationRouteService {
	public int createPlanLocationRoute(PlanLocationRouteDto planLocationRouteDto);
	public PlanLocationRouteDto findPlanLocationRouteById(int id);
	public List<PlanLocationRouteDto> findPlanLocationRouteListByPlanId(int planId);
	public boolean updatePlanLocationRouteById(PlanLocationRouteDto planLocationRouteDto);
	public boolean deletePlanLocationRouteById(int id);
}
