package com.planner.planner.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.planner.planner.Dto.PlanLocationDto;

@Mapper
public interface PlanLocationMapper {
	// 여행지 생성, 조회, 수정, 삭제
	public int insertPlanLocation(int planId, PlanLocationDto planLocationDto);

	public List<PlanLocationDto> findPlanLocationListByPlanId(int planId);

	public int updatePlanLocation(int planLocationId, PlanLocationDto planLocationDto);

	public int deletePlanLocation(int planLocationId);
}
