package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.Dto.PlannerDto;

public class PlannerResultSetExtrator implements ResultSetExtractor<List<PlannerDto>> {

	@Override
	public List<PlannerDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Map<Integer, PlannerDto> planners = new HashMap<Integer, PlannerDto>();
		Map<Integer, PlanDto> plans = new HashMap<Integer, PlanDto>();
		
		while(rs.next()) {
			Integer planId = rs.getObject("plan_id", Integer.class);
			if(planId != null) {
				PlanDto plan = plans.get(planId);
				if(plan == null) {
					List<PlanLocationDto> planLocations = new ArrayList<PlanLocationDto>();
					
					PlanDto newPlan = new PlanDto.Builder()
							.setPlanId(planId)
							.setPlanDate(rs.getDate("plan_date").toLocalDate())
							.setIndex(rs.getInt("plan_index"))
							.setPlannerId(rs.getInt("planner_id"))
							.setPlanLocations(planLocations)
							.build();
					plans.put(planId, newPlan);
				}
				
				Integer locationId = rs.getObject("location_id", Integer.class);
				
				List<PlanLocationDto> planLocations = plans.get(planId).getPlanLocations();
				
				if(planLocations != null) {
					boolean hasLocation = planLocations.stream().anyMatch(item -> item.getLocationId() == locationId);
					
					if(!hasLocation) {
						PlanLocationDto pl = new PlanLocationDto.Builder()
								.setLocationId(rs.getInt("location_id"))
								.setLocationContentId(rs.getInt("location_content_id"))
								.setLocationName(rs.getString("location_name"))
								.setLocationImage(rs.getString("location_image"))
								.setLocationAddr(rs.getString("location_addr"))
								.setLocationMapx(rs.getDouble("location_mapx"))
								.setLocationMapy(rs.getDouble("location_mapy"))
								.setLocationTransportation(rs.getInt("location_transportation"))
								.setIndex(rs.getInt("location_index"))
								.setPlanId(rs.getInt("plan_id"))
								.build();
						planLocations.add(pl);				
					}
				}
			}
			
			Integer plannerId = rs.getObject("planner_id", Integer.class);
			PlannerDto planner = planners.get(plannerId);
			if(planner == null) {
				List<PlanDto> planList = plans.values().stream().collect(Collectors.toList());
				planner = new PlannerDto.Builder()
						.setPlannerId(rs.getInt("planner_id"))
						.setAccountId(rs.getInt("account_id"))
						.setAreaCode(rs.getObject("area_code", Integer.class))
						.setCreator(rs.getString("creator"))
						.setTitle(rs.getString("title"))
						.setPlanDateStart(rs.getDate("plan_date_start").toLocalDate())
						.setPlanDateEnd(rs.getDate("plan_date_end").toLocalDate())
						.setExpense(rs.getInt("expense"))
						.setMemberCount(rs.getInt("member_count"))
						.setMemberTypeId(rs.getInt("member_type_id"))
						.setLikeCount(rs.getInt("like_count"))
						.setLikeState(rs.getInt("like_id") > 0 ? true : false)
						.setPlans(planList)
						.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime())
						.setUpdateDate(rs.getTimestamp("update_date").toLocalDateTime())
						.build();
				
				planners.put(plannerId, planner);
			}
		}
		
		return planners.values().stream().collect(Collectors.toList());
	}

}
