package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Entity.PlanMember;

public interface PlanMemberDao {
	public boolean insertPlanMember(PlanMember planMember);
	public PlanMember findPlanMemberByPlannerId(int planMemberId);
	public List<PlanMember> findPlanMembersByPlannerId(int plannerId);
	//public boolean updatePlanMember(PlanMember planMember);
	public boolean deletePlanMember(int planMemberId);
}
