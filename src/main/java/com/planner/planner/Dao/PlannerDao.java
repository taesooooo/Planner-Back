package com.planner.planner.Dao;

import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Entity.Planner;

public interface PlannerDao {
	public boolean create(Planner planner);
	public PlannerDto read(int plannerId);
	public boolean update(Planner planner);
	public boolean delete(int plannerId);

}
