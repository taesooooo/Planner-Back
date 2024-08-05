package com.planner.planner.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.planner.planner.Dto.PlanMemoDto;

@Mapper
public interface PlanMemoMapper {
	// 메모 생성, 조회, 수정, 삭제
	public int insertPlanMemo(int plannerId, PlanMemoDto planMemoDto);

	public List<PlanMemoDto> findPlanMemoListByPlannerId(int plannerId);

	public int updatePlanMemo(int planMemoId, PlanMemoDto planMemoDto);

	public int deletePlanMemo(int planMemoId);

}
