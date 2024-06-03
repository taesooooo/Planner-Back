package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.PlanMemberDto;

public class PlanMemberRowMapper implements RowMapper<PlanMemberDto> {

	@Override
	public PlanMemberDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return PlanMemberDto.builder()
				.planMemberId(rs.getInt("plan_member_id"))
				.plannerId(rs.getInt("planner_id"))
				.accountId(rs.getInt("account_id"))
				.build();
	}
}
