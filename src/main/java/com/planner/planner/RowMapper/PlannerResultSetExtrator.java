package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.planner.planner.Dto.PlanDto;
import com.planner.planner.Dto.PlanLocationDto;
import com.planner.planner.Dto.PlanMemoDto;
import com.planner.planner.Dto.PlannerDto;

public class PlannerResultSetExtrator implements ResultSetExtractor<PlannerDto> {

	@Override
	public PlannerDto extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<String> members = new ArrayList<String>();
		List<PlanMemoDto> memos = new ArrayList<PlanMemoDto>();
		List<PlanDto> plans = new ArrayList<PlanDto>();
		List<PlanLocationDto> planLocations = null;
		int latestMemoId = 0;
		int latestPlanId = 0;
		int latestLocationId = 0;
		
		PlannerDto plannerDto = null;
		
		while(rs.next()) {
			if(plannerDto == null) {
				plannerDto = new PlannerDto.Builder()
						.setPlannerId(rs.getInt("planner_id"))
						.setAccountId(rs.getInt("account_id"))
						.setCreator(rs.getString("creator"))
						.setTitle(rs.getString("title"))
						.setPlanDateStart(rs.getDate("plan_date_start").toLocalDate())
						.setPlanDateEnd(rs.getDate("plan_date_end").toLocalDate())
						.setExpense(rs.getInt("expense"))
						.setMemberCount(rs.getInt("member_count"))
						.setMemberTypeId(rs.getInt("member_type_id"))
						.setLikeCount(rs.getInt("like_count"))
						.setPlanMembers(members)
						.setPlanMemos(memos)
						.setPlans(plans)
						.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime())
						.setUpdateDate(rs.getTimestamp("update_date").toLocalDateTime())
						.build();
			}
			
			String member = rs.getString("nickname");
			boolean memberCheck = members.stream().anyMatch((m) -> m.equals(member));
			if(!memberCheck) {
				members.add(member);
			}
			
			int memoId = rs.getInt("memo_id");
			if(memoId != 0 && latestMemoId != memoId) {
				latestMemoId = memoId;
				PlanMemoDto memo = new PlanMemoDto.Builder()
						.setMemoId(memoId)
						.setTitle(rs.getString("memo_title"))
						.setContent(rs.getString("memo_content"))
						.setCreateDate(rs.getTimestamp("memo_create_date").toLocalDateTime())
						.setUpdateDate(rs.getTimestamp("memo_update_date").toLocalDateTime())
						.build();
				memos.add(memo);
			}
			
			int planId = rs.getInt("plan_id");
			int locationId = rs.getInt("location_id");
			if(planId != 0) {
				if(latestPlanId != planId) {
					planLocations = new ArrayList<PlanLocationDto>();
					
					latestPlanId = planId;
					
					PlanDto newPlan = new PlanDto.Builder()
							.setPlanId(planId)
							.setPlanDate(rs.getDate("plan_date").toLocalDate())
							.setPlannerId(rs.getInt("planner_id"))
							.setPlanLocations(planLocations)
							.build();
					plans.add(newPlan);
				}
				
				if(latestPlanId == planId && locationId != 0 && latestLocationId != locationId) {
					latestLocationId = locationId;
					
					PlanLocationDto pl = new PlanLocationDto.Builder()
							.setLocationId(rs.getInt("location_id"))
							.setLocationContentId(rs.getInt("location_content_id"))
							.setLocationName(rs.getString("location_name"))
							.setLocationImage(rs.getString("location_image"))
							.setLocationAddr(rs.getString("location_addr"))
							.setLocationMapx(rs.getDouble("location_mapx"))
							.setLocationMapy(rs.getDouble("location_mapy"))
							.setLocationTransportation(rs.getInt("location_transportation"))
							.setPlanId(rs.getInt("plan_id"))
							.build();
					planLocations.add(pl);
				}
			}
		}
		return plannerDto;
	}

}
