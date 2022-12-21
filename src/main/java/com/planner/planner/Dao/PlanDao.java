package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Entity.Plan;

public interface PlanDao {
	public boolean insertPlan(Plan plan);
	public Plan findPlanByPlannerId(int planId);
	public List<Plan> findPlansByPlannerId(int plannerId);
	public boolean updatePlan(Plan plan);
	public boolean deletePlan(int planId);
}
