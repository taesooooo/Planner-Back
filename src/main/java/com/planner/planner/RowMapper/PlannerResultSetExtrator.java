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
		
		PlannerDto plannerDto = null;
		
		while(rs.next()) {
			if(plannerDto == null) {
				plannerDto = new PlannerDto.Builder()
						.setPlannerId(rs.getInt("planner_id"))
						.setAccountId(rs.getInt("account_id"))
						.setCreator(rs.getString("creator"))
						.setTitle(rs.getString("title"))
						.setPlanDateStart(rs.getTimestamp("plan_date_start").toLocalDateTime())
						.setPlanDateEnd(rs.getTimestamp("plan_date_end").toLocalDateTime())
						.setLikeCount(rs.getInt("like_count"))
						.setPlans(plans)
						.setPlanMembers(members)
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
			if(planId != 0) {
				if(latestPlanId != planId) {
					
					latestPlanId = planId;
					planLocations = new ArrayList<PlanLocationDto>();
					
					PlanDto newPlan = new PlanDto.Builder()
							.setPlanId(planId)
							.setPlanDate(rs.getTimestamp("plan_date").toLocalDateTime())
							.setPlannerId(rs.getInt("planner_id"))
							.setPlanLocations(planLocations)
							.build();
					plans.add(newPlan);
				}
				
				PlanLocationDto pl = new PlanLocationDto.Builder()
						.setLocationId(rs.getInt("location_id"))
						.setLocationContentId(rs.getInt("location_content_id"))
						.setLocationImage(rs.getString("location_image"))
						.setLocationTransportation(rs.getInt("location_transportation"))
						.setPlanId(rs.getInt("plan_id"))
						.build();
				planLocations.add(pl);
			}
		}
		return plannerDto;
	}

}
