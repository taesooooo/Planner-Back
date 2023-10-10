package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Dto.PlanMemberDto;

public interface PlanMemberDao {
	// 플래너 멤버 생성, 조회, 수정, 삭제
	public int insertPlanMember(int plannerId, int accountId) throws Exception;

	public List<PlanMemberDto> findMembersByPlannerId(int plannerId) throws Exception;

	public void deletePlanMember(int plannerId, int accountId) throws Exception;

	public void inviteAcceptState(int plannerId, int accountId) throws Exception;

	public void inviteRejectState(int plannerId, int accountId) throws Exception;
}
