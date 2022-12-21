package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Entity.Plan;
import com.planner.planner.Entity.PlanLocation;

public interface PlanLocationDao {
	public boolean insertPlanLocation(PlanLocation planLocation);
	public PlanLocation findPlanLocationByPlanId(int locationId);
	public List<PlanLocation> findPlanLocationsByPlanId(int planId);
	public boolean updatePlanLocation(PlanLocation planLocation);
	public boolean deletePlanLocation(int locationId);
}
