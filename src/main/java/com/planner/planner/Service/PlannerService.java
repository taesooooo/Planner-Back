package com.planner.planner.Service;

import java.util.List;

import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.Dto.PlanMemberDto;
import com.planner.planner.Dto.PlannerDto;

public interface PlannerService {
	public int newPlanner(PlannerDto plannerDto) throws Exception;
	public PlannerDto findPlannerByPlannerId(int plannerId) throws Exception;
	public List<PlannerDto> findPlannersByAccountId(int accountId) throws Exception;
	public List<PlannerDto> findPlannerAll() throws Exception;
	public void modifyPlanner(PlannerDto plannerDto) throws Exception;
	public void deletePlanner(int plannerId) throws Exception;
	
	public List<PlanMemberDto> findMembersByPlannerId(int plannerId) throws Exception;
	public void inviteMembers(int plannerId, List<String> members) throws Exception;
	public void deleteMember(int plannerId, String memberEmail) throws Exception;
	
	public int newPlan(PlanDto planDto) throws Exception;
	public void deletePlan(int plannerId, int planId) throws Exception;
	public int newPlanLocation(PlanLocationDto planLocationDto) throws Exception;
	public void deletePlanLocation(int planId, int planLocationId) throws Exception;
}
