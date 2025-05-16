package com.planner.planner.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.planner.planner.Dto.PlanLocationRouteDto;

@Mapper
public interface PlanLocationRouteMapper {
	public int createPlanLocationRoute(@Param("routeDto") PlanLocationRouteDto planLocationRouteDto, @Param("route") String geometryWKT);
	public PlanLocationRouteDto findPlanLocationRouteById(int id);
	public List<PlanLocationRouteDto> findPlanLocationRouteListByPlanId(int planId);
	public int updatePlanLocationRouteById(@Param("id")int id, @Param("route") String geometryWKT);
	public int deletePlanLocationRouteById(int id);
}
