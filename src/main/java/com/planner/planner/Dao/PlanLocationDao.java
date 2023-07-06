package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Dto.PlanLocationDto;

public interface PlanLocationDao {
	// 여행지 생성, 조회, 수정, 삭제
	public int insertPlanLocation(int planId, PlanLocationDto planLocationDto);

	public List<PlanLocationDto> findPlanLocationsByPlanId(int planId);

	public int updatePlanLocation(int planLocationId, PlanLocationDto planLocationDto);

	public int deletePlanLocation(int planLocationDto);
}
