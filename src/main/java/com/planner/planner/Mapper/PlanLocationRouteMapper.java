package com.planner.planner.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.planner.planner.Dto.PlanLocationRouteDto;

@Mapper
public interface PlanLocationRouteMapper {
	public int createPlanLocationRoute(PlanLocationRouteDto planLocationRouteDto);
	public PlanLocationRouteDto findPlanLocationRouteById(int id);
	public List<PlanLocationRouteDto> findPlanLocationRouteListByPlanId(int planId);
	public int updatePlanLocationRouteById(@Param("id") int locationRouteId, @Param("routeDto") PlanLocationRouteDto routeDto);
	public int deletePlanLocationRouteById(int id);
}
