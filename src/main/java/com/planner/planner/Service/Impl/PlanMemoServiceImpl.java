package com.planner.planner.Service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Dto.PlanMemoDto;
import com.planner.planner.Mapper.PlanMemoMapper;
import com.planner.planner.Service.PlanMemoService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanMemoServiceImpl implements PlanMemoService {
	private final PlanMemoMapper planMemoMapper;

	@Override
	public int newMemo(int plannerId, PlanMemoDto planMemoDto) {
		planMemoMapper.insertPlanMemo(plannerId, planMemoDto);
		
		return planMemoDto.getMemoId();
	}

	@Override
	public void updateMemo(int memoId, PlanMemoDto planMemoDto) {
		planMemoMapper.updatePlanMemo(memoId, planMemoDto);
	}

	@Override
	public void deleteMemo(int memoId) {
		planMemoMapper.deletePlanMemo(memoId);
	}

}
