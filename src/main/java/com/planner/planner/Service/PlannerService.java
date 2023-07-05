package com.planner.planner.Service;

import com.planner.planner.Common.Page;
import com.planner.planner.Dto.CommonRequestParamDto;
import com.planner.planner.Dto.PlannerDto;

public interface PlannerService {
	// 플래너 생성, 가져오기, 수정, 삭제
	public int newPlanner(PlannerDto plannerDto) throws Exception;
	public PlannerDto findPlannerByPlannerId(int plannerId) throws Exception;
	public Page<PlannerDto> findPlannersByAccountId(int accountId, CommonRequestParamDto commonRequestParamDto) throws Exception;
	public Page<PlannerDto> findPlannerAll(Integer accountId, CommonRequestParamDto commonRequestParamDto) throws Exception;
	public Page<PlannerDto> getLikePlannerList(int accountId, CommonRequestParamDto commonRequestParamDto) throws Exception;
	public void updatePlanner(PlannerDto plannerDto) throws Exception;
	public void deletePlanner(int plannerId) throws Exception;
	
	// 메모, 일정, 여행지 플래너 메소드에서 모두 가져옴
}
