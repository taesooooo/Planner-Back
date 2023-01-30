package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.Dto.PlanMemberDto;
import com.planner.planner.Dto.PlannerDto;

public interface PlannerDao {
	public int insertPlanner(PlannerDto plannerDto);
	public PlannerDto findPlannerByPlannerId(int plannerId);
	public PlannerDto findPlannerByPlannerIda(int plannerId);
	public List<PlannerDto> findPlannersByAccountId(int accountId);
	public List<PlannerDto> findPlannerAll();
	public int updatePlanner(int plannerId, PlannerDto plannerDto);
	public int deletePlanner(int plannerId);
	
	public int insertPlanMember(int plannerId, int accountId);
	public List<PlanMemberDto> findMembersByPlannerId(int plannerId);
	public int deletePlanMember(int plannerId, int accountId);
	public int acceptInvitation(int plannerId, int accountId);
	
	public int insertPlan(PlanDto planDto);
	public List<PlanDto> findPlansByPlannerId(int plannerId);
	public int updatePlan(PlanDto planDto);
	public int deletePlan(int plannerId, int planId);
	
	public int insertPlanLocation(PlanLocationDto planLocationDto);
	public List<PlanLocationDto> findPlanLocationsByPlanId(int planId);
	public int updatePlanLocation(PlanLocationDto planLocationDto);
	public int deletePlanLocation(int planId, int locationId);
}


