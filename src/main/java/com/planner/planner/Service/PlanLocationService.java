package com.planner.planner.Service;

import com.planner.planner.Dto.PlanLocationDto;

public interface PlanLocationService {
	// 일정 여행지 추가, 수정, 삭제
	public int newPlanLocation(PlanLocationDto planLocationDto) throws Exception;

	public void updatePlanLocation(int planLocationId, PlanLocationDto planLocationDto) throws Exception;

	public void deletePlanLocation(int planLocationId) throws Exception;

}
