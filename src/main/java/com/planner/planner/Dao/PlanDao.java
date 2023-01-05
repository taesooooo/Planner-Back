package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Entity.Plan;

public interface PlanDao {
	public boolean insertPlan(PlanDto planDto);
	public PlanDto findPlanByPlannerId(int planId);
	public List<PlanDto> findPlansByPlannerId(int plannerId);
	public boolean updatePlan(PlanDto planDto);
	public boolean deletePlan(int planId);
}
