package com.planner.planner.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.planner.planner.Dto.PlanDto;

public class PlanRowMapper implements RowMapper<PlanDto>{

	@Override
	public PlanDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new PlanDto.Builder()
				.setPlanId(rs.getInt("plan_id"))
				.setPlanDate(rs.getTimestamp("plan_date").toLocalDateTime())
				.setPlannerId(rs.getInt("planner_id"))
				.build();
	}
}
