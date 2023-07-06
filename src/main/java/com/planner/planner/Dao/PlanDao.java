package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Dto.PlanDto;

public interface PlanDao {
	// 일정 생성, 조회, 수정, 삭제
	public int insertPlan(int plannerId, PlanDto planDto);

	public List<PlanDto> findPlansByPlannerId(int plannerId);

	public int updatePlan(int planId, PlanDto planDto);

	public int deletePlan(int planId);
}
