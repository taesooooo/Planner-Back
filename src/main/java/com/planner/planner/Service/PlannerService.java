package com.planner.planner.Service;

import java.util.List;

import com.planner.planner.Dto.PlannerDto;

public interface PlannerService {
	
	public boolean create(PlannerDto plannerDto);
	public PlannerDto read(int plannerId);
	public boolean update(PlannerDto plannerDto);
	public boolean delete(int plannerId);
	public boolean like(int plannerId, int accountId);
	public List<PlannerDto> getAllPlanners(); 
}
