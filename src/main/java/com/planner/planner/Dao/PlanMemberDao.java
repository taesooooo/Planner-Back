package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Dto.PlanMemberDto;
import com.planner.planner.Entity.PlanMember;

public interface PlanMemberDao {
	public boolean insertPlanMember(PlanMemberDto planMemberDto);
	public PlanMemberDto findPlanMemberByPlannerId(int planMemberId);
	public List<PlanMemberDto> findPlanMembersByPlannerId(int plannerId);
	//public boolean updatePlanMember(PlanMemberDto planMemberDto);
	public boolean deletePlanMember(int planMemberId);
}
