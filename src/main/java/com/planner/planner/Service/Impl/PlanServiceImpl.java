package com.planner.planner.Service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Mapper.PlanMapper;
import com.planner.planner.Service.PlanService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {
	private final PlanMapper planMapper;

	@Override
	public int newPlan(int plannerId, PlanDto planDto) throws Exception {
		planMapper.insertPlan(plannerId, planDto);
		
		return planDto.getPlanId();
	}

	@Override
	public void updatePlan(int planId, PlanDto planDto) throws Exception {
		planMapper.updatePlan(planId, planDto);
	}

	@Override
	public void deletePlan(int planId) throws Exception {
		planMapper.deletePlan(planId);
	}

}
