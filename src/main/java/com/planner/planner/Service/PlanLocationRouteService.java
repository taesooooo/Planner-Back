package com.planner.planner.Service;

import com.planner.planner.Dto.PlanLocationRouteDto;

public interface PlanLocationRouteService {
	public int createPlanLocationRoute(PlanLocationRouteDto planLocationRouteDto);
	public void findPlanLocationRouteById(int id);
	public void findPlanLocationRouteByPlanId(int planId);
	public boolean updatePlanLocationRouteById(PlanLocationRouteDto planLocationRouteDto);
	public boolean deletePlanLocationRouteById(int id);
}
