package com.planner.planner.Service;

import java.util.List;

import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.Dto.PlanMemberDto;
import com.planner.planner.Dto.PlannerDto;
import com.planner.planner.Entity.Plan;
import com.planner.planner.Entity.PlanLocation;
import com.planner.planner.Entity.PlanMember;

public interface PlannerService {
	public boolean newPlanner(PlannerDto plannerDto);
	public PlannerDto findPlannerByPlannerId(int plannerId);
	public List<PlannerDto> findPlannersByAccountId(int accountId);
	public List<PlannerDto> findPlannerAll();
	public boolean modifyPlanner(PlannerDto plannerDto);
	public boolean deletePlanner(int plannerId);
	
	public boolean newPlanMember(PlanMemberDto planMemberDto);
	public List<PlanMemberDto> findPlanMembersByPlannerId(int plannerId);
	//public boolean modifyPlanMember(PlanMemberDto planMemberDto);
	public boolean deletePlanMember(int planMemberId);
	
	public boolean newPlan(PlanDto planDto);
	public PlanDto findPlanByPlannerId(int planId);
	public List<PlanDto> findPlansByPlannerId(int plannerId);
	public boolean modifyPlan(PlanDto planDto);
	public boolean deletePlan(int planId);
	
	public boolean newPlanLocation(PlanLocationDto planLocationDto);
	public PlanLocationDto findPlanLocationByPlanId(int locationId);
	public List<PlanLocationDto> findPlanLocationsByPlanId(int planId);
	public boolean modifyPlanLocation(PlanLocationDto planLocationDto);
	public boolean deletePlanLocation(int locationId);
	
}
