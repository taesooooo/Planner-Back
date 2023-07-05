package com.planner.planner.Dao;

import java.util.List;

import com.planner.planner.Dto.PlanMemoDto;

public interface PlanMemoDao {
	// 메모 생성, 조회, 수정, 삭제
	public int insertPlanMemo(int plannerId, PlanMemoDto planMemoDto);

	public List<PlanMemoDto> findPlanMemoByPlannerId(int plannerId);

	public int updatePlanMemo(int planMemoId, PlanMemoDto planMemoDto);

	public int deletePlanMemo(int planMemoId);
}
