package com.planner.planner.Service;

import com.planner.planner.Dto.PlannerDto;

public interface PlannerService {
	
	public boolean create(PlannerDto plannerDto);
	public PlannerDto read(int plannerId);
	public boolean update(PlannerDto plannerDto);
	public boolean delete(int plannerId);

}
