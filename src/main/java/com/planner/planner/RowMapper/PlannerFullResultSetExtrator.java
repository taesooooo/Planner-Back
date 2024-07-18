package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.Dto.PlanMemoDto;
import com.planner.planner.Dto.PlannerDto;

public class PlannerFullResultSetExtrator implements ResultSetExtractor<PlannerDto> {

	@Override
	public PlannerDto extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<String> members = new ArrayList<String>();
		List<PlanMemoDto> memos = new ArrayList<PlanMemoDto>();
		Map<Integer, PlanDto> plans = new LinkedHashMap<Integer, PlanDto>();

		PlannerDto plannerDto = null;
		
		while(rs.next()) {
			String member = rs.getString("nickname");
			boolean memberCheck = members.stream().anyMatch((m) -> m.equals(member));
			if(!memberCheck) {
				members.add(member);
			}
			
			int memoId = rs.getInt("memo_id");
			if(memoId != 0) {
				boolean isFound = memos.stream().anyMatch(item -> item.getMemoId() == memoId);
	
				if (!isFound) {
					PlanMemoDto memo = PlanMemoDto.builder()
							.memoId(memoId)
							.title(rs.getString("memo_title"))
							.content(rs.getString("memo_content"))
							.createDate(rs.getTimestamp("memo_create_date").toLocalDateTime())
							.updateDate(rs.getTimestamp("memo_update_date").toLocalDateTime())
							.build();
					memos.add(memo);
				}
			}
			
			Integer planId = rs.getObject("plan_id", Integer.class);
			if(planId != null && planId != null) {
				PlanDto plan = plans.get(planId);
				if(plan == null) {
					List<PlanLocationDto> planLocations = new ArrayList<PlanLocationDto>();
					
					PlanDto newPlan = PlanDto.builder()
							.planId(planId)
							.planDate(rs.getDate("plan_date").toLocalDate())
							.planIndex(rs.getInt("plan_index"))
							.plannerId(rs.getInt("planner_id"))
							.planLocations(planLocations)
							.build();
					plans.put(planId, newPlan);
				}
				
				Integer locationId = rs.getObject("location_id", Integer.class);
				
				List<PlanLocationDto> planLocations = plans.get(planId).getPlanLocations();
				
				if(planLocations != null && locationId != null) {
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
			
			
			if(rs.isLast() && plannerDto == null) {
				List<PlanDto> planList = plans.values().stream().collect(Collectors.toList());
				plannerDto = PlannerDto.builder()
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
						.planMembers(members)
						.planMemos(memos)
						.plans(planList)
						.createDate(rs.getTimestamp("create_date").toLocalDateTime())
						.updateDate(rs.getTimestamp("update_date").toLocalDateTime())
						.build();
			}
		}
		return plannerDto;
	}

}
