package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Dto.PlannerDto;

public interface PlannerDao {
	public boolean insertPlanner(PlannerDto plannerDto);
	public PlannerDto findPlannerByPlannerId(int plannerId);
	public List<PlannerDto> findPlannersByAccountId(int accountId);
	public List<PlannerDto> findPlannersAll();
	public boolean updatePlanner(int plannerId, PlannerDto plannerDto);
	public boolean deletePlanner(int plannerId);
}
