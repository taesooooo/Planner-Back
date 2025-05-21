package com.planner.planner.Service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planner.planner.Common.Coordinate;
import com.planner.planner.Dto.PlanLocationRouteDto;
import com.planner.planner.Mapper.PlanLocationRouteMapper;
import com.planner.planner.Service.PlanLocationRouteService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanLocationRouteServiceImpl implements PlanLocationRouteService {
	
	private static final Logger log = LoggerFactory.getLogger(PlanLocationRouteServiceImpl.class);

	private final PlanLocationRouteMapper planLocationRouteMapper;
	
	@Override
	public int createPlanLocationRoute(PlanLocationRouteDto planLocationRouteDto) {
		int result = planLocationRouteMapper.createPlanLocationRoute(planLocationRouteDto);
		
		return planLocationRouteDto.getId();
	}

	@Override
	public PlanLocationRouteDto findPlanLocationRouteById(int id) {
		PlanLocationRouteDto locationRouteDto = planLocationRouteMapper.findPlanLocationRouteById(id);

		return locationRouteDto;
	}

	@Override
	public List<PlanLocationRouteDto> findPlanLocationRouteListByPlanId(int planId) {
		List<PlanLocationRouteDto> list = planLocationRouteMapper.findPlanLocationRouteListByPlanId(planId);

		return list;
	}

	@Override
	public boolean updatePlanLocationRouteById(int locationRouteId, PlanLocationRouteDto planLocationRouteDto) {
		int result = planLocationRouteMapper.updatePlanLocationRouteById(locationRouteId, planLocationRouteDto);
		
		return result > 0 ? true : false;
	}

	@Override
	public boolean deletePlanLocationRouteById(int id) {
		int result = planLocationRouteMapper.deletePlanLocationRouteById(id);
		
		return result > 0 ? true : false;
	}
}
