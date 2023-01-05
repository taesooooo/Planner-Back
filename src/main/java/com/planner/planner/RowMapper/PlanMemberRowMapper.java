package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.PlanMemberDto;
import com.planner.planner.Entity.PlanMember;

public class PlanMemberRowMapper implements RowMapper<PlanMemberDto> {

	@Override
	public PlanMemberDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new PlanMemberDto.Builder()
				.setPlanMemberId(rs.getInt("plan_member_id"))
				.setPlannerId(rs.getInt("planner_id"))
				.setAccountId(rs.getInt("account_id"))
				.build();
	}
}
