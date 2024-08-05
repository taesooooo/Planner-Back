package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Dto.PlanMemberDto;

public interface PlanMemberDao {
	// 플래너 멤버 생성, 조회, 수정, 삭제
	public int insertPlanMember(int plannerId, int accountId) throws Exception;

	public List<PlanMemberDto> findPlanMemberListByPlannerId(int plannerId) throws Exception;

	public int deletePlanMember(int plannerId, int accountId) throws Exception;
}
