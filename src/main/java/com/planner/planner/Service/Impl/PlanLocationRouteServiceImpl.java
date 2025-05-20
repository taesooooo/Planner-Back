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
		String lineString = coordinateListToWKT(planLocationRouteDto.getRouteList());
		
		int result = planLocationRouteMapper.createPlanLocationRoute(planLocationRouteDto, lineString);
		
		return planLocationRouteDto.getId();
	}

	@Override
	public PlanLocationRouteDto findPlanLocationRouteById(int id) {
		PlanLocationRouteDto locationRouteDto = planLocationRouteMapper.findPlanLocationRouteById(id);
		List<Coordinate> list = WKTToList(locationRouteDto.getRouteWKT());
		
		return locationRouteDto.toBuilder().routeList(list).build();
	}

	@Override
	public List<PlanLocationRouteDto> findPlanLocationRouteListByPlanId(int planId) {
		List<PlanLocationRouteDto> list = planLocationRouteMapper.findPlanLocationRouteListByPlanId(planId);
		
		List<PlanLocationRouteDto> locationRouteDtoList = new ArrayList<PlanLocationRouteDto>();
		for(PlanLocationRouteDto dto : list) {
			List<Coordinate> coordinateList = WKTToList(dto.getRouteWKT());
			locationRouteDtoList.add(dto.toBuilder().routeList(coordinateList).build());
		}
		
		return locationRouteDtoList;
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
		// LINESTRING(0 0, 1 1, 2 2)
		StringBuilder sb = new StringBuilder();
		sb.append("LINESTRING(");
		for(int i=0;i<routeList.size(); i++) {
			Coordinate point = routeList.get(i);
			sb.append(point.getLongitude() + " " + point.getLatitude());
			
			if(i != routeList.size() - 1) {
				sb.append(",");
			}
			
		}
		
		sb.append(")");
		
		return sb.toString();
	}
	
	private List<Coordinate> WKTToList(String route) {
		// LINESTRING(0 0, 1 1, 2 2)
		
		// \\d+\\s\\d+
		// \\d+(\\.\\d+)?\\s\\d+(\\.\\d+)?
		String expression = "\\d+(\\.\\d+)?\\s\\d+(\\.\\d+)?";
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(route);
		
		List<Coordinate> list = new ArrayList<Coordinate>();
		while(matcher.find()) {
			String[] result = matcher.group().split(" ");
			list.add(new Coordinate(Double.parseDouble(result[0]), Double.parseDouble(result[1])));
		}
		
		return list;
	}

}
