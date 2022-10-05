package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Entity.Planner;

public interface PlannerDao {
	public boolean create(Planner planner);
	public PlannerDto read(int plannerId);
	public boolean update(Planner planner);
	public boolean delete(int plannerId);
	public boolean likePlanner(int plannerId, int accountId);
	public boolean like(int plannerId);
	public boolean likeCancel(int plannerId);
	public boolean likeDelete(int plannerId, int accountId);
	public List<Planner> getAllPlanners();
}
