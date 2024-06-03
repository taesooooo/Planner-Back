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
					
					PlanDto newPlan = PlanDto.builder()
							.planId(planId)
							.planDate(rs.getDate("plan_date").toLocalDate())
							.index(rs.getInt("plan_index"))
							.plannerId(rs.getInt("planner_id"))
							.planLocations(planLocations)
							.build();
					plans.put(planId, newPlan);
				}
				
				Integer locationId = rs.getObject("location_id", Integer.class);
				
				List<PlanLocationDto> planLocations = plans.get(planId).getPlanLocations();
				
				if(planLocations != null) {
					boolean hasLocation = planLocations.stream().anyMatch(item -> item.getLocationId() == locationId);
					
					if(!hasLocation) {
						PlanLocationDto pl = PlanLocationDto.builder()
								.locationId(rs.getInt("location_id"))
								.locationContentId(rs.getInt("location_content_id"))
								.locationName(rs.getString("location_name"))
								.locationImage(rs.getString("location_image"))
								.locationAddr(rs.getString("location_addr"))
								.locationMapx(rs.getDouble("location_mapx"))
								.locationMapy(rs.getDouble("location_mapy"))
								.locationTransportation(rs.getInt("location_transportation"))
								.index(rs.getInt("location_index"))
								.planId(rs.getInt("plan_id"))
								.build();
						planLocations.add(pl);				
					}
				}
			}
			
			Integer plannerId = rs.getObject("planner_id", Integer.class);
			PlannerDto planner = planners.get(plannerId);
			if(planner == null) {
				List<PlanDto> planList = plans.values().stream().collect(Collectors.toList());
				planner = PlannerDto.builder()
						.plannerId(rs.getInt("planner_id"))
						.accountId(rs.getInt("account_id"))
						.areaCode(rs.getInt("area_code"))
						.creator(rs.getString("creator"))
						.title(rs.getString("title"))
						.planDateStart(rs.getDate("plan_date_start").toLocalDate())
						.planDateEnd(rs.getDate("plan_date_end").toLocalDate())
						.expense(rs.getInt("expense"))
						.memberCount(rs.getInt("member_count"))
						.memberTypeId(rs.getInt("member_type_id"))
						.likeCount(rs.getInt("like_count"))
						.likeState(rs.getInt("like_id") > 0 ? true : false)
						.plans(planList)
						.createDate(rs.getTimestamp("create_date").toLocalDateTime())
						.updateDate(rs.getTimestamp("update_date").toLocalDateTime())
						.build();
				
				planners.put(plannerId, planner);
			}
		}
		
		return planners.values().stream().collect(Collectors.toList());
	}

}
