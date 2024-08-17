package com.planner.planner.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.planner.planner.Dto.PlanDto;

@Mapper
public interface PlanMapper {
	// 일정 생성, 조회, 수정, 삭제
	public int insertPlan(int plannerId, PlanDto planDto);

	public List<PlanDto> findPlanListByPlannerId(int plannerId);

	public int updatePlan(int planId, PlanDto planDto);

	public int deletePlan(int planId);
}
