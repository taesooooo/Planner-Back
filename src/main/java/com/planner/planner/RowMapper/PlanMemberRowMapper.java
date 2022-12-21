package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Entity.PlanMember;

public class PlanMemberRowMapper implements RowMapper<PlanMember> {

	@Override
	public PlanMember mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new PlanMember.Builder()
				.setPlanMemberId(rs.getInt("plan_member_id"))
				.setPlannerId(rs.getInt("planner_id"))
				.setAccountId(rs.getInt("account_id"))
				.build();
	}
}
