package com.planner.planner.Service;

import com.planner.planner.Dto.PlanDto;

public interface PlanService {
	// 일정 추가, 수정, 삭제
	public int newPlan(PlanDto planDto) throws Exception;

	public void updatePlan(int planId, PlanDto planDto) throws Exception;

	public void deletePlan(int planId) throws Exception;
}
