package com.planner.planner.Service.Impl;

import java.util.List;

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
		String lineString = coordinateListToWKT(planLocationRouteDto.getRouteList());
		
		int result = planLocationRouteMapper.createPlanLocationRoute(planLocationRouteDto, lineString);
		
		return planLocationRouteDto.getId();
	}

	@Override
	public void findPlanLocationRouteById(int id) {
		planLocationRouteMapper.findPlanLocationRouteById(id);
	}

	@Override
	public void findPlanLocationRouteByPlanId(int planId) {
		planLocationRouteMapper.findPlanLocationRouteListByPlanId(planId);
	}

	@Override
	public boolean updatePlanLocationRouteById(PlanLocationRouteDto planLocationRouteDto) {
		String lineString = coordinateListToWKT(planLocationRouteDto.getRouteList());
		
		int result = planLocationRouteMapper.updatePlanLocationRouteById(planLocationRouteDto.getId(), lineString);
		
		return result > 0 ? true : false;
	}

	@Override
	public boolean deletePlanLocationRouteById(int id) {
		int result = planLocationRouteMapper.deletePlanLocationRouteById(id);
		
		return result > 0 ? true : false;
	}
	
	private String coordinateListToWKT(List<Coordinate> routeList) {
		StringBuilder sb = new StringBuilder();
		sb.append("LINESTRING(");
		for(int i=0;i<routeList.size() - 1; i++) {
			Coordinate point = routeList.get(i);
			sb.append(point.getLongitude() + " " + point.getLatitude());
			
			if(i != routeList.size() - 1) {
				sb.append(",");
			}
			
			sb.append(")");
		}
		
		return sb.toString();
	}

}
