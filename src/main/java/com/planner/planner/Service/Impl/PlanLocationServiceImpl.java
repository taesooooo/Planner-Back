package com.planner.planner.Service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.Mapper.PlanLocationMapper;
import com.planner.planner.Service.PlanLocationService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanLocationServiceImpl implements PlanLocationService {
	private final PlanLocationMapper planLocationMapper;

	@Override
	public int newPlanLocation(int planId, PlanLocationDto planLocationDto) throws Exception {
		planLocationMapper.insertPlanLocation(planId, planLocationDto);
		return planLocationDto.getLocationId();
	}

	@Override
	public void updatePlanLocation(int planLocationId, PlanLocationDto planLocationDto) throws Exception {
		planLocationMapper.updatePlanLocation(planLocationId, planLocationDto);
	}

	@Override
	public void deletePlanLocation(int planLocationId) throws Exception {
		planLocationMapper.deletePlanLocation(planLocationId);
	}

}
