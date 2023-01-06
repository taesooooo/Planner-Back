package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Dto.PlanLocationDto;

public interface PlanLocationDao {
	public boolean insertPlanLocation(PlanLocationDto planLocationDto);
	public PlanLocationDto findPlanLocationByPlanId(int locationId);
	public List<PlanLocationDto> findPlanLocationsByPlanId(int planId);
	public boolean updatePlanLocation(PlanLocationDto planLocationDto);
	public boolean deletePlanLocation(int locationId);
}
