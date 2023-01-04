package com.planner.planner.Service;

import java.util.List;

import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Dto.PlannerDto;

public interface PlannerService {
	public boolean add(PlannerDto plannerDto);
	public PlannerDto findPlannerByPlannerId(int plannerId);
	public List<PlannerDto> findPlannersByAccountId(int accountId);
	public List<PlannerDto> findPlannerAll();
}
