package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Entity.Planner;

public interface PlannerDao {
	public boolean insertPlanner(Planner planner);
	public Planner findPlannerByPlannerId(int plannerId);
	public List<Planner> findPlannersByAccountId(int accountId);
	public List<Planner> findPlannersAll();
	public boolean updatePlanner(int plannerId, Planner planner);
	public boolean deletePlanner(int plannerId);
}
